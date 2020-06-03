import java.awt.*
import javax.swing.*

class GameFrame : JFrame("hogehoge"){

    public val panelWidth = 1280
    public val panelHeight = 800

    init {
        setSize(panelWidth,panelHeight)
        layout = BorderLayout()
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        isVisible = true
    }

    fun setVisible(canvas: Canvas,buttonPanel:JPanel){
        add(canvas,BorderLayout.CENTER)
        add(buttonPanel,BorderLayout.SOUTH)
        println(buttonPanel)
        isVisible = true
    }


}