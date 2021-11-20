package com.application.anicaremals.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.application.anicaremals.R
import com.application.anicaremals.remote.response.ResponseModel
import kotlinx.android.synthetic.main.fragment_filter_bottom_sheet.*

class FilterBottomSheet(private val list: List<ResponseModel>,
                        private val click: Filter) :
    Fragment(R.layout.fragment_filter_bottom_sheet) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnFilter.setOnClickListener {
            generateFilterList()
        }

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
            if (cbCow.isChecked) dog = "Cow"
            if (cbCat.isChecked) dog = "Cat"
            if (cbHorse.isChecked) dog = "Horse"
            if (cbGoat.isChecked) dog = "Goat"
            for (i in list){
                var cg = i.animal_category
                if (cg.equals(dog) || cg.equals(cat) || cg.equals(horse)
                    || cg.equals(goat) || cg.equals(cow)) newList.add(i)
            }
        }
        else newList = list as MutableList<ResponseModel>
        click.onFilter(newList)
    }

}