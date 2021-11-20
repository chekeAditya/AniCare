package com.application.anicaremals.ui.premium;

import static com.application.anicaremals.util.CONSTANTS.REQUEST_CODE;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.application.anicaremals.R;

import java.util.ArrayList;

public class ContentProviderPhone_Activity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> listdata = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        listView = findViewById(R.id.list);
        fetchContact();
    }

    private void fetchContact() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, REQUEST_CODE);
        }

        ContentResolver resolver = getContentResolver();
        Uri allMessages = Uri.parse("content://sms/");
//        Cursor cursor = this.getContentResolver().query(allMessages, null,
//                null, null, null);
//        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = null;
        String selection = null;
        String[] selectionArgs = null;
        String order = null;

        Cursor cursor = this.getContentResolver().query(allMessages, projection, selection, selectionArgs, order);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS));
                if (name.contains("57575791")) {
                    String details = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY));
                    String number = details.substring(details.length() - 4);
                    listdata.add(number);
//                @SuppressLint("Range") String name = cursor.getColumnName(cursor);
//                @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                String myContact = name + "\n" + number;
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listdata);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}