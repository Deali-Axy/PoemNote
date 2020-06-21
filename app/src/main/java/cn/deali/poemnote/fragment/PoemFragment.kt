package cn.deali.poemnote.fragment

import android.view.LayoutInflater
import android.view.View
import cn.deali.poemnote.R
import com.qmuiteam.qmui.arch.QMUIFragment
import com.qmuiteam.qmui.kotlin.onClick
import kotlinx.android.synthetic.main.fragment_poem.*

class PoemFragment : QMUIFragment() {
    override fun onCreateView(): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_poem, null)
    }

    override fun onViewCreated(rootView: View) {
        super.onViewCreated(rootView)
        topbar.setTitle("诗词阅读")
        topbar.addLeftBackImageButton().onClick { popBackStack() }
    }
}