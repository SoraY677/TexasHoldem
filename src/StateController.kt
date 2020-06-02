class StateController {
    var state = 0
    val stateMgr = StateModule()
    constructor(){
        stateMgr.state100()
        stateMgr.state101()
        stateMgr.state102()
        when{
            state == 0 ->{
                stateMgr.state0()
                state = 100
            }
            state == 100->{
                stateMgr.state100()
                state = 101
            }
            state == 101 ->{
                stateMgr.state101()
                state = 102
            }
            state == 102 ->{
                stateMgr.state102()
                state == 103
            }
            state == 120 ->{}
            state == 121 ->{}
            state == 130 ->{}
            state == 140 ->{}
            state == 141 ->{}
            state == 142 ->{}
            state == 143 ->{}
            state == 150 ->{}
            state == 151 ->{}
            state == 152 ->{}
            state == 160 ->{}
            else ->{
                println("不明な遷移")
//                exitProcess(-1)
            }




        }
    }
}