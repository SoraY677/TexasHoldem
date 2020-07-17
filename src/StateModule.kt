import javax.swing.JLabel

class StateModule {

    val progress = ProgressController()

    val frame = GameFrame()
    val canvas = FieldCanvas()

    var dealer= 0

    var pot = 0
    var continueFlag = true

    var paintMap = mutableMapOf<String,Any>()
    var flopVaryNum = 0 //場のカードに変化をおよぼすに至った回数
    var battleResult = 0 // 勝敗
    var playNum = 0 // プレイ回数

    /**
     * システム初期化
     */
    fun init(){
        //各パーツをセット
        frame.setVisible(canvas,progress.user.btpanel.panel)
        canvas.settingMapInfo(frame.panelWidth,frame.panelHeight)
        canvas.init()
    }

    /**
     * ゲーム初期化
     */
    fun initGame(){
        pot = 0
        flopVaryNum = 0
        paintMap.clear()
        progress.init()
        canvas.init()
        canvas.addDrawTargetImg("bg")
        canvas.addDrawTargetImg("plateYou")
        canvas.addDrawTargetImg("plateCom")
        canvas.changeDrawTargetText("userAllChipAmount",progress.user.holdMoney.toString())
        canvas.changeDrawTargetText("comAllChipAmount",progress.com.holdMoney.toString())
    }

    /**
     * ディーラーの交代
     */
    fun changeDealer():Int{
        dealer = progress.decideDealer()
        canvas.changeDealer(dealer)
        return dealer
    }

    /**
     * カードの配布
     */
    fun distributeCards(){
        val cardState = progress.divideCards()
        cardState.forEach{
            for(cardi in 0 until it.value.size){
                //キャンバスのトランプを変える
                canvas.changeTrumpCard(it.key.toString() + "Card" + (cardi+1).toString(),it.value[cardi])
            }
        }
    }

    /**
     * 初期ベット
     */
    fun initBet(){
        canvas.addDrawTargetImg("userChip")
        canvas.addDrawTargetImg("comChip")
        canvas.addDrawTargetImg("potChip")
        if(dealer == 0){
            progress.firstBet(5,10)
        }
        else{
            progress.firstBet(10,5)
        }
        canvas.changeDrawTargetText("userBetAmount", progress.user.betMoney.toString())
        canvas.changeDrawTargetText("comBetAmount", progress.com.betMoney.toString())
        canvas.changeDrawTargetText("potBetAmount",pot.toString())
        canvas.changeDrawTargetText("userAllChipAmount",progress.user.holdMoney.toString())
        canvas.changeDrawTargetText("comAllChipAmount",progress.com.holdMoney.toString())

    }

    /**
     * 先攻
     */
    fun changefirstTurn(dealer:Int,prevAct:String):Map<String,String>{
        if(dealer == 0) {
            val userAction = progress.actUserHand(prevAct)
            canvas.changeDrawTargetText("userBetAmount",userAction["bet"]!!)
            canvas.changeDrawTargetText("userAllChipAmount",userAction["holdMoney"]!!)
            return userAction
        }
        else{
            val comAction =  progress.actComHand(prevAct)
            canvas.changeDrawTargetText("comBetAmount",comAction["bet"]!!)
            canvas.changeDrawTargetText("comAllChipAmount",comAction["holdMoney"]!!)
            return comAction
        }
    }

    /**
     * 後攻
     */
    fun changeSecondTurn(dealer:Int,prevAct:String):Map<String,String>{
        if(dealer == 0) {
            val comAction =  progress.actComHand(prevAct)
            canvas.changeDrawTargetText("comBetAmount",comAction["bet"]!!)
            canvas.changeDrawTargetText("comAllChipAmount",comAction["holdMoney"]!!)
            return comAction
        }
        else{
            val userAction = progress.actUserHand(prevAct)
            canvas.changeDrawTargetText("userBetAmount",userAction["bet"]!!)
            canvas.changeDrawTargetText("userAllChipAmount",userAction["holdMoney"]!!)
            return userAction
        }
    }

    /**
     * 両方の行動終了後
     */
    fun tidyStatus():Int{
        pot += progress.user.betMoney
        pot += progress.com.betMoney
        progress.user.betMoney = 0
        progress.com.betMoney = 0
        canvas.changeDrawTargetText("userBetAmount",progress.user.betMoney.toString())
        canvas.changeDrawTargetText("comBetAmount",progress.com.betMoney.toString())
        canvas.changeDrawTargetText("potBetAmount",pot.toString())
        canvas.changeDrawTargetText("userAllChipAmount",progress.user.holdMoney.toString())
        canvas.changeDrawTargetText("comAllChipAmount",progress.com.holdMoney.toString())
        return flopVaryNum
    }

    /**
     * 場に新カードの追加
     */
    fun addNewFlop(){
        when(flopVaryNum){
            0 -> {
                for(cardi in 0 until 3)
                canvas.changeTrumpCard("flopCard" + (cardi+1).toString(),progress.addNewFlopCard())
            }
            1 -> {
                    canvas.changeTrumpCard("flopCard4",progress.addNewFlopCard())
            }
            2 -> {
                    canvas.changeTrumpCard("flopCard5",progress.addNewFlopCard())
            }
        }
        flopVaryNum++
    }

    fun addAllFlop(){
        when(flopVaryNum){
            0 -> {
                for(cardi in 0 until 5)
                    canvas.changeTrumpCard("flopCard" + (cardi+1).toString(),progress.addNewFlopCard())
            }
            1 -> {
                canvas.changeTrumpCard("flopCard4",progress.addNewFlopCard())
                canvas.changeTrumpCard("flopCard4",progress.addNewFlopCard())
            }
            2 -> {
                canvas.changeTrumpCard("flopCard5",progress.addNewFlopCard())
            }
        }
    }


    /**
     * カードオープン
     */
    fun openComCard(){
        val comHand = progress.openHand()
        for(cardi in 0 until comHand.size){
            canvas.changeTrumpCard("comCard" + (cardi + 1).toString(),comHand[cardi].publishId())
        }
    }

    /**
     * ユーザーとコンピュータの役判定
     */
    fun judgeCardPower(){
        battleResult = progress.judgeHandPower()
    }

    /**
     * 結果表示
     */
    fun showResult(){

    }

    /**
     * チップ移動・プレイ数＋１
     */
    fun isMigrateNextGame():Boolean{

        if(battleResult == 0){
            println("user win")
            progress.user.holdMoney += pot
        }
        else if(battleResult == 1){
            println("com win")
            progress.com.holdMoney += pot
        }
        else if(battleResult == 2){
            println("drow")
            progress.user.holdMoney += pot/2
            progress.com.holdMoney += pot/2
        }
        else{
            error("予期せぬ勝敗判定が行われました!")
        }
        pot = 0

        canvas.changeDrawTargetText("userBetAmount", progress.user.betMoney.toString())
        canvas.changeDrawTargetText("comBetAmount", progress.com.betMoney.toString())
        canvas.changeDrawTargetText("potBetAmount",pot.toString())
        canvas.changeDrawTargetText("userAllChipAmount",progress.user.holdMoney.toString())
        canvas.changeDrawTargetText("comAllChipAmount",progress.com.holdMoney.toString())

        //プレイ回数 +1
        playNum++;

        if(progress.isContinueGame((dealer)))return false
        return true

    }


    /**
     * 各処理の仲介で処理を行う
     */
    fun processMediate(){
        canvas.repaintCanvas()
    }

}