import kotlin.random.Random

class TrumpBunch {
    //トランプカードのすべて
    var cardList:ArrayList<TrumpCard> = arrayListOf()

    //全てのトランプの合計数
    //マーク・数の最大
    val MARK_MAX = 4
    val NUM_MAX = 13
    // ♠・♥・♣・♦の各1-13
    val CARD_NUM_MAX = MARK_MAX * NUM_MAX


    constructor(){
        // 山札の各カードを生成
        for(cardi in 0 until CARD_NUM_MAX) {
            cardList.add(TrumpCard(cardi % NUM_MAX + 1,cardi / NUM_MAX))
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


    fun issueCardIdList():ArrayList<String>{
       var cardIdList:ArrayList<String> = arrayListOf()
       for(cardi in 0 until CARD_NUM_MAX)
      {
          cardIdList.add("c" + String.format("%d",(cardi / 13 + 1)) + String.format("%02d",(cardi % 13 + 1)) )
      }
        return cardIdList
    }


    // カードリストの一番上からカードをひく
    fun drawCardfromTop():TrumpCard {
        var topCard = cardList[0]
        cardList.removeAt(0)
        return topCard
    }

}