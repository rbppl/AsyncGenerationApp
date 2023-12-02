package com.rbppl.asyncgenerationapp.ui.fragment
import com.rbppl.asyncgenerationapp.data.Repository
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rbppl.asyncgenerationapp.databinding.FragmentItemBinding
import com.rbppl.asyncgenerationapp.ui.adapter.ItemAdapter
import com.rbppl.asyncgenerationapp.ui.viewmodel.ItemViewModel

class ItemFragment : Fragment() {

    private lateinit var viewModel: ItemViewModel
    private lateinit var adapter: ItemAdapter
    private lateinit var binding: FragmentItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemBinding.inflate(inflater, container, false)
        val recyclerView: RecyclerView = binding.recyclerView

        val repository = Repository(requireContext())
        viewModel = ViewModelProvider(this, ItemViewModel.Factory(repository)).get(ItemViewModel::class.java)

        adapter = ItemAdapter { position ->
            viewModel.removeItem(position)
        }

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapter

        viewModel.itemList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        return binding.root
    }
}
