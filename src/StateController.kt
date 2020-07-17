import kotlin.system.exitProcess

class StateController {
    var state = 0
    val stateMgr = StateModule()
    var dealer = 0
    var act:Map<String,String> = mapOf()
    fun processLoop(){
        when(state){
            0 ->{
                stateMgr.init()
                state = 100
            }
            100->{
                stateMgr.initGame()
                state = 101
            }
            101 ->{
                dealer = stateMgr.changeDealer()
                state = 102
            }
            102 ->{
                stateMgr.distributeCards()
                state = 103
            }
            //
            103 ->{
                stateMgr.initBet()
                act= mapOf("select" to "initBet")
                state = 120
            }
            //先行の行動
            120 ->{
                val prevAct = act
                act = stateMgr.changefirstTurn(dealer,act["select"]!!)
                //一つ前の行動がAll-inであり、かつそれに対してCallされた場合は全開放
                if(prevAct["select"] == "All-in" && act["select"] == "Call")state = 135
                //Foldかコールで次のカード
                else if(act["select"] == "Fold" || act["select"] == "Call") state = 122
                //それ以外であれば後攻の行動に移る
                else state=121
            }
            //後攻の行動
            121 ->{
                val prevAct = act
                act = stateMgr.changeSecondTurn(dealer,act["select"]!!)
                //一つ前の行動がAll-inであり、かつそれに対してCallされた場合は全開放
                if(prevAct["select"] == "All-in" && act["select"] == "Call")state = 135
                //Foldかcallで次のカード
                else if(act["select"] == "Fold" || act["select"] == "Check"||act["select"]== "Call" )
                    state = 122
                //それ以外は継続
                else state = 120
            }
            //両方の行動終了後の情報整理
            122 ->{
                val loopNum = stateMgr.tidyStatus()
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
                stateMgr.addNewFlop()
                act= mapOf("select" to "firstAct")
                state = 120
            }
            //All-inした場合は場のカードを一気に配る
            135 ->{
                stateMgr.tidyStatus()//一旦状況整理
                stateMgr.addAllFlop() //場に全カード開放
                state = 140
            }
            140 ->{
                stateMgr.openComCard()
                state = 141
            }
            141 ->{
                stateMgr.judgeCardPower()
                state = 150
            }
            150 ->{
                stateMgr.showResult()
                state = 151
            }
            151 ->{
                val isContinue = stateMgr.isMigrateNextGame()
                if(isContinue)state = 100
                else state = 160
            }
            160 ->{
            }
            else ->{
                println("不明な遷移")
                exitProcess(-1)
            }
        }
        stateMgr.processMediate()


    }
}