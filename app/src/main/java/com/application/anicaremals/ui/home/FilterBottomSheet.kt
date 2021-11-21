package com.application.anicaremals.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.application.anicaremals.R
import com.application.anicaremals.remote.response.ResponseModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_filter_bottom_sheet.*

class FilterBottomSheet(private val click: Filter) :
    BottomSheetDialogFragment() {

    private var originalList = mutableListOf<ResponseModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filter_bottom_sheet,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnFilter.setOnClickListener {
            generateFilterList()
        }

        FirebaseLiveDataList.livedata.observe(viewLifecycleOwner, Observer {
            originalList.clear()
            originalList.addAll(it)
        })

    }

    private fun generateFilterList() {
        var newList = mutableListOf<ResponseModel>()
        if (cbDog.isChecked || cbGoat.isChecked ||
            cbHorse.isChecked || cbCat.isChecked || cbCow.isChecked) {
            var dog = ""
            var cat = ""
            var goat = ""
            var horse = ""
            var cow = ""
            if (cbDog.isChecked) dog = "Dog"
            if (cbCow.isChecked) cow = "Cow"
            if (cbCat.isChecked) cat = "Cat"
            if (cbHorse.isChecked) horse = "Horse"
            if (cbGoat.isChecked) goat = "Goat"
            for (i in originalList){
                var cg = i.animal_category
                if (cg.equals(dog) || cg.equals(cat) || cg.equals(horse)
                    || cg.equals(goat) || cg.equals(cow)) newList.add(i)
            }
        }
        else newList.addAll(originalList)
        click.onFilter(newList)
    }

}