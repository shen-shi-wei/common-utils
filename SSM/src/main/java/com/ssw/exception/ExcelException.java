package com.ssw.exception;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: ssw
 * @Date: 2020/03/13/13:24
 * @Description:
 */
public class ExcelException extends Exception {
    private String error;

    public ExcelException(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ExcelException{" +
                "error='" + error + '\'' +
                '}';
    }
}