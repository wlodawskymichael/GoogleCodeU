package com.google.codeu.data;

import java.util.UUID;

public class Thread {

    private UUID firstPostId;
    private UUID threadId;
    private String title;
    private String topic;
    private long timestamp;

    public Thread(UUID firstPostId, String title, String topic) {
        this(UUID.randomUUID(),firstPostId, title, topic, System.currentTimeMillis());
    }

    public Thread(UUID threadId, UUID firstPostId,
      String title, String topic, long timestamp) {
        this.firstPostId = firstPostId;
        this.threadId = threadId;
        this.title = title;
        this.topic = topic;
        this.timestamp = timestamp;
    }

    public void setThreadId(UUID Id) {
        this.threadId = Id;
    }

    public void setFirstPostId(UUID id) {
        this.firstPostId = id;
    }

    public UUID getThreadId() {
        return this.threadId;
    }

    public UUID getFirstPostId() {
        return this.firstPostId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getTopic() {
        return this.topic;
    }

    public long getTimestamp() {
      return this.timestamp;
    }
}
