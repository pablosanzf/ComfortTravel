package pablosanzf.comforttravel.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import pablosanzf.comforttravel.R;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        int widgetId =0;
        for(int i = 0; i < appWidgetIds.length; i++) {
             widgetId = appWidgetIds[i];
        }

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.iberia.com/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widgetImage, pendingIntent);

        appWidgetManager.updateAppWidget(widgetId, views);

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

