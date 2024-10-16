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
import java.io.File

class MenuManageViewModel : ViewModel() {
    private val api = HttpReq.getInstance()

    val token = TokenManager.token

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage

    private val _dishList = MutableStateFlow<List<Dish>>(emptyList())
    val dishList: StateFlow<List<Dish>> = _dishList

    private val _deleteMealState = MutableStateFlow<Result<String>?>(null)
    val deleteMealState: StateFlow<Result<String>?> = _deleteMealState


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
        imageBytes: ByteArray?
    ) {
        viewModelScope.launch {
            try {
                if (token != null) {
                    val nameBody = RequestBody.create("text/plain".toMediaTypeOrNull(), name)
                    val priceBody = RequestBody.create("text/plain".toMediaTypeOrNull(), price.toString())
                    val statusBody = RequestBody.create("text/plain".toMediaTypeOrNull(), status)
                    val idTypeBody = RequestBody.create("text/plain".toMediaTypeOrNull(), idType.toString())
                    val informationBody = RequestBody.create("text/plain".toMediaTypeOrNull(), information)

                    val imagePart = imageBytes?.let {
                        val requestFile = it.toRequestBody("image/*".toMediaTypeOrNull())
                        MultipartBody.Part.createFormData("image_url", "image.jpg", requestFile)
                    }

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
                    val nameBody = RequestBody.create("text/plain".toMediaTypeOrNull(), name)
                    val priceBody = RequestBody.create("text/plain".toMediaTypeOrNull(), price.toString())
                    val statusBody = RequestBody.create("text/plain".toMediaTypeOrNull(), status)
                    val idTypeBody = RequestBody.create("text/plain".toMediaTypeOrNull(), idType.toString())
                    val informationBody = RequestBody.create("text/plain".toMediaTypeOrNull(), information)

                    val imagePart = imageUri?.let {
                        val file = File(it.path!!)
                        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                        MultipartBody.Part.createFormData("image_url", file.name, requestFile)
                    }

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

    fun deleteDish(id: String) {
        viewModelScope.launch {
            try {
                if (token != null) {
                    val response = api.deleteDish("Bearer $token", id)
                    if (response.isSuccessful) {
                        _successMessage.value = "Dish deleted successfully!"
                        getAllDishes()
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
