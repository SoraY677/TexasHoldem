import java.awt.Button
import java.util.concurrent.locks.ReentrantLock

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
        when(actionName){
            "Fold" -> {
            }
            "Check" ->{

            }
            "Bet"->{

            }
            "Raise"->{

            }
            "Call"->{

            }
            "All-in"->{

            }
        }
        return actionName
    }


}