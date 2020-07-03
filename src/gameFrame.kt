import java.awt.*
import javax.swing.*

class GameFrame : JFrame("TexasHoldem"){

    public val panelWidth = 1250
    public val panelHeight = 1000

    init {
        setSize(panelWidth,panelHeight)
        layout = BorderLayout()
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    }

    fun setVisible(canvas: Canvas,buttonPanel:JPanel){
        add(canvas,BorderLayout.CENTER)
        add(buttonPanel,BorderLayout.SOUTH)
        isVisible = true
    }


}