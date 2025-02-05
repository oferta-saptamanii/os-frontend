package com.example.os_frontend.screens.catalogs.onboarding

import com.example.os_frontend.R

class OnBoardingItems(
    val image: Int,
    val title: String,
    val description: String
) {
    companion object{
        fun getData(): List<OnBoardingItems>{
            return listOf(
                OnBoardingItems(
                    R.drawable.destination,
                    "Alege orasul dorit!",
                    "Alege orasul dorit si vezi toate ofertele disponibile in zona ta!"
                    ),
                OnBoardingItems(
                    R.drawable.cart,
                    "Alege magazinul preferat!",
                    "Alege magazinul preferat si vezi toate ofertele disponibile in magazinul respectiv!"
                )
            )
        }
    }
}