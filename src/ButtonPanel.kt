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
            buttonList[it] = RoundButton("<html><div style='text-align:center;'>"+it+"</div></html>", Color(189,46,27),100,100,20)
            buttonList[it]!!.addActionListener({
                user.isWaitInput = true
                user.actionName = name
            })

        }

        changeButtonLabel("Bet",5)

        buttonList.forEach {
            panel.add(it.value)
        }

        panel.background = Color(1,106,53)


    }

    /**
     * buttonに書いてあるラベルを変更する
     */
    fun changeButtonLabel(btnName:String,money:Int) {
        if(money != 0){
            buttonList[btnName]!!.text= "<html><div style='text-align:center;'>"+btnName + "<br />" +money.toString()+"</div></html>"
        }
        else{
            buttonList[btnName]!!.text = "<html><div style='text-align:center;'>"+btnName +"</div></html>"
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

}
