package com.example.temp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.temp.Adapters.Adapter0
import com.example.temp.Adapters.Adapter1
import com.example.temp.Database.Database
import com.example.temp.Models.ProgrameData
import com.example.temp.Models.TopicData
import com.example.temp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProgrammePage : AppCompatActivity()
{
    private lateinit var adapter : RecyclerView.Adapter<*>
    private lateinit var rv: RecyclerView
    private lateinit var lm : RecyclerView.LayoutManager

    private lateinit var databaseProgrammeList : ArrayList<ProgrameData>
    private lateinit var programmeList:ArrayList<ProgrameData>

    private lateinit var fab: FloatingActionButton
    private lateinit var database: Database

    private var dialogBuilder: AlertDialog.Builder? = null
    private var dialog: AlertDialog? = null
    private lateinit var tableName:String
    val requestCode:Int = 1999




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_programme_page)

        tableName = intent.getStringExtra("tableName").toString()


            var delimiter = " "
            val parts = tableName.split(delimiter)
            tableName = parts[0]
        title = tableName.capitalize() + " Programmes"

        database = Database(this)

        // Recycler View
        programmeList = ArrayList()
        databaseProgrammeList = ArrayList()
        adapter = Adapter1(this,programmeList,tableName)
        rv = findViewById(R.id.programePageRv)
        lm = LinearLayoutManager(this)
        rv.adapter = adapter
        rv.layoutManager = lm
        fab = findViewById(R.id.addProgrammeFabBtn)


        Log.d("tableName : ","$tableName")

        var count:Int = database.getProgrammeCount(tableName)

        if(count!=0)
        {
            databaseProgrammeList = database.getAllProgrammes(tableName)

            for(programme in databaseProgrammeList.iterator())
            {
                var obj = ProgrameData(programme.programeName,programme.programmeCode)
                programmeList.add(obj)
            }
            adapter.notifyDataSetChanged()
        }

    }

    fun addProgramme(view: View)
    {
        var pop = layoutInflater.inflate(R.layout.upload_popup,null)

        var programmeName =  pop.findViewById<EditText>(R.id.uploadProgrammeNameET).text
        var programmeCode = pop.findViewById<EditText>(R.id.uploadCodeET).text
        var btn = pop.findViewById<Button>(R.id.uploadBtn)

        dialogBuilder = AlertDialog.Builder(this).setView(pop)
        dialog = dialogBuilder!!.create()
        dialog?.show()

        btn.setOnClickListener{

            if(!TextUtils.isEmpty(programmeName) && !TextUtils.isEmpty(programmeCode))
            {
                val pd = ProgrameData(programmeName.toString(),programmeCode.toString())

                database.createProgramme(pd,tableName)
                adapter.notifyDataSetChanged()
                dialog!!.dismiss()

                val intent = Intent(this, ProgrammePage::class.java)
                intent.putExtra("tableName", tableName)
                startActivity(intent)
                finish()
            }

        }
    }


}