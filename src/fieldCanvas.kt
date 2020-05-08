import java.awt.Canvas
import java.awt.Graphics
import java.awt.Image
import javax.swing.ImageIcon



class fieldCanvas : Canvas(){

    var cardImg:ArrayList<Image> = arrayListOf()
    var cardBackImg:Image? = null

    init {
        //TODO:トランプ画像

        //トランプ背景画像
        cardBackImg = ImageIcon(javaClass.getResource("image/card/c999.png")).image

    }

    override fun paint(g: Graphics) {
        g.drawImage(cardBackImg,0,0,null)
    }
}