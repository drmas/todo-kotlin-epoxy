package co.mshaban.todos

import android.view.View
import android.widget.CheckBox
import com.airbnb.epoxy.*

@EpoxyModelClass(layout = R.layout.todo_list_item)
abstract class TodoModel: EpoxyModelWithHolder<TodoModel.EntryHolder>() {

    @EpoxyAttribute
    var text: String? = ""

    @EpoxyAttribute
    var done: Boolean? = false

    @EpoxyAttribute
    var onToggle: View.OnClickListener? = null

    override fun bind(holder: EntryHolder) {
        holder.todoCheckbox.text = text
        holder.todoCheckbox.isChecked = done!!
        holder.todoCheckbox.setOnClickListener(onToggle)
    }

    inner class EntryHolder: EpoxyHolder() {
        lateinit var todoCheckbox: CheckBox

        override fun bindView(itemView: View) {
            todoCheckbox = itemView.findViewById(R.id.todo_checkbox)
        }
    }
}