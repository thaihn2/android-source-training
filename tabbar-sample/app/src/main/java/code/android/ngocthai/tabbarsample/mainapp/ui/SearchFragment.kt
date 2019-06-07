package code.android.ngocthai.tabbarsample.mainapp.ui

import android.os.Bundle
import code.android.ngocthai.tabbarsample.R
import code.android.ngocthai.tabbarsample.base.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : BaseFragment() {

    companion object {
        private val TAG = SearchFragment::class.java.simpleName

        private val KEY_CONTENT = "content"

        @Volatile
        private var INSTANCE: SearchFragment? = null

        fun getInstance(content: String): SearchFragment =
                INSTANCE ?: synchronized(this) {
                    INSTANCE
                            ?: SearchFragment().also {
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
        get() = R.layout.fragment_search

    override fun initComponent(savedInstanceState: Bundle?) {
        mContent = arguments?.getString(KEY_CONTENT)

        textContent.text = mContent
    }
}
