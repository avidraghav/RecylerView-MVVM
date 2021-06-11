package com.example.appdevnotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdevnotes.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    //private lateinit var subscriberViewModel: SubscriberViewModel
    private lateinit var adapter: MyRecyclerViewAdapter
    private val subscriberViewModel: SubscriberViewModel by viewModels {
        SubscriberViewModelFactory((application as SubscriberApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

//        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
//        val repository = SubscriberRepository(dao)
//        val factory = SubscriberViewModelFactory(repository)
//        subscriberViewModel = ViewModelProvider(this, factory).get(SubscriberViewModel::class.java)
        binding.myViewModel = subscriberViewModel
        binding.lifecycleOwner = this

        subscriberViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
        initRecyclerView()
    }
    private fun initRecyclerView(){
        binding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyRecyclerViewAdapter { selectedItem: Subscriber -> listItemClicked(selectedItem) }
        binding.subscriberRecyclerView.adapter = adapter
        displaySubscribersList()
    }

    private fun displaySubscribersList(){
        subscriberViewModel.allSubscribers.observe(this) { words ->
            // Update the cached copy of the words in the adapter.
           // adapter.setList(it)
            words.let {
                //adapter.setList(it)
                adapter.submitList(it) }
        }
    }

    private fun listItemClicked(subscriber: Subscriber){
        subscriberViewModel.initUpdateAndDelete(subscriber)
    }
}
