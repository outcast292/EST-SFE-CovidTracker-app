package com.example.covidtracker.ui.notifications;

import android.content.Context;
import android.graphics.Color;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NotificationsViewModel extends RelativeLayout {

        private TextView someText;

        public NotificationsViewModel(Context context) {
            super(context);

            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.addRule(CENTER_IN_PARENT);
            someText = new TextView(context);
            someText.setTextSize(20);
            someText.setTextColor(Color.BLACK);
            someText.setLayoutParams(params);
            addView(someText);
        }

        /**
         * Remember, your View is an regular object like any other.
         * You can add whatever methods you want and expose it to the world!
         * We have the method take a "MyObject" and do things to the View
         * based on it.
         */

        public void configure(NotificationModel object) {

            someText.setText(object.title);

        }
    }