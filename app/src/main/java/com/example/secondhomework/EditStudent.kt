package com.example.secondhomework

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


private  val EXTRA_STUD_EDIT = "com.example.oneperson_blog.stud_edit"

class EditStudent : AppCompatActivity() {

    companion object{
        fun newIntent(packageContext: Context, edit: Boolean, name: String="", age: Int=0, height: Int=0, weight: Int=0): Intent {
            return Intent(packageContext, EditStudent::class.java).apply{
                putExtra("edit_mode", edit)
                putExtra("name", name)
                putExtra("age", age)
                putExtra("height", height)
                putExtra("weight", weight)
            }
        }
    }

    private  lateinit var saveButton: Button
    private  lateinit var cancelButton: Button
    private lateinit var nameET: EditText
    private lateinit var ageET: EditText
    private lateinit var weightET: EditText
    private lateinit var heightET: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_person)

        val isEdit = intent?.getBooleanExtra("edit_mode", false)

        nameET = findViewById(R.id.editTextName)
        ageET = findViewById(R.id.editTextAge)
        weightET = findViewById(R.id.editTextWeight)
        heightET = findViewById(R.id.editTextHeight)

        if (isEdit!!) {
            nameET.setText(intent.getStringExtra("name"))
            ageET.setText(intent.getIntExtra("age",0).toString())
            weightET.setText(intent.getIntExtra("weight", 0).toString())
            heightET.setText(intent.getIntExtra("height",0).toString())
        }


        saveButton = findViewById(R.id.button4)
        saveButton.setOnClickListener{
            if (nameET.text.isNullOrEmpty() || ageET.text.isNullOrEmpty() || weightET.text.isNullOrEmpty() || heightET.text.isNullOrEmpty()){
                Toast.makeText(this, "Все поля обязательны", Toast.LENGTH_SHORT).show()
            }else if (nameET.text.toString().contains(Regex("[0-9]+"))){
                Toast.makeText(this, "В имени не допустимо использование цифр", Toast.LENGTH_SHORT).show()
            }else{
                val data = Intent().apply {
                    putExtra(EXTRA_STUD_EDIT, true)
                    putExtra("name", nameET.text.toString())
                    putExtra("age", ageET.text.toString().toInt())
                    putExtra("weight", weightET.text.toString().toInt())
                    putExtra("height", heightET.text.toString().toInt())
                }
                setResult(Activity.RESULT_OK, data)
                finish()
            }
        }

        cancelButton = findViewById(R.id.button3)
        cancelButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}