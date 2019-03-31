package cc.mrbird.common.domain;

public class MethodInvokeMeta {
    private Class<?> interfaceClass;
    private String methodName;
    private Object[] args;
    private Class<?>[] parameterTypes;
    private Object returnType;

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object getReturnType() {

        return returnType;
    }

    public void setReturnType(Object returnType) {
        this.returnType = returnType;
    }
}
