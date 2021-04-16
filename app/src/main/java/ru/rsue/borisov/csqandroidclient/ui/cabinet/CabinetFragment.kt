package ru.rsue.borisov.csqandroidclient.ui.cabinet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import ru.rsue.borisov.csqandroidclient.CalendarActivity
import ru.rsue.borisov.csqandroidclient.LoginActivity
import ru.rsue.borisov.csqandroidclient.R
import ru.rsue.borisov.csqandroidclient.db.AppDatabase
import ru.rsue.borisov.csqandroidclient.db.ClientInf

class CabinetFragment : Fragment() {

    private lateinit var cabinetViewModel: CabinetViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cabinetViewModel =
            ViewModelProvider(this).get(CabinetViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_cabinet, container, false)

        /* val textView: TextView = root.findViewById(R.id.text_cabinet)
         cabinetViewModel.text.observe(viewLifecycleOwner, Observer {
             textView.text = it
         })*/

        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "database-name"
        ).build()

        val userDao = db.userDao()
        //val userId: UserInf = userDao.getUserId()
        //val userToken: UserInf = userDao.getUserToken()

        val surnameTextView: TextView = root.findViewById(R.id.surname_head_tv_cab)
        val nameTextView: TextView = root.findViewById(R.id.name_head_tv_cab)
        val patronymicTextView: TextView = root.findViewById(R.id.patronymic_head_tv_cab)
        val emailTextView: TextView = root.findViewById(R.id.user_email_cab)
        val phoneTextView: TextView = root.findViewById(R.id.user_phone_cab)
        var photoImageView: ImageView = root.findViewById(R.id.user_photo_cab)

        val emailEditText: EditText = root.findViewById(R.id.email_et_cab)
        val phoneEditText: EditText = root.findViewById(R.id.phone_et_cab)
        val surnameEditText: EditText = root.findViewById(R.id.surname_et_cab)
        val nameEditText: EditText = root.findViewById(R.id.name_et_cab)
        val patronymicEditText: EditText = root.findViewById(R.id.patronymic_et_cab)

        val saveButton: Button = root.findViewById(R.id.save_but_cab)
        val historyButton: Button = root.findViewById(R.id.history_but_cab)


        saveButton.setOnClickListener {
            val intent= Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }
        historyButton.setOnClickListener {}

        //photoImageView.setImageResource(R.drawable.ic_dashboard_black_24dp)
        return root
    }
}