package com.bfc.android_navigation_java;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class DeepLinkFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = view.findViewById(R.id.text);
        if (getArguments() != null) {
            textView.setText(getArguments().getString("myarg"));
        }

        Button notificationButton = view.findViewById(R.id.send_notification_button);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editArgs = view.findViewById(R.id.args_edit_text);
                Bundle args = new Bundle();
                args.putString("myarg", editArgs.getText().toString());

                PendingIntent deeplink = findNavController(DeepLinkFragment.this).createDeepLink()
                        .setDestination(R.id.deeplink_dest)
                        .setArguments(args)
                        .createPendingIntent();

                if (getContext() == null) return;

                NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

                if (notificationManager == null) return;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notificationManager.createNotificationChannel(new NotificationChannel("deeplink", "Deep Links", NotificationManager.IMPORTANCE_HIGH));
                }

                NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "deeplink")
                        .setContentTitle("Navigation")
                        .setContentText("Deep link to Android")
                        .setSmallIcon(R.drawable.ic_android)
                        .setContentIntent(deeplink)
                        .setAutoCancel(true);

                notificationManager.notify(0, builder.build());
            }
        });
    }
}
