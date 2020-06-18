package cn.deali.poemnote.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import cn.deali.poemnote.R
import com.qmuiteam.qmui.arch.QMUIFragment
import com.qmuiteam.qmui.widget.QMUITopBar
import com.qmuiteam.qmui.widget.QMUITopBarLayout

class HomeFragment : QMUIFragment() {
    @SuppressLint("InflateParams")
    override fun onCreateView(): View {
        val view = LayoutInflater.from(activity).inflate(R.layout.fragment_home, null)

        val topBar: QMUITopBarLayout = view.findViewById(R.id.topbar)
        topBar.setTitle("诗词笔记")

        return view
    }
}