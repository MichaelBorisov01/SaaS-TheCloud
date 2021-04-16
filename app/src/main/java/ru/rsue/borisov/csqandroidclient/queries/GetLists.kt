package ru.rsue.borisov.csqandroidclient.queries

//Класс для функций, которые получают списки с сервера

import android.util.Log
import android.view.View
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import ru.rsue.borisov.csqandroidclient.ui.user_calendar.NetworkService

class GetLists {

  /*  fun getCompanyList() {

        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBdXRoZW50aWNhdGlvbiIsIml" +
                "zcyI6IkNsaWVudCIsImV4cCI6MTYxNzAxMzEyOSwiZW1haWwiOiJHYWNna292c2t5LmRhbmlsQG1" +
                "haWwucnUifQ.Pz3hvbqrh1oqwGGD1WJ5s-dDNHRsR7gARwewHAJoDBlg4ZSEn9DS6Z_Ng3dwldar" +
                "RroxoCZlKKpqOB1YuNROYg"

        val auth = "yes"

        val client = NetworkService.getInstance()
            ?.getApolloClientWithTokenInterceptor(token, auth)

        client
            ?.query(GetUserQuery())
            ?.enqueue(object : ApolloCall.Callback<GetUserQuery.Data>() {

                override fun onFailure(e: ApolloException) {
                    Log.e("ErrorQuery", e.toString())
                }

                override fun onResponse(response: Response<GetUserQuery.Data>) {
                    val users = response.data!!.users
                    if (users == null || response.hasErrors()) {
                        Log.e("ErrorResponse", response.toString())
                    } else {
                        for (user in users) {
                            runOnUiThread {
                                mPasswordTextView.text = user!!.name
                                mLoginTextView.text = user.email
                            }
                        }
                    }
                }
            })
    }*/
}