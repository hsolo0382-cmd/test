package com.example.athousandcourses.presentation.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.athousandcourses.R
import com.example.athousandcourses.databinding.CustomProfileViewBinding
import androidx.core.content.withStyledAttributes

class AccountCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {
//тут через биндинг
    private val binding = CustomProfileViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        if (attrs != null) {
            context.withStyledAttributes(attrs, R.styleable.CustomProfile) {
                val text = getString(R.styleable.CustomProfile_customText)
                binding.customText.text = text
            }
        }

    }

    fun setButtonClickListener(listener: OnButtonAccountViewClickListener) {
        binding.root.setOnClickListener {
            listener.onButtonClick()
        }

    }
    interface OnButtonAccountViewClickListener {
        fun onButtonClick()
    }
}