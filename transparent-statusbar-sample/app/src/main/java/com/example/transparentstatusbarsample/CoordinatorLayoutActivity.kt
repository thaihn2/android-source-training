package com.example.transparentstatusbarsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_coordinator_layout.*
import kotlinx.android.synthetic.main.activity_coordinator_layout.view.*

class CoordinatorLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator_layout)

//        Utils.changeColorLightStatusBar(this)

        updateUI()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateUI() {
        // Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Glide.with(imageBackground.context).load(Utils.URL_IMAGE_DEFAULT).into(imageBackground)
        textContent.text = Utils.TEXT_CONTENT_DEFAULT

        updateCollapsing()
    }

    private fun updateCollapsing() {
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.collapsedappbar)
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.expandedappbar)

        collapsingToolbar.title = "Thaihn2"
    }
}
