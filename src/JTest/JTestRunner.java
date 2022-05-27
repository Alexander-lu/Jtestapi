package JTest;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JTestRunner {
    public static void testYourPackage(String packageName) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        List<String> packagesAllClassName= getClassName(packageName);
        for (String packagesClassName: packagesAllClassName) {
            System.out.println(packagesClassName);
            testYourClass(packagesClassName);
            System.out.println("");
        }
    }
    private static void testYourClass(String testClassName) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        List<JTestResult> jTestResults = runTestClass(testClassName);
        int passedMethod = 0;
        int totalMethod = 0;
        List<String> FailedTests = new ArrayList<String>();
        List<String> ErrorTests = new ArrayList<String>();
        for (JTestResult jTestResult : jTestResults) {
            if (jTestResult.getTestStatus().equals("FAILED")) {
                totalMethod++;
                FailedTests.add(jTestResult.getTestMethodName());
            }
            if (jTestResult.getTestStatus().equals("ERROR")) {
                totalMethod++;
                ErrorTests.add(jTestResult.getTestMethodName());
            }
            if (jTestResult.getTestStatus().equals("PASSED")) {
                passedMethod++;
                totalMethod++;
            }
        }
        System.out.println(passedMethod + "/" + totalMethod + " have passed");
        System.out.println("Failed tests are: " + FailedTests.toString());
        System.out.println("Error tests are: " + ErrorTests.toString());
    }

    /**
     * 找到目标类中所有被{@link JTest}标注的方法
     *
     * @param clazz 需要分析的类。你可以假设这个类里的所有方法都是公有的。
     * @return 该类中所有被{@link JTest.JTest}标注的方法
     */
    private static List<Method> getTestMethods(Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        Stream<Method> stream = Arrays.stream(methods);
        List<Method> collect = stream.filter(x -> x.getAnnotation(JTest.class) != null).collect(Collectors.toList());
        return collect;
    }

    /**
     * 通过反射的方式运行一个方法
     *
     * @param method 想要运行的方法，可以假设这个方法是静态的。
     *               public static JTest.JTestResult runMethod(Method method)
     */
    private static JTestResult runMethod(Method method, Object obj) {
        try {
            method.invoke(obj);
        } catch (Exception e) {
            Throwable cause = e.getCause();
            while (cause != null) {
                // 如果是我们定义的异常
                if (cause instanceof AssertionError) {
                    return new JTestResult("FAILED", method.getName());
                }
                if (cause instanceof InvocationTargetException) {
                    return new JTestResult("ERROR", method.getName());
                }
                if (cause instanceof IllegalAccessException) {
                    return new JTestResult("ERROR", method.getName());
                }
                return new JTestResult("ERROR", method.getName());
            }
        }
        return new JTestResult("PASSED", method.getName());
    }

    /**
     * 运行用户编写的测试类
     *
     * @param testClassName 用户编写的测试类的类名，以字符串形式表示。如P3所说，可以假定这个类里的测试方法都是静态的。
     * @return 运行该类里面所有被{@link JTest.JTest}标注的测试，并以List的形式返回其结果
     */
    private static List<JTestResult> runTestClass(String testClassName) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        Class<?> testClass = Class.forName(testClassName);
        testClass.getClassLoader();
        Constructor<?> constructorJTest = testClass.getDeclaredConstructor();
        Object obj = constructorJTest.newInstance();
        List<Method> testMethods = getTestMethods(testClass);
        List<JTestResult> JTestResultList = new ArrayList<>();
        for (int i = 0; i < testMethods.size(); i++) {
            JTestResult jTestResult = runMethod(testMethods.get(i), obj);
            JTestResultList.add(jTestResult);
        }
        return JTestResultList;
    }
    /**
     * 获取某包下（包括该包的所有子包）所有类
     *
     * @param packageName
     *            包名
     * @return 类的完整名称
     */
    private static List<String> getClassName(String packageName) {
        return getClassName(packageName, true);
    }

    /**
     * 获取某包下所有类
     *
     * @param packageName
     *            包名
     * @param childPackage
     *            是否遍历子包
     * @return 类的完整名称
     */
    private static List<String> getClassName(String packageName, boolean childPackage) {
        List<String> fileNames = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String packagePath = packageName.replace(".", "/");
        URL url = loader.getResource(packagePath);
        if (url != null) {
            String type = url.getProtocol();
            if (type.equals("file")) {
                fileNames = getClassNameByFile(url.getPath(), null, childPackage);
            } else if (type.equals("jar")) {
                fileNames = getClassNameByJar(url.getPath(), childPackage);
            }
        } else {
            fileNames = getClassNameByJars(((URLClassLoader) loader).getURLs(), packagePath, childPackage);
        }
        return fileNames;
    }

    /**
     * 从项目文件获取某包下所有类
     *
     * @param filePath
     *            文件路径
     * @param className
     *            类名集合
     * @param childPackage
     *            是否遍历子包
     * @return 类的完整名称
     */
    private static List<String> getClassNameByFile(String filePath, List<String> className, boolean childPackage) {
        List<String> myClassName = new ArrayList<>();
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                if (childPackage) {
                    myClassName.addAll(getClassNameByFile(childFile.getPath(), myClassName, childPackage));
                }
            } else {
                String childFilePath = childFile.getPath();
                if (childFilePath.endsWith(".class")) {
                    childFilePath = childFilePath.substring(childFilePath.indexOf("\\classes") + 9,
                            childFilePath.lastIndexOf("."));
                    childFilePath = childFilePath.replace("\\", ".");
                    myClassName.add(childFilePath);
                }
            }
        }

        return myClassName;
    }

    /**
     * 从jar获取某包下所有类
     *
     * @param jarPath
     *            jar文件路径
     * @param childPackage
     *            是否遍历子包
     * @return 类的完整名称
     */
    private static List<String> getClassNameByJar(String jarPath, boolean childPackage) {
        List<String> myClassName = new ArrayList<>();
        String[] jarInfo = jarPath.split("!");
        String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
        String packagePath = jarInfo[1].substring(1);
        try {
            JarFile jarFile = new JarFile(jarFilePath);
            Enumeration<JarEntry> entrys = jarFile.entries();
            while (entrys.hasMoreElements()) {
                JarEntry jarEntry = entrys.nextElement();
                String entryName = jarEntry.getName();
                if (entryName.endsWith(".class")) {
                    if (childPackage) {
                        if (entryName.startsWith(packagePath)) {
                            entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                            myClassName.add(entryName);
                        }
                    } else {
                        int index = entryName.lastIndexOf("/");
                        String myPackagePath;
                        if (index != -1) {
                            myPackagePath = entryName.substring(0, index);
                        } else {
                            myPackagePath = entryName;
                        }
                        if (myPackagePath.equals(packagePath)) {
                            entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                            myClassName.add(entryName);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myClassName;
    }

    /**
     * 从所有jar中搜索该包，并获取该包下所有类
     *
     * @param urls
     *            URL集合
     * @param packagePath
     *            包路径
     * @param childPackage
     *            是否遍历子包
     * @return 类的完整名称
     */
    private static List<String> getClassNameByJars(URL[] urls, String packagePath, boolean childPackage) {
        List<String> myClassName = new ArrayList<>();
        if (urls != null) {
            for (int i = 0; i < urls.length; i++) {
                URL url = urls[i];
                String urlPath = url.getPath();
                // 不必搜索classes文件夹
                if (urlPath.endsWith("classes/")) {
                    continue;
                }
                String jarPath = urlPath + "!/" + packagePath;
                myClassName.addAll(getClassNameByJar(jarPath, childPackage));
            }
        }
        return myClassName;
    }

}
