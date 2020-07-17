class Computer:CardHolder() {

    /**
     * 行動を決定するフェーズ
     */
    fun actHand(flopCardList: ArrayList<TrumpCard>,userBet:Int,userAct:String,actState:String):Map<String,String>{

        var betList = mutableMapOf<String,Int>()
        betList["Fold"] = 0
        betList["Check"] = 0
        betList["Bet"] = 5
        betList["Call"] = userBet-betMoney
        betList["Raise"] = userBet* 2
        betList["All-in"] = holdMoney

        var actStr = ioJudgeMaterial(flopCardList,userAct,actState,userBet,betList)

        changeBetNum(userBet,actStr)

        this.latestAct = actStr

        return mapOf(
            "hand" to "com",
            "select" to actStr,
            "bet" to betMoney.toString(),
            "holdMoney" to holdMoney.toString()
        )
    }

    /**
     * 標準入出力を行う
     */
    fun ioJudgeMaterial(flopCardList:ArrayList<TrumpCard>,userAct: String,actState: String,userBet:Int,betList:MutableMap<String, Int>):String{
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
        println("ableAction:")
        println(judgeAbleAction(userAct,actState,userBet,betList))

        //標準入力
        println("comAction:")
        val inputStr = readLine().toString()

        return inputStr
    }

    /**
     * できる行動を指定する
     */
    fun judgeAbleAction(userAct:String,actState:String,userBet:Int,betList: MutableMap<String, Int>):ArrayList<String>{
        var actList = when(userAct){
            "Call" -> arrayListOf("Fold","Check","Bet","All-in")
            "Check" ->arrayListOf("Fold","Check","Bet","All-in")
            "Bet"->arrayListOf("Fold","Raise","Call","All-in")
            "Raise"->arrayListOf("Fold","Raise","Call","All-in")
            else -> arrayListOf("Fold","Check","Bet","Raise","Call","All-in")
        }

        if(actState == "initBet")actList = arrayListOf("Fold","Raise","Call","All-in")
        else if(actState == "firstAct")actList = arrayListOf("Fold","Check","Bet","All-in")


        //all-inの場合は特殊
        if(userAct == "All-in")
            if(holdMoney >= userBet - betMoney) actList = arrayListOf("Fold","Call")
            else actList = arrayListOf("Fold","All-in")

        //手持ちの金額を超えてしまう場合のベットを禁じる
        betList.forEach{
            if(it.value > holdMoney)
                actList.remove(it.key)
        }

        return actList
    }


    fun changeBetNum(userBetMoney:Int,comAct:String){
        var changeMoney = 0
        when(comAct){
            "Bet" -> {
                changeMoney = 5
            }
            "Call" ->{
                changeMoney = userBetMoney - betMoney
            }
            "Raise" -> {
                changeMoney += userBetMoney *2
            }
            "All-in" -> {
                changeMoney = holdMoney
            }
        }
        betMoney += changeMoney
        holdMoney -= changeMoney
    }


    fun openCard():ArrayList<TrumpCard>{
        return cardList
    }
}