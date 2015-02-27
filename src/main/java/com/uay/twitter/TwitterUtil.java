package com.uay.twitter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.List;

import com.uay.twitter.model.AccessToken;
import com.uay.twitter.model.Tweet;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 * Utility class for interaction with Twitter API.
 *
 */
public final class TwitterUtil {
    private static final String HTTPS = "https";
    private static final String TWITTER_HOST = "api.twitter.com";
    private static final String USER_TIMELINE = "/1.1/statuses/user_timeline.json";
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy");

    /**
     * Makes request to retrieve 'bearer token' using API credentials.
     *
     * @param apiToken the API Token.
     * @param apiSecret the API Secret.
     * @return the 'bearer token'
     * @throws URISyntaxException
     * @throws IOException
     */
    public static String getBearerToken(String apiToken, String apiSecret, int timeout)
            throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder();
        builder.setScheme(HTTPS).setHost(TWITTER_HOST).setPath("/oauth2/token");
        URI uri = builder.build();

        HttpPost httpPost = new HttpPost(uri);
        String basicAuthToken = encodeBase64(apiToken + ":" + apiSecret);
        httpPost.addHeader("Authorization", "Basic " + basicAuthToken);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        httpPost.setEntity(new ByteArrayEntity("grant_type=client_credentials".getBytes(Charset.defaultCharset())));

        HttpClient client = new DefaultHttpClient();
        setTimeout(client, timeout);
        HttpResponse response = client.execute(httpPost);
        AccessToken accessToken = parse(response.getEntity().getContent(), new TypeReference<AccessToken>() {});
        return accessToken.getAccessToken();
    }

    private static void setTimeout(final HttpClient client, final int timeout) {
        HttpParams params = client.getParams();
        HttpConnectionParams.setConnectionTimeout(params, timeout);
    }

    /**
     * Makes request to retrieve tweets.
     *
     * @param bearerToken the 'bearer token' needed for authorization purposes.
     * @param userScreenName the 'screen name' of Twitter user.
     * @param maxNumberOfTweets the maximum number of retrieved tweets.
     * @param includeRetweets determines whether to include retweets.
     * @return the list of Tweet objects of specified maximum size.
     * @throws URISyntaxException
     * @throws IOException
     */
    public static List<Tweet> getTweets(final String bearerToken, final String userScreenName,
                                        final int maxNumberOfTweets, final boolean includeRetweets)
            throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder();
        builder.setScheme(HTTPS).setHost(TWITTER_HOST).setPath(USER_TIMELINE)
                .setParameter("count", String.valueOf(maxNumberOfTweets))
                .setParameter("include_rts", String.valueOf(includeRetweets))
                .setParameter("screen_name", userScreenName);
        URI uri = builder.build();
        HttpGet httpget = new HttpGet(uri);
        httpget.addHeader("Authorization", "Bearer " + bearerToken);

        HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(httpget);
        return parse(response.getEntity().getContent(), new TypeReference<List<Tweet>>() {});
    }

    private static <T> T parse(final InputStream stream, final TypeReference<T> typeReference) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(FORMAT);
        return mapper.readValue(stream, typeReference);
    }

    private static String encodeBase64(final String src) {
        byte[] bytes = Base64.encodeBase64(src.getBytes());
        return new String(bytes);
    }
}

