package com.moddakir.moddakir.utils

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.moddakirapps.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.ZoneOffset
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class Utils {
    fun loadImage(context: Context?, url: String?, imageView: ImageView?) {
        if (imageView != null) try {
            if (context != null) Glide.with(context)
                .load(url)
                .centerCrop()
                .into(imageView)
        } catch (e: Exception) {
            Log.e("exceptionImage", e.toString())
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    fun getDateTimeFormat(context: Context?, date_time: String?, timeZoneOffset: Int): String {
        try {
            return getDateFromStart(
                context!!,
                date_time!!,
                timeZoneOffset
            ) + " " + getTimeFormat(date_time, timeZoneOffset)
        } catch (e: java.lang.Exception) {
            Log.e("exception", e.toString())
            return ""
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    fun getDateFromStart(context: Context, date_time: String, timeZoneOffset: Int): String {
        try {
            //Input Date
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            sdf.timeZone = TimeZone.getTimeZone("UTC")

            val output = SimpleDateFormat("yyyy-MM-dd")
            val date1 = sdf.parse(date_time)
            //            sdf.setTimeZone(TimeZone.getDefault());
            val offset = ZoneOffset.ofHours(timeZoneOffset)
            sdf.timeZone = TimeZone.getTimeZone("GMT" + offset.id)
            val formattedDate = output.format(date1)
            //Current Date
            val date2 = Calendar.getInstance().time
            val formattedCurrentDate = output.format(date2)

            if (date1 != null && date2 != null && formattedDate == formattedCurrentDate) {
                return context.resources.getString(R.string.today)
            } else if (date1 != null) {
                val dateFormat: DateFormat = SimpleDateFormat("d/MM/yyyy")
                return dateFormat.format(date1).uppercase(Locale.getDefault())
            }

            return date_time
        } catch (e: java.lang.Exception) {
            Log.e("exception", e.toString())
            return date_time
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    fun getTimeFormat(date_time: String, timeZoneOffset: Int): String {
        try {
            val split = date_time.split("T".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            Log.e("Time", split[1])
            val format1: DateFormat = SimpleDateFormat("HH:mm:ss")
            format1.timeZone = TimeZone.getTimeZone("UTC")
            val date = format1.parse(split[1])
            val format2 = SimpleDateFormat("hh:mm a")
            val offset = ZoneOffset.ofHours(timeZoneOffset)
            format2.timeZone = TimeZone.getTimeZone("GMT" + offset.id)
            var result = ""
            if (date != null) result = format2.format(date)
            return result
        } catch (e: java.lang.Exception) {
            Log.e("exception", e.toString())
            return ""
        }
    }

}