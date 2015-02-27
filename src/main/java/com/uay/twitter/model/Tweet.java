package com.uay.twitter.model;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

/**
 * Tweet model used for deserialization from JSON format.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tweet {
    private Long id;
    private Date createdAt;
    private String text;
    private User user;
    private Boolean retweet = false;

    public Tweet() {
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @JsonProperty("created_at")
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("retweeted_status")
    @JsonDeserialize(using = RetweetStatusConverter.class)
    public Boolean getRetweet() {
        return retweet;
    }

    public void setRetweet(final Boolean retweet) {
        this.retweet = retweet;
    }

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    private static class RetweetStatusConverter extends JsonDeserializer<Boolean> {

        @Override
        public Boolean deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
            JsonNode node = jp.getCodec().readTree(jp);
            return node != null;
        }
    }
}

