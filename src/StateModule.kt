class StateModule {

    val progress = ProgressController()

    val frame = GameFrame()
    val canvas = FieldCanvas()

    var paintArray = arrayListOf<Map<String,Any>>()//mutableListOf("bg" to mutableMapOf("x" to 0,"y" to 0,"img" to ""))
    /**
     *
     */

    /**
     * システム初期化
     */
    fun state0(){
        frame.setVisible(canvas)
    }

    /**
     * ゲーム初期化
     */
    fun state100(){
        progress.init()
    }

    /**
     * ディーラーの交代
     */
    fun state101(){
        progress.decideDealer()
    }

    /**
     * カードの配布
     */
    fun state102(){
        val cardState = progress.divideCards()
        cardState.forEach{
            var y  = 0
            when {
                it.key == "user" -> {
                    y = canvas.height/2 + 200
                }
                it.key == "com" -> {
                    y = 0
                }
                it.key == "flop" -> {
                    y = canvas.height/2
                }
            }
            val startX = canvas.width/2  - it.value.size/2

            var i = 0;
            it.value.forEach{
                paintArray.add(mutableMapOf("x" to startX+200*i , "y" to y, "img" to it))
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