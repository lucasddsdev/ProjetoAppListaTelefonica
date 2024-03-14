package com.example.projetoapplistatelefonica.ui

import android.content.Intent
import android.content.SharedPreferences
import android.icu.text.Transliterator.Position
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetoapplistatelefonica.R
import com.example.projetoapplistatelefonica.adapter.ContactListAdapter
import com.example.projetoapplistatelefonica.adapter.listener.ContactOnClickListener
import com.example.projetoapplistatelefonica.database.DBHelper
import com.example.projetoapplistatelefonica.databinding.ActivityMainBinding
import com.example.projetoapplistatelefonica.databinding.ActivitySplashScreenBinding
import com.example.projetoapplistatelefonica.model.ContactModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var contactList: List<ContactModel>
    private lateinit var adapter: ContactListAdapter
    private lateinit var result: ActivityResultLauncher<Intent>
    private lateinit var dbHelper: DBHelper
    private var ascDesc : Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)
        val sharedPreferences = application.getSharedPreferences("login", MODE_PRIVATE)

        binding.recyclerViewContacts.layoutManager = LinearLayoutManager(applicationContext)
        loadList()


        binding.buttonLogout.setOnClickListener(){
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("username","")
            editor.apply()
            finish()
        }

        binding.buttonAdd.setOnClickListener(){
            result.launch(Intent(applicationContext,NewContactActivity::class.java))

        }

        binding.buttonOrder.setOnClickListener(){
            if (ascDesc){
                binding.buttonOrder.setImageResource(R.drawable.baseline_arrow_upward_24)
            }else{
                binding.buttonOrder.setImageResource(R.drawable.baseline_arrow_downward_24)
            }

            ascDesc = !ascDesc
            contactList = contactList.reversed()
            placeAdapter()


        }

        result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.data != null && it.resultCode == 1){
                contactList = dbHelper.getAllContact()
                loadList()
            }else if(it.data != null && it.resultCode == 0){
                Toast.makeText(applicationContext, "Operation canceled", Toast.LENGTH_SHORT).show()
            }
        }


    }
    private fun placeAdapter(){
        adapter = ContactListAdapter(contactList, ContactOnClickListener { contact ->
            val intent = Intent(applicationContext,ContactDetailActivity::class.java)
            intent.putExtra("id",contact.id)
            result.launch(intent)
        })

        binding.recyclerViewContacts.adapter = adapter
    }


    private fun loadList() {
        contactList = dbHelper.getAllContact().sortedWith(compareBy { it.name })
        placeAdapter()

        }

}