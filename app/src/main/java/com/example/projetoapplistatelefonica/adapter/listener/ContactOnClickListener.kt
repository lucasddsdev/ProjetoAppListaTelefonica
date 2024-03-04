package com.example.projetoapplistatelefonica.adapter.listener

import com.example.projetoapplistatelefonica.model.ContactModel

class ContactOnClickListener(val clickListener: (contact: ContactModel)-> Unit) {
    fun onClick(contact: ContactModel) = clickListener
}