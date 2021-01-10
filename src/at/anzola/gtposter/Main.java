package at.anzola.gtposter;

import at.anzola.response.Post;
import at.anzola.response.PostBody;
import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.apache.commons.io.FileUtils;
import winterwell.jtwitter.OAuthSignpostClient;
import winterwell.jtwitter.Twitter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    static Connection connection;

    public static Twitter twitter;

    static String BASE_URL;
    static String API_KEY;

    static String HOSTNAME;
    static String DATABASE;
    static String USERNAME;
    static String PASSWORD;

    public static void main(String[] args) throws IOException, InterruptedException, SQLException {
        //java -jar ghost-twitter-poster.jar BASE_URL APIKEY DBHOST:PORT DBNAME UNAME PASSWD

        System.out.println("Starting script at " + LocalDateTime.now().toString());

        getOptions(args);

        Bot_DB.connectdb();

        getTwitter(true);

        runScript();

        //Bot_DB.clearValues();

        //devTweet();

    }

    public static void getOptions(String[] args) {
        BASE_URL = args[0];
        API_KEY = args[1];
        HOSTNAME = args[2];
        DATABASE = args[3];
        USERNAME = args[4];
        PASSWORD = args[5];
    }

    public static void runScript() throws SQLException, IOException {
        PostBody pb;
        HttpResponse<String> response = Unirest.get(BASE_URL + "/ghost/api/v2/content/posts/?key=" + API_KEY + "&limit=all").asString();

        Gson gson = new Gson();

        pb = gson.fromJson(String.valueOf(response.getBody()), PostBody.class);

        for (int i = pb.posts.length - 1; i >= 0; i--) {
            Post post = pb.posts[i];
            if (!Bot_DB.searchValue(post.getId())) {
                tweetImage("We released a new Blog: " + post.getTitle() + "\n Check it out at " + post.getUrl() + "!", post.getFeature_image());
                System.out.println("Tweeting " + post.getUrl());
                Bot_DB.putValue(post.getId());
            }
        }

    }


    public static void tweetImage(String text, String imageUrl) throws IOException {
        File image = new File("download.png");
        FileUtils.copyURLToFile(new URL(imageUrl), image);
        twitter.updateStatusWithMedia(text, null, image);
        image.delete();
    }

    public static void devTweet() {
        twitter.updateStatus("Bot currently in development... " + System.nanoTime());
    }

    public static void getTwitter(boolean search) throws SQLException {
        OAuthSignpostClient client;
        if (Bot_DB.readTwitterOauth() != null && search) {
            client = new OAuthSignpostClient(
                    OAuthSignpostClient.JTWITTER_OAUTH_KEY, OAuthSignpostClient.JTWITTER_OAUTH_SECRET,
                    Objects.requireNonNull(Bot_DB.readTwitterOauth())[0], Objects.requireNonNull(Bot_DB.readTwitterOauth())[1]);
        } else {
            client = new OAuthSignpostClient(OAuthSignpostClient.JTWITTER_OAUTH_KEY,
                    OAuthSignpostClient.JTWITTER_OAUTH_SECRET, "oob");
            System.out.print(client.authorizeUrl() + "\nEnter pin here: ");
            client.setAuthorizationCode((new Scanner(System.in)).nextLine());
            String[] accessToken = client.getAccessToken();
            Bot_DB.storeTwitterOauth(accessToken);
        }
        twitter = new Twitter("twitter_bot", client);
    }
}
