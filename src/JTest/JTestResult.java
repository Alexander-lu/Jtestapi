package JTest;

public class JTestResult {
    private String status;
    private String methodName;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public JTestResult(String status, String methodName) {
        this.status = status;
        this.methodName = methodName;
    }

    public String getTestStatus() {
        return status;
    }

    public String getTestMethodName() {
        return methodName;
    }
}

