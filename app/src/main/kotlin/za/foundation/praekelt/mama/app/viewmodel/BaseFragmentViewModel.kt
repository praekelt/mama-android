package za.foundation.praekelt.mama.app.viewmodel

import android.support.v4.app.Fragment
import java.lang.ref.WeakReference

/**
 * Created by eduardokolomajr on 2015/09/07.
 */
public abstract class BaseFragmentViewModel<T: Fragment>(frag: T): BaseViewModel(){
    var fragment: WeakReference<T>? = WeakReference(frag)

    override fun onDestroy() {
        super.onCreate()

        fragment?.clear()
        fragment = null
    }

    open fun onAttachFragment(frag: T){
        fragment = WeakReference(frag)
    }
}