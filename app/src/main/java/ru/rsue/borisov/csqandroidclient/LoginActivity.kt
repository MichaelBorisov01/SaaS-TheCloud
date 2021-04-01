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
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException


class LoginActivity : AppCompatActivity() {
    private lateinit var mLoginEditText: EditText
    private lateinit var mPasswordEditText: EditText
    private lateinit var buttonLogIn: Button
    private lateinit var progressBar: ProgressBar

    private lateinit var mLoginTextView: TextView
    private lateinit var mPasswordTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        mLoginEditText = findViewById(R.id.auth_login)
        mPasswordEditText = findViewById(R.id.auth_password)
        buttonLogIn = findViewById(R.id.button_log_in)
        progressBar = findViewById(R.id.progressBar)

        mLoginTextView = findViewById(R.id.tv_login)
        mPasswordTextView = findViewById(R.id.tv_password)

        get()
    }

    private fun get() {

        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBdXRoZW50aWNhdGlvbiIsIml" +
                "zcyI6IkNsaWVudCIsImV4cCI6MTYxNzAxMzEyOSwiZW1haWwiOiJHYWNna292c2t5LmRhbmlsQG1" +
                "haWwucnUifQ.Pz3hvbqrh1oqwGGD1WJ5s-dDNHRsR7gARwewHAJoDBlg4ZSEn9DS6Z_Ng3dwldar" +
                "RroxoCZlKKpqOB1YuNROYg"

        val auth = "yes"

        val client = NetworkService.getInstance()
            ?.getApolloClientWithTokenInterceptor(token, auth)

        progressBar.visibility = View.VISIBLE

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
                            if (user != null) {
                                user.name?.let { Log.e("Log", it) }
                                user.email?.let { Log.e("Log", it) }
                            }
                        }
                    }
                }


            })
        progressBar.visibility = View.GONE
    }


}