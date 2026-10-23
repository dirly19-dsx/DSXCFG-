package com.dsx.cfg

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import rikka.shizuku.Shizuku
import java.util.Random

class MainActivity : Activity() {

    // Palet Warna Beringin
    private val bgDark = Color.parseColor("#05100B")
    private val cardBg = Color.parseColor("#0D1F16")
    private val neonGreen = Color.parseColor("#00FF88")
    private val softGold = Color.parseColor("#D4AF37")
    private val textGray = Color.parseColor("#A3B8AD")
    private val colorRed = Color.parseColor("#E63946")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showDashboard()
    }

    private fun showDashboard() {
        val root = FrameLayout(this).apply { setBackgroundColor(bgDark) }

        // Background Daun Jatuh (Beringin Vibe)
        val leafView = FallingLeavesView(this)
        root.addView(leafView, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT))

        val scrollView = ScrollView(this)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(30, 50, 30, 30)
        }

        // --- 1. TOP STATS (3 KOTAK) ---
        val statsRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            lp.setMargins(0, 0, 0, 20)
            layoutParams = lp
        }

        val stat1 = createStatBox("🌿", "12", "MODULE")
        val stat2 = createStatBox("👤", "ADMIN", "ROLE")
        val stat3 = createStatBox("🟢", "ONLINE", "STATUS")

        val lpStat = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        lpStat.setMargins(5, 0, 5, 0)
        statsRow.addView(stat1, lpStat)
        statsRow.addView(stat2, lpStat)
        statsRow.addView(stat3, lpStat)


        // --- 2. PROFILE CARD ---
        val profileCard = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            background = createCardBg(neonGreen)
            setPadding(30, 30, 30, 30)
            val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            lp.setMargins(0, 0, 0, 20)
            layoutParams = lp
        }

        val profileRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        val avatar = TextView(this).apply {
            text = "👤"
            textSize = 40f
            setTextColor(neonGreen)
        }

        val nameCol = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 0, 0, 0)
            val lp = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            layoutParams = lp
        }

        val nameText = TextView(this).apply {
            text = "DSX BRINGIN"
            setTextColor(Color.WHITE)
            textSize = 20f
            typeface = Typeface.DEFAULT_BOLD
        }

        val badgeRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(0, 10, 0, 0)
        }
        val badgeAdmin = createBadge("ADMIN", neonGreen)
        val badgeSecured = createBadge("SECURED", softGold)
        val lpBadge = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        lpBadge.setMargins(0, 0, 10, 0)
        badgeRow.addView(badgeAdmin, lpBadge)
        badgeRow.addView(badgeSecured)

        nameCol.addView(nameText)
        nameCol.addView(badgeRow)

        val statusPill = TextView(this).apply {
            text = "● ONLINE"
            setTextColor(Color.BLACK)
            textSize = 10f
            typeface = Typeface.DEFAULT_BOLD
            background = createPillBg(neonGreen)
            setPadding(20, 10, 20, 10)
        }

        profileRow.addView(avatar)
        profileRow.addView(nameCol)
        profileRow.addView(statusPill)

        val expireText = TextView(this).apply {
            text = "⌛ ACCESS EXPIRES: LIFETIME"
            setTextColor(textGray)
            textSize = 12f
            setPadding(0, 20, 0, 0)
        }

        profileCard.addView(profileRow)
        profileCard.addView(expireText)


        // --- 3. BANNER IMAGE (Ganti pakai Kotak Teks Glow) ---
        val bannerCard = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            background = createCardBg(softGold)
            gravity = Gravity.CENTER
            setPadding(0, 50, 0, 50)
            val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            lp.setMargins(0, 0, 0, 20)
            layoutParams = lp
        }
        val bannerText = TextView(this).apply {
            text = "DSX BRINGIN"
            setTextColor(softGold)
            textSize = 30f
            typeface = Typeface.create("serif", Typeface.BOLD)
            setShadowLayer(15f, 0f, 0f, softGold)
        }
        bannerCard.addView(bannerText)


        // --- 4. ATTACK SEQUENCE CARD ---
        val attackCard = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            background = createCardBg(neonGreen)
            setPadding(30, 30, 30, 30)
            val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            lp.setMargins(0, 0, 0, 20)
            layoutParams = lp
        }

        val attackTitle = TextView(this).apply {
            text = "⚡ SYSTEM SEQUENCE"
            setTextColor(neonGreen)
            textSize = 16f
            typeface = Typeface.DEFAULT_BOLD
            setPadding(0, 0, 0, 20)
        }

        // Toggle Row (Tiruan Number/Group)
        val toggleRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            background = createInputBg()
            setPadding(10, 10, 10, 10)
            val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            lp.setMargins(0, 0, 0, 20)
            layoutParams = lp
        }
        val btnMode1 = TextView(this).apply {
            text = "ACTIVE"
            setTextColor(Color.BLACK)
            gravity = Gravity.CENTER
            background = createPillBg(neonGreen)
            setPadding(20, 10, 20, 10)
            val lp = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            layoutParams = lp
        }
        val btnMode2 = TextView(this).apply {
            text = "STANDBY"
            setTextColor(textGray)
            gravity = Gravity.CENTER
            val lp = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            layoutParams = lp
        }
        toggleRow.addView(btnMode1)
        toggleRow.addView(btnMode2)

        val inputLabel = TextView(this).apply {
            text = "⌖ TARGET DESTINATION"
            setTextColor(neonGreen)
            textSize = 12f
            setPadding(0, 0, 0, 10)
        }

        val etInput = EditText(this).apply {
            hint = "Masukkan nomor target / Key..."
            setTextColor(Color.WHITE)
            setHintTextColor(textGray)
            background = createInputBg()
            setPadding(30, 30, 30, 30)
            val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            lp.setMargins(0, 0, 0, 20)
            layoutParams = lp
        }

        val actionRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
        }

        val btnLaunch = Button(this).apply {
            text = "LAUNCH FF"
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
            background = createRoundBtn(neonGreen)
            setPadding(0, 25, 0, 25)
            val lp = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            lp.setMargins(0, 0, 10, 0)
            layoutParams = lp
        }

        val btnBoost = Button(this).apply {
            text = "BOOST FPS"
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
            background = createRoundBtn(softGold)
            setPadding(0, 25, 0, 25)
            val lp = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            lp.setMargins(10, 0, 0, 0)
            layoutParams = lp
        }

        actionRow.addView(btnLaunch)
        actionRow.addView(btnBoost)

        attackCard.addView(attackTitle)
        attackCard.addView(toggleRow)
        attackCard.addView(inputLabel)
        attackCard.addView(etInput)
        attackCard.addView(actionRow)

        // --- 5. BOTTOM NAVIGATION (Tiruan) ---
        val bottomNav = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            background = createCardBg(neonGreen)
            setPadding(0, 20, 0, 20)
            gravity = Gravity.CENTER
        }
        val navItems = listOf("HOME", "RAT", "INFO", "TOOLS", "THEME")
        for (item in navItems) {
            val tv = TextView(this).apply {
                text = item
                setTextColor(if (item == "HOME") neonGreen else textGray)
                textSize = 10f
                typeface = Typeface.DEFAULT_BOLD
                gravity = Gravity.CENTER
                val lp = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                layoutParams = lp
            }
            bottomNav.addView(tv)
        }

        // Susun semua ke layout utama
        layout.addView(statsRow)
        layout.addView(profileCard)
        layout.addView(bannerCard)
        layout.addView(attackCard)
        layout.addView(bottomNav)

        scrollView.addView(layout)
        root.addView(scrollView)
        setContentView(root)

        // Animasi Masuk
        layout.alpha = 0f
        layout.translationY = 50f
        layout.animate().alpha(1f).translationY(0f).setDuration(1000).start()

        // --- LOGIKA TOMBOL ---
        var isSystemOn = false
        btnBoost.setOnClickListener {
            isSystemOn = !isSystemOn
            if (isSystemOn) {
                btnBoost.text = "ON"
                btnBoost.background = createRoundBtn(neonGreen)
                runShell("am kill-all; pm trim-caches 999G; settings put global low_power 0; cmd power set-fixed-performance-mode-enabled true; settings put secure pointer_speed 7; settings put system pointer_speed 7; settings put global window_animation_scale 0.0; settings put global transition_animation_scale 0.0; settings put global animator_duration_scale 0.0; setprop debug.egl.swapinterval 0; settings put system peak_refresh_rate 120.0; settings put system min_refresh_rate 120.0")
            } else {
                btnBoost.text = "OFF"
                btnBoost.background = createRoundBtn(colorRed)
                runShell("settings put global low_power 1; cmd power set-fixed-performance-mode-enabled false; settings put secure pointer_speed 0; settings put system pointer_speed 0; settings put global window_animation_scale 1.0; settings put global transition_animation_scale 1.0; settings put global animator_duration_scale 1.0; settings put system peak_refresh_rate 60.0; settings put system min_refresh_rate 60.0; setprop debug.egl.swapinterval 1; wm density reset")
            }
        }

        btnLaunch.setOnClickListener {
            val intent = packageManager.getLaunchIntentForPackage("com.dts.freefireth")
            if (intent != null) startActivity(intent) else Toast.makeText(this, "Free Fire tidak terinstall!", Toast.LENGTH_SHORT).show()
        }
    }

    // ==========================================
    // KOMPONEN DESAIN
    // ==========================================
    private fun createStatBox(icon: String, value: String, label: String): LinearLayout {
        return LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            background = createCardBg(neonGreen)
            setPadding(20, 20, 20, 20)
            gravity = Gravity.CENTER
            
            val ic = TextView(this@MainActivity).apply { text = icon; textSize = 16f; gravity = Gravity.CENTER }
            val valTxt = TextView(this@MainActivity).apply { text = value; setTextColor(Color.WHITE); typeface = Typeface.DEFAULT_BOLD; textSize = 14f; gravity = Gravity.CENTER }
            val lblTxt = TextView(this@MainActivity).apply { text = label; setTextColor(textGray); textSize = 10f; gravity = Gravity.CENTER }
            
            addView(ic)
            addView(valTxt)
            addView(lblTxt)
        }
    }

    private fun createBadge(text: String, color: Int): TextView {
        return TextView(this).apply {
            this.text = text
            setTextColor(Color.BLACK)
            textSize = 9f
            typeface = Typeface.DEFAULT_BOLD
            background = createPillBg(color)
            setPadding(15, 5, 15, 5)
        }
    }

    private fun createCardBg(strokeColor: Int): GradientDrawable {
        return GradientDrawable().apply {
            setColor(cardBg)
            setCornerRadius(15f)
            setStroke(3, strokeColor)
        }
    }

    private fun createInputBg(): GradientDrawable {
        return GradientDrawable().apply {
            setColor(Color.parseColor("#05100B"))
            setCornerRadius(20f)
            setStroke(2, textGray)
        }
    }

    private fun createPillBg(color: Int): GradientDrawable {
        return GradientDrawable().apply {
            setColor(color)
            setCornerRadius(50f)
        }
    }

    private fun createRoundBtn(color: Int): GradientDrawable {
        return GradientDrawable().apply {
            setColor(color)
            setCornerRadius(20f)
        }
    }

    // ==========================================
    // ANIMASI DAUN JATUH
    // ==========================================
    inner class FallingLeavesView(context: android.content.Context) : View(context) {
        private val paint = Paint().apply { isAntiAlias = true }
        private val leaves = mutableListOf<Leaf>()
        private val random = Random()
        private val handler = Handler(Looper.getMainLooper())
        private val runnable = object : Runnable {
            override fun run() {
                updateLeaves()
                invalidate()
                handler.postDelayed(this, 30)
            }
        }

        init { handler.post(runnable) }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            for (leaf in leaves) {
                paint.color = leaf.color
                paint.alpha = (leaf.alpha * 255).toInt()
                // Gambar bentuk daun (oval lonjong)
                canvas.save()
                canvas.rotate(leaf.rotation, leaf.x, leaf.y)
                canvas.drawOval(leaf.x, leaf.y, leaf.x + leaf.size * 2, leaf.y + leaf.size, paint)
                canvas.restore()
            }
        }

        private fun updateLeaves() {
            if (leaves.size < 30) {
                leaves.add(Leaf(
                    x = random.nextFloat() * width,
                    y = -50f,
                    speed = random.nextFloat() * 2f + 1f,
                    size = random.nextFloat() * 4f + 2f,
                    alpha = random.nextFloat() * 0.4f + 0.2f,
                    color = if (random.nextBoolean()) neonGreen else softGold,
                    drift = (random.nextFloat() - 0.5f) * 1.5f,
                    rotation = random.nextFloat() * 360f
                ))
            }

            val iterator = leaves.iterator()
            while (iterator.hasNext()) {
                val leaf = iterator.next()
                leaf.y += leaf.speed
                leaf.x += Math.sin(leaf.y / 40.0).toFloat() * leaf.drift
                leaf.rotation += 1f
                if (leaf.y > height + 50) iterator.remove()
            }
        }

        override fun onDetachedFromWindow() {
            super.onDetachedFromWindow()
            handler.removeCallbacks(runnable)
        }
    }

    data class Leaf(var x: Float, var y: Float, val speed: Float, val size: Float, val alpha: Float, val color: Int, val drift: Float, var rotation: Float)

    // ==========================================
    // SHIZUKU
    // ==========================================
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
