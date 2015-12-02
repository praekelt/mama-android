package za.foundation.praekelt.mama.app.viewmodel

import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.raizlabs.android.dbflow.sql.builder.Condition
import com.raizlabs.android.dbflow.sql.language.Select
import org.jetbrains.anko.AnkoLogger
import za.foundation.praekelt.mama.api.model.Category
import za.foundation.praekelt.mama.api.model.Category_Table
import za.foundation.praekelt.mama.app.activity.BrowseAllActivity
import za.foundation.praekelt.mama.util.SharedPrefsUtil

/**
 * Created by eduardokolomajr on 15/11/16.
 */
class BrowseAllActivityViewModel(act: BrowseAllActivity):
        BaseActivityViewModel<BrowseAllActivity>(act), AnkoLogger{
    val allCategories: Category = Category().apply { title = "All Categories" }
    var categories: ObservableList<Category> = ObservableArrayList()

    override fun onAttachActivity(activity: BrowseAllActivity) {
        super.onAttachActivity(activity)
        refreshCategories()
    }

    fun refreshCategories(){
        Select().from(Category::class.java)
                .where(Condition.column(Category_Table.LOCALEID)
                        .`is`(SharedPrefsUtil.getLocale(this.act!!.get())))
                .queryList()
                .sortedBy { it.title }
                .let { categories.addAll(listOf(allCategories)+it) }
    }
}