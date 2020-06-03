import com.sun.deploy.panel.PathRenderer
import org.omg.CORBA.Object
import java.awt.Canvas
import java.awt.Graphics
import java.awt.Image
import javax.swing.ImageIcon



class FieldCanvas : Canvas(){

    var imageList:MutableMap<String,Image> = mutableMapOf()

    var paintImgMapReciever:ArrayList<Map<String,Any>> = arrayListOf()

    init {
        //UI画像
        imageList["handYou"] = ImageIcon(javaClass.getResource("image/fieldUI/YOU.png")).image
        imageList["handCom"] = ImageIcon(javaClass.getResource("image/fieldUI/COM.png")).image
        imageList["Dealer"] = ImageIcon(javaClass.getResource("image/fieldUI/Dealer.png")).image

        //トランプ画像
        for (cardId in TrumpBunch().issueCardIdList()){
            imageList[cardId] = ImageIcon(javaClass.getResource("image/card/"+ cardId + ".png")).image
        }

        //トランプ背景画像
        imageList["c999"] = ImageIcon(javaClass.getResource("image/card/c999.png")).image

    }

    override fun paint(g: Graphics) {

        //配列で渡された画像を
        for(paintMap in paintImgMapReciever) {
            g.drawImage(imageList[paintMap["img"]],paintMap["x"].toString().toInt() , paintMap["y"].toString().toInt(), null)
        }
    }

    /**
     * 指定の画像を指定位置に描画する
     */
    fun repaintCanvas(imageMap:ArrayList<Map<String,Any>>){
        paintImgMapReciever = imageMap
        //TODO: 配列で渡されたデータを描画する

        repaint()
    }

    fun getCardSize():Map<String,Int>{
        var x:Int = imageList["c101"]!!.getWidth(null)
        var y:Int = imageList["c101"]!!.getHeight(null)
        return mapOf("x" to x, "y" to y)
    }

}