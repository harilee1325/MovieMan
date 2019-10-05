package com.harilee.movieman.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.harilee.movieman.Model.LoginModel;

public class LoginResponse {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("result")
    @Expose
    private LoginModel result;
    @SerializedName("success")
    @Expose
    private String success;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LoginModel getResult() {
        return result;
    }

    public void setResult(LoginModel result) {
        this.result = result;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

}
