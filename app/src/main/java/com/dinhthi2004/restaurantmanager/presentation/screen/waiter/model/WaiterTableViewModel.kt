import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinhthi2004.restaurantmanager.api.HttpReq
import com.dinhthi2004.restaurantmanager.model.OrderData
import com.dinhthi2004.restaurantmanager.model.TokenManager
import com.dinhthi2004.restaurantmanager.model.bill.BillData
import com.dinhthi2004.restaurantmanager.model.table.Tabledata
import kotlinx.coroutines.launch

class WaiterTableViewModel: ViewModel(){

    private val api = HttpReq.getInstance()

    private val token: String = TokenManager.token.toString()

    private val _aTable = MutableLiveData<Tabledata?>()
    val aTable: LiveData<Tabledata?> = _aTable

    private val _tables = MutableLiveData<List<Tabledata>>()
    val tables: LiveData<List<Tabledata>> = _tables

    var listOfTableOrders: List<OrderData>? = emptyList()

    private val _tableOrders = MutableLiveData<List<OrderData>?>()
    val tableOrders: LiveData<List<OrderData>?> = _tableOrders

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

    fun updateTable(id: String, newTableData: Tabledata){
        viewModelScope.launch {
            try {
                val response = api.updateTable(token, id, newTableData)
                if (response.code() == 200){
                    _aTable.postValue(response.body()?.data)
                }else{
                    _aTable.postValue(null)
                }
            }catch (e: RuntimeException){
                println(e)
            }
        }
    }

    fun getOrdersByTableID(id: Int){
        viewModelScope.launch {
            try{
                val response = api.getAllOrders(token)
                if (response.code() == 200){
                    listOfTableOrders = response.body()?.data?.filter { it.table_id == id }
                    _tableOrders.postValue(listOfTableOrders)
                }else{
                    _tableOrders.postValue(emptyList())
                }
            }catch (e: RuntimeException){
                println(e)
            }
        }
    }
}