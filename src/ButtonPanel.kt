import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JPanel

class ButtonPanel {

    val BUTTON_NAME_LIST = arrayOf("Fold","Check","Bet","Raise","Call","All-in")

    var buttonList = mutableMapOf<String, JButton>()
    var panel = JPanel()


    constructor(user:User) {

        BUTTON_NAME_LIST.forEach {
            val name = it
            buttonList[name] = JButton(name)
            buttonList[name]!!.addActionListener({
                user.actionName = name
                user.isWaitInput = true
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

}
