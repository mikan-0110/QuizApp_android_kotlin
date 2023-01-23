package jp.ac.dhw.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import jp.ac.dhw.quizapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_result)

        binding = ActivityResultBinding.inflate(layoutInflater)
        val  view = binding.root
        setContentView(view)

        //正解数を取得
        //値を渡す時に使ったキー RIGHT_ANSWER_COUNT を使って正解数を受け取る。
        //正解数は Int 型だったので getIntExtra メソッドを使う。
        //書き方は getIntExtra(キー, 値が取得できなかった場合の値) となる。
        val score = intent.getIntExtra("RIGHT_ANSWER_COUNT", 0)

        //TextViewに表示する
        binding.resultLabel.text = getString(R.string.result_score, score)

        //もう一度ボタン
        binding.tryAgainBtn.setOnClickListener {
            startActivity(Intent(this@ResultActivity, MainActivity::class.java))
        }
    }
}