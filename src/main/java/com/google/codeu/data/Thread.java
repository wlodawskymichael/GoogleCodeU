package com.google.codeu.data;

import java.util.UUID;

public class Thread {

    private UUID firstPostId;
    private UUID threadId;
    private UUID forumId;
    private String title;
    private String topic;
    private long timestamp;

    public Thread(UUID forumId, UUID firstPostId, String title, String topic) {
        this(forumId, UUID.randomUUID(),firstPostId, title, topic, System.currentTimeMillis());
    }

    public Thread(UUID forumId, UUID threadId, UUID firstPostId,
      String title, String topic, long timestamp) {
        this.forumId = forumId;
        this.firstPostId = firstPostId;
        this.threadId = threadId;
        this.title = title;
        this.topic = topic;
        this.timestamp = timestamp;
    }

    public void setThreadId(UUID Id) {
        this.threadId = Id;
    }

    public UUID getThreadId() {
        return this.threadId;
    }

    public UUID getForumId() {
        return this.forumId;
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

    public long getTimstamp() {
      return this.timestamp;
    }
}
