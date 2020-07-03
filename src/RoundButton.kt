import java.awt.*
import javax.swing.Icon
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.border.Border
import java.awt.RenderingHints
import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import java.awt.geom.RoundRectangle2D
import kotlin.math.max
import kotlin.math.min
import java.awt.Font.PLAIN




class RoundButton: JButton{

    val label = JLabel()

    constructor(text:String,color:Color,width:Int,height:Int,fontsize:Int){
        this.text = text
        this.preferredSize = Dimension(width,height)
        this.foreground= Color.white
        this.background = color
        this.font = Font("Arial", Font.PLAIN, fontsize);

    }

    override fun updateUI() {
        super.updateUI()
        setContentAreaFilled(false);
        setFocusPainted(false);
    }

    override fun paintComponent(g: Graphics?) {

        var g2 = g!!.create() as Graphics2D
        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        )
        val shape = Ellipse2D.Float(0f,0f,width.toFloat(),height.toFloat())
        val btmodel = model;
        val bgcolor = background;

        val colorDiff = 40
        if(btmodel.isRollover) {//上に来たら明るく
            g2.color = Color(min(bgcolor.red + colorDiff,255),min(bgcolor.green + colorDiff,255),min(bgcolor.blue + colorDiff,255))
        } else if (!btmodel.isEnabled){//使用不可なら暗く
            g2.color = Color(max(bgcolor.red - colorDiff,0),max(bgcolor.green - colorDiff,0),max(bgcolor.blue - colorDiff,0))
        }else {//デフォルトのボタン色
            g2.color = bgcolor
        }
        g2.fill(shape)

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_OFF);
        super.paintComponent(g2);
        g2.dispose();
    }

    override fun paintBorder(g: Graphics?) {
//        super.paintBorder(g)
    }



}