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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.phonebook.service.DatabaseRoom
import com.example.phonebook.model.Kisiler
import com.example.phonebook.service.KisilerDao
import com.example.phonebook.R
import com.example.phonebook.adapter.NumberAdapter
import com.example.phonebook.databinding.FragmentAddBinding
import com.example.phonebook.util.hideKeyboard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var numberAdap: NumberAdapter
    private var veriTabani: DatabaseRoom? = null
    private var kisilerDao: KisilerDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)

        veriTabani = DatabaseRoom.DatabaseAccess(requireContext())
        kisilerDao = veriTabani?.getKisilerDao()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        butonControls()
    }

    @SuppressLint("SetTextI18n")
    fun butonControls() {
        val recyclerView = view?.findViewById(R.id.numberRcyc) as RecyclerView

        val anim = AnimationUtils.loadAnimation(requireContext(), androidx.appcompat.R.anim.abc_grow_fade_in_from_bottom)
        val anim2 = AnimationUtils.loadAnimation(requireContext(), androidx.appcompat.R.anim.abc_fade_out)

        val layoutManager = GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        numberAdap =
            NumberAdapter(context?.getString(R.string.btn_items).orEmpty()) { basilanSayi ->

                /** Number Space Controls*/
                if (binding.inputNumber.text.toString() == "") {
                    if (basilanSayi != "0") {
                        (binding.inputNumber.text.toString() + "0 ").also {
                            binding.inputNumber.text = it
                        }
                        binding.inputNumber.text = binding.inputNumber.text.toString() + basilanSayi
                    } else {
                        binding.inputNumber.text = binding.inputNumber.text.toString() + basilanSayi
                        binding.inputNumber.text = binding.inputNumber.text.toString() + " "
                    }
                } else {
                    if (binding.inputNumber.text.toString().length == 5 || binding.inputNumber.text.toString().length == 9 || binding.inputNumber.text.toString().length == 12) {
                        binding.inputNumber.text = binding.inputNumber.text.toString() + " "
                    }
                    binding.inputNumber.text = binding.inputNumber.text.toString() + basilanSayi
                }
            }
        recyclerView.adapter = numberAdap

        /**DELETE Button*/
        binding.buttonDelete.setOnClickListener {

            binding.buttonDelete.startAnimation(anim)

            if (binding.inputNumber.text != "") {
                var uzunluk = binding.inputNumber.text.toString().length - 1
                binding.inputNumber.text = binding.inputNumber.text.substring(0, uzunluk)
            }
        }

        /**DELETE Button Long*/
        binding.buttonDelete.setOnLongClickListener(View.OnLongClickListener {
            binding.inputNumber.text = ""
            true // <- set to true
        })

        /**Add number Button*/
        binding.buttonSave.setOnClickListener {

            /** Boş değilse */
            if (binding.inputNumber.text.toString() != "" && binding.inputNumber.text.toString() != "0 ") {

                binding.buttonSave.startAnimation(anim)
                binding.numberAddLayout.startAnimation(anim2)
                binding.numberss.text = binding.inputNumber.text.toString()

                Handler().postDelayed({
                    binding.numberAddLayout.isVisible = false
                    binding.userAddLayout.isVisible = true
                }, 350)

                /**  User Add Screen GElicek */
                addUserButtonClick()
            }
        }
    }

    fun addUserButtonClick() {

        binding.userAdd.setOnClickListener {

            if (binding.isimSoyisim.text.toString() != "") {
                hideKeyboard()

                var numara = binding.numberss.text.toString()
                var isimSoyisimm = binding.isimSoyisim.text.toString()

                numara = numara.replace(" ", "")

                val job = CoroutineScope(Dispatchers.Main).launch {
                    val yeniKisi = Kisiler(0, isimSoyisimm, numara)
                    kisilerDao?.kisiEkle(yeniKisi)
                }

                binding.userAddLayout.isVisible = false
                binding.numberAddLayout.isVisible = true

                binding.kayitBasari.isVisible = true

                Handler().postDelayed({
                    binding.kayitBasari.isVisible = false
                }, 1500)
            }
        }
    }
}