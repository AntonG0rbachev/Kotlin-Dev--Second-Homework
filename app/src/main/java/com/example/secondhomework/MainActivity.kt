package com.example.secondhomework

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

private  val EXTRA_STUD_EDIT = "com.example.oneperson_blog.stud_edit"

class MainActivity : AppCompatActivity() {

    private lateinit var btnEdit: Button
    private lateinit var nameTextView: TextView
    private lateinit var ageTextView: TextView
    private lateinit var heightTextView: TextView
    private lateinit var weightTextView: TextView

    var stud = Student("Имя", 0, 0, 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        nameTextView = findViewById(R.id.textName)
        ageTextView = findViewById(R.id.textAge)
        heightTextView = findViewById(R.id.textCourse)
        weightTextView = findViewById(R.id.textGroup)
        btnEdit = findViewById(R.id.buttonEdit)

        fun updateView() {
            nameTextView.text = "Имя: " + stud.PersName
            ageTextView.text = "Возраст: " + stud.PersAge.toString()
            heightTextView.text = "Рост: " + stud.PersHeight.toString()
            weightTextView.text = "Вес: " + stud.PersWeight.toString()
        }

        var isCreate = false

        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    isCreate = data?.getBooleanExtra(EXTRA_STUD_EDIT, false) ?: false
                    val name = result.data?.getStringExtra("name") ?: ""
                    val age = result.data?.getIntExtra("age", 0) ?: 0
                    val height = result.data?.getIntExtra("height", 0) ?: 0
                    val weight = result.data?.getIntExtra("weight", 0) ?: 0
                    stud = Student(name, age, height, weight)
                    updateView()
                }
            }
        findViewById<Button>(R.id.buttonEdit).setOnClickListener {
            //val create = isCreate
            val intent = EditStudent.newIntent(
                this@MainActivity,
                isCreate,
                stud.PersName,
                stud.PersAge,
                stud.PersHeight,
                stud.PersWeight
            )
            resultLauncher.launch(intent)
        }

    }
}
