package com.example.temp.Activities

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.temp.R

class CodeScreen : AppCompatActivity() {

    private lateinit var codeTV:TextView
    private lateinit var programmeNameTV : TextView
    private lateinit var copyButton : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_screen)


        var programmeName = intent.getStringExtra("programmeName").toString()
        var code = intent.getStringExtra("code").toString()

        title = programmeName


        codeTV = findViewById(R.id.code)
        programmeNameTV = findViewById(R.id.programmeNameTV)
        copyButton = findViewById(R.id.copyCode)

        codeTV.text = code
        programmeNameTV.text = programmeName


        copyButton.setOnClickListener{
            copyTextToClipboard(code)
        }

    }

    private fun copyTextToClipboard(textToCopy: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", textToCopy)
        clipboard.setPrimaryClip(clipData)
        Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_LONG).show()
    }
}