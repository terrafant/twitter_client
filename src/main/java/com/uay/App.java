package com.uay;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.uay.twitter.model.Tweet;
import com.uay.twitter.TwitterUtil;

public class App {
    public static final String API_TOKEN = "apiToken";
    public static final String API_SECRET = "apiSecret";
    public static final String ACCOUNT_NAME = "account";
    public static final int TIMEOUT = 3000;
    public static final int MAX_TWEETS = 20;

    public static void main(String[] args) throws IOException, URISyntaxException {
        String bearerToken = TwitterUtil.getBearerToken(API_TOKEN, API_SECRET, TIMEOUT);
        List<Tweet> tweets = TwitterUtil.getTweets(bearerToken, ACCOUNT_NAME, MAX_TWEETS, true);

        for (Tweet tweet: tweets) {
            System.out.println(tweet.getUser().getScreenName() + ": " + tweet.getText());
        }
    }
}
