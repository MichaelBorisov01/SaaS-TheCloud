package ru.rsue.borisov.csqandroidclient.ui.cabinet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.rsue.borisov.csqandroidclient.R

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
        val textView: TextView = root.findViewById(R.id.text_cabinet)
        cabinetViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}