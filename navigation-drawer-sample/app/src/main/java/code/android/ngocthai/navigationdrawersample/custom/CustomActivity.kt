package code.android.ngocthai.navigationdrawersample.custom

import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import code.android.ngocthai.navigationdrawersample.R
import code.android.ngocthai.navigationdrawersample.entity.ItemCustom
import kotlinx.android.synthetic.main.activity_custom.*


class CustomActivity : AppCompatActivity(), CustomItemAdapter.ItemCustomListener {

    private val customAdapter = CustomItemAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom)

        setSupportActionBar(toolbar)

        val toggle = object : ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        ) {
            private val scaleFactor = 6f
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                val slideX = drawerView.width * slideOffset
                containerContent.translationX = slideX
                containerContent.scaleX = 1 - slideOffset / scaleFactor
                containerContent.scaleY = 1 - slideOffset / scaleFactor

                // hide content
                containerContent.scaleX = 1 - slideOffset
                containerContent.scaleY = 1 - slideOffset
            }
        }

        drawer_layout.setScrimColor(Color.TRANSPARENT)
        drawer_layout.drawerElevation = 0f

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        recyclerCustom.apply {
            adapter = customAdapter
            layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        }

        val listItem = createData()
        customAdapter.updateAllData(listItem)

    }

    override fun onItemClicked(item: ItemCustom) {
        Toast.makeText(applicationContext, "Click item: ${item.title}", Toast.LENGTH_SHORT).show()
        drawer_layout.closeDrawer(GravityCompat.START)
    }

    private fun createData(): List<ItemCustom> {
        val items = mutableListOf<ItemCustom>()
        items.add(ItemCustom(R.drawable.ic_home_black_24dp, "Home", 3))
        items.add(ItemCustom(R.drawable.ic_favorite_black_24dp, "Favorite", 0))
        items.add(ItemCustom(R.drawable.ic_search_black_24dp, "Search", 4))
        return items
    }
}
