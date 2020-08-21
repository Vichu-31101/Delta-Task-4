package com.example.delta_task_4

import android.R.attr.animationDuration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.file_layout.view.*


class ListAdapter(private var list: MutableList<Files>)
    : RecyclerView.Adapter<FileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.file_layout, parent, false)
        return FileViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val file: Files = list[position]

        val down = RotateAnimation(0f, 90f)
        val up = RotateAnimation(0f, -90f)

        holder.view.setPadding(file.level*40+10,10,10,10)
        holder.view.name.text = file.name
        if(file.arrow){
            holder.view.arrow.visibility = View.VISIBLE
        }else{
            holder.view.arrow.visibility = View.GONE
        }
        holder.view.setOnClickListener {
            if(!file.isExpanded){
                var subList = file.subFiles
                subList.sortBy { it.name }
                Log.d("hi",file.subFiles.size.toString())
                //list = subList
                addData(subList,position)
                notifyDataSetChanged()
                file.isExpanded = true
            }
            else{
                var subList = file.subFiles
                subList.sortBy { it.name }
                Log.d("hi",file.subFiles.size.toString())
                //list = subList
                removeData(subList)
                notifyDataSetChanged()
                file.isExpanded = false
            }
        }
    }

    override fun getItemCount(): Int = list.size

    fun addData(
        subList: MutableList<Files>,
        position: Int
    ) {
        for(i in subList){
            list.add(position+1+subList.indexOf(i),i)
        }

    }

    fun removeData(subList: MutableList<Files>){
        for(i in subList){
            i.isExpanded = false
            recursiveRemove(i)
        }
    }

    fun recursiveRemove(file: Files){
        file.isExpanded = false
        if(!file.subFiles.isNullOrEmpty()){
            for(i in file.subFiles){
                recursiveRemove(i)
            }
            list.remove(file)
        }
        else{
            list.remove(file)
        }
    }

    fun addData(filesList: MutableList<Files>){
        list = filesList
        notifyDataSetChanged()
    }

}


class FileViewHolder(val view: View) :
    RecyclerView.ViewHolder(view) {
}