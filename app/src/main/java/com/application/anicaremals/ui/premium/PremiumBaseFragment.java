package com.application.anicaremals.ui.premium;

import static com.application.anicaremals.util.CONSTANTS.REQUEST_CODE;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.application.anicaremals.R;
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


public class PremiumBaseFragment extends Fragment {


    ArrayList<BarEntry> barEntries = new ArrayList<>();
    ArrayList<PieEntry> pieEntries = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_premium_base, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initViews
        BarChart barChart = view.findViewById(R.id.barChart);
        PieChart pieChart = view.findViewById(R.id.pieChart);
        fetchSMSDetails();

        //settingUp the layout
        settingUpBarChart(barChart);
        settingUpPieChart(pieChart);

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
                if (name.contains("57575791")) {
                    String details = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY));
                    String number = details.substring(details.length() - 4);
                    pieEntries.add(new PieEntry(cursor.getCount(), number));
                    barEntries.add(new BarEntry(i, Float.parseFloat(number)));
                    i++;
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
//        barEntries.add(new BarEntry(1, 420));
//        barEntries.add(new BarEntry(2, 475));
//        barEntries.add(new BarEntry(3, 508));
//        barEntries.add(new BarEntry(4, 660));
//        barEntries.add(new BarEntry(5, 550));
//        barEntries.add(new BarEntry(6, 630));
//        barEntries.add(new BarEntry(7, 470));

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