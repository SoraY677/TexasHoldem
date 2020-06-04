class StateModule {

    val progress = ProgressController()

    val frame = GameFrame()
    val canvas = FieldCanvas()
    val btpanel = ButtonPanel(progress.user)

    var dealer= 0

    var paintMap = mutableMapOf<String,Any>()

    /**
     * システム初期化
     */
    fun state0(){
        //各パーツをセット
        frame.setVisible(canvas,btpanel.panel)
        canvas.settingMapInfo(frame.panelWidth,frame.panelHeight)
    }

    /**
     * ゲーム初期化
     */
    fun state100(){
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
        if(dealer == 0){
            canvas.addDrawTargetImg("plateDealer")
        }
        else{
            canvas.addDrawTargetImg("plateDealer")
        }
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

    //初期ベット
    fun state103(){
        canvas.addDrawTargetImg("userChip")
        canvas.addDrawTargetImg("comChip")
        if(dealer == 0){
            progress.user.betMoney = 5
            progress.com.betMoney = 10
        }
        else{
            progress.user.betMoney = 10
            progress.com.betMoney = 5
        }
        canvas.changeDrawTargetText("userBetAmount",progress.user.betMoney.toString())
        canvas.changeDrawTargetText("comBetAmount",progress.com.betMoney.toString())

    }

    /**
     * ユーザの行動
     */
    fun state120(){
        progress.actUserHand()
    }

    /**
     * コンピュータの行動
     */
    fun state121(){
        progress.actComHand()
    }

    /**
     * 場に新カードの追加
     */
    fun state130(){
        progress.addNewFlopCard()
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