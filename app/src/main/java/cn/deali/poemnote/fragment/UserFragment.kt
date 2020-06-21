package cn.deali.poemnote.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import cn.deali.poemnote.R
import com.qmuiteam.qmui.arch.QMUIFragment
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : QMUIFragment() {
    @SuppressLint("InflateParams")
    override fun onCreateView(): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_user, null)
    }

    override fun onViewCreated(rootView: View) {
        super.onViewCreated(rootView)
        topbar.setTitle("用户中心")
    }
}