package com.example.phonebook.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.phonebook.service.DatabaseRoom
import com.example.phonebook.service.KisilerDao
import com.example.phonebook.R
import com.example.phonebook.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var veriTabani: DatabaseRoom?= null
    private var kisilerDao: KisilerDao?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val bottomNaviagtionview: BottomNavigationView = findViewById(R.id.bottom_navigation)
        val navController: NavController = findNavController(R.id.fragmentContainerView)
        bottomNaviagtionview.setupWithNavController(navController)

        veriTabani = DatabaseRoom.DatabaseAccess(this)
        kisilerDao = veriTabani?.getKisilerDao()

        kisileriYukle()
    }

    // interfacemiz suspend funct. yani coroutine yapısı içerisinde çalışması için yapı
    fun kisileriYukle (){
        val jobs = CoroutineScope(Dispatchers.Main) .launch {
         var kisiList = kisilerDao?.tumkisiler()

            if (kisiList != null) {
                for (i in kisiList){
                }
            }
        }
    }
}