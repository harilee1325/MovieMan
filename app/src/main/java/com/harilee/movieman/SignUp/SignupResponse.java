package com.harilee.movieman.SignUp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.harilee.movieman.Model.SignupModel;

public class SignupResponse {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("result")
    @Expose
    private SignupModel result;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public SignupModel getResult() {
        return result;
    }

    public void setResult(SignupModel result) {
        this.result = result;
    }
}
