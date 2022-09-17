package com.example.phonebook.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.phonebook.R
import com.example.phonebook.databinding.FragmentRecordBinding
import com.example.phonebook.service.DatabaseRoom
import com.example.phonebook.model.Kisiler
import com.example.phonebook.service.KisilerDao


class RecordAdapter(
    private val mContex: Context,
    private val usersList: List<Kisiler>,
    private val deleteOnClick: (String) -> Unit
) : RecyclerView.Adapter<RecordAdapter.UserHolder>() {

    private var veriTabani: DatabaseRoom? = null
    private var kisilerDao: KisilerDao? = null

    class UserHolder(var view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_user, parent, false)

        veriTabani = DatabaseRoom.DatabaseAccess(mContex)
        kisilerDao = veriTabani?.getKisilerDao()

        return UserHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: UserHolder, position: Int) {

        val listName = holder.itemView.findViewById<TextView>(R.id.listName)
        val listNumber = holder.itemView.findViewById<TextView>(R.id.listNumber)
        val deleteButtonUser = holder.itemView.findViewById<ImageView>(R.id.deleteButtonUser)
        val kisiLayout = holder.itemView.findViewById<LinearLayout>(R.id.kisiLayout)
        val anim = AnimationUtils.loadAnimation(mContex, androidx.appcompat.R.anim.abc_grow_fade_in_from_bottom)

        val user = usersList[position]
        listName.text = user.kisi_ad
        listNumber.text = user.kisi_num

        deleteButtonUser.setOnClickListener {
            deleteButtonUser.startAnimation(anim)
            deleteOnClick.invoke("$" + user.kisi_id +"$"+ user.kisi_ad)
        }

        kisiLayout.setOnClickListener {
            kisiLayout.startAnimation(anim)
            deleteOnClick.invoke("@" + user.kisi_ad +"@"+ user.kisi_num)
        }
    }

    override fun getItemCount(): Int {
        return usersList.size
    }
}