package cn.deali.poemnote.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import cn.deali.poemnote.R
import cn.deali.poemnote.adapter.PoemListAdapter
import com.qmuiteam.qmui.arch.QMUIFragment
import com.qmuiteam.qmui.kotlin.onClick
import kotlinx.android.synthetic.main.fragment_poem_list.*

class PoemListFragment : QMUIFragment() {
    @SuppressLint("InflateParams")
    override fun onCreateView(): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_poem_list, null)
    }

    override fun onViewCreated(rootView: View) {
        super.onViewCreated(rootView)
        initTopBar()

        val listLayoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        poem_list.layoutManager = listLayoutManager
        poem_list.adapter = PoemListAdapter()
    }

    private fun initTopBar() {
        topbar.setTitle("诗词列表")
        topbar.addLeftBackImageButton().onClick { popBackStack() }
    }
}