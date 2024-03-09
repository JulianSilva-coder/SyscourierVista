package com.example.syscourier.activities

import com.example.syscourier.MainActivity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.syscourier.R
import com.example.syscourier.fragments.Asignaciones
import com.example.syscourier.fragments.Devoluciones
import com.example.syscourier.fragments.Entregados
import com.google.android.material.navigation.NavigationView

/**
 * [Menudesplegable] es una actividad que implementa un menú desplegable de navegación.
 * Permite al usuario navegar entre diferentes fragmentos de la aplicación y realizar acciones como logout.
 */
class Menudesplegable : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_desplegable)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val imageButton: ImageButton = this.findViewById(R.id.imageButton)
        imageButton.setOnClickListener {
            if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

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
