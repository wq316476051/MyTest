package com.wang.architecture.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.wang.architecture.R

class UserFragment : Fragment() {

    private val viewModel: UserViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val etName = view.findViewById<EditText>(R.id.et_name)
        val etAge = view.findViewById<EditText>(R.id.et_age)
        viewModel.getUser("id").observe(this, Observer {
            etName.setText(it.name)
            etAge.setText(it.age.toString())
        })
    }
}