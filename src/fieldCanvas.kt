import com.sun.deploy.panel.PathRenderer
import org.omg.CORBA.Object
import java.awt.Canvas
import java.awt.Graphics
import java.awt.Image
import javax.swing.ImageIcon



class fieldCanvas : Canvas(){

    var imageList:MutableMap<String,Image> = mutableMapOf()

    var paintImgMapReciever:ArrayList<Map<String,Any>> = arrayListOf()

//    TODO:状態遷移のクラスを実装する
//    val stateController =

    init {
        //トランプ画像
        for (cardId in TrumpBunch().issueCardIdList()){
            imageList[cardId] = ImageIcon(javaClass.getResource("image/card/"+ cardId + ".png")).image
        }

        //トランプ背景画像
        imageList["c999"] = ImageIcon(javaClass.getResource("image/card/c999.png")).image

    }


    override fun paint(g: Graphics) {
        for(pathel in paintImgMapReciever) {
            g.drawImage(imageList[pathel["imgPath"]], 0, 0, null)
        }
    }

    /**
     * 指定の画像を指定位置に描画する
     */
    fun repaintCanvas(imageMap:ArrayList<Map<String,Any>>){
        paintImgMapReciever = imageMap

        repaint()
    }
}