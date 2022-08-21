package cn.deali.poemnote

import cn.deali.poemnote.fragment.*
import com.qmuiteam.qmui.arch.QMUIFragmentActivity
import com.qmuiteam.qmui.arch.annotation.DefaultFirstFragment
import com.qmuiteam.qmui.arch.annotation.FirstFragments

// 如果我们没在 Activity 的 @FirstFragments 数组里加上 Fragment， 那么 QMUIFragmentActivity.intentOf 会抛错的
@FirstFragments(
    value = [
        MainFragment::class,
        PoemListFragment::class,
        PoemFragment::class,
        SignUpFragment::class,
        SignInFragment::class
    ]
)
// 使用 @DefaultFirstFragment 来指定默认的 First Fragment，
// 这时 new Intent(context, CommonHolderActivity::class.java) 就会启用默认的 First Fragment
@DefaultFirstFragment(MainFragment::class)
class CommonHolderActivity : QMUIFragmentActivity() {
    override fun getContextViewId(): Int {
        // 提供 FragmentContainer 的 id
        return R.id.app_common_holder_fragment_container
    }
}