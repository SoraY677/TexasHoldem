import com.sun.deploy.panel.PathRenderer
import org.omg.CORBA.Object
import java.awt.*
import javax.swing.ImageIcon
import java.awt.Font.ITALIC
import java.awt.Color.yellow





class FieldCanvas : Canvas(){

    var imageList:MutableMap<String,Image> = mutableMapOf()


    var drawImgAllMap:Map<String,Map<String,Any>> = mapOf()
    var drawStringAllMap:Map<String,Map<String,Any>> = mapOf()
    var dealerPosition:Array<Map<String,Int>> = arrayOf()
    var drawTargetImgMap:MutableMap<String,Map<String,Any>> = mutableMapOf()
    var drawTargetStrMap:MutableMap<String,Map<String,Any>> = mutableMapOf()

    private var buffer:Image? = null


    init {
        /*画像読み込み部*/
        //UI画像
        imageList["bg"] = ImageIcon(javaClass.getResource("image/fieldUI/background.png")).image
        imageList["plateYou"] = ImageIcon(javaClass.getResource("image/fieldUI/YOU.png")).image
        imageList["plateCom"] = ImageIcon(javaClass.getResource("image/fieldUI/COM.png")).image
        imageList["plateDealer"] = ImageIcon(javaClass.getResource("image/fieldUI/Dealer.png")).image
        imageList["userChip"] = ImageIcon(javaClass.getResource("image/fieldUI/bet.png")).image
        imageList["comChip"] = imageList["userChip"]!!
        imageList["potChip"] = ImageIcon(javaClass.getResource("image/fieldUI/pot.png")).image!!

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
        /*共通の座標を定義*/
        val playerCardY = height/2+cardSize["y"]!!/2 + 30
        val fieldCardY = height/2-cardSize["y"]!!/2 - 30
        val comCardY = height/2 - cardSize["y"]!!*3/2 - 180
        drawImgAllMap = mapOf(
            "bg" to ImgMap(0,0),
            "plateYou" to ImgMap(width/2- 2*cardSize["x"]!!/2+1*(cardSize["x"]!!+ 5)+160, height/2+cardSize["y"]!!/2 +130),//Youを表すプレート
            "plateCom" to ImgMap(width/2- 2*cardSize["x"]!!/2+0*(cardSize["x"]!!+ 5)-180, height/2-cardSize["y"]!!*3/2 - 180),//Comを表すプレート
            "plateDealer" to ImgMap(0,0),//dealerを表すプレート
            "flopCard1" to ImgMap(width/2- 5*cardSize["x"]!!/2+0*(cardSize["x"]!! + 5), fieldCardY),
            "flopCard2" to ImgMap(width/2- 5*cardSize["x"]!!/2+1*(cardSize["x"]!!+ 5), fieldCardY),
            "flopCard3" to ImgMap(width/2- 5*cardSize["x"]!!/2+2*(cardSize["x"]!!+ 5), fieldCardY),
            "flopCard4" to ImgMap(width/2- 5*cardSize["x"]!!/2+3*(cardSize["x"]!!+ 5), fieldCardY),
            "flopCard5" to ImgMap(width/2- 5*cardSize["x"]!!/2+4*(cardSize["x"]!!+ 5), fieldCardY),
            "userCard1" to ImgMap(width/2- 2*cardSize["x"]!!/2+0*(cardSize["x"]!!+ 5), playerCardY),
            "userCard2" to ImgMap(width/2- 2*cardSize["x"]!!/2+1*(cardSize["x"]!!+ 5), playerCardY),
            "comCard1" to ImgMap(width/2- 2*cardSize["x"]!!/2+0*(cardSize["x"]!!+ 5), comCardY),
            "comCard2" to ImgMap(width/2- 2*cardSize["x"]!!/2+1*(cardSize["x"]!!+ 5), comCardY),
            "userChip" to ImgMap(width/2- 2*cardSize["x"]!!/2+1*(cardSize["x"]!!+ 5) + 180, height/2+cardSize["y"]!!/2 +40),
            "comChip" to ImgMap(width/2- 2*cardSize["x"]!!/2+0*(cardSize["x"]!!+ 5)-180, height/2-cardSize["y"]!!*3/2 - 60),
            "potChip" to ImgMap(width-200,10)
        )

        drawStringAllMap = mapOf(
            "userBetAmount" to mapOf("x" to width/2- 2*cardSize["x"]!!/2+1*(cardSize["x"]!!+ 5) + 250, "y" to height/2+cardSize["y"]!!/2 +75),
            "comBetAmount" to mapOf("x" to width/2- 2*cardSize["x"]!!/2+0*(cardSize["x"]!!+ 5)-110, "y" to height/2-cardSize["y"]!!*3/2 -25),
            "potBetAmount" to mapOf("x" to width-130, "y" to 110 ),
            "userAllChipAmount" to mapOf("x" to width/2- 2*cardSize["x"]!!/2+1*(cardSize["x"]!!+ 5)+200, "y" to height/2+cardSize["y"]!!/2 +210),
            "comAllChipAmount" to mapOf("x" to width/2- 2*cardSize["x"]!!/2+0*(cardSize["x"]!!+ 5)-130, "y" to height/2-cardSize["y"]!!*3/2 - 100)
        )

        dealerPosition = arrayOf(
            mapOf("x" to width/2- 2*cardSize["x"]!!/2+0*(cardSize["x"]!!+ 5) -150 ,"y" to height/2+cardSize["y"]!!/2 +50),
            mapOf("x" to width/2- 2*cardSize["x"]!!/2+0*(cardSize["x"]!!+ 5) + 320 , "y" to height/2-cardSize["y"]!!*3/2 - 120)
        )
    }


    /**
     * 描画処理
     * repaint()で呼び出される
     */
    override fun paint(g: Graphics) {
        val gv = buffer!!.graphics

        gv.clearRect(0, 0, 520, 400);       //バッファ領域クリア

        //配列で渡された画像を表示する
        drawTargetImgMap.forEach{
            gv.drawImage(imageList[it.value["img"]],it.value["x"].toString().toInt() , it.value["y"].toString().toInt(), null)
        }

        drawTargetStrMap.forEach{
            gv.color = Color.white
            gv.font = Font("SansSerif", ITALIC, 32)    //フォント情報を追加
            gv.drawString(it.value["content"].toString(),it.value["x"].toString().toInt(),it.value["y"].toString().toInt())
        }

        //バッファ領域に書き込んでおいたイメージを描画
        g.drawImage(buffer, 0, 0, width, height, this)
    }

    fun init(){
        drawTargetImgMap = mutableMapOf()
        drawTargetStrMap = mutableMapOf()
        //バッファを生成
        buffer = createImage(width, height);

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