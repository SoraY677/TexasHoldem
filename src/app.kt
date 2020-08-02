fun main(args: Array<String>){
//    val test = Test()
    val stateController = StateController()
    while(true){
        stateController.processLoop()
        Thread.sleep(200) // システム小休止
    }
}