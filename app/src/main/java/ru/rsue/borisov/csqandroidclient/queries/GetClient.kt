package ru.rsue.borisov.csqandroidclient.queries

import GetIdAndTokenQuery
import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import ru.rsue.borisov.csqandroidclient.db.ClientDao
import ru.rsue.borisov.csqandroidclient.db.ClientInf
import ru.rsue.borisov.csqandroidclient.ui.user_calendar.NetworkService

class GetClient {

    fun getIdAndToken(login: String, password: String, clientDao: ClientDao) {

        val auth = "no"

        val client = NetworkService.getInstance()
            ?.getApolloClientWithHeader(auth)

        client
            ?.query(GetIdAndTokenQuery(login, password))
            ?.enqueue(object : ApolloCall.Callback<GetIdAndTokenQuery.Data>() {

                override fun onFailure(e: ApolloException) {
                    Log.e("ErrorQuery", e.toString())
                }

                override fun onResponse(response: Response<GetIdAndTokenQuery.Data>) {
                    val clients = response.data!!.authClient
                    if (clients == null || response.hasErrors()) {
                        Log.e("ErrorResponse", response.toString())
                    } else {
                        for (_client in clients) {
                            //передача в объект класса ClientInf информации о клиенте
                            val insert =
                                _client?.id?.let { ClientInf(it, _client.token.toString()) }
                            //вставка id и token клиента в Room
                            if (insert != null) {
                                clientDao.insertClientIdAndToken(insert)
                            }
                        }
                    }
                }
            })
    }
}