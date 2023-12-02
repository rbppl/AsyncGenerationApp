package com.rbppl.asyncgenerationapp.data
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import com.rbppl.asyncgenerationapp.model.ItemModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Random
class Repository(private val context: Context) {
    private val viewModelScope = CoroutineScope(SupervisorJob())
    private val itemList = mutableListOf<ItemModel>()
    private val _itemList = MutableLiveData<List<ItemModel>>()
    private val _nextItemIn = MutableLiveData<String>()
    init {
        val sharedPreferences = context.getSharedPreferences("ItemListPrefs", Context.MODE_PRIVATE)
        val itemCount = sharedPreferences.getInt("itemCount", 15)
        for (i in 1..itemCount) {
            itemList.add(ItemModel(i))
        }
        viewModelScope.launch(Dispatchers.Default) {
            while (true) {
                delay(5000)
                addItem()
                _itemList.postValue(getItems())
            }
        }
    }
    fun getItems(): List<ItemModel> {
        return itemList.toList()
    }
    private fun addItem() {
        val newItem = ItemModel(itemList.size + 1)
        val randomPosition = Random().nextInt(itemList.size + 1)
        itemList.add(randomPosition, newItem)
        val sharedPreferences = context.getSharedPreferences("ItemListPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit {
            putInt("itemCount", itemList.size)
        }
    }
    fun removeItem(position: Int) {
        if (position in 0 until itemList.size) {
            itemList.removeAt(position)
            _itemList.postValue(getItems())
        }
    }
}
