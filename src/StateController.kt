import kotlin.system.exitProcess

class StateController {
    var state = 0
    val stateMgr = StateModule()
    var dealer = 0
    var act:Map<String,String> = mapOf()
    fun processLoop(){
        when(state){
            0 ->{
                stateMgr.state0()
                state = 100
            }
            100->{
                stateMgr.state100()
                state = 101
            }
            101 ->{
                dealer = stateMgr.state101()
                state = 102
            }
            102 ->{
                stateMgr.state102()
//                state = if(dealer == 0) 120 else 121
                state = 103
            }
            //
            103 ->{
                stateMgr.state103()
                act= mapOf("select" to "initBet")
                state = 120
            }
            //先行の行動
            120 ->{
                act = stateMgr.state120(dealer,act["select"]!!)
                if(act["select"] == "Fold" || act["select"] == "Call") state = 122
                //それ以外であれば後攻の行動に移る
                else state=121
            }
            //後攻の行動
            121 ->{
                act = stateMgr.state121(dealer,act["select"]!!)
                //Foldかcallで次のカード
                if(act["select"] == "Fold" || act["select"] == "Check" )
                    state = 122
                else if(act["select"]== "Call")
                    state = 122
                //それ以外は継続
                else state = 120
            }
            //両方の行動終了後の情報整理
            122 ->{
                val loopNum = stateMgr.state122()
                state = 130
                when(act["select"]){
                    //降りていた場合は
                    "Fold" ->{
                        //降りていない方を勝利としてpot金移動フェーズに移行する
                        stateMgr.battleResult = if(act["hand"] == "user") 1 else 0
                        state = 151
                    }
                }
                if(loopNum > 2)state = 140
            }
            //
            130 ->{
                stateMgr.state130()
                act= mapOf("select" to "firstAct")
                state = 120
            }
            140 ->{
                stateMgr.state140()
                state = 141
            }
            141 ->{
                stateMgr.state141()
                state = 150
            }
            150 ->{
                stateMgr.state150()
                state = 151
            }
            151 ->{
                stateMgr.state151()
                state = 100
            }
            160 ->{}
            else ->{
                println("不明な遷移")
                exitProcess(-1)
            }
        }
        stateMgr.processMediate()


    }
}