package com.lrm.grocerybasket

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lrm.grocerybasket.data.GroceryItem
import com.lrm.grocerybasket.databinding.FragmentAddItemBinding

class AddItemFragment: Fragment() {

    private val viewModel: GroceryViewModel by activityViewModels {
        GroceryViewModelFactory(
            (activity?.application as GroceryBasketApplication).database
                .groceryDao()
        )
    }

    lateinit var item: GroceryItem

    private val navigationArgs: ItemDetailFragmentArgs by navArgs()

    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun isEntryValid(): Boolean{
        return viewModel.isEntryValid(
            binding.itemName.text.toString(),
            binding.itemQuantity.text.toString(),
            binding.itemPrice.text.toString()
        )
    }

    private fun addNewItem() {
        if(isEntryValid()) {
            viewModel.addNewItem(
                binding.itemName.text.toString(),
                binding.itemQuantity.text.toString(),
                binding.itemPrice.text.toString()
            )

            val action = AddItemFragmentDirections.actionAddItemFragmentToGroceryList()
            findNavController().navigate(action)

            Toast.makeText(context,"Item saved...",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context,"Please fill all the fields...",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.itemId
        if(id > 0) {
            viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
                item = selectedItem
                bind(item)
            }
        } else {
            binding.saveButton.setOnClickListener {
                addNewItem()
            }
            binding.cancelButton.setOnClickListener {
                findNavController().navigateUp()
                Toast.makeText(context,"Item not saved...",Toast.LENGTH_SHORT).show()
            }

            binding.backButton.setOnClickListener{
                findNavController().navigateUp()
                Toast.makeText(context,"Item not saved...",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateItem() {
        if(isEntryValid()) {
            viewModel.updateItem(
                this.navigationArgs.itemId,
                this.binding.itemName.text.toString(),
                this.binding.itemQuantity.text.toString(),
                this.binding.itemPrice.text.toString()
            )
            val action = AddItemFragmentDirections.actionAddItemFragmentToItemDetailFragment(item.id)
            findNavController().navigate(action)
            Toast.makeText(context,"Item saved...",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context,"Please fill all the fields...",Toast.LENGTH_SHORT).show()
        }
    }

    private fun bind(item: GroceryItem){
        binding.apply {
            addItemFragmentTitle.text = getString(R.string.editItemTitle)
            itemName.setText(item.itemName, TextView.BufferType.SPANNABLE)
            itemQuantity.setText(item.itemQuantity.toString(), TextView.BufferType.SPANNABLE)
            itemPrice.setText(item.itemPrice.toString(), TextView.BufferType.SPANNABLE)

            saveButton.setOnClickListener{ updateItem() }
            cancelButton.setOnClickListener {
                findNavController().navigateUp()
                Toast.makeText(context,"Item not edited...",Toast.LENGTH_SHORT).show()
            }
            backButton.setOnClickListener{
                findNavController().navigateUp()
                Toast.makeText(context,"Item not edited...",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }

}