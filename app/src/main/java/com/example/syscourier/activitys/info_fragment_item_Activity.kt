package com.example.syscourier.activitys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.syscourier.R
import com.example.syscourier.info_fragment_item

class info_fragment_item_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_item)

        // Puedes agregar el fragmento en el contenedor correspondiente
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment = info_fragment_item()

        fragmentTransaction.replace(R.id.info_item_Layout, fragment)
        fragmentTransaction.commit()
    }
}