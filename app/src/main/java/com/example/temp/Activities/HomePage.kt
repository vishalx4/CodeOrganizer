package com.example.temp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.temp.Adapters.Adapter0
import com.example.temp.Database.Database
import com.example.temp.Models.TopicData
import com.example.temp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomePage : AppCompatActivity()
{
    private lateinit var adapter : RecyclerView.Adapter<*>
    private lateinit var rv: RecyclerView
    private lateinit var lm :RecyclerView.LayoutManager

    private lateinit var databaseTopicList : ArrayList<TopicData>
    private lateinit var topicList:ArrayList<TopicData>

    private lateinit var fab:FloatingActionButton
    private lateinit var database: Database
    private lateinit var programmeTableName:String

    private var dialogBuilder:AlertDialog.Builder? = null
    private var dialog:AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage)

        title = "CODE ORGANIZER"

        database = Database(this)

        topicList = ArrayList<TopicData>()
        databaseTopicList = ArrayList<TopicData>()

        adapter = Adapter0(this,topicList)
        rv = findViewById(R.id.homeRv)
        lm = LinearLayoutManager(this)
        rv.adapter = adapter
        rv.layoutManager = lm


        if(database.getTopicCount() != 0)
        {
            databaseTopicList = database.getAllTopics()

            for( topic in databaseTopicList.iterator())
            {

                var t = TopicData(topic.topicName,topic.topicDescription)
                topicList.add(t);
            }
            adapter.notifyDataSetChanged()
        }
        adapter.notifyDataSetChanged()

        fab = findViewById(R.id.topicAddFab)
    }




    fun addTopic(view: View)
    {
        var pop = layoutInflater.inflate(R.layout.popup_home,null)

        var topicName =  pop.findViewById<EditText>(R.id.editTopicNameET).text
        var topicDes = pop.findViewById<EditText>(R.id.editTopicDescriptionET).text
        var btn = pop.findViewById<Button>(R.id.editButton)

        dialogBuilder = AlertDialog.Builder(this).setView(pop)
        dialog = dialogBuilder!!.create()
        dialog?.show()

        btn.setOnClickListener{

            if(!TextUtils.isEmpty(topicName) && !TextUtils.isEmpty(topicDes))
            {
                val td = TopicData(topicName.toString(),topicDes.toString())

                programmeTableName = topicName.toString()
                var delimiter = " "
                val parts = programmeTableName.split(delimiter)
                programmeTableName = parts[0]

                database.createTopic(td)
                database.createProgrammeTable(programmeTableName)
                adapter.notifyDataSetChanged()
                dialog!!.dismiss()
                startActivity(Intent(this, HomePage::class.java))
                finish()
            }

        }

    }




}