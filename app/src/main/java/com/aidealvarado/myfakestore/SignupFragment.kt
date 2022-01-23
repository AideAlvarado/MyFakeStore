package com.aidealvarado.myfakestore

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.aidealvarado.myfakestore.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val TAG = SignupFragment::class.java.simpleName

class SignupFragment : Fragment() {
    lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSignupBinding.inflate(inflater)
        auth = FirebaseAuth.getInstance()
        binding.registerbt.setOnClickListener {
            val email = binding.signemailtv.text.toString()
            val password = binding.signpasstv.text.toString()
            registerUser(email, password)
        }

        return binding.root
    }

    private fun registerUser(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        currentlyLoggedIn()
                    } else {
                        try {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Log.d(TAG, "Task result $task.result")
                            CoroutineScope(Dispatchers.IO).launch {
                                withContext(Dispatchers.Main){ Toast.makeText(
                                    this@SignupFragment.context, "Authentication failed",
                                    Toast.LENGTH_LONG
                                ).show()}

                             }
                        } catch (e: FirebaseAuthInvalidCredentialsException) {
                            Log.d(TAG,"------------> Invalid credentiasl")
                            CoroutineScope(Dispatchers.Main).launch {
                                Toast.makeText(
                                    this@SignupFragment.context, "Invalid credentials, check email?",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } catch (e: Exception) {
                            Log.d(TAG, "===========>$e")
                        }


                    }
                }
        }
    }


    private fun currentlyLoggedIn() {
        val user = auth.currentUser
        if (user != null) {
            val intent = Intent(this@SignupFragment.context, MainActivity::class.java)
            startActivity(intent)
        }
    }
}



