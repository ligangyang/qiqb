package net.qiqb.module.client;

import java.io.Serializable;
import java.util.Collection;

/**
 * 用于包装接口或者响应的结果。
 *
 * @param <T>
 */
public class ResultWrapper<T> implements Serializable {

    /**
     * 业务响应是否成功
     */
    private Boolean success;
    /**
     * 错误代码。
     */
    private String errorCode;
    /**
     * 错误信息
     */
    private String errorMessage;

    private T data;

    protected ResultWrapper(T data) {
        this.data = data;
    }

    public T getData() {
        return this.data;
    }

    public ResultWrapper<T> setData(T data) {
        this.data = data;
        return this;
    }


    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errMessage) {
        this.errorMessage = errMessage;
    }

    public static ResultWrapper buildFailure(String errCode, String errMessage) {
        ResultWrapper<?> response = new ResultWrapper<>(null);
        response.setSuccess(false);
        response.setErrorCode(errCode);
        response.setErrorMessage(errMessage);
        return response;
    }

    public static <T> ResultWrapper<T> buildSuccess(T data) {
        ResultWrapper<T> response = new ResultWrapper<>(data);
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

    @Override
    public String toString() {
        return "ResultWrapper [success=" + this.success + ", errorCode=" + this.errorCode + ", errorMessage=" + this.errorMessage + "]";
    }

    /**
     * 是否是数组集合
     *
     * @return
     */
    public boolean isCollection() {
        return this.getData() instanceof Collection;
    }
}