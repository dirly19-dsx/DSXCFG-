package com.dsx.cfg

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import rikka.shizuku.Shizuku
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // --- KODE LOGIKA LOGIN ---
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(60, 60, 60, 60)
            setBackgroundColor(Color.parseColor("#050506"))
            gravity = Gravity.CENTER_VERTICAL
        }

        val title = TextView(this).apply {
            text = "DSXCFG LOGIN"
            setTextColor(Color.parseColor("#CC0000"))
            textSize = 32f
            setTypeface(null, Typeface.BOLD)
            gravity = Gravity.CENTER
            setPadding(0, 0, 0, 40)
        }

        val inputKey = EditText(this).apply {
            hint = "Masukkan Key (DSX-1BULAN-VIP)"
            setTextColor(Color.WHITE)
            setHintTextColor(Color.GRAY)
            setBackgroundColor(Color.parseColor("#1A1A1A"))
            setPadding(30, 30, 30, 30)
        }

        val btnLogin = Button(this).apply {
            text = "LOGIN"
            setBackgroundColor(Color.parseColor("#CC0000"))
            setTextColor(Color.WHITE)
            setTypeface(null, Typeface.BOLD)
            setPadding(0, 30, 0, 30)
        }

        // Watermark di Halaman Login
        val watermarkLogin = TextView(this).apply {
            text = "WA Admin: 0887437833371\nTikTok: ClayX!"
            setTextColor(Color.parseColor("#888888"))
            textSize = 14f
            gravity = Gravity.CENTER
            setPadding(0, 80, 0, 0)
        }

        layout.addView(title)
        layout.addView(inputKey)
        layout.addView(btnLogin)
        layout.addView(watermarkLogin)
        setContentView(layout)

        // Daftar Key Lu
        val validKeys = mapOf("DSX-1JAM-BG" to 1, "DSX-3JAM-PRO" to 3, "DSX-1BULAN-VIP" to 720)

        btnLogin.setOnClickListener {
            val key = inputKey.text.toString().trim()
            if (validKeys.containsKey(key)) {
                Toast.makeText(this, "Key Valid! Akses Diberikan.", Toast.LENGTH_SHORT).show()
                showDashboard()
            } else {
                Toast.makeText(this, "Key Salah / Expired!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // --- KODE FITUR (DASHBOARD) ---
    private fun showDashboard() {
        // Bungkus pakai ScrollView biar bisa di-scroll ke bawah
        val scrollView = ScrollView(this)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(60, 60, 60, 60)
            setBackgroundColor(Color.parseColor("#050506"))
        }

        val title = TextView(this).apply {
            text = "DSXCFG DASHBOARD"
            setTextColor(Color.parseColor("#00FF00"))
            textSize = 26f
            setTypeface(null, Typeface.BOLD)
            gravity = Gravity.CENTER
            setPadding(0, 0, 0, 40)
        }

        // --- TOMBOL FITUR (ALL DEV UNIVERSAL) ---
        val btnOpt = Button(this).apply { text = "OPTIMASI PERFORMA" }
        val btnStab = Button(this).apply { text = "STABILIZER (ALL DEV)" }
        val btnAim = Button(this).apply { text = "AIM SMOOTH" }
        val btnDrag = Button(this).apply { text = "EASY DRAG" }
        val btnFps = Button(this).apply { text = "BOOST FPS" }
        
        val btnReset = Button(this).apply { 
            text = "RESET KE NORMAL" 
            setBackgroundColor(Color.parseColor("#CC0000"))
            setTextColor(Color.WHITE)
            setPadding(0, 40, 0, 40)
        }

        // Watermark di Halaman Dashboard
        val watermarkDash = TextView(this).apply {
            text = "Support By:\nWA Admin: 0887437833371\nTikTok: ClayX!"
            setTextColor(Color.parseColor("#888888"))
            textSize = 14f
            gravity = Gravity.CENTER
            setPadding(0, 60, 0, 0)
        }

        layout.addView(title)
        layout.addView(btnOpt)
        layout.addView(btnStab)
        layout.addView(btnAim)
        layout.addView(btnDrag)
        layout.addView(btnFps)
        layout.addView(btnReset)
        layout.addView(watermarkDash)
        
        scrollView.addView(layout)
        setContentView(scrollView)

        // --- EKSEKUSI SHIZUKU (ADB SHELL) ---

        // 1. Optimasi Performa (Bunuh background app & Cache)
        btnOpt.setOnClickListener { 
            runShell("am kill-all; pm trim-caches 999G") 
        }

        // 2. Stabilizer (Universal: Bypass Power Saving & Lock CPU)
        // Menggunakan perintah standar Android yang works di semua HP
        btnStab.setOnClickListener { 
            runShell("settings put global low_power 0; settings put global adaptive_battery_management_enabled 0; cmd power set-fixed-performance-mode-enabled true") 
        }

        // 3. Aim Smooth (Maksimalkan sensitivitas layar)
        btnAim.setOnClickListener { 
            runShell("settings put secure pointer_speed 7; settings put system pointer_speed 7; settings put system touch.pressure.scale 0.0") 
        }

        // 4. Easy Drag (Matikan animasi & VSync)
        btnDrag.setOnClickListener { 
            runShell("settings put global window_animation_scale 0.0; settings put global transition_animation_scale 0.0; settings put global animator_duration_scale 0.0; setprop debug.egl.swapinterval 0") 
        }

        // 5. Boost FPS (Paksa Max Hz & GPU Rendering)
        btnFps.setOnClickListener { 
            runShell("settings put system peak_refresh_rate 120.0; settings put system min_refresh_rate 120.0; settings put global force_gpu_rendering 1") 
        }

        // 6. Reset (Balikin semua ke awal)
        btnReset.setOnClickListener { 
            runShell("settings put global low_power 1; settings put global adaptive_battery_management_enabled 1; cmd power set-fixed-performance-mode-enabled false; settings put secure pointer_speed 0; settings put system pointer_speed 0; settings put global window_animation_scale 1.0; settings put global transition_animation_scale 1.0; settings put global animator_duration_scale 1.0; settings put system peak_refresh_rate 60.0; settings put system min_refresh_rate 60.0; settings put global force_gpu_rendering 0; setprop debug.egl.swapinterval 1; wm density reset") 
        }
    }

    // Fungsi Eksekusi Shell via Shizuku
    private fun runShell(cmd: String) {
        try {
            if (Shizuku.checkSelfPermission() == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                val process = Shizuku.newProcess(arrayOf("sh", "-c", cmd), null, null)
                process.waitFor()
                Toast.makeText(this, "Sistem Terpasang!", Toast.LENGTH_SHORT).show()
            } else {
                Shizuku.requestPermission(0)
                Toast.makeText(this, "Izinkan Shizuku dulu!", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Gagal eksekusi. Pastikan Shizuku nyala.", Toast.LENGTH_SHORT).show()
        }
    }
}
