package com.bbmobile.bbappbuilderdemo

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.*
import android.content.res.ColorStateList

class MainActivity : Activity() {
    private val prefs by lazy { getSharedPreferences("bb_settings", MODE_PRIVATE) }
    private var dark = false
    private var current = "Home"
    private lateinit var root: LinearLayout
    private lateinit var content: LinearLayout
    private lateinit var scroll: ScrollView
    private lateinit var nav: LinearLayout

    private val blue = Color.rgb(37, 99, 235)
    private val bg get() = if (dark) Color.rgb(15, 23, 42) else Color.rgb(247, 249, 252)
    private val surface get() = if (dark) Color.rgb(30, 41, 59) else Color.WHITE
    private val fg get() = if (dark) Color.WHITE else Color.rgb(20, 29, 45)
    private val muted get() = if (dark) Color.rgb(177, 190, 210) else Color.rgb(100, 116, 139)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(com.bbmobile.bbappbuilderdemo.R.style.AppTheme)
        super.onCreate(savedInstanceState)
        dark = prefs.getBoolean("dark_mode", false)
        window.statusBarColor = bg
        window.navigationBarColor = bg
        render()
    }

    private fun dp(v: Int) = (v * resources.displayMetrics.density).toInt()
    private fun text(value: String, size: Float, color: Int = fg, bold: Boolean = false): TextView =
        TextView(this).apply {
            text = value; textSize = size; setTextColor(color)
            if (bold) setTypeface(typeface, Typeface.BOLD)
        }
    private fun vertical(): LinearLayout = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
    private fun horizontal(): LinearLayout = LinearLayout(this).apply {
        orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL
    }
    private fun rounded(color: Int, radius: Int = 18): GradientDrawable =
        GradientDrawable().apply { setColor(color); cornerRadius = dp(radius).toFloat() }

    private fun render() {
        root = vertical().apply { setBackgroundColor(bg) }
        val header = horizontal().apply {
            setPadding(dp(20), dp(18), dp(20), dp(14))
            val brand = text("BB", 25f, blue, true)
            addView(brand)
            addView(text("  APP BUILDER", 12f, muted, true))
            addView(View(this@MainActivity), LinearLayout.LayoutParams(0, 1, 1f))
            addView(text("DEMO", 11f, muted, true))
        }
        root.addView(header)
        content = vertical().apply {
            setPadding(dp(20), dp(8), dp(20), dp(16))
        }
        scroll = ScrollView(this).apply { isFillViewport = true; clipToPadding = false }
        scroll.addView(content)
        root.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))
        nav = horizontal().apply {
            setBackgroundColor(surface)
            setPadding(dp(5), dp(7), dp(5), dp(7))
            elevation = dp(8).toFloat()
        }
        root.addView(nav)
        setContentView(root)
        showPage(current)
        updateSystemBars()
    }

    private fun updateSystemBars() {
        window.statusBarColor = bg
        window.navigationBarColor = surface
        window.decorView.systemUiVisibility = if (!dark) View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR else 0
    }

    private fun showPage(page: String) {
        current = page
        content.removeAllViews()
        when (page) {
            "Home" -> homePage()
            "Services" -> servicesPage()
            "Notifications" -> notificationsPage()
            "Settings" -> settingsPage()
            "About" -> aboutPage()
        }
        buildNavigation()
    }

    private fun title(t: String, subtitle: String? = null) {
        content.addView(text(t, 27f, fg, true))
        if (subtitle != null) {
            val sub = text(subtitle, 14f, muted)
            val p = LinearLayout.LayoutParams(-1, -2); p.topMargin = dp(6)
            content.addView(sub, p)
        }
    }

    private fun card(title: String, description: String, symbol: String = "✦") {
        val box = vertical().apply {
            background = rounded(surface)
            setPadding(dp(17), dp(16), dp(17), dp(16))
        }
        val line = horizontal()
        line.addView(text(symbol, 23f, blue, true))
        val labels = vertical().apply { setPadding(dp(12), 0, 0, 0) }
        labels.addView(text(title, 16f, fg, true))
        labels.addView(text(description, 13f, muted))
        line.addView(labels)
        box.addView(line)
        val lp = LinearLayout.LayoutParams(-1, -2); lp.topMargin = dp(12)
        content.addView(box, lp)
    }

    private fun homePage() {
        title("Welcome to BB", "Your simple starting point for great apps.")
        val hero = vertical().apply {
            background = rounded(Color.rgb(16, 24, 39), 22)
            setPadding(dp(20), dp(22), dp(20), dp(22))
        }
        hero.addView(text("BUILD SOMETHING GREAT", 11f, Color.rgb(147, 197, 253), true))
        val h = text("Ideas into apps.\nMade with BB.", 27f, Color.WHITE, true)
        val hp = LinearLayout.LayoutParams(-1, -2); hp.topMargin = dp(10)
        hero.addView(h, hp)
        val desc = text("A clean foundation for your next Android project.", 14f, Color.rgb(203, 213, 225))
        val descParams = LinearLayout.LayoutParams(-1, -2); descParams.topMargin = dp(9)
        hero.addView(desc, descParams)
        val lp = LinearLayout.LayoutParams(-1, -2); lp.topMargin = dp(18)
        content.addView(hero, lp)
        val section = text("Explore", 19f, fg, true)
        val sp = LinearLayout.LayoutParams(-1, -2); sp.topMargin = dp(22)
        content.addView(section, sp)
        card("Services", "Discover what you can build.", "⌘")
        card("Notifications", "Updates and announcements.", "♧")
    }

    private fun servicesPage() {
        title("Services", "A place for the tools and features you add next.")
        card("App development", "Plan and build Android experiences.", "▣")
        card("Design systems", "Reusable colours, layouts and components.", "✧")
        card("Project support", "Keep your app work organised.", "⚙")
    }

    private fun notificationsPage() {
        title("Notifications", "Your updates appear here.")
        card("You're all caught up", "There are no new notifications right now.", "✓")
    }

    private fun settingsPage() {
        title("Settings", "Make BB feel right for you.")
        val panel = vertical().apply {
            background = rounded(surface)
            setPadding(dp(16), dp(14), dp(16), dp(14))
        }
        val row = horizontal()
        val labels = vertical().apply {
            addView(text("Dark mode", 16f, fg, true))
            addView(text("Use a darker appearance", 13f, muted))
        }
        row.addView(labels, LinearLayout.LayoutParams(0, -2, 1f))
        val toggle = Switch(this).apply {
            isChecked = dark
            buttonTintList = ColorStateList.valueOf(blue)
            setOnCheckedChangeListener { _, checked ->
                dark = checked
                prefs.edit().putBoolean("dark_mode", checked).apply()
                render()
            }
        }
        row.addView(toggle)
        panel.addView(row)
        val lp = LinearLayout.LayoutParams(-1, -2); lp.topMargin = dp(16)
        content.addView(panel, lp)
        card("About BB App Builder Demo", "App information, version and credits.", "ⓘ")
        val about = Button(this).apply {
            text = "Open About"
            setTextColor(Color.WHITE)
            background = rounded(blue, 12)
            setOnClickListener { showPage("About") }
        }
        val bp = LinearLayout.LayoutParams(-1, dp(50)); bp.topMargin = dp(12)
        content.addView(about, bp)
    }

    private fun aboutPage() {
        title("About", "A small demo with a reusable foundation.")
        card("BB App Builder Demo", "Version 1.0.0\nPackage: com.bbmobile.bbappbuilderdemo", "BB")
        card("Built for Android", "Native Kotlin application. No account, tracking or network permissions are required.", "✓")
        val back = Button(this).apply {
            text = "Back to Settings"
            setTextColor(Color.WHITE); background = rounded(blue, 12)
            setOnClickListener { showPage("Settings") }
        }
        val lp = LinearLayout.LayoutParams(-1, dp(50)); lp.topMargin = dp(14)
        content.addView(back, lp)
    }

    private fun buildNavigation() {
        nav.removeAllViews()
        listOf("Home", "Services", "Notifications", "Settings").forEach { item ->
            val active = current == item
            val label = when(item) {
                "Home" -> "⌂\nHome"
                "Services" -> "▦\nServices"
                "Notifications" -> "♧\nAlerts"
                else -> "⚙\nSettings"
            }
            val button = TextView(this).apply {
                text = label
                textSize = 11f
                gravity = Gravity.CENTER
                setTextColor(if (active) blue else muted)
                setPadding(dp(2), dp(5), dp(2), dp(5))
                setOnClickListener { showPage(item) }
            }
            nav.addView(button, LinearLayout.LayoutParams(0, -2, 1f))
        }
    }

    @Deprecated("Deprecated in Android")
    override fun onBackPressed() {
        if (current == "About") showPage("Settings")
        else if (current != "Home") showPage("Home")
        else super.onBackPressed()
    }
}
