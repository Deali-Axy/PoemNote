package cn.deali.poemnote.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import cn.deali.poemnote.R
import cn.deali.poemnote.adapter.QDRecyclerViewAdapter
import com.qmuiteam.qmui.arch.QMUIFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : QMUIFragment() {
    @SuppressLint("InflateParams")
    override fun onCreateView(): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_home, null)
    }

    override fun onViewCreated(rootView: View) {
        super.onViewCreated(rootView)
        val recyclerView = RecyclerView(requireContext())
        val pagerLayoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        recyclerView.layoutManager = pagerLayoutManager
        val recyclerViewAdapter = QDRecyclerViewAdapter()
        recyclerViewAdapter.itemCount = 10
        recyclerView.adapter = recyclerViewAdapter
        pagerWrap.addView(recyclerView)

        // PagerSnapHelper每次只能滚动一个item;用LinearSnapHelper则可以一次滚动多个，并最终保证定位
        // mSnapHelper = new LinearSnapHelper();
        val snapHepler = PagerSnapHelper()
        snapHepler.attachToRecyclerView(recyclerView)
    }
}