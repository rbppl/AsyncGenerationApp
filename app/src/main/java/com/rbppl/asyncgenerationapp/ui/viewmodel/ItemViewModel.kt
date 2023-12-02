package com.rbppl.asyncgenerationapp.ui.viewmodel
import com.rbppl.asyncgenerationapp.data.Repository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.rbppl.asyncgenerationapp.model.ItemModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
class ItemViewModel(private val repository: Repository) : ViewModel() {
    private val _itemList = MutableLiveData<List<ItemModel>>()
    val itemList: LiveData<List<ItemModel>> get() = _itemList
    init {
        viewModelScope.launch {
            _itemList.postValue(repository.getItems())
            while (true) {
                delay(5000)
                _itemList.postValue(repository.getItems())
            }
        }
    }

    fun removeItem(position: Int) {
        viewModelScope.launch {
            repository.removeItem(position)
            _itemList.postValue(repository.getItems())
        }
    }

    class Factory(private val repository: Repository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            if (modelClass.isAssignableFrom(ItemViewModel::class.java)) {
                return ItemViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
