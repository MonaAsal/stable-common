package com.moddakir.moddakir.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.moddakirapps.R
import com.moddakir.moddakir.network.model.SessionRecord
import com.moddakir.moddakir.network.model.User
import com.moddakir.moddakir.ui.widget.TextViewCalibriBold
import com.moddakir.moddakir.ui.widget.TextViewUniqueLight
import com.moddakir.moddakir.utils.AccountPreference
import com.moddakir.moddakir.utils.Utils
import me.zhanghai.android.materialratingbar.MaterialRatingBar
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.lang.String
import kotlin.ByteArray
import kotlin.Int

class SessionDetailsAdapter(val context: Context,private val callLogModel: ArrayList<SessionRecord>) :
    RecyclerView.Adapter<SessionDetailsAdapter.ViewHolder>()  {
  val  utils:Utils= Utils()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.session_details_item, parent, false)
        val holder = ViewHolder(view)
        return holder
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user: User? = AccountPreference.getUser()

        if (callLogModel[position].fromVerse == null || callLogModel.get(position)
                .fromVerse.trim().isEmpty()
        ) {
            holder.fromaya.visibility = View.GONE
            holder.fromayanum.visibility = View.GONE
        } else {
            holder.fromaya.visibility = View.VISIBLE
            holder.fromayanum.visibility = View.VISIBLE
            holder.fromayanum.text = callLogModel[position].fromVerse
        }
        if (callLogModel[position].toVerse == null || callLogModel[position]
                .toVerse.trim().isEmpty()
        ) {
            holder.toaya.visibility = View.GONE
            holder.toayanum.visibility = View.GONE
        } else {
            holder.toaya.visibility = View.VISIBLE
            holder.toayanum.visibility = View.VISIBLE
            holder.toayanum.text = callLogModel[position].toVerse
        }
        if (callLogModel[position].fromSurah == null || callLogModel.get(position)
                .fromSurah.trim().isEmpty()
        ) {
            holder.fromsoura.visibility = View.GONE
            holder.fromsouraname.visibility = View.GONE
        } else {
            holder.fromsoura.visibility = View.VISIBLE
            holder.fromsouraname.visibility = View.VISIBLE
            holder.fromsouraname.text = callLogModel[position].fromSurah
        }
        if (callLogModel[position].toSurah == null || callLogModel[position]
                .toSurah.trim().isEmpty()
        ) {
            holder.tosoura.visibility = View.GONE
            holder.tosouraname.visibility = View.GONE
        } else {
            holder.tosoura.visibility = View.VISIBLE
            holder.tosouraname.visibility = View.VISIBLE
            holder.tosouraname.text = callLogModel[position].toSurah
        }
        if (callLogModel[position].isDone) {
            holder.sessionStatus.text = context.getString(R.string.completed)
        } else {
            holder.sessionStatus.text = context.getString(R.string.notCompleted)
        }
        holder.sessiontype.text = callLogModel[position].type
        holder.comment.text = callLogModel[position].comment
        holder.tvWordsErrors.text = String.valueOf(callLogModel[position].wordErrorCount)
        holder.tvHesitation.text = String.valueOf(
            callLogModel[position].hesitationErrorCount
        )
        holder.tvTajweedErrors.text = String.valueOf(
            callLogModel[position].tajweedErrorCount
        )

        if (user!!.isChildDependent && callLogModel[position].isLesson === true) {
            holder.tvHesitation.visibility = View.GONE
            holder.tvTajweedErrors.visibility = View.GONE
            holder.hesitation_errors_tv.visibility = View.GONE
            holder.tajweed_errors_rv.visibility = View.GONE

            holder.fromaya.text = context.resources.getString(R.string.from)
            holder.toaya.text = context.resources.getString(R.string.to)

            //get from verse from json
            var jsLessonsArray: JSONArray? = null
            try {
                jsLessonsArray = JSONArray(getAssetJsonDataForLessons(context))

                for (x in 0 until jsLessonsArray!!.length()) {
                    val jsonObject = jsLessonsArray.getJSONObject(x)
                    val index = jsonObject.getString("index")
                    val value = jsonObject.getString("value")
                    if (callLogModel[position].fromVerse != null) {
                        if (index == callLogModel[position].fromVerse) {
                            holder.fromayanum.text = value
                            holder.fromayanum.textDirection = View.TEXT_DIRECTION_RTL
                        }
                    }
                    if (callLogModel[position].toVerse != null) {
                        if (index == callLogModel[position].toVerse) {
                            holder.toayanum.text = value
                            holder.toayanum.textDirection = View.TEXT_DIRECTION_RTL
                        }
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        if ((callLogModel[position].fromSurah == null || callLogModel[position]
                .fromSurah.trim().isEmpty())
            && callLogModel[position].isLesson === true
        ) {
            holder.tvHesitation.visibility = View.GONE
            holder.tvTajweedErrors.visibility = View.GONE
            holder.hesitation_errors_tv.visibility = View.GONE
            holder.tajweed_errors_rv.visibility = View.GONE

            holder.fromaya.text = context.resources.getString(R.string.from)
            holder.toaya.text = context.resources.getString(R.string.to)

            //get from verse from json
            var jsLessonsArray: JSONArray? = null
            try {
                jsLessonsArray = JSONArray(getAssetJsonDataForLessons(context))

                for (x in 0 until jsLessonsArray!!.length()) {
                    val jsonObject = jsLessonsArray.getJSONObject(x)
                    val index = jsonObject.getString("index")
                    val value = jsonObject.getString("value")
                    if (callLogModel[position].fromVerse != null) {
                        if (index == callLogModel[position].fromVerse) {
                            holder.fromayanum.text = value
                            holder.fromayanum.textDirection = View.TEXT_DIRECTION_RTL
                        }
                    }
                    if (callLogModel[position].toVerse != null) {
                        if (index == callLogModel[position].toVerse) {
                            holder.toayanum.text = value
                            holder.toayanum.textDirection = View.TEXT_DIRECTION_RTL
                        }
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }

    }


        holder.tvAssignmentValue.text = String.valueOf(callLogModel[position].assignmentValue)


}

    override fun getItemCount(): Int {
        return callLogModel.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val sessiontype: TextViewUniqueLight = view.findViewById(R.id.sessiontype)
        val fromsouraname: TextViewUniqueLight = view.findViewById(R.id.fromsouraname)
        val tosouraname: TextViewUniqueLight = view.findViewById(R.id.tosouraname)
        val fromayanum: TextViewUniqueLight = view.findViewById(R.id.fromayanum)
        val fromaya: TextViewUniqueLight = view.findViewById(R.id.fromaya)
        val toayanum: TextViewUniqueLight = view.findViewById(R.id.toayanum)
        val fromsoura: TextViewUniqueLight = view.findViewById(R.id.fromsoura)
        val tosoura: TextViewUniqueLight = view.findViewById(R.id.tosoura)
        val toaya: TextViewUniqueLight = view.findViewById(R.id.toaya)
        val comment: TextViewUniqueLight = view.findViewById(R.id.comment)
        val tvWordsErrors: TextViewUniqueLight = view.findViewById(R.id.words_errors_value_tv)
        val tvTajweedErrors: TextViewUniqueLight = view.findViewById(R.id.tajweed_errors_value_tv)
        val tvHesitation: TextViewUniqueLight = view.findViewById(R.id.hesitation_errors_value_tv)
        val tvAssignmentValue: TextViewUniqueLight = view.findViewById(R.id.assignment_value_tv)
        val sessionStatus: TextViewUniqueLight = view.findViewById(R.id.sessionStatus)
        val ratingBar: MaterialRatingBar = view.findViewById(R.id.ratingBar)
        val hesitation_errors_tv: TextViewCalibriBold = view.findViewById(R.id.hesitation_errors_tv)
        val tajweed_errors_rv: TextViewCalibriBold = view.findViewById(R.id.tajweed_errors_rv)

    }

    private fun getAssetJsonDataForLessons(context: Context): kotlin.String? {
        var json: kotlin.String? = null
        try {
            val `is` = context.assets.open("foundationlessonsplan.json")

            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()

            json = kotlin.text.String(buffer, charset("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }

        Log.e("data", json)
        return json
    }


}
