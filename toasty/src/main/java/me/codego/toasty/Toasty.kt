package me.codego.toasty

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.annotation.CheckResult
import android.support.annotation.ColorInt
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast

/**
 * Created by mengxn on 2017/6/8.
 */
class Toasty {

    companion object {
        private var DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF")
        private var ERROR_COLOR = Color.parseColor("#D50000")
        private var INFO_COLOR = Color.parseColor("#3F51B5")
        private var SUCCESS_COLOR = Color.parseColor("#388E3C")
        private var WARNING_COLOR = Color.parseColor("#FFA900")
        private val NORMAL_COLOR = Color.parseColor("#353A3E")

        private val LOADED_TOAST_TYPEFACE = Typeface.create("sans-serif-condensed", Typeface.NORMAL)
        private var currentTypeface = LOADED_TOAST_TYPEFACE
        private var textSize = 16 // in SP

        private var tintIcon = true
    }

    fun normal(context: Context): Builder {
        return Builder(context)
                .withIcon(false)
                .textColor(DEFAULT_TEXT_COLOR)
                .shouldTint(false)
                .duration(Toast.LENGTH_SHORT)
    }

    fun normal(context: Context, text: CharSequence) {
        normal(context).message(text).show()
    }

    fun warning(context: Context): Builder {
        return Builder(context)
                .withIcon(true)
                .icon(ToastyUtils.getDrawable(context, R.drawable.ic_error_outline_white_48dp))
                .textColor(DEFAULT_TEXT_COLOR)
                .shouldTint(true)
                .tintColor(WARNING_COLOR)
                .duration(Toast.LENGTH_SHORT)
    }

    fun warning(context: Context, text: CharSequence) {
        warning(context).message(text).show()
    }

    fun info(context: Context): Builder {
        val builder = Builder(context)
        return builder
                .withIcon(true)
                .icon(ToastyUtils.getDrawable(context, R.drawable.ic_info_outline_white_48dp))
                .textColor(DEFAULT_TEXT_COLOR)
                .shouldTint(true)
                .tintColor(INFO_COLOR)
                .duration(Toast.LENGTH_SHORT)
    }

    fun info(context: Context, text: CharSequence) {
        info(context).message(text).show()
    }

    fun success(context: Context): Builder {
        return Builder(context)
                .withIcon(true)
                .icon(ToastyUtils.getDrawable(context, R.drawable.ic_check_white_48dp))
                .textColor(DEFAULT_TEXT_COLOR)
                .shouldTint(true)
                .tintColor(SUCCESS_COLOR)
                .duration(Toast.LENGTH_SHORT)
    }

    fun success(context: Context, text: CharSequence) {
        success(context).message(text).show()
    }

    fun error(context: Context): Builder {
        val builder = Builder(context)
        return builder
                .withIcon(true)
                .icon(ToastyUtils.getDrawable(context, R.drawable.ic_clear_white_48dp))
                .textColor(DEFAULT_TEXT_COLOR)
                .shouldTint(true)
                .tintColor(ERROR_COLOR)
                .duration(Toast.LENGTH_SHORT)
    }

    fun error(context: Context, text: CharSequence) {
        error(context).message(text).show()
    }

    fun custom(context: Context, message: CharSequence, icon: Drawable?,
               @ColorInt tintColor: Int, duration: Int,
               withIcon: Boolean, shouldTint: Boolean): Toast {
        var icon = icon
        val currentToast = Toast(context)
        val toastLayout = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.toast_layout, null)
        val toastIcon = toastLayout.findViewById(R.id.toast_icon)
        val toastTextView = toastLayout.findViewById(R.id.toast_text) as TextView
        val drawableFrame: Drawable

        if (shouldTint)
            drawableFrame = ToastyUtils.tint9PatchDrawableFrame(context, tintColor)
        else
            drawableFrame = ToastyUtils.getDrawable(context, R.drawable.toast_frame)
        ToastyUtils.setBackground(toastLayout, drawableFrame)

        if (withIcon) {
            if (icon == null)
                throw IllegalArgumentException("Avoid passing 'icon' as null if 'withIcon' is set to true")
            if (tintIcon)
                icon = ToastyUtils.tintIcon(icon, DEFAULT_TEXT_COLOR)
            ToastyUtils.setBackground(toastIcon, icon)
        } else {
            toastIcon.visibility = View.GONE
        }

