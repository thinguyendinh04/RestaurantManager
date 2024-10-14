package com.dinhthi2004.restaurantmanager.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinhthi2004.restaurantmanager.api.HttpReq
import com.dinhthi2004.restaurantmanager.model.table.Tabledata
import kotlinx.coroutines.launch

class TableViewModel : ViewModel() {
    private val api = HttpReq.getInstance()
    private val TAG = "TableViewModel"

    private val _statusCode = MutableLiveData<Int>()
    private val _tables = MutableLiveData<List<Tabledata>>()
    val tables: LiveData<List<Tabledata>> = _tables

    private val _table = MutableLiveData<Tabledata>()
    val table: LiveData<Tabledata> = _table

    fun getTables(token: String) {
        viewModelScope.launch {
            try {
                // Gọi API để lấy danh sách bàn
                val response = api.getAllTable("Bearer $token") // Sử dụng token để gọi API

                // Kiểm tra phản hồi và cập nhật LiveData
                if (response.isSuccessful && response.body() != null) {
                    _tables.postValue(response.body()?.data) // Lấy danh sách bàn từ phản hồi
                    Log.d(TAG, "Data retrieved successfully: ${response.body()?.data}") // Log dữ liệu nhận được
                } else {
                    _tables.postValue(emptyList())
                    Log.e(TAG, "Failed to retrieve tables: ${response.message()}") // Log thông điệp lỗi
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error retrieving tables: ${e.message}") // Log lỗi
                _statusCode.postValue(500) // Cập nhật mã trạng thái lỗi
            }
        }
    }


    fun addTable(token: String, table: Tabledata, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                // Gọi API để thêm bàn mới
                val response = api.addTable("Bearer $token", table)

                // Kiểm tra phản hồi từ API
                if (response.isSuccessful && response.body() != null) {
                    val addedTable = response.body() // Lấy dữ liệu bàn mới từ phản hồi
                    _tables.postValue(_tables.value?.plus(addedTable) as List<Tabledata>?) // Cập nhật danh sách bàn với bàn mới
                    Log.d(TAG, "Table added successfully: $addedTable")
                    _statusCode.postValue(200) // Cập nhật mã trạng thái khi thành công

                    // Gọi callback onSuccess để cập nhật danh sách bàn
                    onSuccess()
                } else {
                    Log.e(TAG, "Failed to add table: ${response.message()}")
                    _statusCode.postValue(400) // Cập nhật mã trạng thái khi thất bại
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error adding table: ${e.message}")
                _statusCode.postValue(500) // Cập nhật mã trạng thái khi có lỗi
            }
        }
    }
    fun deleteTable(token: String, tableId: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                // Gọi API để xóa bàn
                val response = api.deleteTable("Bearer $token", tableId)

                // Kiểm tra phản hồi từ API
                if (response.isSuccessful) {
                    _statusCode.postValue(200) // Cập nhật mã trạng thái khi thành công

                    // Cập nhật danh sách bàn sau khi xóa thành công
                    _tables.postValue(_tables.value?.filter { it.id != tableId})

                    // Gọi callback onSuccess để thông báo thành công
                    onSuccess()
                } else {
                    Log.e("TableViewModel", "Failed to delete table: ${response.message()}")
                    _statusCode.postValue(response.code()) // Cập nhật mã trạng thái thất bại
                }
            } catch (e: Exception) {
                Log.e("TableViewModel", "Error deleting table: ${e.message}")
                _statusCode.postValue(500) // Cập nhật mã trạng thái khi có lỗi
            }
        }
    }
}

