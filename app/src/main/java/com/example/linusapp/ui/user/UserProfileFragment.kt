package com.example.linusapp.ui.user

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.linusapp.R
import com.example.linusapp.databinding.FragmentUserProfileBinding
import com.example.linusapp.vo.UserVO

class UserProfileFragment : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var userVO: UserVO

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        userVO = UserVO(
            parentFragment?.activity?.intent!!.getLongExtra("idUser", 0),
            parentFragment?.activity?.intent!!.getStringExtra("name").toString(),
            parentFragment?.activity?.intent!!.getStringExtra("username").toString(),
            parentFragment?.activity?.intent!!.getStringExtra("email").toString(),
            parentFragment?.activity?.intent!!.getStringExtra("password").toString(),
            parentFragment?.activity?.intent!!.getStringExtra("genre").toString(),
            parentFragment?.activity?.intent!!.getStringExtra("bornDate").toString(),
            parentFragment?.activity?.intent!!.getStringExtra("phoneNumber").toString(),
            parentFragment?.activity?.intent!!.getStringExtra("adminKey").toString(),
            parentFragment?.activity?.intent!!.getStringExtra("imageCode").toString(),
            parentFragment?.activity?.intent!!.getLongExtra("fkLevel", 0),
            parentFragment?.activity?.intent!!.getIntExtra("isBlocked", 0)
        )
        binding.etUserEmail.setText(userVO.email)
        binding.etUserName.setText(userVO.name)
        binding.etUserUsername.setText(userVO.username)
        binding.etPassword.setText("qwerty123")
        binding.etBornDate.setText(userVO.bornDate)
        binding.etPhone.setText(userVO.phoneNumber)
        return root
    }

    fun getUserInfo() {
        view?.findViewById<TextView>(R.id.et_user_email)?.text = userVO.email
        view?.findViewById<TextView>(R.id.et_user_name)?.text = userVO.name
        view?.findViewById<TextView>(R.id.et_user_username)?.text = userVO.username
        view?.findViewById<TextView>(R.id.et_password)?.text = userVO.password
        view?.findViewById<TextView>(R.id.et_born_date)?.text = userVO.bornDate
        view?.findViewById<TextView>(R.id.et_phone)?.text = userVO.phoneNumber
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}