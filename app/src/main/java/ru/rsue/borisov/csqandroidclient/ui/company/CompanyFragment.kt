package ru.rsue.borisov.csqandroidclient.ui.company


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import ru.rsue.borisov.csqandroidclient.R
import ru.rsue.borisov.csqandroidclient.db.AppDatabase


class CompanyFragment : Fragment() {

    private lateinit var companyViewModel: CompanyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        companyViewModel = ViewModelProvider(this).get(CompanyViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_company, container, false)

        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "UserInf"
        ).build()

        val userDao = db.clientDao()
        //val userId: UserInf = userDao.getUserId()
        //  val userToken: UserInf = userDao.getUserToken()

        //val buttonCalendar: Button = root.findViewById(R.id.button_calendar)


        /*val intent = Intent(context, CalendarActivity::class.java)
        startActivity(intent)*/


        val recyclerView: RecyclerView = root.findViewById(R.id.recycler_com)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = CompanyListAdapter(generateCompanies())

        return root
    }

    //функция по добавлению нужных элементов в список из сервера (компании, мастера и т.д.)
    private fun generateCompanies(): List<String> {
        val companies = mutableListOf<String>()
        for (i in 0..10) {
            companies.add("$i element")
        }
        return companies
    }

    class CompanyListAdapter(private var companies: List<String>) :
        RecyclerView.Adapter<CompanyListAdapter.ViewHolder>() {

        override fun getItemCount() = companies.size

       /* создает новый объект ViewHolder всякий раз,
         когда RecyclerView нуждается в этом*/
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_company, parent, false)
            return ViewHolder(itemView)
        }

        /*принимает объект ViewHolder и устанавливает необходимые данные
        для соответствующей строки во view-компоненте*/
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.companyName?.text = companies[position]
            holder.companyName?.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_menu_gallery,
                0,
                 0,
                0
            )

            // обработчик нажатия для каждого viewHolder
            holder.itemView.setOnClickListener {
                val positionIndex = holder.adapterPosition

                //startActivity(intent)
                Log.e("Click", positionIndex.toString())
            }
        }

        class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
            var companyName: TextView? = null
            init {
                companyName = itemView?.findViewById(R.id.name_company)
            }
        }
    }


}