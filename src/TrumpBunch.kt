import kotlin.random.Random

class TrumpBunch {
    //トランプカードのすべて
    var cardList:ArrayList<TrumpCard> = arrayListOf()

    //全てのトランプの合計数
    // ♠・♥・♣・♦の各1-13 + ババ　2枚
    val CARD_NUM_MAX = 54


    fun Init(){
        // 山札の各カードを生成
        for(cardi in 0 until CARD_NUM_MAX) {
            cardList.add(TrumpCard(cardi % 13 + 1,cardi / 13))
        }
    }

    //カードをシャッフル
    fun shuffle(){
        var cardListTmp:ArrayList<TrumpCard> = arrayListOf()

        // カードリストをシャッフルする処理
        for(cardi in 0 until CARD_NUM_MAX) {
            val cardRandomIndex = Random.nextInt(0, CARD_NUM_MAX - cardi)
            cardListTmp.add(cardList[cardRandomIndex])
            cardList.removeAt(cardRandomIndex)
        }

        cardList  = cardListTmp
    }

    // カードリストの一番上からカードをひく
    fun drawCardfromTop():TrumpCard{
        var topCard = cardList[0]
        cardList.removeAt(0)
        return topCard
    }

}