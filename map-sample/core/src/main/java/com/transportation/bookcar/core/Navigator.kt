package com.transportation.bookcar.core

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.transportation.bookcar.core.view.CoreActivity
import com.transportation.bookcar.core.view.CoreFragment

/**
 * Created on 2/1/2018.
 */

/**
 * This class is for control fragment/activity navigation implementation in an activity
 */
abstract class Navigator(
        var exitPoint: Int,
        val activity: AppCompatActivity,
        val fragmentContainerId: Int
) {
    open val fragmentManager: FragmentManager
        get() = activity.supportFragmentManager
    
    /**
     * Back to root fragment
     */
    constructor(exitPoint: Int, activity: CoreActivity<*>) : this(
            exitPoint,
            activity,
            activity.fragmentContainerId
    )
    
    fun backToRoot(): Boolean {
        if (fragmentManager.backStackEntryCount <= 1)
            return false
        val rootFragmentName = fragmentManager.getBackStackEntryAt(0).name
        fragmentManager.popBackStackImmediate(rootFragmentName, 0)
        return true
    }
    
    /**
     * Navigate back fragment when user press device back button or back action on UI
     * @return true if current fragment is handled this action manually/locally by itself
     * otherwise return false for pop current fragment from back stack
     */
    open fun onBackPressed(): Boolean {
        if (fragmentManager.backStackEntryCount <= exitPoint)
            return false
        //find last fragment in stack
        var lastFragment: CoreFragment<*>? = null
        if (fragmentManager.backStackEntryCount > exitPoint) {
            val lastFragmentName = fragmentManager.getBackStackEntryAt(fragmentManager.backStackEntryCount - 1)
                    .name
            val foundFragment = fragmentManager.findFragmentByTag(lastFragmentName)
            if (foundFragment is CoreFragment<*>) {
                lastFragment = foundFragment
            }
        }
        //if fragment handle back press by itself then let it do, otherwise main fragment manager will
        //handle (pop last fragment)
        if (lastFragment == null)
            return false
        if (!lastFragment.onBackPressed()) {
            fragmentManager.popBackStackImmediate()
        }
        return true
    }
    
    fun startFragment(
            fragmentClass: Class<out CoreFragment<*>>,
            arguments: Bundle? = null,
            pIsAddToBackStack: Boolean = true,
            transitionNames: List<String>? = null,
            transitionViews: List<View>? = null
    ): Int {
        if (fragmentContainerId == 0)
            return -1
        
        val nextFragment = CoreFragment.newInstance(fragmentClass, arguments)
        val fragmentTransaction = fragmentManager.beginTransaction()
        val backStackEntryCount = fragmentManager.backStackEntryCount
        
        //add new fragment to stack
        val tag = fragmentClass.name + backStackEntryCount
        fragmentTransaction.replace(fragmentContainerId, nextFragment, tag)
        if (pIsAddToBackStack)
            fragmentTransaction.addToBackStack(tag)
        
        //add share element transition
        if (transitionNames != null && transitionViews != null && transitionNames.size == transitionViews.size) {
            for (i in transitionNames.indices) {
                fragmentTransaction.addSharedElement(transitionViews[i], transitionNames[i])
            }
        }
        
        val backStackId = fragmentTransaction.commit()
        fragmentManager.executePendingTransactions()
        return backStackId
    }
}

/**
 * This class is for control fragment navigation implementation in an activity or activity in application
 */
abstract class ActivityNavigator(activity: CoreActivity<*>) : Navigator(1, activity)

abstract class AppCompatActivityNavigator(activity: AppCompatActivity, fragmentContainerId: Int) :
        Navigator(1, activity, fragmentContainerId)

/**
 * This class is for control fragment navigation implementation in an activity
 */
abstract class FragmentNavigator(val fragment: Fragment, fragmentContainerId: Int) : Navigator(
        0,
        fragment.activity as AppCompatActivity
        , fragmentContainerId
) {
    constructor(fragment: CoreFragment<*>) : this(fragment, fragment.fragmentContainerId)
    
    override val fragmentManager: FragmentManager
        get() = fragment.childFragmentManager
}
