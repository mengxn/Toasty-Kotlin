package me.codego.toasty

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.Typeface.BOLD_ITALIC
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.widget.Toast
import me.codego.toasty.demo.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById(R.id.button_error_toast).setOnClickListener { toast.error(this, "this is an error toast") }
        findViewById(R.id.button_success_toast).setOnClickListener { toast.success(this, "this is an error toast") }
        findViewById(R.id.button_info_toast).setOnClickListener { toast.info(this, "Here is some info for you.") }
        findViewById(R.id.button_warning_toast).setOnClickListener { toast.warning(this, "Beware of the dog.") }
        findViewById(R.id.button_normal_toast_wo_icon).setOnClickListener { toast.normal(this, "Normal toast w/o icon") }
        findViewById(R.id.button_normal_toast_w_icon).setOnClickListener {
            val icon = resources.getDrawable(R.drawable.ic_pets_white_48dp)
            toast.normal(this)
                    .withIcon(true)
                    .icon(icon)
                    .message("Normal toast w/ icon")
                    .show()
        }
        findViewById(R.id.button_info_toast_with_formatting).setOnClickListener { toast.info(this, getFormattedMessage()) }
        findViewById(R.id.button_custom_config).setOnClickListener {
            Toasty.Config
                    .setTextColor(Color.GREEN)
                    .setToastTypeface(Typeface.createFromAsset(assets, "PCap Terminal.otf"))
                    .apply()
            toast.custom(this, "sudo kill -9 everyone", resources.getDrawable(R.drawable.laptop512),
                    Color.BLACK, Toast.LENGTH_SHORT, true, true).show()
            Toasty.Config.reset()
            toast.success(this, "This is an error toast.")
        }
    }

    private fun getFormattedMessage(): CharSequence {
        val prefix = "Formatted "
        val highlight = "bold italic"
        val suffix = " text"
        val ssb = SpannableStringBuilder(prefix).append(highlight).append(suffix)
        val prefixLen = prefix.length
        ssb.setSpan(StyleSpan(BOLD_ITALIC),
                prefixLen, prefixLen + highlight.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return ssb
    }
}
