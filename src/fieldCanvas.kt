import com.sun.deploy.panel.PathRenderer
import org.omg.CORBA.Object
import java.awt.Canvas
import java.awt.Graphics
import java.awt.Image
import javax.swing.ImageIcon



class FieldCanvas : Canvas(){

    var imageList:MutableMap<String,Image> = mutableMapOf()


    var drawImgAllMap:Map<String,Map<String,Any>> = mapOf()
    var drawStringAllMap:Map<String,Map<String,Any>> = mapOf()
    var dealerPosition:Array<Map<String,Int>> = arrayOf()
    var drawTargetImgMap:MutableMap<String,Map<String,Any>> = mutableMapOf()
    var drawTargetStrMap:MutableMap<String,Map<String,Any>> = mutableMapOf()



    init {
        /*画像読み込み部*/
        //UI画像
        imageList["plateYou"] = ImageIcon(javaClass.getResource("image/fieldUI/YOU.png")).image
        imageList["plateCom"] = ImageIcon(javaClass.getResource("image/fieldUI/COM.png")).image
        imageList["plateDealer"] = ImageIcon(javaClass.getResource("image/fieldUI/Dealer.png")).image
        imageList["userChip"] = ImageIcon(javaClass.getResource("image/fieldUI/betMoney.png")).image
        imageList["comChip"] = imageList["userChip"]!!
        imageList["potChip"] = imageList["userChip"]!!

        //トランプ画像
        for (cardId in TrumpBunch().issueCardIdList()){
            imageList[cardId] = ImageIcon(javaClass.getResource("image/card/"+ cardId + ".png")).image
        }

        //トランプ背景画像
        imageList["c999"] = ImageIcon(javaClass.getResource("image/card/c999.png")).image

    }

    /**
     * 画像・文字の位置情報を定義
     * (Width,Heightが必要になるため小分け)
     */
    fun settingMapInfo(width:Int,height:Int){

        /******************画像の位置情報まとめ***************************/
        //画像情報を簡単羅列するための関数
        val ImgMap = fun(x:Int,y:Int):Map<String,Int>{
                return mapOf("x" to x, "y" to y)
        }

        val cardSize = getCardSize()
        drawImgAllMap = mapOf(
            "plateYou" to ImgMap(800,600),//Youを表すプレート
            "plateCom" to ImgMap(400,20),//Comを表すプレート
            "plateDealer" to ImgMap(200,600),//dealerを表すプレート
            "flopCard1" to ImgMap(width/2- 5*cardSize["x"]!!/2+0*cardSize["x"]!!, height/2-cardSize["y"]!!/2),
            "flopCard2" to ImgMap(width/2- 5*cardSize["x"]!!/2+1*cardSize["x"]!!, height/2-cardSize["y"]!!/2 ),
            "flopCard3" to ImgMap(width/2- 5*cardSize["x"]!!/2+2*cardSize["x"]!!, height/2-cardSize["y"]!!/2),
            "flopCard4" to ImgMap(width/2- 5*cardSize["x"]!!/2+3*cardSize["x"]!!, height/2-cardSize["y"]!!/2),
            "flopCard5" to ImgMap(width/2- 5*cardSize["x"]!!/2+4*cardSize["x"]!!, height/2-cardSize["y"]!!/2 ),
            "userCard1" to ImgMap(width/2- 2*cardSize["x"]!!/2+0*cardSize["x"]!!, height/2+cardSize["y"]!!/2),
            "userCard2" to ImgMap(width/2- 2*cardSize["x"]!!/2+1*cardSize["x"]!!, height/2+cardSize["y"]!!/2),
            "comCard1" to ImgMap(width/2- 2*cardSize["x"]!!/2+0*cardSize["x"]!!, height/2-cardSize["y"]!!*3/2),
            "comCard2" to ImgMap(width/2- 2*cardSize["x"]!!/2+1*cardSize["x"]!!, height/2-cardSize["y"]!!*3/2),
            "userChip" to ImgMap(800,520),
            "comChip" to ImgMap(340,100),
            "potChip" to ImgMap(1000,300)
        )

        drawStringAllMap = mapOf(
            "userBetAmount" to mapOf("x" to 800, "y" to 520),
            "comBetAmount" to mapOf("x" to 340, "y" to 100),
            "potBetAmount" to mapOf("x" to 1200, "y" to 300)
        )

        dealerPosition = arrayOf(
            mapOf("x" to 200  ,"y" to 600 ),
            mapOf("x" to 800 , "y" to 100)
        )
    }


    /**
     * 描画処理
     * repaint()で呼び出される
     */
    override fun paint(g: Graphics) {

        //配列で渡された画像を表示する
        drawTargetImgMap.forEach{
            g.drawImage(imageList[it.value["img"]],it.value["x"].toString().toInt() , it.value["y"].toString().toInt(), null)
        }

        drawTargetStrMap.forEach{
            g.drawString(it.value["content"].toString(),it.value["x"].toString().toInt(),it.value["y"].toString().toInt())
        }
    }

    /**
     * ディーラーを変更
     */
    fun changeDealer(dealer:Int){
        addDrawTargetImg("plateDealer",dealerPosition[dealer]["x"]!!,dealerPosition[dealer]["y"]!!)
    }

    /**
     * トランプカードを変更する
     */
    fun changeTrumpCard(targetKey:String,imgid:String){
        drawTargetImgMap[targetKey] = ( mapOf("x" to drawImgAllMap[targetKey]!!["x"]!!, "y" to drawImgAllMap[targetKey]!!["y"]!!,"img" to imgid))
    }

    /**
     * 描画対象の追加・変更
     */
    fun addDrawTargetImg(targetKey:String,x:Int = - 1 ,y:Int = -1){
        val cx = if(x == -1) drawImgAllMap[targetKey]!!["x"]!! else x
        val cy = if(y == -1) drawImgAllMap[targetKey]!!["y"]!! else y

        val drawTargetTmp:Map<String,Any> = mapOf("x" to cx, "y" to cy,"img" to targetKey)
        drawTargetImgMap[targetKey] = drawTargetTmp
    }


    /**
     * テキストを追加・変更
     */
    fun changeDrawTargetText(targetKey:String,text:String){
        val x = drawStringAllMap[targetKey]!!["x"].toString().toInt()
        val y = drawStringAllMap[targetKey]!!["y"].toString().toInt()
        drawTargetStrMap[targetKey] = mapOf("x" to x, "y" to y, "content" to text)
    }


    /**
     * 描画対象の画像を外す
     */
    //TODO?

    /**
     * 指定の画像を指定位置に描画する
     */
    fun repaintCanvas(){
        repaint()
    }

    
    fun getCardSize():Map<String,Int>{
        var x:Int = imageList["c101"]!!.getWidth(null)
        var y:Int = imageList["c101"]!!.getHeight(null)
        return mapOf("x" to x, "y" to y)
    }

}