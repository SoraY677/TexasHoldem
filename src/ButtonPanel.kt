import javafx.scene.layout.Border
import javafx.scene.layout.BorderStroke
import java.awt.Color
import java.awt.Dimension
import java.awt.Rectangle
import java.util.regex.Pattern
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel

class ButtonPanel {

    val BUTTON_NAME_LIST = arrayOf("Fold","Check","Bet","Raise","Call","All-in")

    var buttonList = mutableMapOf<String, JButton>()
    var panel = JPanel()



    constructor(user:User) {

        BUTTON_NAME_LIST.forEach {
            val name = it
            buttonList[it] = RoundButton(name, Color(189,46,27),100,100,20)
            buttonList[it]!!.addActionListener({
                if (name == "Fold" || name == "Check" || name == "Call") {
                    user.actionName = name
                    user.isWaitInput = true
                }
            })

        }

        buttonList.forEach {
            panel.add(it.value)
        }


    }

    /**
     * 指定のボタンを使用不可にする
     */
    fun disableButton(buttonName:String){
        buttonList[buttonName]!!.isEnabled = false
    }
    /**
     * 指定のボタンを使用可能に知る
     */
    fun ableButton(buttonName:String){
        buttonList[buttonName]!!.isEnabled = true
    }
    /**
     * 全ボタン使用不可
     */
    fun disableAllButton(){
        BUTTON_NAME_LIST.forEach {
            disableButton(it)
        }
    }
    /**
     * 全ボタン使用可
     */
    fun ableAllButton(){
        BUTTON_NAME_LIST.forEach {
            ableButton(it)
        }
    }



    /**
     * ユーザーの入力待ち状態を解除する
     */
    fun exitProcessWait(user:User){
        user.isWaitInput = true
    }

    /**
     * 入力されたmoneyが違反していないか調べる
     */
    fun checkMoneyString(target:String):Boolean{
        val pattern = Pattern.compile("[1-9].[0-9]*")
        val match = pattern.matcher(target)

        if(match.find())return true
        return false
    }

}
