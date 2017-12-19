package kawakuticode.com.ilks.network;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by russeliusernestius on 28/10/17.
 */

public class NetworkUtils {

    final static String LOCAL_WEBSERVICE_URL = "http://10.0.2.2:8080/ILKSWservice/";

    public static URL buildUrl(String param) {
        Uri builtUri = Uri.parse(LOCAL_WEBSERVICE_URL).buildUpon().appendEncodedPath(param).build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String response = null;
        int responseCode = urlConnection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try {
                InputStream in = urlConnection.getInputStream();
                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");
                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    response = scanner.next();
                } else {
                    return null;
                }
            } finally {
                urlConnection.disconnect();
            }
        } else {
            System.out.println("Error Connect WebService " + responseCode);
            urlConnection.disconnect();
        }
        return response;
    }
}

