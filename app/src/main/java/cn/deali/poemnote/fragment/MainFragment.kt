package cn.deali.poemnote.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import cn.deali.poemnote.R
import com.qmuiteam.qmui.arch.QMUIFragment
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import cn.deali.poemnote.ext.toast
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.materialdesigniconic.MaterialDesignIconic
import com.mikepenz.iconics.utils.sizeDp
import com.qmuiteam.qmui.arch.QMUIFragmentPagerAdapter
import com.qmuiteam.qmui.widget.tab.QMUITabSegment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : QMUIFragment() {
    @SuppressLint("InflateParams")
    override fun onCreateView(): View {
        return LayoutInflater.from(activity).inflate(R.layout.fragment_main, null)
    }

    override fun onViewCreated(rootView: View) {
        super.onViewCreated(rootView)

        initTopBar()
        initTabs()
        initPagers()
    }

    private fun initTopBar() {
        topbar.setTitle("诗词笔记")
    }

    private fun initTabs() {
        val builder = tabs.tabBuilder()
        builder.setSelectedIconScale(1.2f)
            .setTextSize(QMUIDisplayHelper.sp2px(context, 13), QMUIDisplayHelper.sp2px(context, 15))
            .setDynamicChangeIconColor(false)

        val home = builder
            .setNormalDrawable(IconicsDrawable(requireContext(), MaterialDesignIconic.Icon.gmi_home).apply { sizeDp(24) })
            .setSelectedDrawable(IconicsDrawable(requireContext(), MaterialDesignIconic.Icon.gmi_home).apply { sizeDp(24) })
            .setText("主页")
            .build(context)

        val playground = builder
            .setNormalDrawable(IconicsDrawable(requireContext(), MaterialDesignIconic.Icon.gmi_mood).apply { sizeDp(24) })
            .setSelectedDrawable(IconicsDrawable(requireContext(), MaterialDesignIconic.Icon.gmi_mood).apply { sizeDp(24) })
            .setText("广场")
            .build(context)

        val user = builder
            .setNormalDrawable(IconicsDrawable(requireContext(), MaterialDesignIconic.Icon.gmi_account).apply { sizeDp(24) })
            .setSelectedDrawable(IconicsDrawable(requireContext(), MaterialDesignIconic.Icon.gmi_account).apply { sizeDp(24) })
            .setText("我")
            .build(context)

        tabs.addTab(home)
            .addTab(playground)
            .addTab(user)

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