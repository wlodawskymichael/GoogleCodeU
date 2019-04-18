package com.google.codeu.data;

import java.util.UUID;

public class Post {
    
    private UUID postId;
    private UUID threadId;
    private String user;
    private String text;
    private long timestamp;

    public Post(UUID threadId, String user, String text) {
        this(UUID.randomUUID(),threadId, user, text, System.currentTimeMillis());
    }

    public Post(UUID postId, UUID threadId, String user, String text, long timestamp) {
        this.postId = postId;
        this.threadId = threadId;
        this.user = user;
        this.text = text;
        this.timestamp = timestamp;
    }

    public UUID getPostId() {
        return this.postId;
    }

    public UUID getThreadId() {
        return this.threadId;
    }

    public String getUser() {
        return this.user;
    }

    public String getText() {
        return this.text;
    }

    public long getTimestamp() {
        return this.timestamp;
    }
}