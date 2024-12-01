package com.example.studentmanagement

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagement.controller.AddStudentFragment
import com.example.studentmanagement.controller.EditStudentFragment
import com.example.studentmanagement.controller.RemoveStudentFragment
import com.example.studentmanagement.controller.StudentAdapter
import com.example.studentmanagement.database.StudentData
import com.example.studentmanagement.model.Student

class MainActivity : AppCompatActivity(), OnStudentFragment {
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var students: MutableList<Student>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        students = StudentData.students
        studentAdapter = StudentAdapter(students, this)

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
                showAddStudentFragment()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showAddStudentFragment() {
        val fragment = AddStudentFragment()
        fragment.setOnStudentAddedListener(object : AddStudentFragment.OnStudentAddedListener {

            override fun onStudentAdded(name: String, id: String) {
                students.add(Student(name, id))
                studentAdapter.notifyItemInserted(students.size - 1)
            }
        })

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun showRemoveStudentFragment(position: Int, student: Student) {
        val fragment = RemoveStudentFragment()
        fragment.setup(studentAdapter, position, student)
        // Begin a fragment transaction to add the fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun showEditStudentFragment(position: Int, student: Student) {
        val fragment = EditStudentFragment()
        fragment.setup(studentAdapter, position, student)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }


    private fun showAddStudentDialog() {
        /*val dialog = Dialog(this)
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
        }*/
    }
}

public interface OnStudentFragment{
    fun showAddStudentFragment()
    fun showRemoveStudentFragment(position: Int, student: Student)
    fun showEditStudentFragment(position: Int, student: Student)
}
