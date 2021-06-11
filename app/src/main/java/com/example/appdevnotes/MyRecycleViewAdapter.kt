package com.example.appdevnotes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appdevnotes.databinding.ListItemBinding


class MyRecyclerViewAdapter(private val clickListener: (Subscriber) -> Unit) :
    ListAdapter<Subscriber, MyRecyclerViewAdapter.MyViewHolder>(WORDS_COMPARATOR) {
   // private val subscribersList = ArrayList<Subscriber>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentpos = getItem(position)
        holder.bind(currentpos, clickListener)
    }

//    fun setList(subscribers: List<Subscriber>) {
//        subscribersList.clear()
//        subscribersList.addAll(subscribers)
//    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<Subscriber>() {
            override fun areItemsTheSame(oldItem: Subscriber, newItem: Subscriber): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Subscriber, newItem: Subscriber): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

   // val asyncListDiffer = AsyncListDiffer(this, RECYCLER_COMPARATOR)

    class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(subscriber: Subscriber, clickListener: (Subscriber) -> Unit) {
            binding.nameTextView.text = subscriber.name
            binding.emailTextView.text = subscriber.email
            binding.listItemLayout.setOnClickListener {
                clickListener(subscriber)
            }
        }


    }
}


