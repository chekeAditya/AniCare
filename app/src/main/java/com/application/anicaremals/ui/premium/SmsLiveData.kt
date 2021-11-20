package com.application.anicaremals.ui.premium

import android.content.Context
import android.net.Uri
import android.provider.Telephony
import com.application.anicaremals.remote.response.Sms

class SmsLiveData(private val context: Context) :
    ContentProviderLiveData<List<Sms>>(context, URI) {


    companion object {
        val URI = Uri.parse("content://sms/")
    }

    private fun getSms(context: Context): List<Sms> {
        val listOfSms = mutableListOf<Sms>()
        val projection: Array<String>? = null
        val selection: String? = null
        val selectionArgs: Array<String>? = null
        val order: String? = null
        val cursor = context.contentResolver.query(
            URI,
            projection,
            null,
            selectionArgs,
            order
        )
        if (cursor!!.count > 0) {
            var i = 0;
            while (cursor.moveToNext()) {
                val name: String =
                    cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
                if (name.contains("57575791")) {
                    val details = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY))
                    val number = details.substring(details.length - 4)
                    listOfSms.add(Sms(i,number))
                    i++;
                }
            }
            cursor.close()
        }
        return listOfSms
    }
    override fun getContentProvidersValue() = getSms(context)
}