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
        btpanel.ableAllButton()
            while(true){
                if(isWaitInput == true)break
                Thread.sleep(100)//入力を受け付けるためにsleep
            }
        btpanel.disableAllButton()

        isWaitInput = false
    }

    /**
     * ユーザーの行動から関連数値の変動を行う
     */
    fun exchangeProperty():String{
            var text = ""
            when (actionName) {
                "Bet" -> {
                    text = btpanel.bettext.getText()
                }
                "Raise" -> {

                }
                "Call" -> {

                }
                "All-in" -> {

                }
            }

        return actionName
    }





}