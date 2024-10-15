package com.example.secondhomework

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar


private const val EXTRA_STUD_EDIT = "com.example.secondhomework.stud_edit"

class EditStudent : AppCompatActivity() {

    companion object{
        fun newIntent(
            packageContext: Context,
            edit: Boolean,
            surname: String="",
            name: String="",
            patronymic: String="",
            age: String="",
            course: Int=0,
            group: String="",
            subGroup: Int=0,
            exam: Int=0
        ): Intent {
            return Intent(packageContext, EditStudent::class.java).apply{
                putExtra("edit_mode", edit)
                putExtra("surname", surname)
                putExtra("name", name)
                putExtra("patronymic", patronymic)
                putExtra("age", age)
                putExtra("course", course)
                putExtra("group", group)
                putExtra("sub_group", subGroup)
                putExtra("exam", exam)
            }
        }
    }

    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button
    private lateinit var surnameET: EditText
    private lateinit var nameET: EditText
    private lateinit var patronymicET: EditText
    private lateinit var ageET: EditText
    private lateinit var courseET: EditText
    private lateinit var groupET: EditText
    private lateinit var subGroupET: EditText
    private lateinit var examET: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_person)

        val isEdit = intent?.getBooleanExtra("edit_mode", false)

        surnameET = findViewById(R.id.editTextSurname)
        nameET = findViewById(R.id.editTextName)
        patronymicET = findViewById(R.id.editTextPatronymic)
        ageET = findViewById(R.id.editTextAge)
        courseET = findViewById(R.id.editTextCourse)
        groupET = findViewById(R.id.editTextGroup)
        subGroupET = findViewById(R.id.editTextSubGroup)
        examET = findViewById(R.id.editTextExam)

        if (isEdit!!) {
            surnameET.setText(intent.getStringExtra("surname"))
            nameET.setText(intent.getStringExtra("name"))
            patronymicET.setText(intent.getStringExtra("patronymic"))
            ageET.setText(intent.getStringExtra("age"))
            courseET.setText(intent.getIntExtra("course", 1).toString())
            groupET.setText(intent.getStringExtra("group"))
            subGroupET.setText(intent.getIntExtra("sub_group",1).toString())
            examET.setText(intent.getIntExtra("exam",1).toString())
        }


        saveButton = findViewById(R.id.buttonSave)
        saveButton.setOnClickListener {
            if (
                nameET.text.isNullOrEmpty()
                || ageET.text.isNullOrEmpty()
                || groupET.text.isNullOrEmpty()
                || courseET.text.isNullOrEmpty()
                || surnameET.text.isNullOrEmpty()
                || patronymicET.text.isNullOrEmpty()
                || subGroupET.text.isNullOrEmpty()
                || examET.text.isNullOrEmpty()
                ) {
                Toast
                    .makeText(
                        this,
                        "Все поля обязательны",
                        Toast.LENGTH_SHORT
                    ).show()
            } else if (
                nameET.text.toString().contains(Regex("[0-9]+"))
                || surnameET.text.toString().contains(Regex("[0-9]+"))
                || patronymicET.text.toString().contains(Regex("[0-9]+"))
                ) {
                Toast
                    .makeText(
                    this,
                    "В имени не допустимо использование цифр",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!tryParseDate(ageET.text.toString(), DateTimeFormatter.ofPattern("dd-MM-yyyy"))) {
                Toast
                    .makeText(
                    this,
                    "Дата рождения должна быть представлена в виде 'дд-мм-гггг'",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val data = Intent().apply {
                    putExtra(EXTRA_STUD_EDIT, true)
                    putExtra("surname", surnameET.text.toString())
                    putExtra("name", nameET.text.toString())
                    putExtra("patronymic", patronymicET.text.toString())
                    putExtra("age", ageET.text.toString())
                    putExtra("group", groupET.text.toString())
                    putExtra("course", courseET.text.toString().toInt())
                    putExtra("sub_group", subGroupET.text.toString().toInt())
                    putExtra("exam", examET.text.toString().toInt())
                }
                setResult(Activity.RESULT_OK, data)
                finish()
            }
        }

        cancelButton = findViewById(R.id.buttonCancel)
        cancelButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun tryParseDate(age: String, dateFormat: DateTimeFormatter): Boolean {
        try {
            LocalDate.parse(age, dateFormat)
            return true
        } catch (exception: ParseException) {
            return false
        }
    }
}