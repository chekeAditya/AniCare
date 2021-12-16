package com.application.anicaremals.ui.fragments.premium;

import static com.application.anicaremals.util.CONSTANTS.CHANNEL_ID;
import static com.application.anicaremals.util.CONSTANTS.REQUEST_CODE;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.application.anicaremals.R;
import com.application.anicaremals.localResponse.Sms;
import com.application.anicaremals.viewmodels.ApplicationViewModels;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PremiumBaseFragment extends Fragment {

    int numberNotification = 0;
    ArrayList<BarEntry> barEntries = new ArrayList<>();
    ArrayList<PieEntry> pieEntries = new ArrayList<>();
    ApplicationViewModels viewModels;
    List<Sms> list = Collections.emptyList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_premium_base, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModels = new ViewModelProvider(this).get(ApplicationViewModels.class);

        //notification channel created
        createNotificationChannel();
        //initViews
        BarChart barChart = view.findViewById(R.id.barChart);
        PieChart pieChart = view.findViewById(R.id.pieChart);
        //onclickListener
        barChart.setOnClickListener(v -> {
            fetchSMSDetails();
        });
        pieChart.setOnClickListener(v -> {
            fetchSMSDetails();
        });


        fetchSMSDetails();

        //settingUp the layout
        settingUpBarChart(barChart);
        settingUpPieChart(pieChart);

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Temperature Notification", "Sensor Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private void fetchSMSDetails() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_SMS}, REQUEST_CODE);
        }

        ContentResolver resolver = requireActivity().getContentResolver();
        Uri allMessages = Uri.parse("content://sms/");
        String[] projection = null;
        String selection = null;
        String[] selectionArgs = null;
        String order = null;

        Cursor cursor = this.getActivity().getContentResolver().query(allMessages, projection, selection, selectionArgs, order);
        if (cursor.getCount() > 0) {
            int i = 0;
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS));
                if (name != null) {
                    if (name.contains("57575791")) {
                        String details = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY));
                        String number = details.substring(details.length() - 4);
                        if (i == 0) {
                            numberNotification = Integer.parseInt(number.trim());
                        }
                        pieEntries.add(new PieEntry(cursor.getCount(), number));
                        barEntries.add(new BarEntry(i, Float.parseFloat(number)));
                        i++;
                    }
                }
            }
            if (numberNotification >= 180) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), CHANNEL_ID);
                builder.setSmallIcon(R.drawable.ic_launcher_foreground);
                builder.setContentTitle("Current Temperature High i.e: " + numberNotification);
                builder.setContentText("Temperature goes above average temperature check precautions else contact expert");
                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(requireContext());
                managerCompat.notify(1, builder.build());

            } else if (numberNotification <= 140) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), CHANNEL_ID);
                builder.setSmallIcon(R.drawable.ic_launcher_foreground);
                builder.setContentTitle("Current Temperature High i.e: " + numberNotification);
                builder.setContentText("Temperature goes below average temperature check precautions else contact expert");
                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(requireContext());
                managerCompat.notify(1, builder.build());
            }
        }
    }


    private void settingUpPieChart(PieChart pieChart) {
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.GREEN);
        pieDataSet.setValueTextSize(8f);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Temperature");
        pieChart.animate();
    }

    private void settingUpBarChart(BarChart barChart) {

        BarDataSet barDataSet = new BarDataSet(barEntries, "");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.GREEN);
        barDataSet.setValueTextSize(8f);

        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Temperature...");
        barChart.animateY(2000);
    }
}
