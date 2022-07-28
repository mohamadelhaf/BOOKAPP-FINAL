package com.example.bookstore

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class BooksActivity : AppCompatActivity() {

    val item = listOf<Books>()

    lateinit var booksRecyclerView: RecyclerView
    lateinit var booksAdapter: BooksAdapter
    lateinit var toolbar: Toolbar
    lateinit var searchIV: ImageView
    lateinit var searchET: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)


        toolbar = findViewById(R.id.toolbar)
        searchIV = toolbar.findViewById(R.id.searchIV)
        searchET= toolbar.findViewById(R.id.SearchET)
        setSupportActionBar(toolbar)

        val retrofit = Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com").addConverterFactory(
            GsonConverterFactory.create()).build()

        val BooksService = retrofit.create(BooksService::class.java)

        val result = BooksService.getBooks()

        result.enqueue(object : Callback<List<Books>> {
            override fun onResponse(call: Call<List<Books>>, response: Response<List<Books>>) {
                if(response.isSuccessful){

                    booksRecyclerView = findViewById(R.id.booksRV)

                    booksAdapter =  BooksAdapter(item)
                    booksRecyclerView.apply {
                        booksAdapter = BooksAdapter(response.body()!!)
                        layoutManager = LinearLayoutManager(this@BooksActivity )
                        adapter = booksAdapter
                    }




                }
            }

            override fun onFailure(call: Call<List<Books>>, t: Throwable) {
                Toast.makeText(applicationContext,"Server Error", Toast.LENGTH_SHORT).show()
            }
        })

        searchIV.setOnClickListener {
            val search =searchET.text.toString()
            booksAdapter.filter.filter(search)
        }








    }
}




/*  val items : List<Books> = response.body() as List<Books>
                    var posts = arrayOfNulls<String>(items!!.size)

                    for(i in items!!.indices)
                        posts[i] = items!![i]!!.title

                    var adapter = ArrayAdapter<String>(applicationContext,android.R.layout.list_content,posts)
                    booksRecyclerView.adapter = adapter*/