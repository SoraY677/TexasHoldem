class Computer:CardHolder() {

    /**
     * 行動を決定するフェーズ
     */
    fun actHand(flopCardList: ArrayList<TrumpCard>):String{

//        var actStr = this.ioJudgeMaterial(flopCardList)

        //TODO:どういった処理をするのか決める
        //例えばSwitchでどの行動を行うか決めるとか


        println("com action done.")//TODO : DEL AFTER
        return "Check"
    }

    /**
     * 標準入出力を行う
     */
    fun ioJudgeMaterial(flopCardList:ArrayList<TrumpCard>):String{
        //外部出力： {自身の手・場のカード}
        var outputObj = mutableMapOf<String,ArrayList<TrumpCard>>()
        outputObj["com"] = this.cardList
        outputObj["flop"] = flopCardList
        println(outputObj)

        //TODO: 何を受け取るかを決めて受け取った値を整形する
        //標準入力
        val inputStr = readLine().toString()

        return inputStr
    }

    fun openCard():ArrayList<TrumpCard>{
        return cardList
    }



}