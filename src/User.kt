import java.awt.Button
import java.util.concurrent.locks.ReentrantLock
import java.util.regex.Pattern

class User : CardHolder(){
    var isWaitInput = false
    val btpanel = ButtonPanel(this)

    var actionName = ""

    //コンピュータの各アクションに対して入力できないボタンを定義
    var disableBtList:ArrayList<String> = arrayListOf()

    /**
     * ユーザの行動
     */
    fun actHand(act:String,comBetMoney:Int,actState:String):Map<String,String>{
        var betList = mutableMapOf<String,Int>()
        betList["Fold"] = 0
        betList["Check"] = 0
        if(comBetMoney != 0){
            betList["Bet"] = 0
            betList["Call"] = comBetMoney-betMoney
            betList["Raise"] = comBetMoney* 2
            betList["All-in"] = holdMoney
        }
        else{
            betList["Bet"] = 5
            betList["Call"] = 0
            betList["Raise"] = 0
            betList["All-in"] = 0
        }

        limitActionButton(act,actState)//行動制限
        changeBetNum(betList)
        while(true){
                if(isWaitInput == true)break
                Thread.sleep(100)//入力を受け付けるためにsleep
        }
        exchangeProperty(betList)
        btpanel.disableAllButton()

        isWaitInput = false

        this.latestAct = actionName

        return mapOf(
            "hand" to "user",
            "select" to actionName,
            "bet" to betMoney.toString(),
            "holdMoney" to holdMoney.toString()
        )
    }

    /**
     * 現在の状況から押せるボタンを制限する
     * @return 使えなくするボタンリスト
     */
    fun limitActionButton(act:String,actState: String){


        //一度、全てのボタンを使えるようにする
        btpanel.ableAllButton()


            disableBtList =
            when(act){
                "Check" ->arrayListOf("Raise","Call")
                "Call" -> arrayListOf("Raise","Call")
                "Bet"->arrayListOf("Check","Bet")
                "Raise"->arrayListOf("Check","Bet")
                "All-in"->arrayListOf("Raise","Call")//TODO:こいつは曲者だ！
                else -> arrayListOf()
            }

        if(actState == "initBet")disableBtList = arrayListOf("Check","Bet")
        else if(actState == "firstAct")disableBtList = arrayListOf("Raise","Call")



        disableBtList.forEach {
            btpanel.disableButton(it)
        }
    }

    // ベットできる金額提示機能を追加
    fun changeBetNum(betList:MutableMap<String,Int>){
        betList.forEach{
            btpanel.changeButtonLabel(it.key,it.value)
        }
    }

    /**
     * ユーザーの行動から関連数値の変動を行う
     */
    fun exchangeProperty(betList:MutableMap<String,Int>){
        betMoney += betList[actionName]!!
        holdMoney -= betList[actionName]!!
        latestAct = actionName
    }
}