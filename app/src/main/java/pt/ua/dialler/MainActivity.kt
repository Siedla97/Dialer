package pt.ua.dialler

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var phoneNumber = ""
        val contact1 = Contact("Contact1", "Number")
        val contact2 = Contact("Contact2", "Number")
        val contact3 = Contact("Contact3", "Number")

        findViewById<Button>(R.id.button0).setOnClickListener {
            phoneNumber = addSymbolToPhoneNumber("0", phoneNumber)
        }
        findViewById<Button>(R.id.button0).setOnLongClickListener {
            phoneNumber = addSymbolToPhoneNumber("+", phoneNumber)
            true
        }
        findViewById<Button>(R.id.button1).setOnClickListener {
            phoneNumber = addSymbolToPhoneNumber("1", phoneNumber)
        }
        findViewById<Button>(R.id.button2).setOnClickListener {
            phoneNumber = addSymbolToPhoneNumber("2", phoneNumber)
        }
        findViewById<Button>(R.id.button3).setOnClickListener {
            phoneNumber = addSymbolToPhoneNumber("3", phoneNumber)
        }
        findViewById<Button>(R.id.button4).setOnClickListener {
            phoneNumber = addSymbolToPhoneNumber("4", phoneNumber)
        }
        findViewById<Button>(R.id.button5).setOnClickListener {
            phoneNumber = addSymbolToPhoneNumber("5", phoneNumber)
        }
        findViewById<Button>(R.id.button6).setOnClickListener {
            phoneNumber = addSymbolToPhoneNumber("6", phoneNumber)
        }
        findViewById<Button>(R.id.button7).setOnClickListener {
            phoneNumber = addSymbolToPhoneNumber("7", phoneNumber)
        }
        findViewById<Button>(R.id.button8).setOnClickListener {
            phoneNumber = addSymbolToPhoneNumber("8", phoneNumber)
        }
        findViewById<Button>(R.id.button9).setOnClickListener {
            phoneNumber = addSymbolToPhoneNumber("9", phoneNumber)
        }
        findViewById<Button>(R.id.buttonHash).setOnClickListener {
            phoneNumber = addSymbolToPhoneNumber("#", phoneNumber)
        }
        findViewById<Button>(R.id.buttonStar).setOnClickListener {
            phoneNumber = addSymbolToPhoneNumber("*", phoneNumber)
        }

        findViewById<Button>(R.id.buttonRemove).setOnClickListener {
            phoneNumber = removeLastSymbolOfPhoneNumber(phoneNumber)
        }

        findViewById<Button>(R.id.buttonRemove).setOnClickListener {
            phoneNumber = removeLastSymbolOfPhoneNumber(phoneNumber)
        }

        findViewById<Button>(R.id.buttonCall).setOnClickListener { call(phoneNumber) }

        val button1 = findViewById<Button>(R.id.buttonShort1)
        button1.setOnClickListener {
            val phoneNumberShort1 = contact1.returnPhoneNumber()
            if (phoneNumberShort1 == "Number") {
                Toast.makeText(this, "Edit contact first!", Toast.LENGTH_SHORT).show()
            } else {
                call(phoneNumberShort1)
            }
        }
        button1.setOnLongClickListener {
            openDialog(contact1, button1)
            true
        }
        val button2 = findViewById<Button>(R.id.buttonShort2)
        button2.setOnClickListener {
            val phoneNumberShort2 = contact2.returnPhoneNumber()
            if (phoneNumberShort2 == "Number") {
                Toast.makeText(this, "Edit contact first!", Toast.LENGTH_SHORT).show()
            } else {
                call(phoneNumberShort2)
            }
        }
        button2.setOnLongClickListener {
            openDialog(contact2, button2)
            true
        }
        val button3 = findViewById<Button>(R.id.buttonShort3)
        button3.setOnClickListener {
            val phoneNumberShort3 = contact3.returnPhoneNumber()
            if (phoneNumberShort3 == "Number") {
                Toast.makeText(this, "Edit contact first!", Toast.LENGTH_SHORT).show()
            } else {
                call(phoneNumberShort3)
            }
        }
        button3.setOnLongClickListener {
            openDialog(contact3, button3)
            true
        }

        //Check permissions for call
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CALL_PHONE),
                123
            )
        }
    }

    private fun addSymbolToPhoneNumber(symbolToAdd: String, phoneNumber: String): String {
        val newPhoneNumber = phoneNumber + symbolToAdd
        val textView: EditText = findViewById(R.id.editTextPhone)
        textView.setText(newPhoneNumber)
        return newPhoneNumber
    }

    private fun removeLastSymbolOfPhoneNumber(phoneNumber: String): String {
        val newPhoneNumber = phoneNumber.dropLast(1)
        val textView: EditText = findViewById(R.id.editTextPhone)
        textView.setText(newPhoneNumber)
        return newPhoneNumber
    }

    //if no permissions for call, swap to default dialer
    private fun call(phoneNumber: String) {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:$phoneNumber")
            startActivity(dialIntent)
        } else {
            val dialIntent = Intent(Intent.ACTION_CALL)
            dialIntent.data = Uri.parse("tel:$phoneNumber")
            startActivity(dialIntent)
        }
    }

    private fun openDialog(currentContact: Contact, clickedButton: Button) {
        val dialog = LayoutInflater.from(this).inflate(R.layout.contact_dialog, null)
        val builder = AlertDialog.Builder(this).setView(dialog).setTitle("Adjust contact")
        val alertDialog = builder.show()
        dialog.findViewById<EditText>(R.id.editTextName).setText(currentContact.returnName())
        dialog.findViewById<EditText>(R.id.editTextNumber)
            .setText(currentContact.returnPhoneNumber())

        dialog.findViewById<Button>(R.id.buttonSave).setOnClickListener {
            alertDialog.dismiss()
            val newName = dialog.findViewById<EditText>(R.id.editTextName).text.toString()
            val newPhoneNumber = dialog.findViewById<EditText>(R.id.editTextNumber).text.toString()
            currentContact.setName(newName)
            currentContact.setPhoneNumber(newPhoneNumber)

            clickedButton.text = currentContact.returnName()
        }

        dialog.findViewById<Button>(R.id.buttonCancel).setOnClickListener {
            alertDialog.dismiss()

            clickedButton.text = currentContact.returnName()
        }
    }
}

class Contact(private var name: String, private var phoneNumber: String) {

    fun returnName(): String {
        return this.name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun returnPhoneNumber(): String {
        return this.phoneNumber
    }

    fun setPhoneNumber(phoneNumber: String) {
        this.phoneNumber = phoneNumber
    }
}


