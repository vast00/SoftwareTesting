package com.android.ecnu.hospital;

public class MyMessage {
    private String id;
    private String messageType;
    private String messageTitle;
    private String messageContent;
    private String messageUrl;
    private String creator;
    private String eventId;
    private String creatorId;
    private String messageTo;
    private String readed;
    private String updateTime;

    public String getEventId() {
        return eventId;
    }

    public String getId() {
        return id;
    }

    public String getCreator() {
        return creator;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public String getMessageTo() {
        return messageTo;
    }

    public String getMessageType() {
        return messageType;
    }

    public String getMessageUrl() {
        return messageUrl;
    }

    public String getReaded() {
        return readed;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public void setMessageTo(String messageTo) {
        this.messageTo = messageTo;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public void setMessageUrl(String messageUrl) {
        this.messageUrl = messageUrl;
    }

    public void setReaded(String readed) {
        this.readed = readed;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
