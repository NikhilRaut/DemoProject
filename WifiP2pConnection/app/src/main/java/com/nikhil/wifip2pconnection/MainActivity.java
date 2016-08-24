package com.nikhil.wifip2pconnection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, android.content.DialogInterface.OnClickListener, ConnectionInfoListener {

    private WifiP2pManager mManager;
    private Channel mChannel;
    private BroadcastReceiver mReceiver;
    private IntentFilter mIntentFilter;

    private Button mDiscover;
    private TextView mDevices;
    public ArrayAdapter mAdapter;
    private ArrayList<WifiP2pDevice> mDeviceList = new ArrayList<WifiP2pDevice>();
    protected static final int CHOOSE_FILE_RESULT_CODE = 20;
    int flag = 0;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDiscover = (Button) findViewById(R.id.discover);
        mDiscover.setOnClickListener(this);

        mDevices = (TextView) findViewById(R.id.peers);

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        mReceiver = new WiFiDirectReceiver(mManager, mChannel, this);

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);


    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregisterReceiver(mReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private class WiFiDirectReceiver extends BroadcastReceiver {

        private WifiP2pManager mManager;
        private Channel mChannel;
        private MainActivity mActivity;

        public WiFiDirectReceiver(WifiP2pManager manager, Channel channel, MainActivity activity) {
            super();
            mManager = manager;
            mChannel = channel;
            mActivity = activity;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {

                int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
                if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                    String title = "ANDROID_ID[" + getAndroid_ID() + "]";
                    title += "   MAC[" + getMACAddress() + "]";
                    Toast.makeText(mActivity, "Wi-Fi Direct is enabled." + title, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mActivity, "Wi-Fi Direct is disabled.", Toast.LENGTH_SHORT).show();
                }

            } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

                if (mManager != null) {
                    mManager.requestPeers(mChannel, new PeerListListener() {

                        @Override
                        public void onPeersAvailable(WifiP2pDeviceList peers) {
                            if (peers != null) {
                                mDeviceList.addAll(peers.getDeviceList());
                                ArrayList<String> deviceNames = new ArrayList<String>();
                                for (WifiP2pDevice device : mDeviceList) {
                                    deviceNames.add(device.deviceName);
                                }
                                if (deviceNames.size() > 0) {
                                    mAdapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_list_item_1, deviceNames);
                                    if (flag == 0) {
                                        flag = 1;
                                        showDeviceListDialog();
                                    }
                                } else {
                                    Toast.makeText(mActivity, "Device list is empty.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }

            } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {


            } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {

            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.discover:
                onDiscover();
                break;
        }
    }

    private void onDiscover() {
        mManager.discoverPeers(mChannel, new ActionListener() {

            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "Discover peers successfully.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int reason) {
                Toast.makeText(MainActivity.this, "Discover peers failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDeviceListDialog() {
        DeviceListDialog deviceListDialog = new DeviceListDialog();
        deviceListDialog.show(getFragmentManager(), "devices");
    }

    private class DeviceListDialog extends DialogFragment {


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Select a device")
                    .setSingleChoiceItems(mAdapter, 0, MainActivity.this)
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }

                    });

            return builder.create();
        }

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        onDeviceSelected(which);
        dialog.dismiss();
    }

    private void onDeviceSelected(int which) {
        WifiP2pDevice device = mDeviceList.get(which);
        if (device == null) {
            return;
        }

        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;

        Log.e("config.deviceAddress", "Address -= -" + config.deviceAddress.toString());
        mManager.connect(mChannel, config, new ActionListener() {

            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, CHOOSE_FILE_RESULT_CODE);
            }

            @Override
            public void onFailure(int reason) {
                Toast.makeText(MainActivity.this, "Failed to connect", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * ANDROID_ID
     */
    private String getAndroid_ID() {
        return Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * Wi-Fi MAC
     */
    private String getMACAddress() {
        WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = manager.getConnectionInfo();
        String mac = wifiInfo.getMacAddress();


        try {
           /* List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());

            for (NetworkInterface networkInterface : interfaces) {
                Log.v("", "interface name " + networkInterface.getName() + "mac = " + getMACAddresss(networkInterface.getName()));
            }

            WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
            String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
            for (NetworkInterface intf : interfaces) {
                if (!getMACAddresss(intf.getName()).equalsIgnoreCase(ip)) {
                    Log.e("", "ignore the interface " + intf.getName());
                    continue;
                }
                if (!intf.getName().contains("p2p"))
                    continue;

                Log.e("Address -- ", intf.getName() + "   " + getMACAddresss(intf.getName()));

                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());

                for (InetAddress addr : addrs) {
                    // Log.v(TAG, "inside");

                    Log.e("InetAddress", "addrs --" + addr.toString());
//                    if (!addr.isLoopbackAddress()) {
                    // Log.v(TAG, "isnt loopback");
                    String sAddr = addr.getHostAddress().toUpperCase();
                    Log.e("", "ip=" + sAddr);

//                        boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                    boolean isIPv4 = true;
                    return sAddr;

//                        if (isIPv4) {
//                            if (sAddr.contains("192.168.49.")) {
//                                Log.v("", "ip = " + sAddr);
//                                return sAddr;
//                            }
//                        }

//                    }

                }
            }

//            new FileServerAsyncTask(getApplicationContext()).execute();

//            return "38:2c:4a:2d:b9:f6";*/

            Log.e("", "ip address 192.168.0.93");
            return "192.168.0.93";
        } catch (Exception ex) {
            Log.e("", "error in parsing " + ex.toString());
        } // for now eat exceptions
        Log.e("", "returning empty ip address");
        return "38:2c:4a:2d:b9:f6";
        // After the group negotiation, we assign the group owner as the file
        // server. The file server is single threaded, single connection server
        // socket.


    }

    public static String getMACAddresss(String interfaceName) {
        try {
            List<NetworkInterface> interfaces = Collections
                    .list(NetworkInterface.getNetworkInterfaces());

            for (NetworkInterface intf : interfaces) {
                if (interfaceName != null) {
                    if (!intf.getName().equalsIgnoreCase(interfaceName))
                        continue;
                }
                byte[] mac = intf.getHardwareAddress();
                if (mac == null)
                    return "";
                StringBuilder buf = new StringBuilder();
                for (int idx = 0; idx < mac.length; idx++)
                    buf.append(String.format("%02X:", mac[idx]));
                if (buf.length() > 0)
                    buf.deleteCharAt(buf.length() - 1);
                return buf.toString();
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
        /*
         * try { // this is so Linux hack return
         * loadFileAsString("/sys/class/net/" +interfaceName +
         * "/address").toUpperCase().trim(); } catch (IOException ex) { return
         * null; }
         */
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // User has picked an image. Transfer it to group owner i.e peer using
        // FileTransferService.
        uri = data.getData();

        Log.d("intent", "Intent----------- " + uri);
        Intent serviceIntent = new Intent(MainActivity.this, FileTransferService.class);
        serviceIntent.setAction(FileTransferService.ACTION_SEND_FILE);
        serviceIntent.putExtra(FileTransferService.EXTRAS_FILE_PATH, uri.toString());
        serviceIntent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_ADDRESS, getMACAddress());
        serviceIntent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_PORT, 8980);
        startService(serviceIntent);

//        new FileServerAsyncTask(this).execute();
    }

    /**
     * A simple server socket that accepts connection and writes some data on
     * the stream.
     */
    public static class FileServerAsyncTask extends AsyncTask<Void, Void, String> {

        private Context context;


        public FileServerAsyncTask(Context context) {
            this.context = context;

        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                System.out.println("insideeeeeeeeeeeeeeeeeeeeeeee");
                Log.e("Server: Socket opened", "Server: Socket opened");
                ServerSocket serverSocket = new ServerSocket(8980);

                Socket client = serverSocket.accept();
                Log.e("Server: connection done", "Server: connection done");
                final File f = new File(Environment.getExternalStorageDirectory() + "/"
                        + context.getPackageName() + "/wifip2pshared-" + System.currentTimeMillis()
                        + ".jpg");

                File dirs = new File(f.getParent());
                if (!dirs.exists())
                    dirs.mkdirs();

                f.createNewFile();

                Log.e("server: copying files ", "server: copying files" + f.toString());
                InputStream inputstream = client.getInputStream();
                copyFile(inputstream, new FileOutputStream(f));
                serverSocket.close();
                return f.getAbsolutePath();
            } catch (IOException e) {
                Log.e("expFileServerAsyncTask", e.getMessage());
                System.out.println(":iooo:" + e);
                return null;
            }
        }

        /*
         * (non-Javadoc)
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(String result) {
            if (result != null) {

                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("file://" + result), "image/*");
                context.startActivity(intent);
            }

        }

        /*
         * (non-Javadoc)
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {

        }

    }

    public static boolean copyFile(InputStream inputStream, OutputStream out) {
        byte buf[] = new byte[1024];
        int len;
        long startTime = System.currentTimeMillis();

        try {
            while ((len = inputStream.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            out.close();
            inputStream.close();
            long endTime = System.currentTimeMillis() - startTime;
            Log.e("copyFile", "Time taken to transfer all bytes is : " + endTime);

        } catch (IOException e) {
            Log.e("exp-copyFile", e.toString());
            return false;
        }
        return true;
    }

    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo info) {
        // TODO Auto-generated method stub

        Toast.makeText(getApplicationContext(), "connectioninfoo", Toast.LENGTH_LONG).show();

    }

}