        toastTextView.setTextColor(DEFAULT_TEXT_COLOR)
        toastTextView.text = message
        toastTextView.typeface = currentTypeface
        toastTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize.toFloat())

        currentToast.view = toastLayout
        currentToast.duration = duration
        return currentToast
    }


    object Config {
        @ColorInt
        private var DEFAULT_TEXT_COLOR = Toasty.DEFAULT_TEXT_COLOR
        @ColorInt
        private var ERROR_COLOR = Toasty.ERROR_COLOR
        @ColorInt
        private var INFO_COLOR = Toasty.INFO_COLOR
        @ColorInt
        private var SUCCESS_COLOR = Toasty.SUCCESS_COLOR
        @ColorInt
        private var WARNING_COLOR = Toasty.WARNING_COLOR

        private var typeface = Toasty.currentTypeface
        private var textSize = Toasty.textSize

        private var tintIcon = Toasty.tintIcon

        @CheckResult
        fun setTextColor(@ColorInt textColor: Int): Config {
            DEFAULT_TEXT_COLOR = textColor
            return this
        }

        @CheckResult
        fun setErrorColor(@ColorInt errorColor: Int): Config {
            ERROR_COLOR = errorColor
            return this
        }

        @CheckResult
        fun setInfoColor(@ColorInt infoColor: Int): Config {
            INFO_COLOR = infoColor
            return this
        }

        @CheckResult
        fun setSuccessColor(@ColorInt successColor: Int): Config {
            SUCCESS_COLOR = successColor
            return this
        }

        @CheckResult
        fun setWarningColor(@ColorInt warningColor: Int): Config {
            WARNING_COLOR = warningColor
            return this
        }

        @CheckResult
        fun setToastTypeface(typeface: Typeface): Config {
            this.typeface = typeface
            return this
        }

        @CheckResult
        fun setTextSize(sizeInSp: Int): Config {
            this.textSize = sizeInSp
            return this
        }

        @CheckResult
        fun tintIcon(tintIcon: Boolean): Config {
            this.tintIcon = tintIcon
            return this
        }

        fun apply() {
            Toasty.DEFAULT_TEXT_COLOR = DEFAULT_TEXT_COLOR
            Toasty.ERROR_COLOR = ERROR_COLOR
            Toasty.INFO_COLOR = INFO_COLOR
            Toasty.SUCCESS_COLOR = SUCCESS_COLOR
            Toasty.WARNING_COLOR = WARNING_COLOR
            Toasty.currentTypeface = typeface
            Toasty.textSize = textSize
            Toasty.tintIcon = tintIcon
        }

        fun reset() {
            Toasty.DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF")
            Toasty.ERROR_COLOR = Color.parseColor("#D50000")
            Toasty.INFO_COLOR = Color.parseColor("#3F51B5")
            Toasty.SUCCESS_COLOR = Color.parseColor("#388E3C")
            Toasty.WARNING_COLOR = Color.parseColor("#FFA900")
            Toasty.currentTypeface = LOADED_TOAST_TYPEFACE
            Toasty.textSize = 16
            Toasty.tintIcon = true
        }
    }


    inner class Builder(val mContext: Context) {
        private var mMessage: CharSequence? = null
        private var mDuration: Int = 0
        private var mIcon: Drawable? = null
        private var mTextColor: Int = 0
        private var mTintColor: Int = 0
        private var mWithIcon: Boolean = false
        private var mShouldTint: Boolean = false

        fun message(message: CharSequence): Builder {
            mMessage = message
            return this
        }

        fun duration(duration: Int): Builder {
            mDuration = duration
            return this
        }

        fun icon(icon: Drawable): Builder {
            mIcon = icon
            mWithIcon = true
            return this
        }

        fun withIcon(withIcon: Boolean): Builder {
            mWithIcon = withIcon
            return this
        }

        fun textColor(color: Int): Builder {
            mTextColor = color
            return this
        }

        fun tintColor(color: Int): Builder {
            mTintColor = color
            mShouldTint = true
            return this
        }

        fun shouldTint(shouldTint: Boolean): Builder {
            mShouldTint = shouldTint
            return this
        }

        fun build(): Toast {
            return custom(mContext, mMessage!!, mIcon, mTintColor, mDuration, mWithIcon, mShouldTint)
        }

        fun show() {
            build().show()
        }
    }

}

val Context.toast: Toasty
    get() = Toasty()