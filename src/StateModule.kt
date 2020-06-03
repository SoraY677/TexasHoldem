class StateModule {

    val progress = ProgressController()

    val frame = GameFrame()
    val canvas = FieldCanvas()
    val btpanel = ButtonPanel(arrayOf("yes","no"))

    var paintArray = arrayListOf<Map<String,Any>>()//mutableListOf("bg" to mutableMapOf("x" to 0,"y" to 0,"img" to ""))
    /**
     *
     */

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
        paintArray.clear()
        progress.init()
        paintArray.add(mutableMapOf("x" to  400, "y" to 20, "img" to "handCom"))
        paintArray.add(mutableMapOf("x" to  800, "y" to 600, "img" to "handYou"))
    }

    /**
     * ディーラーの交代
     */
    fun state101(){
        val dealer = progress.decideDealer()
        if(dealer == 0){
            paintArray.add(mutableMapOf("x" to  200, "y" to 600, "img" to "Dealer"))
        }
        else{
            paintArray.add(mutableMapOf("x" to  800, "y" to 20, "img" to "Dealer"))
        }



    }

    /**
     * カードの配布
     */
    fun state102(){
        val cardState = progress.divideCards()
        val cardSize = canvas.getCardSize()
        cardState.forEach{
            var y  = 0
            println(it)
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
            println(y)
            val startX = frame.width/2  - it.value.size*cardSize["x"]!!/2
            println(startX)

            for(cardi in 0 until it.value.size){
                paintArray.add(mutableMapOf("x" to startX+cardSize["x"]!!*cardi , "y" to y, "img" to it.value[cardi]))
            }
        }

        canvas.repaintCanvas(paintArray)

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
    fun processMedate(){

    }


}