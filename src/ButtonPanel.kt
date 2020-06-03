import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JPanel

class ButtonPanel {

    var buttonList = mutableMapOf<String, JButton>()
    var panel = JPanel()

    constructor() {
        buttonList["raise"] = JButton("raise")
        buttonList["raise"]!!.addActionListener(ActionListener {
        })
        buttonList.forEach {
            panel.add(it.value)
        }
    }
}
