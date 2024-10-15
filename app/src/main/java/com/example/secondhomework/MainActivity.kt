package com.example.secondhomework

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

private const val EXTRA_STUD_EDIT = "com.example.secondhomework.stud_edit"

class MainActivity : AppCompatActivity() {

    private lateinit var btnEdit: Button
    private lateinit var surnameTextView: TextView
    private lateinit var nameTextView: TextView
    private lateinit var patronymicTextView: TextView
    private lateinit var ageTextView: TextView
    private lateinit var courseTextView: TextView
    private lateinit var groupTextView: TextView
    private lateinit var subGroupTextView: TextView
    private lateinit var examTextView: TextView

    private var student = Student(
        "Фамилия",
        "Имя",
        "Отчество",
        "Дата рождения",
        1,
        "Группа",
        1,
        1
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        surnameTextView = findViewById(R.id.textSurname)
        nameTextView = findViewById(R.id.textName)
        patronymicTextView = findViewById(R.id.textPatronymic)
        ageTextView = findViewById(R.id.textAge)
        courseTextView = findViewById(R.id.textCourse)
        groupTextView = findViewById(R.id.textGroup)
        subGroupTextView = findViewById(R.id.textSubGroup)
        examTextView = findViewById(R.id.textExam)
        btnEdit = findViewById(R.id.buttonEdit)

        setStudentInfo()

        fun updateView() {
            setStudentInfo()
        }

        var isCreate = false

        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    isCreate = data?.getBooleanExtra(EXTRA_STUD_EDIT, false) ?: false
                    val surname = result.data?.getStringExtra("surname") ?: ""
                    val name = result.data?.getStringExtra("name") ?: ""
                    val patronymic = result.data?.getStringExtra("patronymic") ?: ""
                    val age = result.data?.getStringExtra("age") ?: ""
                    val course = result.data?.getIntExtra("course", 1) ?: 1
                    val group = result.data?.getStringExtra("group") ?: ""
                    val subGroup = result.data?.getIntExtra("sub_group", 1) ?: 1
                    val exam = result.data?.getIntExtra("exam", 1) ?: 1
                    student = Student(surname, name, patronymic, age, course, group, subGroup, exam)
                    updateView()
                }
            }
        findViewById<Button>(R.id.buttonEdit).setOnClickListener {
            val intent = EditStudent.newIntent(
                this@MainActivity,
                isCreate,
                student.studentSurname,
                student.studentName,
                student.studentPatronymic,
                student.studentAge,
                student.studentCourse,
                student.studentGroup,
                student.studentSubGroup,
                student.studentExam
            )
            resultLauncher.launch(intent)
        }

    }

    private fun setStudentInfo() {
        surnameTextView.text = "Фамилия: ${student.studentSurname}"
        nameTextView.text = "Имя: ${student.studentName}"
        patronymicTextView.text = "Отчество: ${student.studentPatronymic}"
        ageTextView.text = "Дата рождения: ${student.studentAge}"
        courseTextView.text = "Курс: ${student.studentCourse}"
        groupTextView.text = "Группа: ${student.studentGroup}"
        subGroupTextView.text = "Подгруппа: ${student.studentSubGroup}"
        examTextView.text = "Номер билета: ${student.studentExam}"
    }
}
