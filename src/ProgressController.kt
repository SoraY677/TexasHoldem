import java.awt.Canvas
import kotlin.collections.ArrayList
import kotlin.random.Random

/**
 * ステージを管理するクラス
 * User,Computer,flop
 */
class ProgressController {
    val user = User()
    val com = Computer()
    val flop = Flop()
    val trumpBunch = TrumpBunch()

    var pot = 0;

    val rules = Rules()

    var img:ArrayList<MutableMap<String,Any>>? = arrayListOf()

    // 現在のディーラーがcomかuserのどちらかを示すナンバー
    // 0 => user | 1 => com
    var dealerNum = Random.nextInt(0,2)

    constructor(){

    }

    /**
     * ゲームの初期化
     */
    fun init(){
        user.Init()
        user.btpanel.disableAllButton()
        com.Init()
        com.Init()
        flop.Init()
        trumpBunch.init()
        trumpBunch.shuffle()
    }

    /**
     * ディーラーを交代する
     * 初回ディーラー決定時も生成時にランダムから決定しているので以下を実行すればＯＫ
     */
    fun decideDealer():Int{
        dealerNum = if(dealerNum == 0){1}else{0}
        return dealerNum
    }

    /**
     * user,com,それぞれに
     * 初期のカードの配布を行う
     */
    fun divideCards():MutableMap<String,ArrayList<String>>{
        //user,com = 2 | flop = 3 でカードを配る
        val userCard = arrayListOf(trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop())
        user.recieveCards(userCard)
        val comCard = arrayListOf(trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop())
        com.recieveCards(comCard)

        //カード情報を返す
        var result = mutableMapOf("user" to arrayListOf<String>(), "com" to arrayListOf<String>(),"flop" to arrayListOf<String>())
        userCard.forEach {
            result["user"]!!.add(it.publishId())
        }
        comCard.forEach {
            result["com"]!!.add("c999")
        }

        return result
    }

    /**
     * 場に新カードを追加する
     */
    fun addNewFlopCard():String{
        val card = trumpBunch.drawCardfromTop()
        flop.recieveCards(arrayListOf(card))
        return card.publishId()
    }

    /**
     * 最初の強制ベット
     */
    fun firstBet(userbet:Int,combet:Int){
        user.betMoney =  userbet
        user.holdMoney -= userbet
        com.betMoney = combet
        com.holdMoney -= combet
    }

    /**
     * ユーザの行動
     */
    fun actUserHand(act:String):Map<String,String>{
        return user.actHand(act,com.betMoney)
    }


    /**
     * コンピュータの行動
     */
    fun actComHand():Map<String,String>{
        return com.actHand(flop.cardList,user.betMoney,user.latestAct)
    }


    /**
     *
     */
    fun openHand():ArrayList<TrumpCard>{
        return com.openCard()
    }

    /**
     * ユーザ・コンピュータの役判定
     * @return judgeResult
     */
    fun judgeHandPower():Int{
        val userHandPower = rules.searchHand(user.cardList,flop.cardList)
        val comHandPower = rules.searchHand(com.cardList,flop.cardList)
        if(userHandPower["power"]!! > comHandPower["power"]!!){
            return 0
        }
        else if(userHandPower["power"]!! < comHandPower["power"]!!){
            return 1
        }
        else{
            if(userHandPower["highNum"]!! > comHandPower["highNum"]!!) return 0 //ユーザの勝ち
            else if(userHandPower["highNum"]!! > comHandPower["highNum"]!!) return 1 //コンピュータの勝ち
            else return 2 //　引き分け
        }
    }




    /**
     *
     */
    fun setDeckPos(){

    }

    fun shuffleDeck(){

    }




    fun setUserCard(){

    }




}