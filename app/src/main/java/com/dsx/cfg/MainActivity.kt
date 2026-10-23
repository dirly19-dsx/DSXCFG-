package com.dsx.cfg

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import rikka.shizuku.Shizuku

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // --- KODE LOGIKA LOGIN ---
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 50, 50, 50)
            setBackgroundColor(android.graphics.Color.parseColor("#050506"))
        }

        val title = TextView(this).apply {
            text = "DSXCFG LOGIN"
            setTextColor(android.graphics.Color.parseColor("#CC0000"))
            textSize = 30f
            setPadding(0, 0, 0, 30)
        }

        val inputKey = EditText(this).apply {
            hint = "Masukkan Key (DSX-1BULAN-VIP)"
            setTextColor(android.graphics.Color.WHITE)
        }

        val btnLogin = Button(this).apply {
            text = "LOGIN"
            setBackgroundColor(android.graphics.Color.parseColor("#CC0000"))
            setTextColor(android.graphics.Color.WHITE)
        }

        layout.addView(title)
        layout.addView(inputKey)
        layout.addView(btnLogin)
        setContentView(layout)

        // Daftar Key Lu
        val validKeys = mapOf("DSX-1JAM-BG" to 1, "DSX-3JAM-PRO" to 3, "DSX-1BULAN-VIP" to 720)

        btnLogin.setOnClickListener {
            val key = inputKey.text.toString().trim()
            if (validKeys.containsKey(key)) {
                Toast.makeText(this, "Key Valid! Akses Diberikan.", Toast.LENGTH_SHORT).show()
                showDashboard() // Pindah ke halaman fitur
            } else {
                Toast.makeText(this, "Key Salah / Expired!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // --- KODE FITUR (DASHBOARD) ---
    private fun showDashboard() {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 50, 50, 50)
            setBackgroundColor(android.graphics.Color.parseColor("#050506"))
        }

        val title = TextView(this).apply {
            text = "DSXCFG DASHBOARD"
            setTextColor(android.graphics.Color.parseColor("#00FF00"))
            textSize = 24f
            setPadding(0, 0, 0, 30)
        }

        val btnHz = Button(this).apply { text = "MAX HZ (120Hz)" }
        val btnRam = Button(this).apply { text = "CLEAN RAM" }
        val btnAnim = Button(this).apply { text = "ZERO DELAY TOUCH" }

        layout.addView(title)
        layout.addView(btnHz)
        layout.addView(btnRam)
        layout.addView(btnAnim)
        setContentView(layout)

        // Eksekusi Shizuku (Pastikan lu udah install Shizuku)
        btnHz.setOnClickListener { runShell("settings put system peak_refresh_rate 120.0") }
        btnRam.setOnClickListener { runShell("am kill-all") }
        btnAnim.setOnClickListener { runShell("settings put global window_animation_scale 0.0") }
    }

    private fun runShell(cmd: String) {
        try {
            if (Shizuku.checkSelfPermission() == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                val process = Shizuku.newProcess(arrayOf("sh", "-c", cmd), null, null)
                process.waitFor()
                Toast.makeText(this, "Berhasil: $cmd", Toast.LENGTH_SHORT).show()
            } else {
                Shizuku.requestPermission(0)
                Toast.makeText(this, "Izinkan Shizuku dulu!", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Gagal eksekusi. Pastikan Shizuku nyala.", Toast.LENGTH_SHORT).show()
        }
    }
}
