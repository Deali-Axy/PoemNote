package cn.deali.poemnote.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import cn.deali.poemnote.CommonHolderActivity
import cn.deali.poemnote.R
import cn.deali.poemnote.adapter.PoemNoteListAdapter
import com.qmuiteam.qmui.arch.QMUIFragment
import com.qmuiteam.qmui.arch.QMUIFragmentActivity
import com.qmuiteam.qmui.kotlin.onClick
import kotlinx.android.synthetic.main.fragment_playground.*

class PlaygroundFragment : QMUIFragment() {
    @SuppressLint("InflateParams")
    override fun onCreateView(): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_playground, null)
    }

    override fun onViewCreated(rootView: View) {
        super.onViewCreated(rootView)
        topbar.setTitle("笔记广场")

        val listLayoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        poemnote_list.layoutManager = listLayoutManager
        poemnote_list.adapter = PoemNoteListAdapter()
    }
}