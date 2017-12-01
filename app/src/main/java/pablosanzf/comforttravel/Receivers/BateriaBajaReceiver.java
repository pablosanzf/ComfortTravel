package pablosanzf.comforttravel.Receivers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import pablosanzf.comforttravel.R;

/**
 * Created by master on 01/12/2017.
 */

public class BateriaBajaReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        // First, create the notification
        Notification.Builder nBuilder =
                new Notification.Builder(context)
                        .setSmallIcon(R.mipmap.ic_new_launcher)
                        .setContentTitle("Batería baja")
                        .setContentText("Por favor, seleccione el perfil de seguridad dentro de ComfortTravel");
//                        .setChannelId(NotificationChannel.DEFAULT_CHANNEL_ID);
        // Second, display the notification
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // For Oreo versions and up a Notification Channels must be defined
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("channel1", "Alert", NotificationManager.IMPORTANCE_LOW);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(notificationChannel);
            nBuilder.setChannelId("channel1");
            Log.i("","Here");
        }
        Notification noti = nBuilder.build();

        mNotificationManager.notify(0, noti);

    }
}
