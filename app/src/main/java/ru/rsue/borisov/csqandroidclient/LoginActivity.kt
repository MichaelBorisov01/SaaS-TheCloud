package ru.rsue.borisov.csqandroidclient

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import ru.rsue.borisov.csqandroidclient.db.AppDatabase
import ru.rsue.borisov.csqandroidclient.queries.GetClient

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
        val login = mLoginEditText.toString()
        val password = mPasswordEditText.toString()

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "ClientInf"
        ).build()

        val clientDao = db.clientDao()

        GetClient().getIdAndToken(login, password, clientDao)

    }
}