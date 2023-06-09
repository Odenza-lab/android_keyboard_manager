package com.example.searchviewapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView


class MainActivity : AppCompatActivity() {

    private lateinit var searchView: SearchView
    private lateinit var contentView: View

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchView = findViewById(R.id.search_view)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.v("QueryTextSubmit TAG", "" + query)
                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.v("QueryTextChange TAG", "" + newText)
                return false
            }
        })

        contentView = findViewById(R.id.content)

//        contentView.viewTreeObserver.addOnGlobalLayoutListener {
//            val contentHeight = contentView.height
//
//            if (height < contentHeight && isKeyboardVisible) {
//                isKeyboardVisible = false
//                searchView.clearFocus()
//                Log.v("onKeyboardHidden TAG", "" + true)
//
//            } else if (height > contentHeight) {
//                val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
//                val isAcceptingText = imm?.isAcceptingText ?: false
//
//                if (isAcceptingText) {
//                    isKeyboardVisible = true
//                }
//                if (isKeyboardVisible) {
//                    // listener?.onKeyboardVisible()
//                    Log.v("onKeyboardVisible TAG", "" + true)
//
//                }
//            }
//
//            height = contentHeight
//        }

        val onKeyboardManager = object : KeyboardManager.OnKeyboardListener {
            override fun onKeyboardVisible() {
                Log.v("onKeyboardVisible TAG", "")
            }

            override fun onKeyboardHidden() {
                searchView.clearFocus()
                Log.v("onKeyboardHidden TAG", "")
            }
        }

        val keyboardManager = KeyboardManager()
        keyboardManager.setOnKeyboardListener(contentView, onKeyboardManager)
    }

    fun hideKeyboard(view: View) {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.v("onBackPressed TAG", "" + false)
    }
}

