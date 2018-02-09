package com.teamwork.sample.maicon.ui.project.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teamwork.sample.maicon.R
import com.teamwork.sample.maicon.data.model.Project
import com.teamwork.sample.maicon.util.extension.formatToServerDateDefaults
import com.teamwork.sample.maicon.util.extension.formatToViewDateDefaults
import com.teamwork.sample.maicon.util.extension.gone
import com.teamwork.sample.maicon.util.extension.visible
import kotlinx.android.synthetic.main.item_project.view.*
import rx.Observable
import rx.subjects.PublishSubject
import javax.inject.Inject

class ProjectAdapter
@Inject
constructor() : RecyclerView.Adapter<ProjectAdapter.RecyclerViewAdapterViewHolder>() {

    private val clickSubject = PublishSubject.create<Project>()
    val clickEvent: Observable<Project> = clickSubject

    var  dataList: MutableList<Project> = emptyList<Project>().toMutableList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_project, parent, false)
        return RecyclerViewAdapterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapterViewHolder, position: Int) {
        val data = dataList[position]

        holder.textViewProjectName.text = data.name
        holder.textViewUserName.text = data.company?.name

        if(data.starred == true){
            holder.imageViewStarred.setImageResource(R.drawable.ic_star_yellow_24dp)
        }else{
            holder.imageViewStarred.setImageResource(R.drawable.ic_star_border_black_24dp)
        }

        if(data.description == null || data.description?.isEmpty() == true){
            holder.textViewInformation.gone()
        }else{
            holder.textViewInformation.visible()
        }
        if(data.startDate == null){
            holder.textViewDateRange.gone()
        }else{
            holder.textViewDateRange.visible()
        }
        val c = holder.itemView.context
        holder.textViewInformation.text = data.description
        holder.textViewDateRange.text = c.getString(R.string.date_range,
                data.startDate?.formatToViewDateDefaults(),
                data.endDate?.formatToViewDateDefaults())
    }

    override fun getItemCount(): Int = dataList.size

    inner class RecyclerViewAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textViewProjectName = itemView.textViewProjectName
        val textViewUserName = itemView.textViewUserName
        val textViewDateRange = itemView.textViewDateRange
        val textViewInformation = itemView.textViewInformation
        val imageViewStarred = itemView.imageViewStarred

        init {
            itemView.setOnClickListener{
                clickSubject.onNext(dataList[layoutPosition])
            }
        }
    }
}