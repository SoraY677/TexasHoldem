import java.awt.*
import javax.swing.*

class gameFrame : JFrame("hogehoge"){

    val panelWidth = 500
    val panelHeight = 500

    init {
        setSize(panelWidth,panelHeight)
        layout = BorderLayout()
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        isVisible = true
    }

    fun setVisible(canvas: Canvas){
        add(canvas,BorderLayout.CENTER)
        isVisible = true
    }


}