package com.example.phonebook.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.phonebook.R
import com.example.phonebook.adapter.RecordAdapter
import com.example.phonebook.databinding.FragmentRecordBinding
import com.example.phonebook.service.DatabaseRoom
import com.example.phonebook.model.Kisiler
import com.example.phonebook.service.KisilerDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecordFragment : Fragment() {

    private lateinit var adapter: RecordAdapter
    private lateinit var binding: FragmentRecordBinding
    private var veriTabani: DatabaseRoom? = null
    private var kisilerDao: KisilerDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_record, container, false)
        veriTabani = DatabaseRoom.DatabaseAccess(requireContext())
        kisilerDao = veriTabani?.getKisilerDao()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        veriGoster()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun veriGoster() {

        val anim = AnimationUtils.loadAnimation(requireContext(), androidx.appcompat.R.anim.abc_grow_fade_in_from_bottom)

        binding.recordRcyc.layoutManager = LinearLayoutManager(requireContext())
        val jobs = CoroutineScope(Dispatchers.Main).launch {
            val kisiList = kisilerDao?.tumkisiler()
            adapter = RecordAdapter(requireContext(), kisiList!!) { its ->

                if (its.contains("@")) {
                    /**Kişi Listeleme Gelmiş*/
                    val go = RecordFragmentDirections.actionRecordFragmentToUserModalSheet(its)
                    view?.let { Navigation.findNavController(it).navigate(go) }


                } else {

                    val list = its.split("$")

                    binding?.deleteLayout?.isVisible = true
                    binding?.deleteLayout?.setOnClickListener{

                    }
                    binding?.silinecekName?.text = list.get(2)

                    binding?.buttonSilEvet?.setOnClickListener {
                        /**Kişi Silme gelmiş*/
                        val job = CoroutineScope(Dispatchers.Main).launch {
                            val silinenKullanici = Kisiler(list.get(1).toInt(), "", "")
                            kisilerDao?.kisiSil(silinenKullanici)
                        }
                        adapter.notifyDataSetChanged()
                        veriGoster()
                        binding?.buttonSilEvet?.startAnimation(anim)
                        Handler().postDelayed({
                            binding?.deleteLayout?.isVisible = false
                        }, 100)
                    }

                    binding?.buttonSilHayir?.setOnClickListener {
                        binding?.buttonSilHayir?.startAnimation(anim)
                        Handler().postDelayed({
                            binding?.deleteLayout?.isVisible = false
                        }, 100)
                    }
                }

            }
            binding.recordRcyc.adapter = adapter
        }
    }
}