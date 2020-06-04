import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JPanel

class ButtonPanel {

    var buttonList = mutableMapOf<String, JButton>()
    var panel = JPanel()

    constructor(user:User) {
        buttonList["Fold"] = JButton("Fold")
        buttonList["Fold"]!!.addActionListener(ActionListener {
            exitProcessWait(user)
        })
        buttonList["Check"] = JButton("Check")
        buttonList["Check"]!!.addActionListener(ActionListener {
            exitProcessWait(user)
        })
        buttonList["Bet"] = JButton("Bet")
        buttonList["Bet"]!!.addActionListener(ActionListener {
            exitProcessWait(user)
        })
        buttonList["Raise"] = JButton("Raise")
        buttonList["Raise"]!!.addActionListener(ActionListener {
            exitProcessWait(user)
        })
        buttonList["Call"] = JButton("Call")
        buttonList["Call"]!!.addActionListener(ActionListener {
            exitProcessWait(user)
        })
        buttonList["All-in"] = JButton("All-in")
        buttonList["All-in"]!!.addActionListener(ActionListener {
            exitProcessWait(user)
        })

        buttonList.forEach {
            panel.add(it.value)
        }
    }

    /**
     * 入力待ち状態を解除する
     */
    fun exitProcessWait(user:User){
        user.isWaitInput = true
    }

    fun disableButton(buttonName:String){
        buttonList[buttonName]!!.isEnabled = false
    }

}
