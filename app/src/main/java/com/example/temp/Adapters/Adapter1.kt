package com.example.temp.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.temp.Activities.CodeScreen
import com.example.temp.Database.Database
import com.example.temp.Models.ProgrameData
import com.example.temp.Models.TopicData
import com.example.temp.R

class Adapter1(val context: Context,val programmeList:ArrayList<ProgrameData>,val tableName:String):RecyclerView.Adapter<Adapter1.ViewHolder>()
{
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        fun bindItems(programme: ProgrameData, position: Int)
        {
            var ProgrammeName = itemView.findViewById<TextView>(R.id.programeName)
            var deleteBTN = itemView.findViewById<ImageView>(R.id.deleteProgrammeBtn)
            ProgrammeName.text = "$position)   " + programme.programeName


            deleteBTN.setOnClickListener {
                val database = Database(context)
                database.deleteProgramme(programme.programeName,tableName)
                programmeList.removeAt(position)
                notifyItemRemoved(position)
                Toast.makeText(context,"${programme.programeName} deleted successfully ",Toast.LENGTH_SHORT).show()
            }

            itemView.setOnClickListener {

                val intent = Intent(context,CodeScreen::class.java)
                intent.putExtra("programmeName",programme.programeName)
                intent.putExtra("code",programme.programmeCode)
                context.startActivity(intent)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.programme_card,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(programmeList[position],position)
    }

    override fun getItemCount(): Int {
        return programmeList.size
    }
}