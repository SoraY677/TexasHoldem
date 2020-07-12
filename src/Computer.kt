class Computer:CardHolder() {

    /**
     * 行動を決定するフェーズ
     */
    fun actHand(flopCardList: ArrayList<TrumpCard>,userBet:Int,userAct:String):Map<String,String>{

        var actStr = ioJudgeMaterial(flopCardList,userAct)

        //TODO:どういった処理をするのか決める
        //例えばSwitchでどの行動を行うか決めるとか

        return mapOf("hand" to "com","select" to "Raise")
    }

    /**
     * 標準入出力を行う
     */
    fun ioJudgeMaterial(flopCardList:ArrayList<TrumpCard>,userAct: String):String{
        //外部出力： {自身の手・場のカード}
        var outputObj = mutableMapOf<String,ArrayList<TrumpCard>>()
        //コンピュータのカード情報
        for(cardi in 0 until this.cardList.size){
            println("com" + (cardi+1) + ":" + ((cardList[cardi].mark+1)*100 + cardList[cardi].num))
        }
        //場のカード情報
        for(cardi in 0 until flopCardList.size){
            println("flop" + (cardi+1) + ":" + ((flopCardList[cardi].mark+1)*100 + flopCardList[cardi].num))
        }

        //できる行動を表示
        println(judgeAbleAction(userAct))

        //TODO: 何を受け取るかを決めて受け取った値を整形する
        //標準入力
        println("comAction:")
        val inputStr = readLine().toString()

        return inputStr
    }

    /**
     * できる行動を指定する
     */
    fun judgeAbleAction(userAct:String):ArrayList<String>{
        val actList = when(userAct){
            "Call" -> arrayListOf("Fold","Check","Bet","All-in")
            "Check" ->arrayListOf("Fold","Check","Bet","All-in")
            "Bet"->arrayListOf("Fold","Raise","Call","All-in")
            "Raise"->arrayListOf("Fold","Raise","Call","All-in")
            else -> arrayListOf("Fold","Raise","Call","All-in")
        }

        return actList
    }


    fun openCard():ArrayList<TrumpCard>{
        return cardList
    }



}