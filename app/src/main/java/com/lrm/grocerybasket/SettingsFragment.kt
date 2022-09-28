package com.lrm.grocerybasket

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.transition.AutoTransition
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.lrm.grocerybasket.databinding.FragmentAddItemBinding
import com.lrm.grocerybasket.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private val viewModel: GroceryViewModel by activityViewModels {
        GroceryViewModelFactory(
            (activity?.application as GroceryBasketApplication).database
                .groceryDao()
        )
    }

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            backButton.setOnClickListener { findNavController().navigateUp() }

            expandAction.setOnClickListener {
                if(InfoTabLL.visibility == View.GONE) {
                    InfoTabLL.visibility = View.VISIBLE
                    TransitionManager.beginDelayedTransition(aboutAppCard, AutoTransition())
                    expandAction.setImageResource(R.drawable.ic_expand_less)
                } else {
                    InfoTabLL.visibility = View.GONE
                    TransitionManager.beginDelayedTransition(aboutAppCard, AutoTransition())
                    expandAction.setImageResource(R.drawable.ic_expand_more)
                }
            }

            driveLinkTextView.setOnClickListener{
                val uri = Uri.parse("https://drive.google.com/drive/folders/1kH8wVaqsDJh-8f0QUFLvtpqGpbXLgZt1?usp=sharing")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }

            gitHubLink1.setOnClickListener{
                val uri2 = Uri.parse("https://github.com/smartinternz02/SPSGP-102704-Virtual-Internship---Android-Application-Development-Using-Kotlin/tree/master")
                startActivity(Intent(Intent.ACTION_VIEW, uri2))
            }

            gitHubLink2.setOnClickListener {
                val uri3 = Uri.parse("https://github.com/JungleMystic/Project-Grocery-Basket")
                startActivity(Intent(Intent.ACTION_VIEW, uri3))
            }
        }
    }
}