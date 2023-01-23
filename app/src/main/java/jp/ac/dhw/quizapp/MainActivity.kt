package jp.ac.dhw.quizapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import jp.ac.dhw.quizapp.databinding.ActivityMainBinding
import android.content.DialogInterface
import android.content.Intent


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var rightAnswer: String? = null

    //正解を入れるために使う
    private var rightAnswerCount = 0

    //正解数をカウントするために使う
    private var quizCount = 1
    //何問目を出題しているのかをカウントするために使う

    //問題を何問出題するか
    private val QUIZ_COUNT = 5

    private val quizData = mutableListOf(
        mutableListOf("apple", "apple", "banana", "grapes", "pineapple"),
        mutableListOf("car", "car", "cap", "cell", "cat"),
        mutableListOf("stone", "stone", "song", "south", "spring"),
        mutableListOf("computer", "computer", "country", "cousin", "company"),
        mutableListOf("town", "town", "thirty", "touch", "telephone")


//        mutableListOf("リンゴ", "apple", "banana", "grapes", "pineapple"),
//        mutableListOf("くるま", "car", "cap", "cell", "cat"),
//        mutableListOf("石", "stone", "song", "south", "spring"),
//        mutableListOf("パソコン", "computer", "country", "cousin", "company"),
//        mutableListOf("町", "town", "thirty", "touch", "telephone")


        //MutableListは要素のシャッフルと削除ができる

        //MutableList の中に MutableList を入れる二次元配列の形にすることで
        //出題順序のシャッフル
        //選択肢のシャッフル
        //も簡単にできるようになる
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //View Bindingの使い方
        // findViewById を使わずに TextView や Button などにアクセスできるようになる
        //https://codeforfun.jp/android-studio-how-to-use-view-binding-with-kotlin/

        quizData.shuffle()

        showNextQuiz()
    }

    //問題を出題する
    fun showNextQuiz() {

        //カウントラベルの更新
        //getString メソッドを使って strings.xml に定義したこの文字列を取得して、
        // 第二引数に %d の部分に入れる数値を指定している。
        binding.countLabel.text = getString(R.string.count_label, quizCount)

        //問題を１問取り出す
        val quiz = quizData[0]

        //問題をセット
        //ここから
        val imageName = quiz[0]
        val image = getResources().getIdentifier(imageName, "drawable", getPackageName());
        binding.questionLabel.setImageResource(image);

//        binding.questionLabel.text = quiz[0]

        //正解をセット
        rightAnswer = quiz[1]

        //単語の意味を削除
        quiz.removeAt(0)

        //正解と選択肢３つをシャッフル
        quiz.shuffle()

        //選択肢をセット
        binding.answerBtn1.text = quiz[0]
        binding.answerBtn2.text = quiz[1]
        binding.answerBtn3.text = quiz[2]
        binding.answerBtn4.text = quiz[3]

        //出題した問題を削除
        quizData.removeAt(0)

    }


    //解答ボタンが押されたら呼び出される
    fun checkAnswer(view: View) {
        //どの解答ボタンが押されたか
        val answerBtn: Button = findViewById(view.id)
        val btnText = answerBtn.text.toString()

        //ダイアログのタイトルを作成
        val alertTitle: String
        if (btnText == rightAnswer) {
            alertTitle = "正解！"
            rightAnswerCount++
        }else{
            alertTitle = "不正解.."
        }

        //ダイアログを作成
        AlertDialog.Builder(this)
            .setTitle(alertTitle)
            .setMessage("答え ： $rightAnswer")
            .setPositiveButton("OK") { dialogInterface, i ->
                checkQuizCount()
            }
            //setCancelable はダイアログの外側をタップしたときにダイアログを閉じるかどうかを指定している
            .setCancelable(false)
            .show()
    }


    //出題数をチェックする
    fun checkQuizCount() {
        if (quizCount == QUIZ_COUNT) {
            //結果画面を表示
            //intentクラスは新しくアクティビティを開くときに使用する

            //val intent = Intent(Context context, Class<?> cls)
            //startActivity(intent)

            //contextには現在のアクティビティ、cls には 表示するアクティビティを Class オブジェクトで書く。
            //次に作成したインテントを startActivity に渡すことでアクティビティを表示することができる。
            val intent = Intent(this@MainActivity, ResultActivity::class.java)

            //putExtra(取り出す時に使うキー, 渡したい値) と書くことで、
            // 遷移先のアクティビティに値を渡すことができる。
            //キーは String 型（文字列）ですが、値には String, Int, Float などをセットすることができる。
            intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount)
            startActivity(intent)

        } else {
            quizCount++
            showNextQuiz()
        }
    }
}
