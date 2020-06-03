import javax.swing.JButton
import javax.swing.JPanel

class ButtonPanel{

    var buttonList = arrayListOf<JButton>()
    var panel =JPanel()

    constructor(array: Array<String>){
        array.forEach {
            buttonList.add(JButton(it))
        }
        buttonList.forEach{
            panel.add(it)
        }
    }


}