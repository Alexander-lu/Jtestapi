package JTest;

public class JAssertions {
    public static void assertTrue(boolean condition) {
        if (!condition) {
            throw new AssertionError();
        }
    }

    public static void assertFalse(boolean condition) {
        if (condition) {
            throw new AssertionError();
        }
    }

    public static void assertNull(Object expected) {
        if (!(expected == null)) {
            throw new AssertionError();
        }
    }

    public static void assertNotNull(Object expected) {
        if (expected == null) {
            throw new AssertionError();
        }
    }

    public static void assertSame(Object expected, Object actual) {
        if (expected != null | actual != null) {
            if (!(expected == actual)) {
                throw new AssertionError();
            }
        }
    }

    public static void assertNotSame(Object expected, Object actual) {
        if (expected != null | actual != null) {
            if (expected.equals(actual)) {
                throw new AssertionError();
            }
        } else {
            throw new AssertionError();
        }
    }

    public static void assertEquals(Object expected, Object actual) {
        if (expected != null | actual != null) {
            if (!expected.equals(actual)) {
                throw new AssertionError();
            }
        }
    }

    public static void assertNotEquals(Object expected, Object actual) {
        if (expected != null | actual != null) {
            if (expected.equals(actual)) {
                throw new AssertionError();
            }
        } else {
            throw new AssertionError();
        }
    }

    public static void assertEquals(Integer expected, Integer actual) {
        if (expected != null | actual != null) {
            if (!expected.equals(actual)) {
                throw new AssertionError();
            }
        }
    }

    public static void assertNotEquals(Integer expected, Integer actual) {
        if (expected != null | actual != null) {
            if (expected.equals(actual)) {
                throw new AssertionError();
            }
        } else {
            throw new AssertionError();
        }
    }

    public static void assertEquals(Float expected, Float actual) {
        if (expected != null | actual != null) {
            if (!expected.equals(actual)) {
                throw new AssertionError();
            }
        }
    }

    public static void assertNotEquals(Float expected, Float actual) {
        if (expected != null | actual != null) {
            if (expected.equals(actual)) {
                throw new AssertionError();
            }
        } else {
            throw new AssertionError();
        }
    }

    public static void assertEquals(Double expected, Double actual) {
        if (expected != null | actual != null) {
            if (!expected.equals(actual)) {
                throw new AssertionError();
            }
        }
    }

    public static void assertNotEquals(Double expected, Double actual) {
        if (expected != null | actual != null) {
            if (expected.equals(actual)) {
                throw new AssertionError();
            }
        } else {
            throw new AssertionError();
        }
    }

    public static void assertEquals(boolean expected, boolean actual) {
        if (!(expected == actual)) {
            throw new AssertionError();
        }
    }

    public static void assertNotEquals(boolean expected, boolean actual) {
        if (expected == actual) {
            throw new AssertionError();
        }
    }

    public static void assertEquals(char expected, char actual) {
        if (!(expected == actual)) {
            throw new AssertionError();
        }
    }

    public static void assertNotEquals(char expected, char actual) {
        if (expected == actual) {
            throw new AssertionError();
        }
    }

    public static void assertEquals(double expected, double actual) {
        if (!(expected == actual)) {
            throw new AssertionError();
        }
    }

    public static void assertNotEquals(double expected, double actual) {
        if (expected == actual) {
            throw new AssertionError();
        }
    }

    public static void assertEquals(float expected, float actual) {
        if (!(expected == actual)) {
            throw new AssertionError();
        }
    }

    public static void assertNotEquals(float expected, float actual) {
        if (expected == actual) {
            throw new AssertionError();
        }
    }

    public static void assertEquals(long expected, long actual) {
        if (!(expected == actual)) {
            throw new AssertionError();
        }
    }

    public static void assertNotEquals(long expected, long actual) {
        if (expected == actual) {
            throw new AssertionError();
        }
    }

    public static void assertEquals(short expected, short actual) {
        if (!(expected == actual)) {
            throw new AssertionError();
        }
    }

    public static void assertNotEquals(short expected, short actual) {
        if (expected == actual) {
            throw new AssertionError();
        }
    }

    public static void assertEquals(int expected, int actual) {
        if (!(expected == actual)) {
            throw new AssertionError();
        }
    }

    public static void assertNotEquals(int expected, int actual) {
        if (expected == actual) {
            throw new AssertionError();
        }
    }

    public static void assertEquals(byte expected, byte actual) {
        if (!(expected == actual)) {
            throw new AssertionError();
        }
    }

    public static void assertNotEquals(byte expected, byte actual) {
        if (expected == actual) {
            throw new AssertionError();
        }
    }

    public static void assertThrows(Class<?> expectedType, ExecutableJTest method) {
        try {
            method.execute();
        } catch (Throwable e) {
            if (e.getClass().equals(expectedType)) {
            } else {
                throw new AssertionError();
            }
        }
    }
}
