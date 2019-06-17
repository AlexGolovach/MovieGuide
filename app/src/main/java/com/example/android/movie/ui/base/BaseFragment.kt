package com.example.android.movie.ui.base

import android.content.Context
import android.net.ConnectivityManager
import android.support.v4.app.Fragment
import android.widget.Toast

abstract class BaseFragment : Fragment() {

    fun onBack() {
        val fragments = fragmentManager?.fragments
        val backStackEntry = fragmentManager?.backStackEntryCount

        if (fragments != null && fragments.isNotEmpty()) {
            val fragment = fragments[fragments.size - 1]
            if (backStackEntry != null) {
                if (fragment is BaseFragment && backStackEntry > 0) {
                    fragmentManager?.popBackStackImmediate()
                }
            }
        }

        if (backStackEntry == 0) {
            activity?.finish()
        }
    }

    fun isOnline(): Boolean {
        val cm = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val netInfo = cm!!.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    fun checkConnection() {
        if (isOnline()) {
            Toast.makeText(activity, "You are connected to Internet", Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(
                activity,
                "You are not connected to Internet",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}