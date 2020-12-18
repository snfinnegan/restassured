package org.restassured.enums;

public enum Endpoint {
    UPDATE_STATUS("/update.json"),
    USER_TIMELINE("/user_timeline.json");

    private final String url;

    Endpoint(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }
}
