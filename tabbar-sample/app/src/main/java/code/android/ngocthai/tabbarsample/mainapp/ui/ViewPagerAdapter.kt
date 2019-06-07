package code.android.ngocthai.tabbarsample.mainapp.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val mFragments = arrayListOf<Fragment>()
    private val mTitles = arrayListOf<String>()

    override fun getCount(): Int = mFragments.size

    override fun getItem(position: Int): Fragment = mFragments[position]

    fun addFragment(fragment: Fragment, title: String) {
        mFragments.add(fragment)
        mTitles.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        // return null if you want to show only icon
        return mTitles[position]
    }
}
