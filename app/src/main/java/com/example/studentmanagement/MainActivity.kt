package com.example.studentmanagement

import android.app.ActionBar
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagement.controller.StudentAdapter
import com.example.studentmanagement.database.StudentData
import com.example.studentmanagement.model.Student

class MainActivity : AppCompatActivity() {
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var students: MutableList<Student>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        students = StudentData.students
        studentAdapter = StudentAdapter(students)

        findViewById<RecyclerView>(R.id.recycler_view_students).run {
            adapter = studentAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_student -> {
                showAddStudentDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showAddStudentDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.layout_dialog_add)

        dialog.window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        val btnSave = dialog.findViewById<Button>(R.id.btn_add)
        val btnCancel = dialog.findViewById<Button>(R.id.btn_cancel)

        btnSave.setOnClickListener {
            val name = dialog.findViewById<EditText>(R.id.edit_name).text.toString()
            val id = dialog.findViewById<EditText>(R.id.edit_id).text.toString()

            if (name.isNotEmpty() && id.isNotEmpty()) {
                students.add(Student(name, id))
                studentAdapter.notifyItemInserted(students.size - 1)
                dialog.dismiss()
            } else {
                // Optionally handle validation error
            }
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }
}
