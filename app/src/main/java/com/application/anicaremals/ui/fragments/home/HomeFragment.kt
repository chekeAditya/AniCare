package com.application.anicaremals.ui.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.anicaremals.R
import com.application.anicaremals.adapters.AnimalAdaptor
import com.application.anicaremals.adapters.ClickListener
import com.application.anicaremals.adapters.HorizontalAdaptor
import com.application.anicaremals.databinding.FragmentDummyBinding
import com.application.anicaremals.local.responses.ResponseModel
import com.application.anicaremals.ui.fragments.scanner.ScanAnimalActivity
import com.application.anicaremals.viewmodels.ApplicationViewModels
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_dummy.*

class HomeFragment : Fragment(), ClickListener {

    private lateinit var homeFragmentBinding: FragmentDummyBinding
    private var list = mutableListOf<ResponseModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_dummy,
            container,
            false
        )
        return homeFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonToAdd.setOnClickListener {
            val intent = Intent(requireContext(), ScanAnimalActivity::class.java)
            intent.putExtra("add", "add")
            startActivity(intent)
        }

        setRecyclerView()

        profileSection.setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setRecyclerView() {
        ApplicationViewModels.livedata.observe(viewLifecycleOwner, Observer {
            list.clear()
            list.addAll(it)
            val animalAdaptor = AnimalAdaptor(list, this@HomeFragment)
            val horizontalAdaptor = HorizontalAdaptor(list)
            homeFragmentBinding.apply {
                mainrecyclerview.adapter = animalAdaptor
                mainrecyclerview.layoutManager = GridLayoutManager(context, 2)
                horizontalrecycler.adapter = horizontalAdaptor
                horizontalrecycler.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        })
    }

    override fun OnClick(responseModel: ResponseModel) {
        val intent = Intent(requireContext(), OnClickDetailActivity::class.java)
        intent.putExtra("username", responseModel.user_name)
        intent.putExtra("animalbread", responseModel.animal_category)
        intent.putExtra("animaldetials", responseModel.animal_details)
        intent.putExtra("userlocation", responseModel.user_address)
        intent.putExtra("userNumber", responseModel.user_phoneNumber)
        intent.putExtra("image", responseModel.animal_image)
        startActivity(intent)
    }

}