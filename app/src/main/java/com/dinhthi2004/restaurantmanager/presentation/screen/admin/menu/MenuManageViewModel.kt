package com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinhthi2004.restaurantmanager.api.HttpReq
import com.dinhthi2004.restaurantmanager.model.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.dinhthi2004.restaurantmanager.model.dish.Dish
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.File

class MenuManageViewModel : ViewModel() {
    private val api = HttpReq.getInstance()

    val token = TokenManager.token

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage

    // State to hold the meal list
    private val _dishList = MutableStateFlow<List<Dish>>(emptyList())
    val dishList: StateFlow<List<Dish>> = _dishList

    // State to manage search query
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    // State to manage delete meal
    private val _deleteMealState = MutableStateFlow<Result<String>?>(null)
    val deleteMealState: StateFlow<Result<String>?> = _deleteMealState

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun filteredItems(dishes: List<Dish>): List<Dish> {
        val query = searchQuery.value.lowercase()
        return dishes.filter { it.name.lowercase().contains(query) }
    }

    fun getAllDishes() {
        viewModelScope.launch {
            try {
                if (token != null) {
                    val response = api.getAllDishes("Bearer $token")
                    if (response.isSuccessful) {
                        _dishList.value = response.body()?.data ?: emptyList()
                    } else {
                        _errorMessage.value = "Failed to fetch dishes: ${response.message()}"
                    }
                } else {
                    _errorMessage.value = "Token is missing."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.localizedMessage}"
            }
        }
    }


    fun createDish(
        name: String,
        price: Float,
        status: String,
        idType: Int,
        information: String,
        imageBytes: ByteArray? // Nhận byte[] từ UI
    ) {
        viewModelScope.launch {
            try {
                if (token != null) {
                    // Tạo các RequestBody từ các tham số
                    val nameBody = RequestBody.create("text/plain".toMediaTypeOrNull(), name)
                    val priceBody = RequestBody.create("text/plain".toMediaTypeOrNull(), price.toString())
                    val statusBody = RequestBody.create("text/plain".toMediaTypeOrNull(), status)
                    val idTypeBody = RequestBody.create("text/plain".toMediaTypeOrNull(), idType.toString())
                    val informationBody = RequestBody.create("text/plain".toMediaTypeOrNull(), information)

                    // Xử lý imageBytes nếu có
                    val imagePart = imageBytes?.let {
                        val requestFile = it.toRequestBody("image/*".toMediaTypeOrNull())
                        MultipartBody.Part.createFormData("image_url", "image.jpg", requestFile)
                    }

                    // Gọi API để tạo món ăn mới
                    val response = api.addNewDish(
                        token = "Bearer $token",
                        name = nameBody,
                        price = priceBody,
                        status = statusBody,
                        idType = idTypeBody,
                        information = informationBody,
                        image_url = imagePart
                    )

                    if (response.isSuccessful) {
                        _successMessage.value = "Dish created successfully!"
                        getAllDishes()
                    } else {
                        _errorMessage.value = "Failed to create dish: ${response.message()}"
                    }
                } else {
                    _errorMessage.value = "Token is missing."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error occurred: ${e.localizedMessage}"
            }
        }
    }





    fun updateDish(
        id: String,
        name: String,
        price: Float,
        status: String,
        idType: Int,
        information: String,
        imageUri: Uri?
    ) {
        viewModelScope.launch {
            try {
                if (token != null) {
                    // Create RequestBody for each field
                    val nameBody = RequestBody.create("text/plain".toMediaTypeOrNull(), name)
                    val priceBody = RequestBody.create("text/plain".toMediaTypeOrNull(), price.toString())
                    val statusBody = RequestBody.create("text/plain".toMediaTypeOrNull(), status)
                    val idTypeBody = RequestBody.create("text/plain".toMediaTypeOrNull(), idType.toString())
                    val informationBody = RequestBody.create("text/plain".toMediaTypeOrNull(), information)

                    // Handle image if provided
                    val imagePart = imageUri?.let {
                        val file = File(it.path!!)
                        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                        MultipartBody.Part.createFormData("image_url", file.name, requestFile)
                    }

                    // Make API call
                    val response = api.updateDish(
                        token = "Bearer $token",
                        dishId = id,
                        name = nameBody,
                        price = priceBody,
                        status = statusBody,
                        idType = idTypeBody,
                        information = informationBody,
                        image_url = imagePart
                    )

                    if (response.isSuccessful) {
                        _successMessage.value = "Dish updated successfully!"
                    } else {
                        _errorMessage.value = "Failed to update dish: ${response.message()}"
                    }
                } else {
                    _errorMessage.value = "Token is missing."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.localizedMessage}"
            }
        }
    }


    // DELETE an existing dish
    fun deleteDish(id: String) {
        viewModelScope.launch {
            try {
                if (token != null) {
                    val response = api.deleteDish("Bearer $token", id)
                    if (response.isSuccessful) {
                        val dishResponse = response.body()
                        if (dishResponse != null) {
                            _successMessage.value = "Dish deleted successfully!"
                            getAllDishes()
                        } else {
                            _errorMessage.value = "Failed to delete dish: Response body is null"
                        }
                    } else {
                        _errorMessage.value = "Failed to delete dish: ${response.message()}"
                    }
                } else {
                    _errorMessage.value = "Token is missing, please log in again."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error occurred: ${e.localizedMessage}"
            }
        }
    }
}


