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

    val rules = Rules()

    var img:ArrayList<MutableMap<String,Any>>? = arrayListOf()

    // 現在のディーラーがcomかuserのどちらかを示すナンバー
    // 0 => user | 1 => com
    var dealerNum = Random.nextInt(0,2)

    constructor(){

    }

    fun init(){
        user.Init()
        com.Init()
        flop.Init()
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
     * user,com,flopそれぞれに
     * カードの配布を行う
     */
    fun divideCards():MutableMap<String,ArrayList<String>>{
        //user,com = 2 | flop = 3 でカードを配る
        val userCard = arrayListOf(trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop())
        user.recieveCards(userCard)
        val comCard = arrayListOf(trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop())
        com.recieveCards(comCard)
        val flopCard = arrayListOf(trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop())
        flop.recieveCards(flopCard)

        //カード情報を返す
        var result = mutableMapOf("user" to arrayListOf<String>(), "com" to arrayListOf<String>(),"flop" to arrayListOf<String>())
        userCard.forEach {
            result["user"]!!.add("c" + (it.mark+1).toString() + String.format("%02d",(it.num)))
        }
        comCard.forEach {
            result["com"]!!.add("c999")
        }
        flopCard.forEach {
            result["flop"]!!.add("c" + (it.mark+1).toString() + String.format("%02d",(it.num)))
        }

        return result
    }


    /**
     * 場に新カードを追加する
     */
    fun addNewFlopCard(){
        flop.recieveCards(arrayListOf(trumpBunch.drawCardfromTop()))
    }

    /**
     * ユーザの行動
     */
    fun actUserHand(){
        user.actHand()
    }


    /**
     * コンピュータの行動
     */
    fun actComHand(){
        com.actHand(flop.cardList)
    }


    /**
     * ユーザ・コンピュータの役判定
     * @return judgeResult
     */
    fun judgeHandPower(){

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