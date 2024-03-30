package az.edu.bhos.l14todoapp.flows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.edu.bhos.l14todoapp.data.TodoRepository
import az.edu.bhos.l14todoapp.entities.TodoBundle
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(
    todoRepo: TodoRepository
) : ViewModel() {

    private val _todoBundles: MutableLiveData<List<TodoBundle>> = MutableLiveData()
    private val _weekdays = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

    val todoBundles: LiveData<List<TodoBundle>>
        get() = _todoBundles

    init {
        viewModelScope.launch {
            todoRepo.syncTodos()
        }

        todoRepo.observeTodoEntries()
            .onEach { todos ->
                val todoBundles = todos.groupBy { it.weekday }
                val todoBundleList = todoBundles.map { (weekday, todos) ->
                    TodoBundle(weekday, todos)
                }

                val sortedTodoBundleList = todoBundleList.sortedBy {
                    _weekdays.indexOf(it.weekday)
                }

                _todoBundles.postValue(sortedTodoBundleList)
            }.launchIn(viewModelScope)
    }
}
