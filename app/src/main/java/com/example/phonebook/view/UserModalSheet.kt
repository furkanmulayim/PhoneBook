package com.example.phonebook.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.phonebook.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UserModalSheet : BottomSheetDialogFragment() {

    private var userBilgi: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userBilgi = UserModalSheetArgs.fromBundle(it).userBilgi
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_modal_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomName = view.findViewById<TextView>(R.id.bottomName)
        val bottomNumber = view.findViewById<TextView>(R.id.bottomNumber)
        val list = userBilgi?.split("@")

        bottomName.text = list?.get(1)
        bottomNumber.text = list?.get(2)
    }

}