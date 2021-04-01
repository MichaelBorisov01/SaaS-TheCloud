package ru.rsue.borisov.csqandroidclient


import GetUserQuery
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.cache.CacheHeaders
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.request.RequestHeaders
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class LoginActivity : AppCompatActivity() {
    private lateinit var mLoginEditText: EditText
    private lateinit var mPasswordEditText: EditText
    private lateinit var buttonLogIn: Button
    private lateinit var progressBar: ProgressBar

    private lateinit var mLoginTextView: TextView
    private lateinit var mPasswordTextView: TextView

    private val apolloClient: ApolloClient = ApolloClient.builder()
        .serverUrl("http://192.168.0.105:8080/csq-api")
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        mLoginEditText = findViewById(R.id.auth_login)
        mPasswordEditText = findViewById(R.id.auth_password)
        buttonLogIn = findViewById(R.id.button_log_in)
        progressBar = findViewById(R.id.progressBar)

        mLoginTextView = findViewById(R.id.tv_login)
        mPasswordTextView = findViewById(R.id.tv_password)


        buttonLogIn.setOnClickListener {

             getting()
        }

    }

    private fun login() {

        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBdXRoZW50aWNhdGlvbiIsImlzcy" +
                "I6IkNsaWVudCIsImV4cCI6MTYxNjkyMjYyNywiZW1haWwiOiJHYWNna292c2t5LmRhbmlsQG1haWwuc" +
                "nUsIG5hbWU90JPQsNGH0LrQvtCy0YHQutC40Lkg0JTQsNC90LjQuNC7In0.VDk34B0uiEo7UvxnoTVT" +
                "7SexFHBWYETpbdrkq17FA2Q9E3Y1MhUYZN1hlrlAhLKzrusuJYPFvmqoRHw_YP7uWA"
        val client = NetworkService.getInstance()
            ?.getApolloClientWithTokenInterceptor(token)

        client
            ?.query(GetUserQuery())
            ?.enqueue(object : ApolloCall.Callback<GetUserQuery.Data>() {


                override fun onResponse(response: Response<GetUserQuery.Data>) {
                    //if (!response.hasErrors()) {

                    progressBar.visibility = View.VISIBLE
                    mLoginTextView.text = response.data?.users.toString()
                    mPasswordTextView.text = response.data?.users.toString()

                    //Log.e("response", name.toString())
                    //val familyName = response.data?.auth?.family_name()
                    // }
                }

                override fun onFailure(e: ApolloException) {
                    Log.e("error", e.toString())
                }
            })
    }


    fun get() {

        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBdXRoZW50aWNhdGlvbiIsImlzcy" +
                "I6IkNsaWVudCIsImV4cCI6MTYxNjkyMjYyNywiZW1haWwiOiJHYWNna292c2t5LmRhbmlsQG1haWwuc" +
                "nUsIG5hbWU90JPQsNGH0LrQvtCy0YHQutC40Lkg0JTQsNC90LjQuNC7In0.VDk34B0uiEo7UvxnoTVT" +
                "7SexFHBWYETpbdrkq17FA2Q9E3Y1MhUYZN1hlrlAhLKzrusuJYPFvmqoRHw_YP7uWA"
        val client = NetworkService.getInstance()
            ?.getApolloClientWithTokenInterceptor(token)

        apolloClient.query(GetUserQuery())
            .toBuilder()
            .requestHeaders(
                RequestHeaders.builder()
                    .addHeader(
                        "Authorization",
                        "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBdX" +
                                "RoZW50aWNhdGlvbiIsImlzcyI6IkNsaWVudCIsImV4cCI6MTYxNjkyNDI5OSwi" +
                                "ZW1haWwiOiJib3JpLm1peEBtYWlsLnJ1In0.HR6wcsHlVqX9ZQEP4wVCHPQ4Zf" +
                                "rk0LXJeST7k2-dETr24OuYU4tyT5dl39cP8r4FMvRPFZq76iofZyTGknjCHQ"
                    )
                    .addHeader("Authorized", "yes")
                    .build()
            ).build().enqueue(object : ApolloCall.Callback<GetUserQuery.Data>() {


                override fun onFailure(e: ApolloException) {
                    Log.e("errorQuery", e.toString())
                }

                override fun onResponse(response: Response<GetUserQuery.Data>) {
                    val users = response.data!!.users
                    if (users == null || response.hasErrors()) {
                        Log.e("errorResponse", response.toString())
                    } else {
                        for (user in users) {
                            mLoginTextView.text = user!!.email
                            mLoginTextView.text = user.name
                        }
                    }
                }
            })
    }

    private fun getting() {

        apolloClient.query(GetUserQuery())
            .toBuilder()
            .requestHeaders(
                RequestHeaders.builder()
                    .addHeader(
                        "Authorization",
                        "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBdXRoZW50aWNhdGlvbiIsImlzcyI6IkNsaWVudCIsImV4cCI6MTYxNjkyNDI5OSwiZW1haWwiOiJib3JpLm1peEBtYWlsLnJ1In0.HR6wcsHlVqX9ZQEP4wVCHPQ4Zfrk0LXJeST7k2-dETr24OuYU4tyT5dl39cP8r4FMvRPFZq76iofZyTGknjCHQ"
                    )
                    .addHeader("Authorized", "yes")
                    .build()
            )
            .build().enqueue(object : ApolloCall.Callback<GetUserQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    Log.e("errorQuery", e.toString())
                }

                override fun onResponse(response: Response<GetUserQuery.Data>) {
                    val users = response.data!!.users
                    if (users == null || response.hasErrors()) {
                        mPasswordTextView.text = users.toString()
                    }
                    else{
                        for (user in users)
                            mLoginTextView.text = user!!.email
                    }
                }
            })
    }
}




