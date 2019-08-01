package com.transportation.bookcar.core.adapter

import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.SparseArray
import android.view.ViewGroup
import com.transportation.bookcar.core.view.CoreFragment

/**
 * Created on 2/13/2018.
 */
abstract class BaseFragmentPagerAdapter<T : CoreFragment<*>> constructor(activity: AppCompatActivity) :
        FragmentPagerAdapter(activity.supportFragmentManager) {
    private val registeredFragments: SparseArray<T> = SparseArray<T>()
    var currentPosition: Int = 0
    
    // Register the fragment when the item is instantiated
    @Suppress("UNCHECKED_CAST")
    override fun instantiateItem(container: ViewGroup, position: Int): T {
        val fragment = super.instantiateItem(container, position) as T
        registeredFragments.put(position, fragment)
        onInstantiateItem(position)
        return fragment
    }
    
    override fun setPrimaryItem(container: ViewGroup, position: Int, item: Any) {
        super.setPrimaryItem(container as ViewGroup, position, item)
        currentPosition = position
    }
    
    @SuppressWarnings("unused")
    protected fun onInstantiateItem(pPosition: Int) {}
    
    // Unregister when the item is inactive
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        registeredFragments.remove(position)
        super.destroyItem(container, position, `object`)
        onDestroyItem(position)
    }
    
    @SuppressWarnings("unused")
    protected fun onDestroyItem(pPosition: Int) {}
    
    // Returns the fragment for the position (if instantiated)
    fun getRegisteredFragment(position: Int): T = registeredFragments.get(position)
    
    fun getActiveFragment(): T = registeredFragments.get(currentPosition)
}
