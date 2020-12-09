package com.example.temp.Adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.temp.Activities.ProgrammePage
import com.example.temp.Database.Database
import com.example.temp.Models.TopicData
import com.example.temp.R


class Adapter0(val context: Context, val topicList: ArrayList<TopicData>):RecyclerView.Adapter<Adapter0.ViewHolder>()
{

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {

        fun bindItems(topic: TopicData, position: Int)
        {
            var topicName = itemView.findViewById<TextView>(R.id.topicName)
            var topicDescription = itemView.findViewById<TextView>(R.id.topicDescription)
            var deleteButton = itemView.findViewById<ImageView>(R.id.deleteTopicBtn)

            topicName.text = "TOPIC :  " + topic.topicName
            topicDescription.text = "Description :  "+topic.topicDescription

            deleteButton.setOnClickListener{
                val db = Database(context)
                db.deleteTopic(topic.topicName)
                //startActivity(context,Intent(context,HomePage::class.java),null)
                topicList.removeAt(position)
                notifyItemRemoved(position)
                Toast.makeText(context,"${topic.topicName} deleted successfully ",Toast.LENGTH_SHORT).show()
            }


            itemView.setOnClickListener {
                val intent = Intent(context, ProgrammePage::class.java)
                intent.putExtra("tableName", topic.topicName)
                context.startActivity(intent)
               // (context as Activity).finish()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.topic_card, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.bindItems(topicList[position], position)
    }

    override fun getItemCount(): Int {
        return topicList.size
    }
}