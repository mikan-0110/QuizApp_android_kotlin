package jp.ac.dhw.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import jp.ac.dhw.quizapp.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest


class SigninActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySigninBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        binding = ActivitySigninBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth


//        ユーザー登録
//        新規ユーザー登録が上手くできない
//        おそらく原因はニックネームの新規登録が上手くいっていないから
        binding.btnRegister.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()

            Firebase.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = Firebase.auth.currentUser
//                        user?.let {
//                            val name = user.displayName
//                        }
                        Toast.makeText(baseContext, "emailとパスワードが登録されました", Toast.LENGTH_SHORT)
                            .show()
                        val profileUpdates = userProfileChangeRequest {
                            displayName = binding.inputGameName.text.toString()
                        }
                        user!!.updateProfile(profileUpdates)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        baseContext,
                                        "ニックネームが登録されました",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }


//                        updateName(user?.displayName)
                        Toast.makeText(baseContext, "認証に成功しました", Toast.LENGTH_SHORT).show()

//                        updateUI(user?.email)
                    } else {
//                        Toast.makeText(baseContext, "認証に失敗しました", Toast.LENGTH_SHORT).show()
//                        updateUI(null)

                        Firebase.auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    val user = Firebase.auth.currentUser

                                    val profileUpdates = userProfileChangeRequest {
                                        displayName = binding.inputGameName.text.toString()
                                    }
                                    user!!.updateProfile(profileUpdates)
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                Toast.makeText(
                                                    baseContext,
                                                    "ニックネームが更新されました",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }

                                    Toast.makeText(baseContext, "すでに登録されています", Toast.LENGTH_SHORT)
                                        .show()
//                                    updateUI(user?.email)

                                } else {
                                    Toast.makeText(baseContext, "認証に失敗しました", Toast.LENGTH_SHORT)
                                        .show()
//                                    updateUI(null)
                                }
                            }

                    }
                }

        }

        //ログイン
        binding.btnLogin.setOnClickListener {
            val email = binding.inputLoginEmail.text.toString()
            val password = binding.inputLoginPassword.text.toString()


            Firebase.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = Firebase.auth.currentUser

//                        updateUI(user?.email)
//                        updateName(user?.displayName)
                        val display_name = user?.displayName

                        AlertDialog.Builder(this)
                            .setTitle("ようこそ！$display_name さん！")
                            .setMessage("問題を始めますか？")
                            .setPositiveButton("Yes") { dialogInterface, i ->
                                startActivity(Intent(this@SigninActivity, MainActivity::class.java))
                            }
                            .setCancelable(false)
                            .show()


                    } else {
                        Toast.makeText(baseContext, "認証に失敗しました", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}



