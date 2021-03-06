package za.foundation.praekelt.mama.app.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import rx.Observable
import za.foundation.praekelt.mama.app.viewmodel.CategoryListFragmentViewModel
import za.foundation.praekelt.mama.databinding.FragmentCategoryListBinding
import za.foundation.praekelt.mama.inject.component.CategoryListFragmentComponent
import za.foundation.praekelt.mama.inject.component.DaggerCategoryListFragmentComponent
import za.foundation.praekelt.mama.inject.module.CategoryListFragmentModule
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Fragments for list of stories for a particular category
 */
class CategoryListFragment(var uuid: String = "", var locale: String = "") : Fragment(), AnkoLogger {
    private object argsKeys {
        val uuidKey: String = "uuid"
        val localeKey: String = "locale"
    }

    val fragComp: CategoryListFragmentComponent by lazy { getFragmentComponent() }
    lateinit var viewModel: CategoryListFragmentViewModel
        @Inject set

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        savedInstanceState?.keySet()?.forEach {
            when (it) {
                argsKeys.uuidKey -> uuid = savedInstanceState.getString(it)
                argsKeys.localeKey -> locale = savedInstanceState.getString(it)
                else -> {}
            }
        }

        val bind: FragmentCategoryListBinding =
                FragmentCategoryListBinding.inflate(inflater, container, false)
        fragComp.inject(this)
        viewModel.onAttachFragment(this)
        bind.setCategoryListVM(viewModel)
        return bind.getRoot()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(argsKeys.uuidKey, uuid)
        outState?.putString(argsKeys.localeKey, locale)
    }

    override fun onDestroy() {
        viewModel.onDestroy()
        super.onDestroy()
    }

    fun getFragmentComponent(): CategoryListFragmentComponent {
        return DaggerCategoryListFragmentComponent.builder()
                .categoryListFragmentModule(CategoryListFragmentModule(this))
                .build()
    }
}
