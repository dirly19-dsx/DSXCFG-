package com.dsx.cfg

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup // <--- INI YANG KURANG TADI
import android.view.animation.OvershootInterpolator
import android.widget.*
import rikka.shizuku.Shizuku
import kotlin.random.Random

class MainActivity : Activity() {

    private var isSystemOn = false

    // Palet Warna: Hitam & Biru Neon
    private val colorBlack = Color.parseColor("#050505")
    private val colorDarkBlue = Color.parseColor("#0A1128")
    private val colorNeonBlue = Color.parseColor("#00F0FF")
    private val colorNeonBlueGlow = Color.parseColor("#8800F0FF")
    private val colorRed = Color.parseColor("#FF0033")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBaseLayout()
    }

    private fun setupBaseLayout() {
        val rootLayout = FrameLayout(this).apply {
            setBackgroundColor(colorBlack)
        }

        val loginLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(60, 60, 60, 60)
            gravity = Gravity.CENTER
        }

        val title = TextView(this).apply {
            text = "DSXCFG"
            setTextColor(colorNeonBlue)
            textSize = 60f
            typeface = Typeface.create("sans-serif-black", Typeface.BOLD)
            gravity = Gravity.CENTER
            letterSpacing = 0.1f
            setShadowLayer(25f, 0f, 0f, colorNeonBlue)
        }

        val subTitle = TextView(this).apply {
            text = "SYSTEM BREACH PROTOCOL"
            setTextColor(Color.parseColor("#88FFFFFF"))
            textSize = 14f
            letterSpacing = 0.3f
            gravity = Gravity.CENTER
            setPadding(0, 10, 0, 80)
        }

        val inputKey = EditText(this).apply {
            hint = "ENTER ACCESS KEY"
            setTextColor(colorNeonBlue)
            setHintTextColor(Color.parseColor("#4400F0FF"))
            setBackgroundColor(colorDarkBlue)
            setPadding(40, 40, 40, 40)
            gravity = Gravity.CENTER
            letterSpacing = 0.2f
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 0, 0, 50)
            layoutParams = params
        }

        val btnLogin = createCyberButton("INITIALIZE ACCESS", colorDarkBlue, colorNeonBlue, true)
        btnLogin.setOnClickListener {
            val key = inputKey.text.toString().trim()
            val validKeys = mapOf("DSX-1JAM-BG" to 1, "DSX-3JAM-PRO" to 3, "DSX-1BULAN-VIP" to 720)
            if (validKeys.containsKey(key)) {
                triggerLightningStrike()
                rootLayout.postDelayed({ showDashboard() }, 800)
            } else {
                triggerGlitchEffect(rootLayout)
                Toast.makeText(this, "ACCESS DENIED", Toast.LENGTH_SHORT).show()
            }
        }

        val watermarkLogin = TextView(this).apply {
            text = "WA Admin: 0887437833371\nTikTok: ClayX!"
            setTextColor(Color.parseColor("#333333"))
            textSize = 12f
            gravity = Gravity.CENTER
            setPadding(0, 100, 0, 0)
        }

        loginLayout.addView(title)
        loginLayout.addView(subTitle)
        loginLayout.addView(inputKey)
        loginLayout.addView(btnLogin)
        loginLayout.addView(watermarkLogin)
        
        rootLayout.addView(loginLayout)
        setContentView(rootLayout)

        loginLayout.alpha = 0f
        loginLayout.translationY = -50f
        loginLayout.animate().alpha(1f).translationY(0f).setDuration(1000).start()

        rootLayout.postDelayed({ triggerLightningStrike() }, 300)
    }

    private fun showDashboard() {
        val rootLayout = FrameLayout(this).apply {
            setBackgroundColor(colorBlack)
        }

        val scrollView = ScrollView(this)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 100, 40, 40)
            gravity = Gravity.CENTER_HORIZONTAL
        }

        val headerPanel = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            background = createCyberPanel()
            setPadding(40, 40, 40, 40)
            gravity = Gravity.CENTER
        }
        
        val title = TextView(this).apply {
            text = "DSX CORE"
            setTextColor(colorNeonBlue)
            textSize = 28f
            typeface = Typeface.create("sans-serif-black", Typeface.BOLD)
            setShadowLayer(10f, 0f, 0f, colorNeonBlue)
        }
        
        val statusText = TextView(this).apply {
            text = "STATUS: STANDBY"
            setTextColor(Color.GRAY)
            textSize = 14f
            letterSpacing = 0.2f
            setPadding(0, 10, 0, 0)
        }

        headerPanel.addView(title)
        headerPanel.addView(statusText)

        val btnToggle = createCyberButton("⚡ TAP TO ACTIVATE", colorDarkBlue, colorNeonBlue, true)
        btnToggle.textSize = 20f
        val toggleParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        toggleParams.setMargins(0, 40, 0, 40)
        btnToggle.layoutParams = toggleParams

        val btnFF = createCyberButton("LAUNCH FREE FIRE", colorDarkBlue, Color.WHITE, false)
        val btnOpt = createCyberButton("BOOST RAM & CPU", colorDarkBlue, Color.WHITE, false)
        val btnAim = createCyberButton("OVERRIDE TOUCH", colorDarkBlue, Color.WHITE, false)
        val btnDrag = createCyberButton("ZERO DELAY UI", colorDarkBlue, Color.WHITE, false)
        val btnReset = createCyberButton("PURGE SYSTEM", colorDarkBlue, colorRed, true)

        val watermarkDash = TextView(this).apply {
            text = "POWERED BY DSX PROJECT"
            setTextColor(Color.parseColor("#333333"))
            textSize = 12f
            letterSpacing = 0.2f
            setPadding(0, 60, 0, 0)
        }

        layout.addView(headerPanel)
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

        for (i in 0 until layout.childCount) {
            val child = layout.getChildAt(i)
            child.alpha = 0f
            child.translationX = -100f
            child.animate().alpha(1f).translationX(0f).setStartDelay((i * 100).toLong()).setDuration(500).start()
        }

        btnToggle.setOnClickListener {
            isSystemOn = !isSystemOn
            if (isSystemOn) {
                triggerLightningStrike()
                btnToggle.text = "⚡ SYSTEM ONLINE"
                btnToggle.background = createCyberButtonBg(colorDarkBlue, colorNeonBlue, true)
                btnToggle.setTextColor(colorNeonBlue)
                statusText.text = "STATUS: OVERCLOCKED"
                statusText.setTextColor(colorNeonBlue)
                
                runShell("am kill-all; pm trim-caches 999G; settings put global low_power 0; cmd power set-fixed-performance-mode-enabled true; settings put secure pointer_speed 7; settings put system pointer_speed 7; settings put global window_animation_scale 0.0; settings put global transition_animation_scale 0.0; settings put global animator_duration_scale 0.0; setprop debug.egl.swapinterval 0; settings put system peak_refresh_rate 120.0; settings put system min_refresh_rate 120.0")
            } else {
                btnToggle.text = "⚡ TAP TO ACTIVATE"
                btnToggle.background = createCyberButtonBg(colorDarkBlue, Color.GRAY, false)
                btnToggle.setTextColor(Color.GRAY)
                statusText.text = "STATUS: STANDBY"
                statusText.setTextColor(Color.GRAY)
                Toast.makeText(this, "SYSTEM STANDBY", Toast.LENGTH_SHORT).show()
            }
        }

        btnFF.setOnClickListener {
            val intent = packageManager.getLaunchIntentForPackage("com.dts.freefireth")
            if (intent != null) startActivity(intent) else Toast.makeText(this, "FF NOT INSTALLED", Toast.LENGTH_SHORT).show()
        }

        btnOpt.setOnClickListener { runShell("am kill-all; pm trim-caches 999G") }
        btnAim.setOnClickListener { runShell("settings put secure pointer_speed 7; settings put system pointer_speed 7; settings put system touch.pressure.scale 0.0") }
        btnDrag.setOnClickListener { runShell("settings put global window_animation_scale 0.0; settings put global transition_animation_scale 0.0; settings put global animator_duration_scale 0.0; setprop debug.egl.swapinterval 0") }
        
        btnReset.setOnClickListener { 
            isSystemOn = false
            btnToggle.text = "⚡ TAP TO ACTIVATE"
            btnToggle.background = createCyberButtonBg(colorDarkBlue, Color.GRAY, false)
            btnToggle.setTextColor(Color.GRAY)
            statusText.text = "STATUS: STANDBY"
            statusText.setTextColor(Color.GRAY)
            runShell("settings put global low_power 1; cmd power set-fixed-performance-mode-enabled false; settings put secure pointer_speed 0; settings put system pointer_speed 0; settings put global window_animation_scale 1.0; settings put global transition_animation_scale 1.0; settings put global animator_duration_scale 1.0; settings put system peak_refresh_rate 60.0; settings put system min_refresh_rate 60.0; setprop debug.egl.swapinterval 1; wm density reset") 
            triggerGlitchEffect(rootLayout)
        }
    }

    private fun triggerLightningStrike() {
        val root = window.decorView as ViewGroup
        
        val flashView = View(this).apply {
            setBackgroundColor(Color.parseColor("#6600F0FF"))
        }
        root.addView(flashView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))

        val lightningView = LightningView(this)
        root.addView(lightningView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))

        flashView.animate().alpha(0f).setDuration(400).withEndAction {
            root.removeView(flashView)
            root.removeView(lightningView)
        }.start()

        val shake = android.view.animation.TranslateAnimation(-10f, 10f, -10f, 10f)
        shake.duration = 300
        shake.repeatCount = 1
        root.startAnimation(shake)
    }

    private fun triggerGlitchEffect(view: View) {
        val shake = android.view.animation.TranslateAnimation(-20f, 20f, 0f, 0f)
        shake.duration = 100
        shake.repeatCount = 2
        view.startAnimation(shake)
    }

    inner class LightningView(context: android.content.Context) : View(context) {
        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            val paint = Paint().apply {
                color = Color.WHITE
                strokeWidth = 8f
                style = Paint.Style.STROKE
                setShadowLayer(25f, 0f, 0f, colorNeonBlue)
            }
            val path = Path()
            val w = width.toFloat()
            val h = height.toFloat()
            
            path.moveTo(w * 0.3f, 0f)
            path.lineTo(w * 0.4f, h * 0.4f)
            path.lineTo(w * 0.2f, h * 0.6f)
            path.lineTo(w * 0.5f, h)

            path.moveTo(w * 0.7f, 0f)
            path.lineTo(w * 0.6f, h * 0.5f)
            path.lineTo(w * 0.8f, h * 0.7f)
            path.lineTo(w * 0.65f, h)
            
            canvas.drawPath(path, paint)
        }
    }

    private fun createCyberPanel(): GradientDrawable {
        return GradientDrawable().apply {
            setColor(colorDarkBlue)
            setCornerRadius(15f)
            setStroke(2, colorNeonBlue)
        }
    }

    private fun createCyberButtonBg(bgColor: Int, strokeColor: Int, glow: Boolean): GradientDrawable {
        return GradientDrawable().apply {
            setColor(bgColor)
            setCornerRadius(10f)
            setStroke(3, strokeColor)
        }
    }

    private fun createCyberButton(textStr: String, bgColor: Int, textColor: Int, glow: Boolean): Button {
        return Button(this).apply {
            text = textStr
            setTextColor(textColor)
            textSize = 16f
            typeface = Typeface.create("sans-serif-black", Typeface.BOLD)
            background = createCyberButtonBg(bgColor, if (glow) colorNeonBlue else Color.parseColor("#333333"), glow)
            letterSpacing = 0.1f
            
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 0, 0, 30)
            layoutParams = params
            setPadding(0, 35, 0, 35)
        }
    }

    private fun runShell(cmd: String) {
        try {
            if (Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED) {
                val process = Shizuku.newProcess(arrayOf("sh", "-c", cmd), null, null)
                process.waitFor()
            } else {
                Shizuku.requestPermission(0)
            }
        } catch (e: Exception) { }
    }
}
