package com.thomasfriberg.autohome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
//import android.widget.Toast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Dashboard extends AppCompatActivity {

    String udpString=null;
    int sb1val;
    int sb2val;
    int sb3val;
    String serverIP;
    Integer serverPort;
    String but1desc;
    String but2desc;
    String but3desc;
    String but4desc;
    String but5desc;
    String but1ID;
    String but2ID;
    String but3ID;
    String but4ID;
    String but5ID;


    String but0ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Load the layout
        setContentView(R.layout.activity_dashboard);

        final Button bt0 = (Button) findViewById(R.id.button0);
        final Button bt1 = (Button) findViewById(R.id.button1);
        final Button bt2 = (Button) findViewById(R.id.button2);
        final Button bt3 = (Button) findViewById(R.id.button3);
        final Button bt4 = (Button) findViewById(R.id.button4);
        final Button bt4a = (Button) findViewById(R.id.button4a);
        final Button bt5 = (Button) findViewById(R.id.button5);

        final TextView tx1 = (TextView) findViewById(R.id.textView1);
        final TextView tx2 = (TextView) findViewById(R.id.textView2);
        final TextView tx3 = (TextView) findViewById(R.id.textView3);
        final TextView tx4 = (TextView) findViewById(R.id.textView4);
        //final TextView tx5 = (TextView) findViewById(R.id.textView5);
        final TextView tx6 = (TextView) findViewById(R.id.statusOut);

        final SeekBar sb1 = (SeekBar) findViewById(R.id.seekBar1);
        final SeekBar sb2 = (SeekBar) findViewById(R.id.seekBar2);
        final SeekBar sb3 = (SeekBar) findViewById(R.id.seekBar3);

        //Set memory variables
        loadPreferences();
        tx1.setText(but1desc);
        tx2.setText(but2desc);
        tx3.setText(but3desc);
        tx4.setText(but4desc);

        //Short click to update the values for the weather report
        bt0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                udpString = "REG," + but0ID + ",TF Mobile";
                new udpSendMessage().execute(udpString);
                udpString = "LOG," + but0ID + ",Weather status request";
                new udpEchoMessage().execute(udpString);
                tx6.setText(udpString);
            }
        });

        //Short click to yield action
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                udpString = "FWD," + but1ID + ",toggle";
                new udpSendMessage().execute(udpString);
                tx6.setText(udpString);
            }
        });

        //Long click to take action, true returned to avoid 'reclick' to normal value
        bt1.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                udpString = "FWD," + but1ID + ",off";
                new udpSendMessage().execute(udpString);
                tx6.setText(udpString);
                return true;
            }
        });

        //Seekbar
        sb1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
                udpString = "FWD,"+but1ID+","+String.valueOf(sb1val);
                new udpSendMessage().execute(udpString);
                tx6.setText(udpString);
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
                //No requirement here, leave blank
            }
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sb1val = progress;
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                udpString = "FWD," + but2ID + ",toggle";
                //loadPreferences();
                new udpSendMessage().execute(udpString);
                tx6.setText(udpString);

            }
        });

        //Long click to take action, true returned to avoid 'reclick' to normal value
        bt2.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                udpString = "FWD," + but2ID + ",off";
                new udpSendMessage().execute(udpString);
                tx6.setText(udpString);
                return true;
            }
        });

        //Seekbar
        sb2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
                udpString = "FWD," + but2ID + "," + String.valueOf(sb2val);
                new udpSendMessage().execute(udpString);
                tx6.setText(udpString);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                //No requirement here, leave blank
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sb2val = progress;
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                udpString = "FWD," + but3ID + ",toggle";
                //loadPreferences();
                new udpSendMessage().execute(udpString);
                tx6.setText(udpString);

            }
        });

        //Long click to take action, true returned to avoid 'reclick' to normal value
        bt3.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                udpString = "FWD," + but3ID + ",off";
                new udpSendMessage().execute(udpString);
                tx6.setText(udpString);
                return true;
            }
        });

        //Seekbar
        sb3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
                udpString = "FWD," + but3ID + "," + String.valueOf(sb3val);
                new udpSendMessage().execute(udpString);
                tx6.setText(udpString);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                //No requirement here, leave blank
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sb3val = progress;
            }
        });

        //Long click to take action, true returned to avoid 'reclick' to normal value
        bt4.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                udpString = "FWD," + but4ID + ",instant on";
                new udpSendMessage().execute(udpString);
                tx6.setText(udpString);
                return true;
            }
        });
        //Long click to take action, true returned to avoid 'reclick' to normal value
        bt4a.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                udpString = "FWD,"+but4ID+",instant off";
                new udpSendMessage().execute(udpString);
                tx6.setText(udpString);
                return true;
            }
        });

        //Long click to take action, true returned to avoid 'reclick' to normal value
        bt5.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                udpString = "LOG," + but5ID + ",all off";
                new udpSendMessage().execute(udpString);
                tx6.setText(udpString);
                return true;
            }
        });

    }

    @Override
    public  boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.settings_id:


                Intent intent = new Intent(this, Preferences.class);
                startActivity(intent);
                break;
            case R.id.about_us_id:

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadPreferences(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        serverIP = sharedPreferences.getString("server_IP", getString(R.string.defaultServerIP));
        String tempPort = sharedPreferences.getString("server_Port", getString(R.string.defaultServerPort));
        serverPort = Integer.parseInt(tempPort);
        but1desc = sharedPreferences.getString("desc_1", getString(R.string.defaultButton1Desc));
        but2desc = sharedPreferences.getString("desc_2", getString(R.string.defaultButton2Desc));
        but3desc = sharedPreferences.getString("desc_3", getString(R.string.defaultButton3Desc));
        but4desc = sharedPreferences.getString("desc_4", getString(R.string.defaultButton4Desc));
        but5desc = sharedPreferences.getString("desc_5", getString(R.string.defaultButton5Desc));
        but0ID = sharedPreferences.getString("cmd_0", getString(R.string.defaultButton0ID));
        but1ID = sharedPreferences.getString("cmd_1", getString(R.string.defaultButton1ID));
        but2ID = sharedPreferences.getString("cmd_2", getString(R.string.defaultButton2ID));
        but3ID = sharedPreferences.getString("cmd_3", getString(R.string.defaultButton3ID));
        but4ID = sharedPreferences.getString("cmd_4", getString(R.string.defaultButton4ID));
        but5ID = sharedPreferences.getString("cmd_5", getString(R.string.defaultButton5ID));
    }

    //This method is to update the values of the temperatures remotely
    public void updateTemperatures(String time, String temp1, String temp2, String hum1,String hum2) {
        final TextView tx6 = (TextView) findViewById(R.id.textView6);

        tx6.setText("Living room: Temperature of "+temp1+"°C at "+hum1+"% humidity.\nOutdoor: Temperature of " + temp2+"°C at "+hum2+"% humidity.\nLast update at: " + time);
    }

    private class udpSendMessage extends AsyncTask<String,Void,Void> {
        protected Void doInBackground(String... param) {
            byte[] send_data;

            String udpMsg=param[0];

            try{

                DatagramSocket s = new DatagramSocket();

                InetAddress localIP = InetAddress.getByName(serverIP);

                //Set up the string to be sent
                send_data=udpMsg.getBytes();

                //Open the socket and send
                DatagramPacket send_packet = new DatagramPacket(send_data,udpMsg.length(), localIP, serverPort);
                s.send(send_packet);
                Log.d("UDPClass", "Sent the following packet: " + udpMsg);


            }
            catch (Exception e) {
                Log.d("UDPClass", "UDP catch error triggered");
                e.printStackTrace();
            }
            return null;
        }

    }

    class udpEchoMessage extends AsyncTask<String,Void,String> {
        protected String doInBackground(String... param) {
            byte[] send_data;
            byte[] buffer = new byte[1024];
            String udpMsg=param[0];
            String udpReceive=null;

            try{

                DatagramSocket s = new DatagramSocket(serverPort);
                InetAddress localIP = InetAddress.getByName(serverIP);

                //Set up the string to be sent
                send_data=udpMsg.getBytes();

                //Open the socket and send
                DatagramPacket send_packet = new DatagramPacket(send_data,udpMsg.length(), localIP, serverPort);
                s.send(send_packet);
                Log.d("UDPClass2", "Sent the following packet: " + udpMsg);
                DatagramPacket receive_packet = new DatagramPacket(buffer, buffer.length);
                s.receive(receive_packet);
                s.close();
                buffer=receive_packet.getData();
                Log.d("UDPClass2", "Received: "+new String(buffer,0,receive_packet.getLength()));
                udpReceive=new String(buffer,0,receive_packet.getLength());
            }
            catch (Exception e) {
                Log.d("UDPClass2", "UDP catch error triggered");
                e.printStackTrace();
            }
            return udpReceive;
        }

        /* Not required
        protected void onProgressUpdate(String... progress) {
            Log.d("UDPClass", "In progress");
        }
        */
        protected void onPostExecute(String result) {
            String[] values = result.split(",");
            updateTemperatures(values[0],values[1],values[2],values[3],values[4]);
        }

    }
}
