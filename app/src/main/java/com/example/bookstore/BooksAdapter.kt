package com.example.bookstore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class BooksAdapter(var  item:List<Books>):RecyclerView.Adapter<BooksAdapter.BooksViewHolder>(), Filterable {

    var booksFilteredList: List<Books> = ArrayList()

    init {
        booksFilteredList= item
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?) : FilterResults {
                var charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    booksFilteredList = item
                } else {
                    var resultList = ArrayList<Books>()
                    for (Books in item) {
                        if (Books.title.contains(charSearch)
                            || Books.author.contains(charSearch)) {
                            resultList.add(Books)
                        }
                    }
                    booksFilteredList = resultList
                }
                val FilterResults = FilterResults()
                FilterResults.values=booksFilteredList
                return FilterResults
            }




            override fun publishResults(constraint: CharSequence, result: FilterResults) {
                booksFilteredList=result.values as ArrayList<Books>
                notifyDataSetChanged()

            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.item_books, parent, false)

        return BooksViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        val Books= booksFilteredList[position]
        holder.bind(Books)
    }

    override fun getItemCount() = booksFilteredList.size



    class  BooksViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        var  tvTitle: TextView
        var  tvSummary: TextView
        var  tvAuthor: TextView
        var  imageBook: ImageView
        var  ratingBar: RatingBar

        init {
            tvTitle= itemView.findViewById(R.id.titleTV)
            tvSummary= itemView.findViewById(R.id.summaryTV)
            tvAuthor= itemView.findViewById(R.id.authorTV)
            imageBook= itemView.findViewById(R.id.imagebook)
            ratingBar= itemView.findViewById(R.id.ratingBar)
        }
        fun bind(books : Books){
            tvTitle.text = books.title
            tvAuthor.text = books.author
            tvSummary.text = books.summary
            imageBook.setImageResource(books.image)
            Glide.with(itemView.context).load(books.image).into(imageBook)

        }
    }


}