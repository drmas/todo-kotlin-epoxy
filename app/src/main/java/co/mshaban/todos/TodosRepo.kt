package co.mshaban.todos

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import data.TodoQueries
import data.Todos

class TodosRepo(val context: Context) {
    companion object {
        @Volatile private var INSTANCE: TodosRepo? = null

        fun getInstance(context: Context): TodosRepo =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: TodosRepo(context).also { INSTANCE = it }
            }
    }

    private lateinit var todoQueries: TodoQueries
     init {
         val driver: SqlDriver = AndroidSqliteDriver(Database.Schema, context, "todo.db")
         val database = Database(driver)
         todoQueries = database.todoQueries
     }

     fun getAll(): List<Todos> {
         return todoQueries.selectAll().executeAsList()
     }

    fun addTodo(todo: Todos) {
        todo.run {
            todoQueries.insert(text, isDone)
        }
     }

    fun update(todo: Todos) {
        todo.run {
            todoQueries.update(todo.text, todo.isDone, todo.id)
        }
     }

    fun clearDoneItems() {
        todoQueries.clearDoneItems()
     }
 }