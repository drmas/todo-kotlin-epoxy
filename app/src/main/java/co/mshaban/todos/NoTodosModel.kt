package co.mshaban.todos

import android.view.View
import android.widget.CheckBox
import com.airbnb.epoxy.*

@EpoxyModelClass(layout = R.layout.no_todos)
abstract class NoTodosModel: EpoxyModelWithHolder<NoTodosModel.EntryHolder>() {

    inner class EntryHolder: EpoxyHolder() {
        override fun bindView(itemView: View) {

        }
    }
}