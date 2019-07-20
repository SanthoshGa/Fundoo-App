package com.bridgelabz.fundoo.Utility.HttpCodeUtil;

public enum HttpResponseCode {
    SUCCESS(200, "Success"),
    NOT_FOUND(404, "Not Found Error"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    int errorCode;
    String localizedDescription;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getLocalizedDescription() {
        return localizedDescription;
    }

    public void setLocalizedDescription(String localizedDescription) {
        this.localizedDescription = localizedDescription;
    }

    HttpResponseCode(int errorCode, String localizedDesc) {
        this.errorCode = errorCode;
        this.localizedDescription = localizedDesc;
    }
}
