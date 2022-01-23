package com.aidealvarado.myfakestore

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aidealvarado.myfakestore.databinding.FragmentAuthenticationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val TAG = AuthenticationFragment::class.java.simpleName

class AuthenticationFragment : Fragment() {
    lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentAuthenticationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthenticationBinding.inflate(inflater)
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()

        binding.signupbt.setOnClickListener {
            findNavController().navigate(R.id.action_authenticationFragment_to_signupFragment)
        }
        binding.signInbt.setOnClickListener {
            // validamos que se hace sign-in en la cuenta.
            logInUser()
        }
        return binding.root

    }

    private  fun logInUser() {
        val email = binding.EmailText.text.toString()
        val password = binding.passwordText.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    currentlyLoggedIn(auth.currentUser)
                } else {
                    CoroutineScope(Dispatchers.IO).launch {
                        withContext(Dispatchers.Main) {
                            toast("Authentication failed")
                        }
                    }
                }
            }
        } else if (!email.isNotEmpty()) {
            Log.d(TAG, "Email is empty")
            toast("Email is empty")
        } else if (!password.isNotEmpty()) {
            Log.d(TAG, "Password is not empty")
            toast("Password is empty")
        } else {
            Log.e(TAG, "This should not happen")
        }
    }

    private fun currentlyLoggedIn(currentUser: FirebaseUser?) {
               Config.user = currentUser!!
        Log.d(TAG,"-----> User is ${Config.user} <----")
        findNavController().navigate(R.id.action_authenticationFragment_to_mainActivity)
        //val intent = Intent(this@AuthenticationFragment.context,MainActivity::class.java)
        //startActivity(intent)
    }

    fun navegar() {
        //findNavController().navigate(R.layout.fragment_signup)
    }

    fun toast(text: String) {
        if (text.isNotEmpty()) {
            Toast.makeText(
                this@AuthenticationFragment.context, text,
                Toast.LENGTH_LONG
            ).show()
        }

    }
}