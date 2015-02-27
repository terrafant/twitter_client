package com.uay.twitter.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class AccessToken {

    private String tokenType;
    private String token;

    public AccessToken() {
    }

    @JsonProperty("token_type")
    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(final String tokenType) {
        this.tokenType = tokenType;
    }

    @JsonProperty("access_token")
    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(final String accessToken) {
        this.token = accessToken;
    }
}
