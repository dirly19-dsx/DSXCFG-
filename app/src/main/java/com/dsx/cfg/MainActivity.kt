package com.dsx.cfg

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.*
import rikka.shizuku.Shizuku
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.animation.ObjectAnimator
import android.animation.AnimatorSet

class MainActivity : Activity() {

    private var isSystemOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // --- HALAMAN LOGIN ---
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(60, 60, 60, 60)
            setBackgroundColor(Color.parseColor("#020105")) // Hitam pekat
            gravity = Gravity.CENTER
        }

        val title = TextView(this).apply {
            text = "DSXCFG"
            setTextColor(Color.parseColor("#CC0000")) // Merah Darah
            textSize = 50f
            setTypeface(null, Typeface.BOLD)
            gravity = Gravity.CENTER
            // Efek Glow Merah
            setShadowLayer(15f, 0f, 0f, Color.parseColor("#FF0033"))
        }

        val subTitle = TextView(this).apply {
            text = "PREMIUM SYSTEM OPTIMIZER"
            setTextColor(Color.parseColor("#888888"))
            textSize = 14f
            letterSpacing = 0.2f
            gravity = Gravity.CENTER
            setPadding(0, 10, 0, 80)
        }

        val inputKey = EditText(this).apply {
            hint = "ENTER LICENSE KEY"
            setTextColor(Color.WHITE)
            setHintTextColor(Color.GRAY)
            setBackgroundColor(Color.parseColor("#111115"))
            setPadding(40, 40, 40, 40)
            gravity = Gravity.CENTER
            letterSpacing = 0.1f
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 0, 0, 50)
            layoutParams = params
        }

        val btnLogin = createPremiumButton("VERIFY ACCESS", "#CC0000", "#FFFFFF")
        btnLogin.setOnClickListener {
            val key = inputKey.text.toString().trim()
            val validKeys = mapOf("DSX-1JAM-BG" to 1, "DSX-3JAM-PRO" to 3, "DSX-1BULAN-VIP" to 720)
            if (validKeys.containsKey(key)) {
                Toast.makeText(this, "ACCESS GRANTED", Toast.LENGTH_SHORT).show()
                showDashboard()
            } else {
                Toast.makeText(this, "INVALID KEY", Toast.LENGTH_SHORT).show()
            }
        }

        val watermarkLogin = TextView(this).apply {
            text = "WA Admin: 0887437833371\nTikTok: ClayX!"
            setTextColor(Color.parseColor("#444444"))
            textSize = 12f
            gravity = Gravity.CENTER
            setPadding(0, 100, 0, 0)
        }

        layout.addView(title)
        layout.addView(subTitle)
        layout.addView(inputKey)
        layout.addView(btnLogin)
        layout.addView(watermarkLogin)
        setContentView(layout)

        // Animasi Masuk (Fade & Slide Up)
        layout.alpha = 0f
        layout.translationY = 100f
        layout.animate().alpha(1f).translationY(0f).setDuration(800).start()
    }

    // --- HALAMAN DASHBOARD ---
    private fun showDashboard() {
        val rootLayout = FrameLayout(this).apply {
            setBackgroundColor(Color.parseColor("#020105"))
        }

        // Efek Petir/Kedipan Merah (Overlay)
        val lightningOverlay = View(this).apply {
            setBackgroundColor(Color.parseColor("#55FF0000"))
            alpha = 0f
        }
        rootLayout.addView(lightningOverlay, FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        ))

        val scrollView = ScrollView(this)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 120, 50, 50)
            gravity = Gravity.CENTER_HORIZONTAL
        }

        val title = TextView(this).apply {
            text = "DSX DASHBOARD"
            setTextColor(Color.parseColor("#00FFCC")) // Cyan Neon
            textSize = 30f
            setTypeface(null, Typeface.BOLD)
            gravity = Gravity.CENTER
            setShadowLayer(10f, 0f, 0f, Color.parseColor("#00FFCC"))
            setPadding(0, 0, 0, 60)
        }

        // TOMBOL SAKLAR ON/OFF (Bentuk Pil)
        val btnToggle = createPremiumButton("TAP TO ACTIVATE", "#1A1A1A", "#FFFFFF")
        btnToggle.textSize = 20f
        
        // Fitur Tombol Lain
        val btnFF = createPremiumButton("LAUNCH FREE FIRE", "#00FFCC", "#000000")
        val btnOpt = createPremiumButton("OPTIMIZE RAM", "#111115", "#FFFFFF")
        val btnAim = createPremiumButton("AIM SMOOTH", "#111115", "#FFFFFF")
        val btnDrag = createPremiumButton("EASY DRAG", "#111115", "#FFFFFF")
        
        val btnReset = createPremiumButton("RESET SYSTEM", "#CC0000", "#FFFFFF")

        val watermarkDash = TextView(this).apply {
            text = "Powered by DSX\nWA: 0887437833371 | TT: ClayX!"
            setTextColor(Color.parseColor("#444444"))
            textSize = 12f
            gravity = Gravity.CENTER
            setPadding(0, 80, 0, 0)
        }

        layout.addView(title)
        layout.addView(btnToggle)
        layout.addView(btnFF)
        layout.addView(btnOpt)
        layout.addView(btnAim)
        layout.addView(btnDrag)
        layout.addView(btnReset)
        layout.addView(watermarkDash)
        
        scrollView.addView(layout)
        rootLayout.addView(scrollView)
        setContentView(rootLayout)

        // Animasi Masuk (Fade & Slide Down untuk judul)
        title.alpha = 0f
        title.translationY = -50f
        title.animate().alpha(1f).translationY(0f).setDuration(800).start()
        
        // Animasi Tombol Muncul Bergantian (Staggered)
        val buttons = listOf(btnToggle, btnFF, btnOpt, btnAim, btnDrag, btnReset)
        buttons.forEachIndexed { index, button ->
            button.alpha = 0f
            button.translationY = 50f
            button.animate().alpha(1f).translationY(0f).setStartDelay((index * 100).toLong()).setDuration(500).start()
        }

        // --- AKSI TOMBOL ON/OFF ---
        btnToggle.setOnClickListener {
            isSystemOn = !isSystemOn
            if (isSystemOn) {
                // Animasi Petir Merah
                val flashAnimator = ObjectAnimator.ofFloat(lightningOverlay, "alpha", 0f, 1f, 0f, 0.8f, 0f)
                flashAnimator.duration = 400
                flashAnimator.start()

                // Animasi Tombol Getar (Punch Effect)
                val scaleX = ObjectAnimator.ofFloat(btnToggle, "scaleX", 1f, 0.9f, 1.1f, 1f)
                val scaleY = ObjectAnimator.ofFloat(btnToggle, "scaleY", 1f, 0.9f, 1.1f, 1f)
                val animSet = AnimatorSet()
                animSet.playTogether(scaleX, scaleY)
                animSet.duration = 300
                animSet.start()

                btnToggle.text = "SYSTEM ACTIVE"
                val bg = GradientDrawable().apply {
                    setColor(Color.parseColor("#00FF00")) // Hijau Nyala
                    setCornerRadius(50f)
                    setStroke(4, Color.parseColor("#88FF88"))
                }
                btnToggle.background = bg
                btnToggle.setTextColor(Color.BLACK)

                // Eksekusi Semua Script Shizuku
                runShell("am kill-all; pm trim-caches 999G; settings put global low_power 0; cmd power set-fixed-performance-mode-enabled true; settings put secure pointer_speed 7; settings put system pointer_speed 7; settings put global window_animation_scale 0.0; settings put global transition_animation_scale 0.0; settings put global animator_duration_scale 0.0; setprop debug.egl.swapinterval 0; settings put system peak_refresh_rate 120.0; settings put system min_refresh_rate 120.0")
                Toast.makeText(this, "⚡ SYSTEM BOOSTED ⚡", Toast.LENGTH_SHORT).show()
            } else {
                btnToggle.text = "TAP TO ACTIVATE"
                val bg = GradientDrawable().apply {
                    setColor(Color.parseColor("#1A1A1A"))
                    setCornerRadius(50f)
                    setStroke(3, Color.parseColor("#444444"))
                }
                btnToggle.background = bg
                btnToggle.setTextColor(Color.WHITE)
                Toast.makeText(this, "SYSTEM STANDBY", Toast.LENGTH_SHORT).show()
            }
        }

        // --- AKSI BUKA FF ---
        btnFF.setOnClickListener {
            val intent = packageManager.getLaunchIntentForPackage("com.dts.freefireth")
            if (intent != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "Free Fire tidak terinstall!", Toast.LENGTH_SHORT).show()
            }
        }

        btnOpt.setOnClickListener { runShell("am kill-all; pm trim-caches 999G") }
        btnAim.setOnClickListener { runShell("settings put secure pointer_speed 7; settings put system pointer_speed 7; settings put system touch.pressure.scale 0.0") }
        btnDrag.setOnClickListener { runShell("settings put global window_animation_scale 0.0; settings put global transition_animation_scale 0.0; settings put global animator_duration_scale 0.0; setprop debug.egl.swapinterval 0") }
        
        btnReset.setOnClickListener { 
            isSystemOn = false
            btnToggle.text = "TAP TO ACTIVATE"
            val bg = GradientDrawable().apply {
                setColor(Color.parseColor("#1A1A1A"))
                setCornerRadius(50f)
                setStroke(3, Color.parseColor("#444444"))
            }
            btnToggle.background = bg
            btnToggle.setTextColor(Color.WHITE)
            runShell("settings put global low_power 1; cmd power set-fixed-performance-mode-enabled false; settings put secure pointer_speed 0; settings put system pointer_speed 0; settings put global window_animation_scale 1.0; settings put global transition_animation_scale 1.0; settings put global animator_duration_scale 1.0; settings put system peak_refresh_rate 60.0; settings put system min_refresh_rate 60.0; setprop debug.egl.swapinterval 1; wm density reset") 
            Toast.makeText(this, "SYSTEM RESET", Toast.LENGTH_SHORT).show()
        }
    }

    // --- DESAIN TOMBOL PREMIUM ---
    private fun createPremiumButton(textStr: String, bgColorHex: String, textColorHex: String): Button {
        return Button(this).apply {
            text = textStr
            setTextColor(Color.parseColor(textColorHex))
            textSize = 15f
            setTypeface(null, Typeface.BOLD)
            setBackgroundColor(Color.TRANSPARENT)
            letterSpacing = 0.1f
            
            val drawable = GradientDrawable().apply {
                setColor(Color.parseColor(bgColorHex))
                setCornerRadius(50f) // Ujung membulat (Pil)
                if (bgColorHex == "#111115" || bgColorHex == "#1A1A1A") {
                    setStroke(3, Color.parseColor("#333333"))
                } else if (bgColorHex == "#CC0000") {
                    setStroke(3, Color.parseColor("#FF3333"))
                } else if (bgColorHex == "#00FFCC") {
                    setStroke(3, Color.parseColor("#FFFFFF"))
                }
            }
            background = drawable
            
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 0, 0, 40)
            layoutParams = params
            setPadding(0, 35, 0, 35)
        }
    }

    // --- EKSEKUSI SHIZUKU ---
    private fun runShell(cmd: String) {
        try {
            if (Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED) {
                val process = Shizuku.newProcess(arrayOf("sh", "-c", cmd), null, null)
                process.waitFor()
            } else {
                Shizuku.requestPermission(0)
            }
        } catch (e: Exception) {
            // Abaikan error
        }
    }
}
