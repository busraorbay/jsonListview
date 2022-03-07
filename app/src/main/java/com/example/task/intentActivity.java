package com.example.task;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class intentActivity extends Activity {
    private TextView pk_device, mac_address,Platform, Server_Device,Server_Event,Server_Account,InternalIP,LastAliveReported;

    private ImageView imageView;

    String pkdevice = "";
    String macaddress = "";
    String platform="", serverdevice="",serverevent="",serveraccount="",internalip="",lastalivereported="", image="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);

        pk_device = findViewById(R.id.textpkdevice);
        mac_address = findViewById(R.id.textmacaddress);
        Platform=findViewById(R.id.textplatform);
        Server_Device=findViewById(R.id.Server_Device);
        Server_Event=findViewById(R.id.Server_Event);
        Server_Account=findViewById(R.id.Server_Account);
        InternalIP=findViewById(R.id.InternalIP);
        LastAliveReported=findViewById(R.id.LastAliveReported);

        imageView=findViewById(R.id.imageView1);


        Intent intent = getIntent();

        pkdevice = intent.getStringExtra("pk_device");
        macaddress = intent.getStringExtra("mac_address");
        platform=intent.getStringExtra("platform");
        serverdevice=intent.getStringExtra("Server_Device");
        serverevent=intent.getStringExtra("Server_Event");
        serveraccount=intent.getStringExtra("Server_Account");
        internalip=intent.getStringExtra("InternalIP");
        lastalivereported=intent.getStringExtra("LastAliveReported");
        image=intent.getStringExtra("image");


            pk_device.setText(pkdevice);
            mac_address.setText(macaddress);
            Platform.setText(platform);
            Server_Device.setText(serverdevice);
            Server_Event.setText(serverevent);
            Server_Account.setText(serveraccount);
            InternalIP.setText(internalip);
            LastAliveReported.setText(lastalivereported);
            imageView.setImageResource(Integer.parseInt(image));

    }
}
