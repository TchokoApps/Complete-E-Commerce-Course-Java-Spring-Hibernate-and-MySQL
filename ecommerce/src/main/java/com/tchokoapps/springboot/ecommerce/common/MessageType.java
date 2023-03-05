package com.tchokoapps.springboot.ecommerce.common;

import java.util.Arrays;
import java.util.List;

public enum MessageType {

    SUCCESS("success"),
    INFO("info"),
    WARNING("warning"),
    DANGER("danger"),
    SECONDARY("secondary");

    private final String value;

    private MessageType(String value) {
        this.value = value;
    }

    // get the message type from a string value
    public static MessageType fromValue(String value) {
        for (MessageType messageType : MessageType.values()) {
            if (messageType.getValue().equalsIgnoreCase(value)) {
                return messageType;
            }
        }
        return null;
    }

    // get a list of all message types
    public static List<MessageType> getMessageTypes() {
        return Arrays.asList(MessageType.values());
    }

    // get the CSS class for a given message type
    public static String getCssClass(MessageType messageType) {
        return switch (messageType) {
            case SUCCESS -> "alert alert-success";
            case INFO -> "alert alert-info";
            case WARNING -> "alert alert-warning";
            case DANGER -> "alert alert-danger";
            case SECONDARY -> "alert alert-secondary";
            default -> throw new IllegalArgumentException("Invalid message type: " + messageType);
        };
    }

    public String getValue() {
        return value;
    }
}
