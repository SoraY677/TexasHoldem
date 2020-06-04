import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class User : CardHolder(){
    var isWaitInput = false

    /**
     * ユーザの行動
     */
    fun actHand(){
        //withLockと無限ループによってシステムを一時停止し、ボタン入力(=isWaitInputがtrueになる)を待機する
        val lock = ReentrantLock()
            while(true){
                if(isWaitInput == true)break
                Thread.sleep(100)//入力を受け付けるためにsleep
            }

        isWaitInput = false
    }

}