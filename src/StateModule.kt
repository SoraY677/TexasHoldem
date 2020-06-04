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
        frame.setVisible(canvas,btpanel.panel)
    }

    /**
     * ゲーム初期化
     */
    fun state100(){
        paintMap.clear()
        progress.init()
        paintMap["handUser"] = mapOf("x" to  400, "y" to 20, "img" to "handCom")
        paintMap["handYou"] = mapOf("x" to  800, "y" to 600, "img" to "handYou")
    }

    /**
     * ディーラーの交代
     */
    fun state101():Int{
        dealer = progress.decideDealer()
        if(dealer == 0){
            paintMap["dealer"] = mapOf("x" to  200, "y" to 600, "img" to "dealer")
        }
        else{
            paintMap["dealer"] =  mapOf("x" to  800, "y" to 20, "img" to "dealer")
        }
        return dealer
    }

    /**
     * カードの配布
     */
    fun state102(){
        val cardState = progress.divideCards()
        val cardSize = canvas.getCardSize()
        cardState.forEach{
            var y  = 0
            when {
                it.key == "user" -> {
                    y = frame.height/2 +cardSize["y"]!!/2
                }
                it.key == "com" -> {
                    y = frame.height/2-cardSize["y"]!!*3/2
                }
                it.key == "flop" -> {
                    y = frame.height/2-cardSize["y"]!!/2

                }
            }
            val startX = frame.width/2  - it.value.size*cardSize["x"]!!
            var paintArray = arrayListOf<Map<String,Any>>()
            for(cardi in 0 until it.value.size){
                paintArray.add(mapOf("x" to startX+cardSize["x"]!!*cardi , "y" to y, "img" to it.value[cardi]))
            }
            paintMap[it.key] = paintArray
        }
    }

    //初期ベット
    fun state103(){
        paintMap["userBetMoney"] = mapOf("x" to 20, "y" to 20, "img" to "betMoney")
        paintMap["comBetMoney"] = mapOf("x" to 20, "y" to 20, "img" to "betMoney")
        if(dealer == 0){
            
        }
        else{

        }

    }

    /**
     * ユーザの行動
     */
    fun state120(){
//        btpanel.exitProcessWait(progress.user)
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

        canvas.repaintCanvas(paintArray)
    }


}