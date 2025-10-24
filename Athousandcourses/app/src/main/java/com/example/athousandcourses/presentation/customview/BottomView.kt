package com.example.athousandcourses.presentation.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.athousandcourses.R

class BottomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStylerAttr: Int = 0,
) : LinearLayout(context, attrs, defStylerAttr) {
    //тут через findViewById
    private val homeLin: LinearLayout
    private val homeimg: ImageView
    private val homeTxt: TextView

    private val favoriteLin: LinearLayout
    private val favoriteImg: ImageView
    private val favoriteTxt: TextView

    private val persLin: LinearLayout
    private val persimg: ImageView
    private val persTxt: TextView


    init {

        val root = inflate(context, R.layout.custombottomnav, this)
        homeLin = root.findViewById(R.id.for_home)
        homeimg = root.findViewById(R.id.home_img)
        homeTxt = root.findViewById(R.id.home_txt)

        favoriteLin = root.findViewById(R.id.for_favorite)
        favoriteImg = root.findViewById(R.id.favorite_img)
        favoriteTxt = root.findViewById(R.id.favorite_txt)

        persLin = root.findViewById(R.id.for_account)
        persimg = root.findViewById(R.id.account_img)
        persTxt = root.findViewById(R.id.account_txt)

        homeLin.setOnClickListener { home() }
        favoriteLin.setOnClickListener { fav() }
        persLin.setOnClickListener { pers() }

    }

    fun setButtonClickListener(listener: OnButtonClickListener) {

        homeLin.setOnClickListener {
            home()
            listener.onButton1Click()
        }
        favoriteLin.setOnClickListener {
            fav()
            listener.onButton2Click()
        }
        persLin.setOnClickListener {
            pers()
            listener.onButton3Click()
        }

    }

    interface OnButtonClickListener {
        fun onButton1Click()
        fun onButton2Click()
        fun onButton3Click()
    }
    fun setSelectedIndex(index: Int) {

        when(index) {
            1 -> home()
            2 -> fav()
            3 -> pers()
        }
    }
    fun pers() {


        homeimg.setImageResource(R.drawable.home)
        homeTxt.setTextColor(ContextCompat.getColor(context, R.color.white))

        favoriteImg.setImageResource(R.drawable.favorite)
        favoriteTxt.setTextColor(ContextCompat.getColor(context, R.color.white))


        persimg.setImageResource(R.drawable.account_green)
        persTxt.setTextColor(ContextCompat.getColor(context, R.color.green))
    }


    fun fav() {
        homeimg.setImageResource(R.drawable.home)
        homeTxt.setTextColor(ContextCompat.getColor(context, R.color.white))
        
        favoriteImg.setImageResource(R.drawable.favorite_green)
        favoriteTxt.setTextColor(ContextCompat.getColor(context, R.color.green))

        persimg.setImageResource(R.drawable.account)
        persTxt.setTextColor(ContextCompat.getColor(context, R.color.white))
    }

    fun home() {
        homeimg.setImageResource(R.drawable.home_green)
        homeTxt.setTextColor(ContextCompat.getColor(context, R.color.green))

        favoriteImg.setImageResource(R.drawable.favorite)
        favoriteTxt.setTextColor(ContextCompat.getColor(context, R.color.white))

        persimg.setImageResource(R.drawable.account)
        persTxt.setTextColor(ContextCompat.getColor(context, R.color.white))
    }
}