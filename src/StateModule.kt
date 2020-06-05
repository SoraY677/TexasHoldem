class StateModule {

    val progress = ProgressController()

    val frame = GameFrame()
    val canvas = FieldCanvas()

    var dealer= 0

    var pot = 0
    var continueFlag = true

    var paintMap = mutableMapOf<String,Any>()

    var flopCardNum = 4

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
        paintMap.clear()
        progress.init()
        canvas.addDrawTargetImg("plateYou")
        canvas.addDrawTargetImg("plateCom")
    }

    /**
     * ディーラーの交代
     */
    fun state101():Int{
        dealer = progress.decideDealer()
        dealer = 1
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
    fun state122(){
        pot += progress.user.betMoney
        pot += progress.com.betMoney
        progress.user.betMoney = 0
        progress.com.betMoney = 0
        canvas.changeDrawTargetText("userBetAmount",progress.user.betMoney.toString())
        canvas.changeDrawTargetText("comBetAmount",progress.com.betMoney.toString())
        canvas.changeDrawTargetText("potBetAmount",pot.toString())
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
     * 各処理の仲介で処理を行う
     */
    fun processMediate(){
        var paintArray = arrayListOf<Map<String,Any>>()
        paintMap.forEach(){
            val target = it.value
            if(target is Map<*,*>){
                val x = target["x"]!!
                val y = target["y"]
                val img = target["img"]
                paintArray.add(mapOf("x" to x!!, "y" to y!!,"img" to img!!))

            } else if(target is ArrayList<*>){
                target.forEach {
                    if(it is Map<*,*>) {
                        val x = it["x"]
                        val y = it["y"]
                        val img = it["img"]
                        paintArray.add(mapOf("x" to x!!, "y" to y!!, "img" to img!!))
                    }
                }
            }
            else{
                println("予期しない描画用配列が与えられました。確認してください！")
            }
        }

        canvas.repaintCanvas()
    }


}