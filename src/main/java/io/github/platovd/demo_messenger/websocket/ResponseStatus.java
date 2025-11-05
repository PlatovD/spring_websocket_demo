package io.github.platovd.demo_messenger.websocket;

public enum ResponseStatus {
    OK("OK"),
    ERROR("ERROR");

    private final String desc;

    ResponseStatus(String s) {
        desc = s;
    }

    @Override
    public String toString() {
        return desc;
    }
}
