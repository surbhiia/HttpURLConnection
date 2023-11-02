package com.test.httpurlconnection;

import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.simpleButton1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //Test for APIs added in URLConnection
                            urlConnectionAPIs();
                            urlConnectionAPIsWithHttp();
                            urlConnectionAPIsWithHttps();

                            //Test for APIs overriden and new APIs added in HttpURLConnection
                            httpURLConnectionAPIs();
                            httpURLConnectionAPIsWithHttps();

                            //getOutputStream Test
                            urlConnectionPost();
                            httpUrlConnectionPost();
                            httpsUrlConnectionPost();

                            //getErrorStreamTest
                            httpURLConnectionErrorStream();
                            httpsURLConnectionErrorStream();

                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }).start();
                showToast();
            }
        });
    }

    public void urlConnectionAPIs() throws IOException {

        URL url = new URL("http://httpbin.org/headers");
        URLConnection urlConnection = url.openConnection();

        urlConnection.connect();

        StringBuilder builder = new StringBuilder();

        Object content = urlConnection.getContent();
        Class contentClass = null;
        if(content != null) {
            contentClass = content.getClass();
            builder.append("Content: ");
            builder.append(content);
            builder.append("\n");
        }

        if(contentClass != null) {
            Object contentByClass = urlConnection.getContent(new Class[]{contentClass});
            if (contentByClass != null) {
                builder.append("Content based on class type: ");
                builder.append(contentByClass);
                builder.append("\n");
            }
        }

        builder.append("Content Type: ");
        builder.append(urlConnection.getContentType());
        builder.append("\n");

        builder.append("Content Encoding: ");
        builder.append(urlConnection.getContentEncoding());
        builder.append("\n");

        builder.append("Content Length: ");
        builder.append(urlConnection.getContentLength());
        builder.append("\n");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.append("Content Length Long: ");
            builder.append(urlConnection.getContentLengthLong());
            builder.append("\n");
        }

        builder.append("Date: ");
        builder.append(urlConnection.getDate());
        builder.append("\n");

        builder.append("Expiration: ");
        builder.append(urlConnection.getExpiration());
        builder.append("\n");

        builder.append("Last Modified: ");
        builder.append(urlConnection.getLastModified());
        builder.append("\n");

        builder.append("Header Fields: ");
        Map<String, List<String>> map = urlConnection.getHeaderFields();
        for (Map.Entry<String, List<String>> entry : map.entrySet())
        {
            if (entry.getKey() == null)
                continue;
            builder.append( entry.getKey())
                    .append(": ");

            List<String> headerValues = entry.getValue();
            Iterator<String> it = headerValues.iterator();
            if (it.hasNext()) {
                builder.append(it.next());

                while (it.hasNext()) {
                    builder.append(", ")
                            .append(it.next());
                }
            }

            builder.append("\n");
        }

        builder.append("Header Field given string name: ");
        builder.append(urlConnection.getHeaderField("Accept-Encoding"));
        builder.append("\n");

        builder.append("Header Field given int index : ");
        builder.append(urlConnection.getHeaderField(0));
        builder.append("\n");

        builder.append("Header Field Key given int index: ");
        builder.append(urlConnection.getHeaderFieldKey(0));
        builder.append("\n");

        builder.append("Header Field as Date: ");
        builder.append(urlConnection.getHeaderFieldDate("Date", -1));
        builder.append("\n");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.append("Header Field as Long: ");
            builder.append(urlConnection.getHeaderFieldLong("X-Android-Sent-Millis", -1));
            builder.append("\n");
        }

        builder.append("Header Field as Int: ");
        builder.append(urlConnection.getHeaderFieldInt("X-Android-Sent-Millis", -1));
        builder.append("\n");

        builder.append("Input Stream: ");
        InputStream in = urlConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while((line = reader.readLine()) != null) {
            builder.append(line);
        }
        builder.append("\n");

        Log.d("URLConnectionAPIs", " " + builder);
    }

    private void urlConnectionAPIsWithHttp() throws IOException {
        URL url = new URL("http://httpbin.org/headers");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.connect();

        StringBuilder builder = new StringBuilder();

        Object content = httpURLConnection.getContent();
        Class contentClass = null;
        if(content != null) {
            contentClass = content.getClass();
            builder.append("Content: ");
            builder.append(content);
            builder.append("\n");
        }

        if(contentClass != null) {
            Object contentByClass = httpURLConnection.getContent(new Class[]{contentClass});
            if (contentByClass != null) {
                builder.append("Content based on class type: ");
                builder.append(contentByClass);
                builder.append("\n");
            }
        }

        builder.append("Content Type: ");
        builder.append(httpURLConnection.getContentType());
        builder.append("\n");

        builder.append("Content Encoding: ");
        builder.append(httpURLConnection.getContentEncoding());
        builder.append("\n");

        builder.append("Content Length: ");
        builder.append(httpURLConnection.getContentLength());
        builder.append("\n");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.append("Content Length Long: ");
            builder.append(httpURLConnection.getContentLengthLong());
            builder.append("\n");
        }

        builder.append("Date: ");
        builder.append(httpURLConnection.getDate());
        builder.append("\n");

        builder.append("Expiration: ");
        builder.append(httpURLConnection.getExpiration());
        builder.append("\n");

        builder.append("Last Modified: ");
        builder.append(httpURLConnection.getLastModified());
        builder.append("\n");

        builder.append("Header Fields: ");
        Map<String, List<String>> map = httpURLConnection.getHeaderFields();
        for (Map.Entry<String, List<String>> entry : map.entrySet())
        {
            if (entry.getKey() == null)
                continue;
            builder.append( entry.getKey())
                    .append(": ");

            List<String> headerValues = entry.getValue();
            Iterator<String> it = headerValues.iterator();
            if (it.hasNext()) {
                builder.append(it.next());

                while (it.hasNext()) {
                    builder.append(", ")
                            .append(it.next());
                }
            }

            builder.append("\n");
        }

        builder.append("Header Field given string name: ");
        builder.append(httpURLConnection.getHeaderField("Accept-Encoding"));
        builder.append("\n");

        builder.append("Header Field given int index : ");
        builder.append(httpURLConnection.getHeaderField(0));
        builder.append("\n");

        builder.append("Header Field Key given int index: ");
        builder.append(httpURLConnection.getHeaderFieldKey(0));
        builder.append("\n");

        builder.append("Header Field as Date: ");
        builder.append(httpURLConnection.getHeaderFieldDate("Date", -1));
        builder.append("\n");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.append("Header Field as Long: ");
            builder.append(httpURLConnection.getHeaderFieldLong("X-Android-Sent-Millis", -1));
            builder.append("\n");
        }

        builder.append("Header Field as Int: ");
        builder.append(httpURLConnection.getHeaderFieldInt("X-Android-Sent-Millis", -1));
        builder.append("\n");

        builder.append("Input Stream: ");
        InputStream in = httpURLConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while((line = reader.readLine()) != null) {
            builder.append(line);
        }
        builder.append("\n");

        Log.d("urlConnectionAPIsWithHttp", " " + builder);
    }

    private void urlConnectionAPIsWithHttps() throws IOException {
        URL url = new URL("https://httpbin.org/headers");
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();

        httpsURLConnection.connect();

        StringBuilder builder = new StringBuilder();

        Object content = httpsURLConnection.getContent();
        Class contentClass = null;
        if(content != null) {
            contentClass = content.getClass();
            builder.append("Content: ");
            builder.append(content);
            builder.append("\n");
        }

        if(contentClass != null) {
            Object contentByClass = httpsURLConnection.getContent(new Class[]{contentClass});
            if (contentByClass != null) {
                builder.append("Content based on class type: ");
                builder.append(contentByClass);
                builder.append("\n");
            }
        }

        builder.append("Content Type: ");
        builder.append(httpsURLConnection.getContentType());
        builder.append("\n");

        builder.append("Content Encoding: ");
        builder.append(httpsURLConnection.getContentEncoding());
        builder.append("\n");

        builder.append("Content Length: ");
        builder.append(httpsURLConnection.getContentLength());
        builder.append("\n");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.append("Content Length Long: ");
            builder.append(httpsURLConnection.getContentLengthLong());
            builder.append("\n");
        }

        builder.append("Date: ");
        builder.append(httpsURLConnection.getDate());
        builder.append("\n");

        builder.append("Expiration: ");
        builder.append(httpsURLConnection.getExpiration());
        builder.append("\n");

        builder.append("Last Modified: ");
        builder.append(httpsURLConnection.getLastModified());
        builder.append("\n");

        builder.append("Header Fields: ");
        Map<String, List<String>> map = httpsURLConnection.getHeaderFields();
        for (Map.Entry<String, List<String>> entry : map.entrySet())
        {
            if (entry.getKey() == null)
                continue;
            builder.append( entry.getKey())
                    .append(": ");

            List<String> headerValues = entry.getValue();
            Iterator<String> it = headerValues.iterator();
            if (it.hasNext()) {
                builder.append(it.next());

                while (it.hasNext()) {
                    builder.append(", ")
                            .append(it.next());
                }
            }

            builder.append("\n");
        }

        builder.append("Header Field given string name: ");
        builder.append(httpsURLConnection.getHeaderField("Accept-Encoding"));
        builder.append("\n");

        //Call goes to the overriden method in HttpURLConnection
        builder.append("Header Field given int index : ");
        builder.append(httpsURLConnection.getHeaderField(0));
        builder.append("\n");

        //Call goes to the overriden method in HttpURLConnection
        builder.append("Header Field Key given int index: ");
        builder.append(httpsURLConnection.getHeaderFieldKey(0));
        builder.append("\n");

        //Call goes to the overriden method in HttpURLConnection
        builder.append("Header Field as Date: ");
        builder.append(httpsURLConnection.getHeaderFieldDate("Date", -1));
        builder.append("\n");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.append("Header Field as Long: ");
            builder.append(httpsURLConnection.getHeaderFieldLong("X-Android-Sent-Millis", -1));
            builder.append("\n");
        }

        builder.append("Header Field as Int: ");
        builder.append(httpsURLConnection.getHeaderFieldInt("X-Android-Sent-Millis", -1));
        builder.append("\n");

        builder.append("Input Stream: ");
        InputStream in = httpsURLConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while((line = reader.readLine()) != null) {
            builder.append(line);
        }
        builder.append("\n");

        Log.d("urlConnectionAPIsWithHttps", " " + builder);
    }

    private void httpURLConnectionAPIs() throws IOException {
        URL url = new URL("http://httpbin.org/headers");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        StringBuilder builder = new StringBuilder();
        //HttpURLConnection Overrides this from URLConnection
        builder.append("Header Field given Int: ");
        builder.append(httpURLConnection.getHeaderField(0));
        builder.append("\n");
        //HttpURLConnection Overrides this from URLConnection
        builder.append("Header Field Key given Int: ");
        builder.append(httpURLConnection.getHeaderFieldKey(0));
        builder.append("\n");
        //HttpURLConnection Overrides this from URLConnection
        builder.append("Header Field as Date: ");
        builder.append(httpURLConnection.getHeaderFieldDate("Date", 0));
        builder.append("\n");
        //Added in HttpURLConnection
        builder.append("Response Code: ");
        builder.append(httpURLConnection.getResponseCode());
        builder.append("\n");
        builder.append("Response Message: ");
        builder.append(httpURLConnection.getResponseMessage());
        builder.append("\n");
        Log.d("HttpURLConnectionAPIs: ", " " + builder);
    }

    private void httpURLConnectionAPIsWithHttps() throws IOException {
        URL url = new URL("https://httpbin.org/headers");
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
        StringBuilder builder = new StringBuilder();
        builder.append("Response Code: ");
        builder.append(httpsURLConnection.getResponseCode());
        builder.append("\n");
        builder.append("Response Message: ");
        builder.append(httpsURLConnection.getResponseMessage());
        builder.append("\n");
        Log.d("httpURLConnectionAPIsWithHttps: ", " " + builder);
    }

    private void urlConnectionPost() throws IOException {
        URL url = new URL("http://httpbin.org/post");
        URLConnection urlConnection = url.openConnection();
        urlConnection.setDoOutput(true);

        OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
        out.write("Output test content");
        out.close();

        StringBuilder builder = new StringBuilder();
        builder.append("Input Stream: ");
        InputStream inputStream = null;
        try {
            inputStream = urlConnection.getInputStream();
        } catch(IOException exception) {
            Log.e("URLConnectionPost", "Error occured in urlConnection post ");
        }

        //Printing entire output stream, "form" contains the posted content
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while((line = reader.readLine()) != null) {
            builder.append(line);
        }
        builder.append("\n");

        Log.d("URLConnectionPost", " " + builder);
    }

    private void httpUrlConnectionPost() throws IOException {
        URL url = new URL("http://httpbin.org/post");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoOutput(true);

        OutputStreamWriter out = new OutputStreamWriter(httpURLConnection.getOutputStream());
        out.write("Output test content");
        out.close();

        StringBuilder builder = new StringBuilder();
        builder.append("Input Stream: ");
        InputStream inputOrErrorStream = null;
        try {
            inputOrErrorStream = httpURLConnection.getInputStream();
        } catch(IOException exception) {
            inputOrErrorStream = httpURLConnection.getErrorStream();
            Log.e("HttpURLConnectionPost", "Error occured in HttpURLConnection post");
        }

        //Printing entire output stream, "form" contains the posted content
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputOrErrorStream));
        String line;
        while((line = reader.readLine()) != null) {
            builder.append(line);
        }
        builder.append("\n");

        Log.d("HttpURLConnectionPost", " " + builder);
    }

    private void httpsUrlConnectionPost() throws IOException {
        URL url = new URL("https://httpbin.org/post");
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
        httpsURLConnection.setDoOutput(true);

        OutputStreamWriter out = new OutputStreamWriter(httpsURLConnection.getOutputStream());
        out.write("Output test content");
        out.close();

        StringBuilder builder = new StringBuilder();
        builder.append("Input Stream: ");
        InputStream inputOrErrorStream = null;
        try {
            inputOrErrorStream = httpsURLConnection.getInputStream();
        } catch(IOException exception) {
            inputOrErrorStream = httpsURLConnection.getErrorStream();
            Log.e("HttpURLConnectionPost", "Error occured in HttpsURLConnection post");
        }

        //Printing entire output stream, "form" contains the posted content
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputOrErrorStream));
        String line;
        while((line = reader.readLine()) != null) {
            builder.append(line);
        }
        builder.append("\n");

        Log.d("HttpsURLConnectionPost", " " + builder);
    }

    private void httpURLConnectionErrorStream() throws IOException {
        URL url = new URL("http://httpbin.org/status/400");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        StringBuilder builder = new StringBuilder();
        builder.append("Response Code: ");
        builder.append(httpURLConnection.getResponseCode());
        builder.append("\n");

        builder.append("Response Message: ");
        builder.append(httpURLConnection.getResponseMessage());
        builder.append("\n");

        builder.append("Error Stream: ");
        InputStream errorStream = httpURLConnection.getErrorStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream));
        String line;
        while((line = reader.readLine()) != null) {
            builder.append(line);
        }
        builder.append("\n");

        Log.d("HttpURLConnection400", " " + builder);
    }

    private void httpsURLConnectionErrorStream() throws IOException {
        URL url = new URL("https://httpbin.org/status/400");
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();

        StringBuilder builder = new StringBuilder();
        builder.append("Response Code: ");
        builder.append(httpsURLConnection.getResponseCode());
        builder.append("\n");

        builder.append("Response Message: ");
        builder.append(httpsURLConnection.getResponseMessage());
        builder.append("\n");

        builder.append("Error Stream: ");
        InputStream errorStream = httpsURLConnection.getErrorStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream));
        String line;
        while((line = reader.readLine()) != null) {
            builder.append(line);
        }
        builder.append("\n");

        Log.d("HttpsURLConnection400", " " + builder);
    }

    private void showToast() {
        Toast.makeText(this, "Button Clicked", Toast.LENGTH_LONG).show();//display the text of button1
    }

}