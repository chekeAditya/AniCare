package com.application.anicaremals.ui.fragments.premium

import android.content.Context
import android.provider.Telephony
import com.application.anicaremals.local.responses.Sms
import com.application.anicaremals.util.CONSTANTS.URI

class SmsLiveData(private val context: Context) :
    ContentProviderLiveData<List<Sms>>(context, URI) {

    private fun getSms(context: Context): List<Sms> {
        val listOfSms = mutableListOf<Sms>()
        val projection: Array<String>? = null
        val selection: String? = null
        val selectionArgs: Array<String>? = null
        val order: String? = null
        val cursor = context.contentResolver.query(
            URI,
            projection,
            selection,
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