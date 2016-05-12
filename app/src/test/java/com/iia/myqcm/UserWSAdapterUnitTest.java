package com.iia.myqcm;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;

import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class UserWSAdapterUnitTest {

    private static final String BASE_URL = "http://192.168.218.13/myQCM/web/app_dev.php/api";
    private static final String ENTITY = "users";

    /**
     * Test function getUser
     * @throws IOException
     */
    @Test
    public void getUser() throws IOException {
        String username = "maxy";

        String urlStr = String.format("%s/%s/%s", BASE_URL, ENTITY, username);

        try {
            URL url = new URL(urlStr);
            HttpURLConnection httpURL = (HttpURLConnection) url.openConnection();
            httpURL.connect();

            assertEquals(HttpURLConnection.HTTP_OK, httpURL.getResponseCode());

        } catch (IOException e) {
            System.err.println("ERROR HTTP CONNECTION");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Test function getAll users
     * @throws IOException
     */
    @Test
    public void getAll() throws IOException {
        String urlStr = String.format("%s/%s", BASE_URL, ENTITY);

        try {
            URL url = new URL(urlStr);
            HttpURLConnection httpURL = (HttpURLConnection) url.openConnection();
            httpURL.connect();

            assertEquals(HttpURLConnection.HTTP_OK, httpURL.getResponseCode());

        } catch (IOException e) {
            System.err.println("ERROR HTTP CONNECTION");
            e.printStackTrace();
            throw e;
        }
    }

}