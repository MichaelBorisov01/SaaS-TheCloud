package ru.rsue.borisov.csqandroidclient

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import kotlinx.coroutines.runBlocking

class LoginActivity : AppCompatActivity() {
    private lateinit var mLoginEditText: EditText
    private lateinit var mPasswordEditText: EditText
    private lateinit var buttonLogIn: Button
    private var respond: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        mLoginEditText = findViewById(R.id.auth_login)
        mPasswordEditText = findViewById(R.id.auth_password)
        buttonLogIn = findViewById(R.id.button_log_in)


        buttonLogIn.setOnClickListener {
            try {
                if (mPasswordEditText.text.toString().length > 5
                ) {
                    if (mLoginEditText.text.toString().length > 4) {

                        runBlocking {
                            getLogin(
                                mLoginEditText.text.toString(),
                                mPasswordEditText.text.toString()
                            )
                        }
                        if (respond!!.length > 50) {
                            Toast.makeText(this, "Здравствуйте", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, ProfileEditActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this,
                                "Нет такого логина или пароля",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else mLoginEditText.error = "Убедитесь в корректности логина или email"
                } else mPasswordEditText.error = "Убедитесь в корректности пароля"
            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    "Hет подключения к серверу",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun loginUser() {

        val client = NetworkService.getInstance()?.getApolloClient()
        val loginMutation = LoginUserMutation
            .builder()
            .email(mLoginEditText.text.toString())
            .password(mPasswordEditText.text.toString())
            .build()

        client
            ?.mutate(loginMutation)
            ?.enqueue(object : ApolloCall.Callback<LoginUserMutation.Data>() {

                override fun onResponse(response: Response<LoginUserMutation.Data>) {
                    if (!response.hasErrors()) {
                        val token = response.data?.login()?.token()
                        val email = response.data?.login()?.email()
                        //Делаем операции, не трогающие ui, например сохраняем токен в БД
                        runOnUiThread {
                            //Выводим на экран то, что хотим
                        }
                    }
                }

                override fun onFailure(e: ApolloException) {}
            })
    }

}