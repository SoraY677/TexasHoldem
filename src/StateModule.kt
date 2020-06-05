class StateModule {

    val progress = ProgressController()

    val frame = GameFrame()
    val canvas = FieldCanvas()

    var dealer= 0

    var pot = 0
    var continueFlag = true

    var paintMap = mutableMapOf<String,Any>()
    var flopCardNum = 4 //場のカード数
    var battleResult = 0 // 勝敗
    var playNum = 0 // プレイ回数

    /**
     * システム初期化
     */
    fun state0(){
        //各パーツをセット
        frame.setVisible(canvas,progress.user.btpanel.panel)
        canvas.settingMapInfo(frame.panelWidth,frame.panelHeight)
    }

    /**
     * ゲーム初期化
     */
    fun state100(){
        pot = 0
        flopCardNum = 4
        paintMap.clear()
        progress.init()
        canvas.init()
        canvas.addDrawTargetImg("plateYou")
        canvas.addDrawTargetImg("plateCom")
    }

    /**
     * ディーラーの交代
     */
    fun state101():Int{
        dealer = progress.decideDealer()
        canvas.changeDealer(dealer)
        return dealer
    }

    /**
     * カードの配布
     */
    fun state102(){
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
    fun state103(){
        canvas.addDrawTargetImg("userChip")
        canvas.addDrawTargetImg("comChip")
        canvas.addDrawTargetImg("potChip")
        if(dealer == 0){
            progress.firstBet(5,10)
        }
        else{
            progress.firstBet(10,5)
        }
        canvas.changeDrawTargetText("userBetAmount",progress.user.betMoney.toString())
        canvas.changeDrawTargetText("comBetAmount",progress.com.betMoney.toString())
        canvas.changeDrawTargetText("potBetAmount",pot.toString())

    }

    /**
     * 先攻
     */
    fun state120(dealer:Int):Map<String,String>{
        if(dealer == 0) {
            return progress.actUserHand()
        }
        else{
            return progress.actComHand()
        }
    }

    /**
     * 後攻
     */
    fun state121(dealer:Int):Map<String,String>{
        if(dealer == 0) {
            return progress.actComHand()
        }
        else{
            return progress.actUserHand()
        }
    }

    /**
     * 両方の行動
     */
    fun state122():Int{
        pot += progress.user.betMoney
        pot += progress.com.betMoney
        progress.user.betMoney = 0
        progress.com.betMoney = 0
        canvas.changeDrawTargetText("userBetAmount",progress.user.betMoney.toString())
        canvas.changeDrawTargetText("comBetAmount",progress.com.betMoney.toString())
        canvas.changeDrawTargetText("potBetAmount",pot.toString())
        return flopCardNum
    }

    /**
     * 場に新カードの追加
     */
    fun state130(){
        val id = progress.addNewFlopCard()
        canvas.changeTrumpCard("flopCard" + flopCardNum.toString(),id)
        flopCardNum ++
    }

    /**
     * カードオープン
     */
    fun state140(){
        val comHand = progress.openHand()
        for(cardi in 0 until comHand.size){
            canvas.changeTrumpCard("comCard" + (cardi + 1).toString(),comHand[cardi].publishId())
        }
    }

    /**
     * ユーザーとコンピュータの役判定
     */
    fun state141(){
        battleResult = progress.judgeHandPower()
    }

    /**
     * 結果表示
     */
    fun state150(){

    }

    /**
     * チップ移動・プレイ数＋１
     */
    fun state151(){
        if(battleResult == 0){
            progress.user.betMoney += pot
        }
        else if(battleResult == 1){
            progress.com.betMoney += pot}

        else if(battleResult == 2){
            progress.user.betMoney += pot/2
            progress.com.betMoney += pot/2
        }
        else{
            error("予期せぬ勝敗判定が行われました!")
        }
        pot = 0

        //プレイ回数 +1
        playNum++;

    }



    /**
     * 各処理の仲介で処理を行う
     */
    fun processMediate(){
        canvas.repaintCanvas()
    }

}