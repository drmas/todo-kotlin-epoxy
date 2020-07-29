package co.mshaban.todos

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import data.Todos
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var todos: List<Todos>
    private var todoRepo = TodosRepo.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        todos = todoRepo.getAll()
        bindUI()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.clear_done -> {
                clearDoneItems()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun bindUI () {
        add_button.setOnClickListener(View.OnClickListener {
            onAdd(it)
        })
        todo_text.setOnEditorActionListener { v, actionId, event ->
            if ((event != null && event.action == KeyEvent.ACTION_DOWN) || actionId == EditorInfo.IME_ACTION_DONE) {
                onAdd(v)
                true
            } else {
                false
            }
        }

        todos_list.withModels {
            if (todos.count() > 0) {
                todos.forEachIndexed { index, todo ->
                    todo {
                        id(index)
                        text(todo.text)
                        done(todo.isDone.toInt() == 1)
                        onToggle(View.OnClickListener {
                            todoRepo.update(todo.copy(isDone = if (todo.isDone.toInt() == 1) 0 else 1))
                        })
                    }
                }
            } else {
                noTodos{
                    id(0)
                }
            }
        }

    }

    private fun onAdd(view: View) {
        Todos(text = todo_text.text.toString(), isDone = 0, id = 1).run {
            todoRepo.addTodo(this)
        }
        updateUI()
        todo_text.setText("")
        todo_text.clearFocus()
        hideKeyboard(view)
    }

    private fun hideKeyboard(view: View) {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun clearDoneItems () {
        todoRepo.clearDoneItems()
        updateUI();
    }

    private fun updateUI() {
        todos = todoRepo.getAll()
        todos_list.requestModelBuild()
    }
}
