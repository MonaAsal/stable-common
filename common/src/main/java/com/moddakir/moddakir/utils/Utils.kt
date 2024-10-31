package com.moddakir.moddakir.utils

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.util.Patterns
import android.webkit.URLUtil
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.moddakirapps.R
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.ZoneOffset
import java.util.Calendar
import java.util.Date
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

    fun isValidate(urlString: String?): Boolean {
        return URLUtil.isValidUrl(urlString) && Patterns.WEB_URL.matcher(urlString).matches()
    }
    fun getDateFromUTC(inputDate: String?): Date {
        val format1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000'Z'")
        try {
            return format1.parse(inputDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return Date()
    }

    fun getFormattedDateUTC(time: String, dayOfMonth: Int, year: Int, month: Int): String? {
        try {
            val currentTimeSDF = SimpleDateFormat("HH:mm", Locale.ENGLISH)
            currentTimeSDF.timeZone = TimeZone.getTimeZone("UTC")

            val current = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
            current.timeZone = TimeZone.getDefault()
            //            String currentDate = current.format(new Date());
            val sdfUTC = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000'Z'", Locale.ENGLISH)
            sdfUTC.timeZone = TimeZone.getTimeZone("UTC")


            val formattedTime = if (time.isEmpty()) currentTimeSDF.format(Date()) else time
            val dateString = "$year-$month-$dayOfMonth $formattedTime"
            Log.e(
                "Date",
                "current " + dateString + " " + "UTC " + sdfUTC.format(current.parse(dateString))
            )
            return sdfUTC.format(current.parse(dateString))
        } catch (e: java.lang.Exception) {
            Log.e("exception", e.toString())
        }
        return null
    }
    fun share(message: String, context: Context) {
        val sendIntent = Intent()
        sendIntent.setAction(Intent.ACTION_SEND)
        sendIntent.setType("text/plain")
        sendIntent.putExtra(
            Intent.EXTRA_TEXT,
            """
            ${context.getString(R.string.share_message)}
            $message
            """.trimIndent()
        )

        context.startActivity(sendIntent)
    }

}