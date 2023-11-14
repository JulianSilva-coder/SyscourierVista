package com.example.syscourier.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.syscourier.MainActivity
import com.example.syscourier.R
import com.example.syscourier.fragments.Asignaciones
import com.example.syscourier.fragments.Devoluciones
import com.example.syscourier.fragments.Entregados
import com.example.syscourier.fragments.Transporte
import com.google.android.material.navigation.NavigationView

class Menudesplegable : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_desplegable)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.abrir_nav,
            R.string.cerrar_nav
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Asignaciones()).commit()
            navigationView.setCheckedItem(R.id.nav_asignaciones)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_asignaciones -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Asignaciones()).commit()
            R.id.nav_transito -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Transporte()).commit()
            R.id.nav_entregados -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Entregados()).commit()
            R.id.nav_devoluciones -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Devoluciones()).commit()
            R.id.nav_logout -> {
                Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}