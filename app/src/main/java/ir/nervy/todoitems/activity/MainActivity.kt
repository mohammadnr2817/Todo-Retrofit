package ir.nervy.todoitems.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ir.nervy.todoitems.R
import ir.nervy.todoitems.adapter.TodoAdapter
import ir.nervy.todoitems.api.RetrofitInstance
import ir.nervy.todoitems.databinding.ActivityMainBinding
import java.io.IOException
import java.lang.Exception

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()

        lifecycleScope.launchWhenCreated {
            binding.mainProgress.isVisible = true
            val response = try {
                RetrofitInstance.api.getTodos()
            }catch (e : Exception){
                binding.mainProgress.isVisible = false
                Log.e(TAG, "Exception: ", e.cause)
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null){
                todoAdapter.todos = response.body()!!
            }else{
                Log.e(TAG, "Exception: response was not OK")
            }
            binding.mainProgress.isVisible = false
        }

    }

    private fun setUpRecyclerView() = binding.mainRecycler.apply {
        todoAdapter = TodoAdapter()
        adapter = todoAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }

}