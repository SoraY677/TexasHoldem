open class CardHolder {
    val BasePosition:MutableMap<String,Int> = mutableMapOf("x" to 0 , "y" to 0)
    var betMoney = 0
    var holdMoney = 500
    var latestAct = ""
    var cardList:ArrayList<TrumpCard> = arrayListOf()

    open fun Init(){
        cardList = arrayListOf()
    }

    /**
     * 配られたカードを受け取る
     */
    fun recieveCards(receiveCardList:ArrayList<TrumpCard>){
        receiveCardList.forEach {  recieveCard ->
            cardList.add(recieveCard)
        }
    }


}