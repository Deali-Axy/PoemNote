package cn.deali.poemnote.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import androidx.viewpager.widget.PagerAdapter
import cn.deali.poemnote.R
import com.qmuiteam.qmui.arch.QMUIFragment
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import cn.deali.poemnote.ext.toast
import com.qmuiteam.qmui.arch.QMUIFragmentPagerAdapter
import com.qmuiteam.qmui.util.QMUIDrawableHelper
import com.qmuiteam.qmui.widget.tab.QMUITabSegment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : QMUIFragment() {
    @SuppressLint("InflateParams")
    override fun onCreateView(): View {
        return LayoutInflater.from(activity).inflate(R.layout.fragment_main, null)
    }

    override fun onViewCreated(rootView: View) {
        super.onViewCreated(rootView)

        topbar.setTitle("诗词笔记")

        initTabs()
        initPagers()
    }


    private fun initTabs() {
        context?.toast("初始化")

        val builder = tabs.tabBuilder()
        builder.setSelectedIconScale(1.2f)
            .setTextSize(QMUIDisplayHelper.sp2px(context, 13), QMUIDisplayHelper.sp2px(context, 15))
            .setDynamicChangeIconColor(false)

        val component = builder
            .setNormalDrawable(context?.getDrawable(R.mipmap.icon_tabbar_component))
            .setSelectedDrawable(context?.getDrawable(R.mipmap.icon_tabbar_component_selected))
            .setText("主页")
            .build(context)

        val lab = builder
            .setNormalDrawable(context?.getDrawable(R.mipmap.icon_tabbar_lab))
            .setSelectedDrawable(context?.getDrawable(R.mipmap.icon_tabbar_lab_selected))
            .setText("广场")
            .build(context)

        val utils = builder
            .setNormalDrawable(context?.getDrawable(R.mipmap.icon_tabbar_util))
            .setSelectedDrawable(context?.getDrawable(R.mipmap.icon_tabbar_util_selected))
            .setText("功能")
            .build(context)

        tabs.addTab(component)
            .addTab(lab)
            .addTab(utils)

        tabs.mode = QMUITabSegment.MODE_FIXED
    }

    private fun initPagers() {
        val pagerAdapter = object : QMUIFragmentPagerAdapter(childFragmentManager) {
            override fun getCount(): Int = 3

            override fun createFragment(position: Int): QMUIFragment {
                return when (position) {
                    0 -> HomeFragment()
                    1 -> PlaygroundFragment()
                    2 -> UserFragment()
                    else -> EmptyFragment()
                }
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return when (position) {
                    0 -> "主页"
                    1 -> "广场"
                    2 -> "用户"
                    else -> "未找到"
                }
            }
        }

        pager.adapter = pagerAdapter
        tabs.setupWithViewPager(pager, false)
    }
}