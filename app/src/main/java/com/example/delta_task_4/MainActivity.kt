package com.example.delta_task_4

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.security.Permission


class MainActivity : AppCompatActivity() {
    var file = Files()
    var path = ""
    var listAdapter = ListAdapter(mutableListOf<Files>())

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        path = Environment.getExternalStorageDirectory().absolutePath
        permissionCheck()

        dirList.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(application)
            // set the custom adapter to the RecyclerView
            adapter = listAdapter
        }


        //now presenting the data into screen

    }

    fun getAllDir(name: String,path: String,level: Int): Files {
        val f = File(path)
        val values = f.list()
        var file = Files()
        file.name = name
        file.level = level
        if(!values.isNullOrEmpty()){
            file.arrow = true
            for(i in values){
                var tempFile = getAllDir(i, path+"/"+i, level+1)
                file.subFiles.add(tempFile)
            }
        }
        return file
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun permissionCheck(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            file = getAllDir("sdcard",path,0)
            listAdapter.addData(mutableListOf(file))
            listAdapter.notifyDataSetChanged()
        }else{
            requestPermissions(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), 1
            )
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    file = getAllDir("sdcard",path,0)
                    listAdapter.addData(mutableListOf(file))
                    listAdapter.notifyDataSetChanged()

                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }


}