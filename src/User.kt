import java.awt.Button
import java.util.concurrent.locks.ReentrantLock
import java.util.regex.Pattern

class User : CardHolder(){
    var isWaitInput = false
    val btpanel = ButtonPanel(this)

    var actionName = ""

    /**
     * ユーザの行動
     */
    fun actHand(){
            while(true){
                if(isWaitInput == true)break
                Thread.sleep(100)//入力を受け付けるためにsleep
            }
        exchangeProperty()
        btpanel.disableAllButton()

        isWaitInput = false
    }

    /**
     * 現在の状況から押せるボタンを制限する
     * @return 使えなくするボタンリスト
     */
    fun limitActionButton(act:String){


        //一度、全てのボタンを使えるようにする
        btpanel.ableAllButton()

        //コンピュータの各アクションに対して入力できないボタンを定義
        var disableBtList:ArrayList<String> = arrayListOf()
            disableBtList =
            when(act){
                "initBet" -> arrayListOf("Check","Bet")
                "firstAct" -> arrayListOf("Raise","Call")//何も行われていない最初の状態
                "Check" ->arrayListOf("Raise","Call","Bet")
                "Bet"->arrayListOf("Check","Bet")
                "Raise"->arrayListOf("Check","Bet")
                "All-in"->arrayListOf("Raise","Call")//TODO:こいつは曲者だ！
                else -> arrayListOf("")
            }


        disableBtList.forEach {
            btpanel.disableButton(it)
        }
    }

    /**
     * ユーザーの行動から関連数値の変動を行う
     */
    fun exchangeProperty(){
            when (actionName) {
                "Bet" -> {

                }
                "Raise" -> {

                }
                "Call" -> {

                }
                "All-in" -> {

                }
            }
        latestAct = mapOf("hand" to "user","select" to actionName)
    }
}