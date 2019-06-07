package code.android.ngocthai.tabbarsample.mainapp.ui

import android.os.Bundle
import code.android.ngocthai.tabbarsample.R
import code.android.ngocthai.tabbarsample.base.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {

    companion object {

        private val TAG = HomeFragment::class.java.simpleName

        private const val KEY_CONTENT = "content"

        @Volatile
        private var INSTANCE: HomeFragment? = null

        fun getInstance(content: String): HomeFragment =
                INSTANCE ?: synchronized(this) {
                    INSTANCE
                            ?: HomeFragment().also {
                        val args = Bundle().apply {
                            putString(KEY_CONTENT, content)
                        }
                        it.arguments = args
                        INSTANCE = it
                    }
                }
    }

    private var mContent: String? = null

    override val layoutResource: Int
        get() = R.layout.fragment_home

    override fun initComponent(savedInstanceState: Bundle?) {
        mContent = arguments?.getString(KEY_CONTENT)

        textContent.text = mContent
    }
}
