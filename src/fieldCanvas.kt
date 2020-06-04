import com.sun.deploy.panel.PathRenderer
import org.omg.CORBA.Object
import java.awt.Canvas
import java.awt.Graphics
import java.awt.Image
import javax.swing.ImageIcon



class FieldCanvas : Canvas(){

    var imageList:MutableMap<String,Image> = mutableMapOf()

    var paintImgMapReciever:ArrayList<Map<String,Any>> = arrayListOf()
    var paintStringMapReciever:ArrayList<Map<String,Any>> = arrayListOf()

    var drawImgAllMap:Map<String,Map<String,Any>> = mapOf()
    var drawStringAllMap:Map<String,Map<String,Any>> = mapOf()
    var drawTargetImgMap:ArrayList<Map<String,Any>> = arrayListOf()
    var drawTargetStrMap:ArrayList<Map<String,Any>> = arrayListOf()

    var isBetMoney = false


    init {
        /*画像読み込み部*/
        //UI画像
        imageList["plateYou"] = ImageIcon(javaClass.getResource("image/fieldUI/YOU.png")).image
        imageList["plateCom"] = ImageIcon(javaClass.getResource("image/fieldUI/COM.png")).image
        imageList["plateDealer"] = ImageIcon(javaClass.getResource("image/fieldUI/Dealer.png")).image
        imageList["userChip"] = ImageIcon(javaClass.getResource("image/fieldUI/betMoney.png")).image
        imageList["comChip"] = imageList["userChip"]!!

        //トランプ画像
        for (cardId in TrumpBunch().issueCardIdList()){
            imageList[cardId] = ImageIcon(javaClass.getResource("image/card/"+ cardId + ".png")).image
        }

        //トランプ背景画像
        imageList["c999"] = ImageIcon(javaClass.getResource("image/card/c999.png")).image

    }

    fun settingMapInfo(width:Int,Height:Int){

        /******************画像の位置情報まとめ***************************/
        //画像情報を簡単羅列するための関数
        val ImgMap = fun(x:Int,y:Int,imgpath:String?):Map<String,Any>{

            if(imgpath==null){
                return mapOf("x" to x, "y" to y)
            }
            else{
                return mapOf("x" to x, "y" to y,"imgpath" to imgpath)
            }

        }
        val cardSize = getCardSize()
        drawImgAllMap = mapOf(
            "plateYou" to ImgMap(800,600,null),//Youを表すプレート
            "plateCom" to ImgMap(400,20,null),//Comを表すプレート
            "plateDealer" to ImgMap(200,600,null),//dealerを表すプレート
            "flopCard1" to ImgMap(width/2- 5*cardSize["x"]!!/2+0*cardSize["x"]!!, Height/2-cardSize["y"]!!/2, "c999" ),
            "flopCard2" to ImgMap(width/2- 5*cardSize["x"]!!/2+1*cardSize["x"]!!, Height/2-cardSize["y"]!!/2,"c999" ),
            "flopCard3" to ImgMap(width/2- 5*cardSize["x"]!!/2+2*cardSize["x"]!!, Height/2-cardSize["y"]!!/2, "c999" ),
            "flopCard4" to ImgMap(width/2- 5*cardSize["x"]!!/2+3*cardSize["x"]!!, Height/2-cardSize["y"]!!/2 ,"c999"),
            "flopCard5" to ImgMap(width/2- 5*cardSize["x"]!!/2+4*cardSize["x"]!!, Height/2-cardSize["y"]!!/2 ,"c999"),
            "userCard1" to ImgMap(width/2- 2*cardSize["x"]!!/2+0*cardSize["x"]!!, Height/2+cardSize["y"]!!/2,"c999"),
            "userCard2" to ImgMap(width/2- 2*cardSize["x"]!!/2+1*cardSize["x"]!!, Height/2+cardSize["y"]!!/2,"c999"),
            "comCard1" to ImgMap(width/2- 2*cardSize["x"]!!/2+0*cardSize["x"]!!, Height/2-cardSize["y"]!!*3/2,"c999"),
            "comCard2" to ImgMap(width/2- 2*cardSize["x"]!!/2+1*cardSize["x"]!!, Height/2-cardSize["y"]!!*3/2,"c999"),
            "userChip" to ImgMap(20,20,null),
            "comChip" to ImgMap(100,100,null)
        )

        drawStringAllMap = mapOf(
            "userBetAmount" to mapOf("x" to 300, "y" to 40),
            "comBetAmount" to mapOf("x" to 500, "y" to 500)
        )
    }



    override fun paint(g: Graphics) {

        println(drawTargetImgMap)

        //配列で渡された画像を表示する
        for(paintMap in drawTargetImgMap) {
            g.drawImage(imageList[paintMap["img"]],paintMap["x"].toString().toInt() , paintMap["y"].toString().toInt(), null)
        }

//        //ベット金を表示する
//        if(isBetMoney) {
//            for(paintMap in paintStringMapReciever) {
//                g.drawString(paintMap["amount"].toString(),paintMap["x"].toString().toInt(),paintMap["y"].toString().toInt())
//            }
//        }
    }

    fun changeTrumpCard(img:String,imgid:String){


    }

    /**
     * 描画対象の画像を追加する
     */
    fun addDrawTargetImg(targetKey:String){
        //基本的にはkey名 = imageList名の割り当てだが、
        //トランプのカードに関しては別途与える必要があるため、imgpathを作成
        var imgpath = targetKey
        println(drawImgAllMap[targetKey]!!["imgpath"])
        if(drawImgAllMap[targetKey]!!["imgpath"] != null){
            imgpath = drawImgAllMap[targetKey]!!["imgpath"].toString()
            println(imgpath)
        }
        val drawTargetTmp:Map<String,Any> = mapOf("x" to drawImgAllMap[targetKey]!!["x"]!!, "y" to drawImgAllMap[targetKey]!!["y"]!!,"img" to imgpath)
        println(drawTargetTmp)
        drawTargetImgMap.add(drawTargetTmp)
    }

//    fun addDrawTargetText(targetKey:String){
//        val drawTargetTmp : Map<String,Any> = mapOf("x" to drawImgAllMap[targetKey]!!["x"]!!, "y" to drawImgAllMap[targetKey]!!["y"]!!,"image" to targetKey)
//    }


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