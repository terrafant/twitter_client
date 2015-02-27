package com.uay.twitter.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * User model used for deserialization from JSON format.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String screenName;
    private String name;
    private String avatar;

    @JsonProperty("screen_name")
    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(final String screenName) {
        this.screenName = screenName;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @JsonProperty("profile_image_url")
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(final String avatar) {
        this.avatar = avatar;
    }
}