package com.example.tonyso.mobilevtlwebview

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.apache.commons.io.FilenameUtils
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private var path: String = "";

    private var downloadPath: String = "";

    private var itemList: ArrayList<String>? = null;

    private var currentPosition = 0;

    private val listener = object: AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            loadHtmlFromFilePath(filePath = p0?.selectedItem.toString())
            currentPosition = p2
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        path = FileUtils.getExternalSdCardPath()
        downloadPath = FileUtils.getFile(path)
        itemList = scanHtmlFromPath()

        spinner.adapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,itemList)
        spinner.onItemSelectedListener = listener

        backButton.setOnClickListener {
            spinner.setSelection(if(currentPosition-1 == -1) 0 else currentPosition - 1)
        }

        forwardButton.setOnClickListener {
            spinner.setSelection(if(currentPosition+1 > itemList?.size!!) (itemList?.size!!.minus(1)) else currentPosition +1 )
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadHtmlFromFilePath(filePath: String){
        with(webview.settings){
            allowUniversalAccessFromFileURLs = true
            builtInZoomControls = true
            javaScriptEnabled = true
        }
        webview.loadUrl("file:///"+filePath)
    }

    private fun scanHtmlFromPath(): ArrayList<String> {
        var downloadFile = File(downloadPath)
        Log.d(this.localClassName, downloadFile.path)
        val result: ArrayList<String>? = ArrayList()
        downloadFile.listFiles()
                .filter { FilenameUtils.isExtension(it.absolutePath,"html") }
                .forEach { Log.d(this.localClassName, it.absolutePath);
                    result?.add(it.absolutePath)
                }

        return result!!
    }

}