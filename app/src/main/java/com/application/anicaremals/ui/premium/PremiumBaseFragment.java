package com.application.anicaremals.ui.premium;

import static android.app.Activity.RESULT_OK;
import static com.application.anicaremals.util.CONSTANTS.REQUEST_CODE;
import static com.application.anicaremals.util.CONSTANTS.REQ_USER_CONSENT;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.application.anicaremals.R;
import com.application.anicaremals.remote.response.Sms;
import com.application.anicaremals.ui.premium.broadcastReceiver.SmsBroadcastReceiver;
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
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PremiumBaseFragment extends Fragment {


    ArrayList<BarEntry> barEntries = new ArrayList<>();
    ArrayList<PieEntry> pieEntries = new ArrayList<>();
    ApplicationViewModels viewModels;
    List<Sms> list =  Collections.emptyList();

    //BroadCast Receiver
    SmsBroadcastReceiver smsBroadcastReceiver;


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
        createNodtificationChannel();
        //initViews
        BarChart barChart = view.findViewById(R.id.barChart);
        PieChart pieChart = view.findViewById(R.id.pieChart);

        //settingup viewModel to get live update
//        viewModels.getSms().observe(getViewLifecycleOwner(), new Observer<List<? extends Sms>>() {
//            @Override
//            public void onChanged(List<? extends Sms> sms) {
//
//            }
//        });


        getSmsTemperature();
        fetchSMSDetails();

        //settingUp the layout
        settingUpBarChart(barChart);
        settingUpPieChart(pieChart);

    }

    private void createNodtificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Temperature Notification", "Sensor Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private void getSmsTemperature() {
        SmsRetrieverClient client = SmsRetriever.getClient(requireContext());
        client.startSmsUserConsent(null);
    }

    private void registerBroadcastReceiver() {

        smsBroadcastReceiver = new SmsBroadcastReceiver();

        smsBroadcastReceiver.smsBroadcastReceiverListener = new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
            @Override
            public void onSuccess(Intent intent) {

                startActivityForResult(intent, REQ_USER_CONSENT);

            }

            @Override
            public void onFailure() {

            }
        };
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        getContext().registerReceiver(smsBroadcastReceiver, intentFilter);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_USER_CONSENT) {
            if ((resultCode == RESULT_OK) && (data != null)) {
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                getTemperature(message);
            }
        }
    }

    private void getTemperature(String message) {
        Pattern temperaturePattern = Pattern.compile("(|^)\\d{3}"); //here we are extracting 3 digit
        Matcher matcher = temperaturePattern.matcher(message);
        if (matcher.find()) {
            pieEntries.add(new PieEntry(1, matcher.group()));
            barEntries.add(new BarEntry(1, matcher.groupCount()));
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
                if(name != null) {
                    if (name.contains("57575791")) {
                        String details = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY));
                        String number = details.substring(details.length() - 4);
                        if (Integer.parseInt(number) >= 180) {
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "Sensor Notification");
                            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
                            builder.setContentTitle("The Temperature is more than expected ");
                            builder.setContentText("Temperature is too High");

                            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getContext());
                            managerCompat.notify(1, builder.build());
                        }else if(Integer.parseInt(number) <= 140){
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "Sensor Notification");
                            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
                            builder.setContentTitle("The Temperature is less than expected ");
                            builder.setContentText("Temperature is too Less");

                            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getContext());
                            managerCompat.notify(1, builder.build());
                        }
                        pieEntries.add(new PieEntry(cursor.getCount(), number));
                        barEntries.add(new BarEntry(i, Float.parseFloat(number)));
                        i++;
                    }
                }
            }
        }
    }

    //coroutine fetching live data

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

    private void setSmsList(List<Sms> newSmsList){
        list = newSmsList;
        notify();
    }


    @Override
    public void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }

    @Override
    public void onStop() {
        super.onStop();
        getContext().unregisterReceiver(smsBroadcastReceiver);
    }

}

/*


private var contacts: List<Contact>? = emptyList()

fun setContactList(newContactList: List<Contact>?) {
        contacts = newContactList
        notifyDataSetChanged()
    }

 */