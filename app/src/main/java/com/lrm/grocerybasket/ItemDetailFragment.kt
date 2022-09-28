package com.lrm.grocerybasket

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lrm.grocerybasket.data.GroceryItem
import com.lrm.grocerybasket.databinding.FragmentItemDetailBinding

class ItemDetailFragment: Fragment() {

    private val viewModel: GroceryViewModel by activityViewModels {
        GroceryViewModelFactory(
            (activity?.application as GroceryBasketApplication).database
                .groceryDao()
        )
    }

    lateinit var item: GroceryItem

    private val navigationArgs: ItemDetailFragmentArgs by navArgs()

    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Displays an alert dialog to get the user's confirmation before deleting the item.
    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.deleteDialogTitle))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteItem()
                Toast.makeText(context,"Item Deleted...",Toast.LENGTH_SHORT).show()
            }
            .show()

    }

    private fun deleteItem() {
        viewModel.deleteItem(item)
        findNavController().navigateUp()
    }

    private fun editItem() {
        val action = ItemDetailFragmentDirections.actionItemDetailFragmentToAddItemFragment(item.id)
        this.findNavController().navigate(action)
    }

    private fun bind(item: GroceryItem) {
        binding.apply {
            itemName.text = item.itemName
            itemQuantity.text = item.itemQuantity.toString()
            itemPrice.text = "₹ " + item.itemPrice.toString()

            val totalAmount = item.itemQuantity * item.itemPrice
            itemAmount.text = "₹ " + totalAmount.toString()

            deleteButton.setOnClickListener{ showConfirmationDialog() }
            editButton.setOnClickListener { editItem() }

            backButton.setOnClickListener{ findNavController().navigateUp() }
            settingsButton.setOnClickListener {
                findNavController().navigate(R.id.action_itemDetailFragment_to_settingsFragment)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.itemId
        viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
            item = selectedItem
            bind(item)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}