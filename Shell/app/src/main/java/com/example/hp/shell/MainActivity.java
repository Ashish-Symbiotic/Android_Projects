package com.example.hp.shell;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.Image;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    public static int a=0;
    public static int h=0;
    AudioManager audioManager;
    BluetoothAdapter bluetoothAdapter;
    ImageView image,Screen;
     WifiManager wifiManager;
    LinearLayout L1;
    private static final int REQUEST_ENABLE_BT=1;
    BitmapDrawable bitmapDrawable;
    Bitmap bm;
    FrameLayout airplane,bluetooth,silent,hotspot,music,video,screenshot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !notificationManager.isNotificationPolicyAccessGranted()) {

            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

            startActivity(intent);
        }
        final BluetoothManager bluetoothManager = (BluetoothManager) this.getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
         wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        L1=(LinearLayout)findViewById(R.id.linear1);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);//done
        image=(ImageView)findViewById(R.id.Screen);//done
        bluetooth = (FrameLayout) findViewById(R.id.bluetooth_frame);//done
        hotspot = (FrameLayout) findViewById(R.id.hotspot_frame);
        music = (FrameLayout) findViewById(R.id.music_frame);//done
        video = (FrameLayout) findViewById(R.id.video_player_frame);//done
        silent = (FrameLayout) findViewById(R.id.silent_frame);//done
        screenshot = (FrameLayout) findViewById(R.id.screenShot_frame);//done
        bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bluetoothAdapter==null)
                {
                    Toast.makeText(MainActivity.this, "Bluetooth not supported", Toast.LENGTH_SHORT).show();
                }
                else {
                    String name=bluetoothAdapter.getName();
                    Toast.makeText(MainActivity.this, "Bluetooth name is "+name, Toast.LENGTH_SHORT).show();
                    if (bluetoothAdapter.isEnabled()) {
                        a=0;
                        bluetoothAdapter.disable();
                        updateBluetoothUI();
                    } else {
                        a=1;
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                        updateBluetoothUI();

                    }
                }
            }
        });

        hotspot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wifiManager.isWifiEnabled())
                {
                    h=0;
                    wifiManager.setWifiEnabled(false);
                    updateWifiUi();


                }
                else if (!wifiManager.isWifiEnabled())
                {
                    h=1;
                    wifiManager.setWifiEnabled(true);

                    updateWifiUi();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
silent.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        RingerHelper.performToogle(audioManager);
        updateUI();
    }
});
music.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
});
video.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent vieo=new Intent().setAction(Intent.ACTION_VIEW);
        Toast.makeText(MainActivity.this, "Video Player Launched", Toast.LENGTH_SHORT).show();
        vieo.setType("video/mp4");
        vieo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(vieo);
    }
});
screenshot.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        View v1 = L1.getRootView();
        v1.setDrawingCacheEnabled(true);
        bm = v1.getDrawingCache();
        bitmapDrawable = new BitmapDrawable(bm);
        image.setBackground(bitmapDrawable);
    }
});

    }
private void updateBluetoothUI(){
        ImageView bluetoothImage=(ImageView)findViewById(R.id.bluetooth);
        int bluetoothI;
        if(a==0)
        {
            bluetoothI=R.drawable.bluetooth_off;
        }
        else
        {
            bluetoothI=R.drawable.bluetooth_on;

        }
        bluetoothImage.setImageResource(bluetoothI);
}
private void updateUI(){
    ImageView silentImage=(ImageView)findViewById(R.id.silent);
    int phoneImage=RingerHelper.isPhoneSilent(audioManager)
            ? R.drawable.silent_on
            :R.drawable.silent_off;
    silentImage.setImageResource(phoneImage);
    }
private void updateWifiUi()
{
    ImageView hotpot=(ImageView)findViewById(R.id.hotspot);
    int hotspot2=0;
    if(h==0){

        Toast.makeText(this, "Wifi Disabled", Toast.LENGTH_SHORT).show();
        hotspot2=R.drawable.hotspot_off;
    }
    else
    {

        Toast.makeText(this, "Wifi Enabled", Toast.LENGTH_SHORT).show();
        hotspot2=R.drawable.hotspot_on;
h=0;
    }
    hotpot.setImageResource(hotspot2);
}
    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
        updateBluetoothUI();
    }




}


