package cn.deali.poemnote.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import cn.deali.poemnote.CommonHolderActivity
import cn.deali.poemnote.R
import cn.deali.poemnote.adapter.HomePoemListAdapter
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.materialdesigniconic.MaterialDesignIconic
import com.mikepenz.iconics.utils.colorInt
import com.mikepenz.iconics.utils.sizeDp
import com.qmuiteam.qmui.arch.QMUIFragment
import com.qmuiteam.qmui.arch.QMUIFragmentActivity
import com.qmuiteam.qmui.kotlin.onClick
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : QMUIFragment() {
    @SuppressLint("InflateParams")
    override fun onCreateView(): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_home, null)
    }

    override fun onViewCreated(rootView: View) {
        super.onViewCreated(rootView)

        initTopBar()

        // 构建卡片列表
        val recyclerView = RecyclerView(requireContext())
        val pagerLayoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        recyclerView.layoutManager = pagerLayoutManager
        val recyclerViewAdapter = HomePoemListAdapter()
        recyclerViewAdapter.itemCount = 10
        recyclerView.adapter = recyclerViewAdapter
        pagerWrap.addView(recyclerView)

        // PagerSnapHelper每次只能滚动一个item;用LinearSnapHelper则可以一次滚动多个，并最终保证定位
        // mSnapHelper = new LinearSnapHelper();
        val snapHepler = PagerSnapHelper()
        snapHepler.attachToRecyclerView(recyclerView)
    }

    private fun initTopBar() {
        // 设置标题
        topbar.setTitle("随机推荐")

        // 添加右边的按钮
        val poemListButton = topbar.addRightImageButton(R.mipmap.icon_topbar_about, R.id.home_topbar_right_button)
        poemListButton.setImageDrawable(IconicsDrawable(requireContext(), MaterialDesignIconic.Icon.gmi_view_headline).apply {
            colorInt(Color.WHITE)
            sizeDp(24)
        })
        poemListButton.onClick {
            startActivity(
                QMUIFragmentActivity.intentOf(
                    requireContext(),
                    CommonHolderActivity::class.java,
                    PoemListFragment::class.java
                )
            )
        }
    }
}