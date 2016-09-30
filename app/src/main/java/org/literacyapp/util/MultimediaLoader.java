package org.literacyapp.util;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MultimediaLoader {

    public static byte[] loadMultimedia(String urlValue) {
        Log.d(MultimediaLoader.class, "loadMultimedia");

        Log.d(MultimediaLoader.class, "Downloading from " + urlValue + "...");

        byte[] bytes = null;

        try {
            URL url = new URL(urlValue);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            int responseCode = httpURLConnection.getResponseCode();
            Log.d(MultimediaLoader.class, "responseCode: " + responseCode);
            InputStream inputStream = null;
            if (responseCode == 200) {
                inputStream = httpURLConnection.getInputStream();
                bytes = IOUtils.toByteArray(inputStream);
            } else {
                inputStream = httpURLConnection.getErrorStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String response = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
                Log.w(MultimediaLoader.class, "responseCode: " + responseCode + ", response: " + response);
            }
        } catch (MalformedURLException e) {
            Log.e(MultimediaLoader.class, "MalformedURLException", e);
            e.printStackTrace();
        } catch (ProtocolException e) {
            Log.e(MultimediaLoader.class, "ProtocolException", e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(MultimediaLoader.class, "IOException", e);
            e.printStackTrace();
        }

        return bytes;
    }
}
