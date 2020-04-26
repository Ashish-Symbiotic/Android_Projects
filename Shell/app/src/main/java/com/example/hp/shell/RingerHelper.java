package com.example.hp.shell;

import android.media.AudioManager;

/**
 * Created by HP on 15-Feb-18.
 */

public class RingerHelper  {
    private RingerHelper(){}
    public static void performToogle(AudioManager audioManager){

        audioManager.setRingerMode(
                isPhoneSilent(audioManager)
                ?AudioManager.RINGER_MODE_NORMAL
                        :AudioManager.RINGER_MODE_SILENT

        );
    }
    public static boolean isPhoneSilent(AudioManager audioManager){
        return  audioManager.getRingerMode()==AudioManager.RINGER_MODE_SILENT;
    }
}
