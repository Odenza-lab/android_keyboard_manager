package com.example.searchviewapp

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager

class KeyboardManager {

    interface OnKeyboardListener {
        fun onKeyboardVisible()
        fun onKeyboardHidden()
    }

    private var isKeyboardVisible = false
    private var height = 0

    fun setOnKeyboardListener(view: View, listener: OnKeyboardListener?) {

        val onGlobalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
            val contentHeight = view.height
            if (height != 0) {
                if (height < contentHeight && isKeyboardVisible) {
                    isKeyboardVisible = false
                    listener?.onKeyboardHidden()
                } else {
                    val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    val isAcceptingText = imm.isAcceptingText

                    if (isAcceptingText) {
                        isKeyboardVisible = true
                    }

                    if (isKeyboardVisible) {
                        listener?.onKeyboardVisible()
                    }
                }
            }
            height = contentHeight
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
    }
}

//class KeyboardManager2(private val mContext: Context) {
//
//    interface OnKeyboardListener {
//        fun onKeyboardVisible()
//        fun onKeyboardHidden()
//    }
//
//    private var mContentView: View? = null
//    private var mOnGlobalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener? = null
//    private var mIsKeyboardVisible = false
//
//    fun registerKeyboardListener(listener: OnKeyboardListener?, view: View?) {
//        mContentView = view
//        unregisterKeyboardListener()
//        mOnGlobalLayoutListener = object : ViewTreeObserver.OnGlobalLayoutListener {
//            private var mPreviousHeight = 0
//
//            override fun onGlobalLayout() {
//                val newHeight = mContentView!!.height
//                if (mPreviousHeight != 0) {
//                    if (mPreviousHeight < newHeight) {
//                        // In this place keyboard is hidden but navigation bar is appeared
//                        // will hide it
//                        Log.d(TAG, "onLayoutChangedDown")
//                        if (mIsKeyboardVisible) {
//                            mIsKeyboardVisible = false
//                            hideNavigationBar()
//                            listener?.onKeyboardHidden()
//                        }
//                    } else if (mPreviousHeight > newHeight) {
//                        // This block will be called when navigation bar is appeared
//                        // There are two cases:
//                        // 1. When something modal view (like dialog) is appeared
//                        // 2. When keyboard is appeared
//                        Log.d(TAG, "onLayoutChangedUp")
//
//                        // Will ask InputMethodManager.isAcceptingText() to detect if keyboard appeared or not.
//                        val imm = mContentView!!.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                        val isAcceptingText = imm.isAcceptingText
//
//                        if (isAcceptingText) {
//                            mIsKeyboardVisible = true
//                        }
//                        if (mIsKeyboardVisible) {
//                            listener?.onKeyboardVisible()
//                        }
//                    }
//                }
//                mPreviousHeight = newHeight
//            }
//        }
//        mContentView!!.viewTreeObserver.addOnGlobalLayoutListener(mOnGlobalLayoutListener)
//    }
//
//    private fun unregisterKeyboardListener() {
//        if (mOnGlobalLayoutListener != null) {
//            mContentView!!.viewTreeObserver.removeOnGlobalLayoutListener(mOnGlobalLayoutListener)
//        }
//    }
//
//    private fun hideNavigationBar() {
//        if (mContext is Activity) {
//            mContext.window.decorView.systemUiVisibility =
//                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//        }
//    }
//
//    companion object {
//        private val TAG = KeyboardManager::class.java.simpleName
//    }
//}
