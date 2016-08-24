package com.nikhil.youtubedownload;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Nikhil on 29-01-2016.
 */
public class YouTubeDownloadActivity extends Activity {
    Button button;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        button = (Button) findViewById(R.id.button);

        editText = (EditText) findViewById(R.id.editText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//              final  String url = "http://www.youtube.com/watch?v=1FJHYqE0RDg";


                final String url = editText.getText().toString();

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        InputStream is = null;
                        try {
                            final String videoUrl = getUrlVideoRTSP(url);
                            URL u = new URL(videoUrl);
                            is = u.openStream();
                            HttpURLConnection huc = (HttpURLConnection) u.openConnection(); //to know the size of video
                            int size = huc.getContentLength();
                            Log.e("Ddddd", "Start Success");

                            if (huc != null) {
                                String fileName = "youtube.flv";
                                String storagePath = Environment.getExternalStorageDirectory().toString();
                                File f = new File(storagePath, fileName);

                                FileOutputStream fos = new FileOutputStream(f);
                                byte[] buffer = new byte[1024];
                                int len1 = 0;
                                if (is != null) {
                                    while ((len1 = is.read(buffer)) > 0) {
                                        fos.write(buffer, 0, len1);
                                    }
                                }
                                if (fos != null) {
                                    fos.close();
                                }
                            }

                            Log.e("Ddddd", "Done Success");

                        } catch (MalformedURLException mue) {
                            mue.printStackTrace();
                            Log.e("MalformedURLException", "" + mue.toString());

                        } catch (IOException ioe) {
                            Log.e("IOException", "" + ioe.toString());
                            ioe.printStackTrace();
                        } finally {
                            Log.e("finally", "finally");
                            try {
                                if (is != null) {
                                    is.close();
                                }
                            } catch (IOException ioe) {
                                // just going to ignore this one
                            }
                        }
                    }
                });
            }
        });


    }


    public static String getUrlVideoRTSP(String urlYoutube) {
        try {
            String gdy = "http://gdata.youtube.com/feeds/api/videos/";
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String id = extractYoutubeId(urlYoutube);
            URL url = new URL(gdy + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            Document doc = documentBuilder.parse(connection.getInputStream());
            Element el = doc.getDocumentElement();
            NodeList list = el.getElementsByTagName("media:content");///media:content
            String cursor = urlYoutube;
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node != null) {
                    NamedNodeMap nodeMap = node.getAttributes();
                    HashMap<String, String> maps = new HashMap<String, String>();
                    for (int j = 0; j < nodeMap.getLength(); j++) {
                        Attr att = (Attr) nodeMap.item(j);
                        maps.put(att.getName(), att.getValue());
                    }
                    if (maps.containsKey("yt:format")) {
                        String f = maps.get("yt:format");
                        if (maps.containsKey("url")) {
                            cursor = maps.get("url");
                        }
                        if (f.equals("1"))
                            return cursor;
                    }
                }
            }
            return cursor;
        } catch (Exception ex) {
            Log.e("Get Url Video RTSP Exception======>>", ex.toString());
        }
        return urlYoutube;

    }

    protected static String extractYoutubeId(String url) throws MalformedURLException {
        String id = null;
        try {
            String query = new URL(url).getQuery();
            if (query != null) {
                String[] param = query.split("&");
                for (String row : param) {
                    String[] param1 = row.split("=");
                    if (param1[0].equals("v")) {
                        id = param1[1];
                    }
                }
            } else {
                if (url.contains("embed")) {
                    id = url.substring(url.lastIndexOf("/") + 1);
                }
            }
        } catch (Exception ex) {
            Log.e("Exception", ex.toString());
        }
        return id;
    }

}
