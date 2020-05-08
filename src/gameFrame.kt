import java.awt.*
import javax.swing.*

class gameFrame : JFrame("hogehoge"){
    init {
        setSize(500,500)
        layout = BorderLayout()
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        val canvas = fieldCanvas()
        add(canvas,BorderLayout.CENTER)
        isVisible = true
    }

}