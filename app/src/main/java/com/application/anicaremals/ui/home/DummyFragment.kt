package com.application.anicaremals.ui.home

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.anicaremals.R
import com.application.anicaremals.databinding.FragmentDummyBinding
import com.application.anicaremals.remote.response.ResponseModel
import com.application.anicaremals.ui.scanner.ScanAnimalActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_dummy.*

class DummyFragment : Fragment(), CLickinter {

    private lateinit var dummyBinding: FragmentDummyBinding

    private var list = mutableListOf<ResponseModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dummyBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_dummy,
            container,
            false)
        return dummyBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonToAdd.setOnClickListener {
            val intent: Intent = Intent(requireContext(), ScanAnimalActivity::class.java)
            intent.putExtra("add","add")
            startActivity(intent)
        }

            setRecyclerView()

        profileSection.setOnClickListener {
            var intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setRecyclerView() {
        FirebaseLiveDataList.livedata.observe(viewLifecycleOwner, Observer {
            list.clear()
            list.addAll(it)
            var adaptor = AnimalAdaptor( list, this@DummyFragment)
            var adaptor1 = HorizontalAdaptor( list)
            dummyBinding.mainrecyclerview.adapter = adaptor
            dummyBinding.mainrecyclerview.layoutManager = GridLayoutManager(context,2)
            dummyBinding.horizontalrecycler.adapter = adaptor1
            dummyBinding.horizontalrecycler.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)
        })
    }

    override fun OnClick(responseModel: ResponseModel) {
        var intent = Intent(requireContext(), OnClickDetailActivity::class.java)
        intent.putExtra("username", responseModel.user_name)
        intent.putExtra("animalbread", responseModel.animal_category)
        intent.putExtra("animaldetials", responseModel.animal_details)
        intent.putExtra("userlocation", responseModel.user_address)
        intent.putExtra("userNumber", responseModel.user_phoneNumber)
        intent.putExtra("image", responseModel.animal_image)
        startActivity(intent)

    }

}