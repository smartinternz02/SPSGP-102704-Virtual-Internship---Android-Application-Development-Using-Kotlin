package com.lrm.grocerybasket

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lrm.grocerybasket.data.GroceryItem
import com.lrm.grocerybasket.databinding.FragmentGroceryListBinding

// This is the Main fragment, displays details for all items in the database.

class GroceryListFragment : Fragment(){

    private val viewModel: GroceryViewModel by activityViewModels {
        GroceryViewModelFactory(
            (activity?.application as GroceryBasketApplication).database
                .groceryDao()
        )
    }

    private var _binding: FragmentGroceryListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroceryListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = GroceryListAdapter {
            val action = GroceryListFragmentDirections.actionGroceryListToItemDetailFragment(it.id)
            this.findNavController().navigate(action)
        }

        binding.recyclerview.adapter = adapter
        viewModel.allItems.observe(this.viewLifecycleOwner){ items ->
            items.let {
                adapter.submitList(it)
            }
        }

        binding.recyclerview.layoutManager = LinearLayoutManager(this.context)

        binding.addFAB.setOnClickListener {
            val action = GroceryListFragmentDirections.actionGroceryListToAddItemFragment()
            this.findNavController().navigate(action)
        }

        binding.settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_groceryList_to_settingsFragment)
        }
    }
}