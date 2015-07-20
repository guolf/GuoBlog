
package cn.guolf.guoblog.entity;

import java.util.List;

/**
 * Created by ywwxhz on 2014/11/2.
 */
public class ResponseObject<T> {
    private T Data;
    private String ResultCode;
    private String ResultMsg;

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public String getResultCode() {
        return ResultCode;
    }

    public void setResultCode(String resultCode) {
        ResultCode = resultCode;
    }

    public String getResultMsg() {
        return ResultMsg;
    }

    public void setResultMsg(String resultMsg) {
        ResultMsg = resultMsg;
    }
}
