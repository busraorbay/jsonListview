package com.example.task;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;


public class MainActivity extends Activity {
    Intent intent;
    private ListView listView;
    private EditText edit;

 
    String PK_Device, MacAddress, PK_DeviceType, PK_DeviceSubType, Firmware, Server_Device, Server_Event,
            Server_Account, InternalIP, LastAliveReported, Platform;


    private static String JSON_url = "https://veramobile.mios.com/test_android/items.test";

    ArrayList<HashMap<String, String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();
        listView = findViewById(R.id.listView);


        GetData getData=new GetData();
        getData.execute();

    }

    public class GetData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";

            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(JSON_url);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream in = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(in);

                    int data = isr.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isr.read();


                    }
                    return current;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;
        }

        @Override
        public void onPostExecute(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("Devices");


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                    PK_Device= jsonObject1.getString("PK_Device").toLowerCase(Locale.getDefault());
                    Platform=jsonObject1.getString("Platform");
                    MacAddress=jsonObject1.getString("MacAddress");
                    Server_Device=jsonObject1.getString("Server_Device");
                    Firmware=jsonObject1.getString("Firmware");
                    Server_Event=jsonObject1.getString("Server_Event");
                    Server_Account=jsonObject1.getString("Server_Account");
                    InternalIP=jsonObject1.getString("InternalIP");
                    LastAliveReported=jsonObject1.getString("LastAliveReported");

                    HashMap<String, String> lists = new HashMap<>();


              int edge=R.drawable.vera_edge_big;
              int plus=R.drawable.vera_plus_big;
              int secure=R.drawable.vera_secure_big;

                    //Verilerin listeye eklenmesi
                    lists.put("PK_Device", PK_Device);
                    lists.put("Platform",Platform);
                    lists.put("MacAddress",MacAddress);
                    lists.put("Server_Device",Server_Device);
                    lists.put("Server_Event",Server_Event);
                    lists.put("Firmware",Firmware);
                    lists.put("Server_Account",Server_Account);
                    lists.put("InternalIP",InternalIP);
                    lists.put("LastAliveReported",LastAliveReported);

                    if(Platform.equals("Sercomm NA301")&& Platform.equals(("MiCasaVerde VeraLite"))&&Platform.equals(("Sercomm NA900")) &&Platform.equals(("Sercomm NA930"))) {
                        lists.put("image", Integer.toString(edge));
                    }
                    else if(Platform.equals("Sercomm G450")) {
                        lists.put("image", Integer.toString(plus));

                    }else if(Platform.equals("Sercomm G550")){
                        lists.put("image", Integer.toString(secure));
                    }
                    else{
                        lists.put("image",Integer.toString(edge));
                    }

                    list.add(lists);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }



            SimpleAdapter adapter = new SimpleAdapter(
                    MainActivity.this, list, R.layout.row_layout,
                    new String[]{"PK_Device", "Platform","image"},
                    new int[]{R.id.textView, R.id.platform,R.id.imageView});
            listView.setAdapter(adapter);

            //uzun tıklandığında yapılacak silme ve iptal işlemi
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               int pos, long id) {

                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Are you Sure?")

                            //İstenilen öğenin listeden silinmesi için yapılan işlemler
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    list.remove(pos);

                                    adapter.notifyDataSetChanged();

                                }
                            })
                            //Kullanıcı iptale tıkladığında ise aynı liste ile karşılaşmaktadır.
                            .setNegativeButton("Cancel", null)
                            .show();
                    return true;
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    HashMap<String, String> a = list.get(position);

                    //Her bir öğeye tıklandığında o öğenin bütün bilgilerinin intent ile başka bir sayfaya gönderilmesi
                    intent = new Intent(getApplicationContext(), intentActivity.class);
                    intent.putExtra("pk_device", a.get("PK_Device"));
                    intent.putExtra("platform", a.get("Platform"));
                    intent.putExtra("mac_address", a.get("MacAddress"));
                    intent.putExtra("Server_Device", a.get("Server_Device"));
                    intent.putExtra("Server_Event", a.get("Server_Event"));
                    intent.putExtra("Firmware",a.get("Firmware"));
                    intent.putExtra("Server_Account", a.get("Server_Account"));
                    intent.putExtra("InternalIP", a.get("InternalIP"));
                    intent.putExtra("LastAliveReported", a.get("LastAliveReported"));
                        intent.putExtra("image", a.get("image"));


                        startActivity(intent);

                }
            });

        }



    }


}
