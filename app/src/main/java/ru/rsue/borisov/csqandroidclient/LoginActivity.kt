package ru.rsue.borisov.csqandroidclient


//import GetIdAndTokenQuery
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import ru.rsue.borisov.csqandroidclient.db.AppDatabase
import ru.rsue.borisov.csqandroidclient.db.ClientInf
import ru.rsue.borisov.csqandroidclient.ui.user_calendar.NetworkService


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


        //get()
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "ClientInf"
        ).build()

        val client = ClientInf(1,"dffef")
        val userDao = db.userDao()
        //вставить id и token пользователя в БД
         userDao.insertClientIdAndToken(client)
    }

    /*private fun get() {

        *//* val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBdXRoZW50aWNhdGlvbiIsIml" +
                 "zcyI6IkNsaWVudCIsImV4cCI6MTYxNzAxMzEyOSwiZW1haWwiOiJHYWNna292c2t5LmRhbmlsQG1" +
                 "haWwucnUifQ.Pz3hvbqrh1oqwGGD1WJ5s-dDNHRsR7gARwewHAJoDBlg4ZSEn9DS6Z_Ng3dwldar" +
                 "RroxoCZlKKpqOB1YuNROYg"*//*

        val auth = "no"

        val client = NetworkService.getInstance()
            ?.getApolloClientWithHeader(auth)

        progressBar.visibility = View.VISIBLE

        client
            ?.query(GetIdAndTokenQuery(mLoginEditText.toString(), mPasswordEditText.toString()))
            ?.enqueue(object : ApolloCall.Callback<GetIdAndTokenQuery.Data>() {

                override fun onFailure(e: ApolloException) {
                    Log.e("ErrorQuery", e.toString())
                }

                override fun onResponse(response: Response<GetIdAndTokenQuery.Data>) {
                    val clients = response.data!!.authClient
                    if (clients == null || response.hasErrors()) {
                        Log.e("ErrorResponse", response.toString())
                    } else {
                        for (client in clients) {
                            mLoginTextView.text = client!!.token.toString()
                            mPasswordTextView.text = client.id.toString()
                        }
                    }
                }
            })
        progressBar.visibility = View.GONE
    }*/
}