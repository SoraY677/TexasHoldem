
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
    fun decideDealer(){
        dealerNum = if(dealerNum == 0){1}else{0}
    }

    /**
     * user,com,flopそれぞれに
     * カードの配布を行う
     */
    fun divideCards(){
        //user,com = 2 | flop = 3 でカードを配る
        user.recieveCards(arrayListOf(trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop()))
        com.recieveCards(arrayListOf(trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop()))
        flop.recieveCards(arrayListOf(trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop()))
    }


    /**
     * 場に新カードを追加する
     */
    fun addNewFlopCard(){
        flop.recieveCards(arrayListOf(trumpBunch.drawCardfromTop()))
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