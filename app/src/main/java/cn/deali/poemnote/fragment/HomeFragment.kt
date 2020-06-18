package cn.deali.poemnote.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import cn.deali.poemnote.R
import com.qmuiteam.qmui.arch.QMUIFragment
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import cn.deali.poemnote.ext.toast
import com.qmuiteam.qmui.widget.tab.QMUITabSegment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : QMUIFragment() {
    @SuppressLint("InflateParams")
    override fun onCreateView(): View {
        return LayoutInflater.from(activity).inflate(R.layout.fragment_home, null)
    }

    override fun onViewCreated(rootView: View) {
        super.onViewCreated(rootView)

        topbar.setTitle("诗词笔记")

        initTabs()
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
            .setText("组件")
            .build(context)

        val lab = builder
            .setNormalDrawable(context?.getDrawable(R.mipmap.icon_tabbar_lab))
            .setSelectedDrawable(context?.getDrawable(R.mipmap.icon_tabbar_lab_selected))
            .setText("实验室")
            .build(context)

        val utils = builder
            .setNormalDrawable(context?.getDrawable(R.mipmap.icon_tabbar_util))
            .setSelectedDrawable(context?.getDrawable(R.mipmap.icon_tabbar_util_selected))
            .setText("工具")
            .build(context)

        tabs.addTab(component)
            .addTab(lab)
            .addTab(utils)

        tabs.mode = QMUITabSegment.MODE_FIXED
    }
}