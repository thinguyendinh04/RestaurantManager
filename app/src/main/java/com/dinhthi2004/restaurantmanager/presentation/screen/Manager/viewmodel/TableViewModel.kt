package com.dinhthi2004.restaurantmanager.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinhthi2004.restaurantmanager.api.HttpReq
import com.dinhthi2004.restaurantmanager.model.Table
import kotlinx.coroutines.launch

class TableViewModel : ViewModel() {
    private val api = HttpReq.getInstance()
    private val TAG = "TableViewModel" // Thêm tag cho log

    private val _statusCode = MutableLiveData<Int>()
    val statusCode: LiveData<Int> = _statusCode

    private val _tables = MutableLiveData<List<Table>>()
    val tables: LiveData<List<Table>> = _tables

    private val _table = MutableLiveData<Table>()
    val table: LiveData<Table> = _table

    fun resetStatusCode() {
        _statusCode.postValue(0)
    }

    fun getTables(token: String) {
        viewModelScope.launch {
            try {
                // Gọi API để lấy danh sách bàn
                val response = api.getTables("Bearer $token") // Thay api.getIngredients bằng api.getTables

                // Kiểm tra phản hồi và cập nhật LiveData
                if (response.message == "List tables") {
                    _tables.postValue(response.tables) // Giả định response.tables trả về danh sách bàn
                    Log.d(TAG, "Data retrieved successfully: ${response.tables}") // Log dữ liệu nhận được
                } else {
                    _tables.postValue(emptyList())
                    Log.e(TAG, "Failed to retrieve tables: ${response.message}") // Log thông điệp lỗi
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error retrieving tables: ${e.message}") // Log lỗi
                _statusCode.postValue(500) // Cập nhật mã trạng thái lỗi
            }
        }
    }

    fun addTable(token: String, table: Table, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                // Gọi API để thêm bàn mới
                val response = api.addTable("Bearer $token", table)

                // Kiểm tra phản hồi từ API
                if (response.message == "Table added successfully") {
                    _table.postValue(response.table)
                    Log.d(TAG, "Table added successfully: ${response.table}")
                    _statusCode.postValue(200) // Cập nhật mã trạng thái khi thành công

                    // Gọi callback onSuccess để cập nhật danh sách bàn
                    onSuccess()
                } else {
                    Log.e(TAG, "Failed to add table: ${response.message}")
                    _statusCode.postValue(400) // Cập nhật mã trạng thái khi thất bại
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error adding table: ${e.message}")
                _statusCode.postValue(500) // Cập nhật mã trạng thái khi có lỗi
            }
        }
    }

}
