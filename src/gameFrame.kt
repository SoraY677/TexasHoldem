import java.awt.*
import javax.swing.*

class GameFrame : JFrame("hogehoge"){

    public val panelWidth = 500
    public val panelHeight = 500

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