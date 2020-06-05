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
            103 ->{
                stateMgr.state103()
                state = 120
            }
            //先行の行動
            120 ->{
                act = stateMgr.state120(dealer)
                //Foldしていたら集計
                if(act["select"] == "Fold") state = 122
                //それ以外であれば後攻の行動に移る
                else state=121
            }
            //後攻の行動
            121 ->{
                act = stateMgr.state121(dealer)
                //Foldかcallで次のカード
                if(act["select"] == "Fold" || act["select"] == "Call")state = 122
                //それ以外は継続
                else state = 120
            }
            //両方の行動終了後の情報整理
            122 ->{
                stateMgr.state122()
                println("122")
                when(act["select"]){
                    "Flod" ->{

                    }
                    "Call" ->{
                        state = 130
                    }

                }
            }
            //
            130 ->{
                stateMgr.state130()
                state = 120
            }
            140 ->{}
            141 ->{}
            142 ->{}
            143 ->{}
            150 ->{}
            151 ->{}
            152 ->{}
            160 ->{}
            else ->{
//                println("不明な遷移")
//                exitProcess(-1)
            }
        }
        stateMgr.processMediate()


    }
}