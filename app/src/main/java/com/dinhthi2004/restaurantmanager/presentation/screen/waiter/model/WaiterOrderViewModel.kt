package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinhthi2004.restaurantmanager.api.HttpReq
import com.dinhthi2004.restaurantmanager.model.Bill
import com.dinhthi2004.restaurantmanager.model.Order
import com.dinhthi2004.restaurantmanager.model.TokenManager
import com.dinhthi2004.restaurantmanager.model.bill.BillData
import com.dinhthi2004.restaurantmanager.model.table.Tabledata
import kotlinx.coroutines.launch

class WaiterOrderViewModel: ViewModel() {
    private val api = HttpReq.getInstance()

    private val token: String = TokenManager.token.toString()

    private val _statuscode = MutableLiveData<Int>()
    val statuscode: LiveData<Int> = _statuscode

    private val _bills = MutableLiveData<List<BillData>>()
    val bills: LiveData<List<BillData>> = _bills

    private val _aBill = MutableLiveData<BillData?>()
    val aBill: LiveData<BillData?> = _aBill

    private val _billDetails = MutableLiveData<List<Order>>()
    val billDetails: LiveData<List<Order>> = _billDetails

    private val _aBillDetail = MutableLiveData<Order?>()
    val aBillDetail: LiveData<Order?> = _aBillDetail

    private val _aTable = MutableLiveData<Tabledata?>()
    val aTable: LiveData<Tabledata?> = _aTable

    private val _tables = MutableLiveData<List<Tabledata>>()
    val tables: LiveData<List<Tabledata>> = _tables

    fun getBills(){
        viewModelScope.launch {
            try {
                val response = api.getAllBills(token)
                if (response.code() == 200){
                    _bills.postValue(response.body()?.data)
                }else{
                    _bills.postValue(emptyList())
                }
            }catch (e: RuntimeException){
                println(e)
            }
        }
    }

    fun get1Bill(id: String){
        viewModelScope.launch {
            try {
                val response = api.get1Bill(token, id)
                _aBill.postValue(response.data)
            }catch (e: RuntimeException){
                println(e)
            }
        }
    }

    fun updateBill(id: String, newBill: BillData){
        viewModelScope.launch {
            try {
                val response = api.updateBill(token, id, newBill)
                if (response.code() == 200){
                    _aBill.postValue(response.body())
                }else{
                    _aBill.postValue(null)
                }
            }catch (e: RuntimeException){
                println(e)
            }
        }
    }

    fun getTablebyID(id: String){
        viewModelScope.launch {
            try{
                val response = api.getTableByID(token, id)
                if (response.code() == 200){
                    _aTable.postValue(response.body())
                }else{
                    _aTable.postValue(null)
                }
            }catch (e: RuntimeException){
                println(e)
            }
        }
    }

    fun getTables(){
        viewModelScope.launch {
            try{
                val response = api.getAllTable(token)
                if (response.code() == 200){
                    _tables.postValue(response.body()?.data)
                }else{
                    _tables.postValue(emptyList())
                }
            }catch (e: RuntimeException){
                println(e)
            }
        }
    }



}