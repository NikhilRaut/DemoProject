package com.nikhil.wifip2pconnection;

/**
 * Created by Nikhil on 13-01-2016.
 */

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * A service that process each file transfer request i.e Intent by opening a
 * socket connection with the WiFi Direct Group Owner and writing the file
 */
public class FileTransferService extends IntentService {

    private static final int SOCKET_TIMEOUT = 50000;
    public static final String ACTION_SEND_FILE = "jing.app.directwifi.SEND_FILE";
    public static final String EXTRAS_FILE_PATH = "file_url";
    public static final String EXTRAS_GROUP_OWNER_ADDRESS = "go_host";
    public static final String EXTRAS_GROUP_OWNER_PORT = "go_port";

    public FileTransferService(String name) {
        super(name);
    }

    public FileTransferService() {
        super("FileTransferService");
    }

    /*
     * (non-Javadoc)
     * @see android.app.IntentService#onHandleIntent(android.content.Intent)
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        String fileName = "";

        ServerSocket welcomeSocket = null;
        Socket socket = null;
        String fileUri = intent.getExtras().getString(EXTRAS_FILE_PATH);
        String host = intent.getExtras().getString(EXTRAS_GROUP_OWNER_ADDRESS);
        int port = intent.getExtras().getInt(EXTRAS_GROUP_OWNER_PORT);

        try {
            welcomeSocket = new ServerSocket(port);
            socket = welcomeSocket.accept();
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            String inputData = "";
            Log.e("Start", "About to start handshake");

            File f = new File(fileUri);

            //signalActivity("Handshake complete, getting file: " + fileName);

            String savedAs = "WDFL_File_" + System.currentTimeMillis();
            File file = new File(f, savedAs);

            byte[] buffer = new byte[4096];
            int bytesRead;

            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            while (true) {
                bytesRead = is.read(buffer, 0, buffer.length);
                if (bytesRead == -1) {
                    break;
                }
                bos.write(buffer, 0, bytesRead);
                bos.flush();

            }


            bos.close();
            socket.close();


            Log.e("", "File Transfer Complete, saved as: " + savedAs);
            //Start writing to file


        } catch (IOException e) {
            Log.e("IOException", e.getMessage());


        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
    }

}