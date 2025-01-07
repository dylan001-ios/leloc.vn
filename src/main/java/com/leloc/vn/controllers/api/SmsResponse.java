package com.leloc.vn.controllers.api;

public class SmsResponse {
    private String status;
    private Data data;

    // Nested class for 'data' field
    public static class Data {
        private String message;

        public Data(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    // Constructors
    public SmsResponse() {}

    public SmsResponse(String status, String message) {
        this.status = status;
        this.data = new Data(message);
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
