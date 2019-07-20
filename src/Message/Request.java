package Message;

import java.io.Serializable;

/**
 * @author Ryan Li
 * @date 2019/07/20
 */
public class Request implements Serializable {

    private String className;
    private String methodName;
    private Class<?>[] typeParameters;
    private Object[] parametersVal;

    public Request() {}

    public Request(String className, String methodName, Class<?>[] typeParameters, Object[] parametersVal) {
        this.className = className;
        this.methodName = methodName;
        this.typeParameters = typeParameters;
        this.parametersVal = parametersVal;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getTypeParameters() {
        return typeParameters;
    }

    public void setTypeParameters(Class<?>[] typeParameters) {
        this.typeParameters = typeParameters;
    }

    public Object[] getParametersVal() {
        return parametersVal;
    }

    public void setParametersVal(Object[] parametersVal) {
        this.parametersVal = parametersVal;
    }
}
