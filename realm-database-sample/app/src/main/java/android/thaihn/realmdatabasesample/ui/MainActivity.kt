package android.thaihn.realmdatabasesample.ui

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.thaihn.realmdatabasesample.R
import android.thaihn.realmdatabasesample.databinding.ActivityMainBinding
import android.thaihn.realmdatabasesample.entity.Contact
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import io.realm.Realm
import io.realm.Sort

class MainActivity : AppCompatActivity(), ContactAdapter.ContactListener {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private var mRealm: Realm? = null

    private var mContactEdit: Contact? = null

    private val mContactAdapter = ContactAdapter(arrayListOf(), this)

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mRealm = Realm.getDefaultInstance()

        mBinding.rvContact.apply {
            adapter = mContactAdapter
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        }

        addListenerDatabase()

        mBinding.btnAdd.setOnClickListener {
            insertStudent()
        }

        mBinding.btnEdit.setOnClickListener {
            updateStudent()
        }

        mBinding.btnDelete.setOnClickListener {
            deleteByAddress()
        }
    }

    override fun deleteContact(item: Contact) {
        mRealm?.executeTransaction { realm ->
            val result = realm.where(Contact::class.java).equalTo("id", item.id).findAll()
            result.deleteAllFromRealm()
        }
    }

    override fun editContact(item: Contact) {
        mContactEdit = item
        mBinding.edtName.setText(item.name)
        mBinding.edtPhone.setText(item.phone)
        mBinding.edtAddress.setText(item.address)
    }

    private fun deleteByAddress() {
        val address = mBinding.edtAddress.text.trim().toString()

        if (address.isEmpty()) {
            Toast.makeText(applicationContext, "Address is empty!", Toast.LENGTH_SHORT).show()
            return
        }

        mRealm?.executeTransaction {realm->
            val result = realm
                .where(Contact::class.java).equalTo("address", address)
                .findAll()
            result.deleteAllFromRealm()
        }
    }

    private fun updateStudent() {
        mContactEdit?.let {
            val name = mBinding.edtName.text.trim().toString()
            val phone = mBinding.edtPhone.text.trim().toString()
            val address = mBinding.edtAddress.text.trim().toString()

            if (name.isEmpty() && phone.isEmpty() && address.isEmpty()) {
                Toast.makeText(applicationContext, "Unless have one field name", Toast.LENGTH_SHORT)
                    .show()
                return
            }

            mRealm?.executeTransaction { realm ->
                val newContact = Contact(it.id, name, phone, address)
                realm.insertOrUpdate(newContact)
            }

            releaseInput()
            hideKeyboard()
        }
    }

    private fun insertStudent() {

        val name = mBinding.edtName.text.trim().toString()
        val phone = mBinding.edtPhone.text.trim().toString()
        val address = mBinding.edtAddress.text.trim().toString()

        if (name.isEmpty()) {
            Toast.makeText(applicationContext, "Name is empty!", Toast.LENGTH_SHORT).show()
            return
        }
        if (phone.isEmpty()) {
            Toast.makeText(applicationContext, "Phone is empty!", Toast.LENGTH_SHORT).show()
            return
        }
        if (address.isEmpty()) {
            Toast.makeText(applicationContext, "Address is empty!", Toast.LENGTH_SHORT).show()
            return
        }

        mRealm?.executeTransaction { realm ->
            val maxId = realm.where(Contact::class.java).max("id")
            val nextId: Long = if (maxId == null) {
                1
            } else {
                maxId.toLong() + 1
            }

            val contact = realm.createObject(Contact::class.java, nextId)
            contact.address = address
            contact.name = name
            contact.phone = phone

//            val contact = Contact(nextId, name, phone, address)
//            realm.copyToRealmOrUpdate(contact)

            Toast.makeText(applicationContext, "Add contact success", Toast.LENGTH_SHORT).show()

        }

        releaseInput()
        hideKeyboard()
    }

    private fun addListenerDatabase() {
        mRealm?.where(Contact::class.java)
            ?.sort("id", Sort.ASCENDING)
            ?.findAll()
            ?.let { contactResults ->
                if (contactResults.count() > 0) {
                    val results = arrayListOf<Contact>()
                    contactResults.forEach {
                        results.add(it)
                    }

                    Log.d(TAG, "Contacts:$results")
                    mContactAdapter.addAllContact(results)
                }

                contactResults.addChangeListener { realmResults, changeSet ->
                    mContactAdapter.addAllContact(realmResults)
                }
            }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun releaseInput() {
        mBinding.edtName.setText("")
        mBinding.edtAddress.setText("")
        mBinding.edtPhone.setText("")
    }
}
