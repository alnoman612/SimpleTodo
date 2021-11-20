package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    var listofTasks = mutableListOf<String>()
    lateinit var adapter: taskItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        findViewById<Button>(R.id.bottom).setOnClickListener{
//
//        }

        val onLongClickListener= object: taskItemAdapter.onLongClickListener{
            override fun onItemClickListener(position: Int) {
                //1. Remove the list
                listofTasks.removeAt(position)
                //2.notify the adapter
                adapter.notifyDataSetChanged()
                saveItems()
            }

        }

        loadItems()
        // Lookup the recyclerview in activity layout
        val recyclerView = findViewById<RecyclerView>(R.id.recycleView) //as RecyclerView
        // Create adapter passing in the sample user data
       adapter = taskItemAdapter(listofTasks, onLongClickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)


        val input = findViewById<EditText>(R.id.addTaskField)
        //setup the button and input field so users can setup list
        findViewById<Button>(R.id.button).setOnClickListener{
            //1. input
            val userInput = input.text.toString()
            //2. add to list
            listofTasks.add(userInput)
            //notify the adapter
            adapter.notifyItemInserted(listofTasks.size-1)

            //3.Reset the text field
            findViewById<EditText>(R.id.addTaskField).setText("")
            saveItems()
        }

    }

    //save the data the user has inputed
    //save by read and write

    ///create a method to get the data file we need
    fun getDataFile(): File {
        //everyline is represent task
        return File(filesDir, "data.txt")

    }



    //load the item by reading line by line from the file
    fun loadItems(){
        try {
            listofTasks =
                org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch(ioException: IOException){
            ioException.printStackTrace()
        }

    }

    //save the items by writing to the file.
    fun saveItems(){
        try {

            org.apache.commons.io.FileUtils.writeLines(getDataFile(), listofTasks)
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }

    }



}