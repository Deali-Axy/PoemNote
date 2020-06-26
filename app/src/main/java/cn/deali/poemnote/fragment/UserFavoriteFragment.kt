package cn.deali.poemnote.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import cn.deali.poemnote.App
import cn.deali.poemnote.ObjectBox
import cn.deali.poemnote.R
import cn.deali.poemnote.adapter.PoemListAdapter
import cn.deali.poemnote.model.UserFavorite
import cn.deali.poemnote.model.UserFavorite_
import com.qmuiteam.qmui.arch.QMUIFragment
import com.qmuiteam.qmui.kotlin.onClick
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import io.objectbox.kotlin.query
import kotlinx.android.synthetic.main.fragment_poem_list.*

class UserFavoriteFragment : QMUIFragment() {
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

        val favoriteBox: Box<UserFavorite> = ObjectBox.boxStore.boxFor()
        poem_list.adapter = PoemListAdapter(
            favoriteBox.query { equal(UserFavorite_.userId, App.instance.currentUser!!.id) }
                .find().map { userFavorite -> userFavorite.poem.target }.toList()
        )
    }

    fun initTopBar() {
        topbar.setTitle("我喜欢的诗词")
        topbar.addLeftBackImageButton().onClick { popBackStack() }
    }
}