import com.sun.javafx.css.Rule
import java.lang.reflect.Field
import kotlin.system.exitProcess

class Test {

    constructor(){
        println("--------------テストモードが作動中です------------------")
        process()
    }


    //テストモード中に実行する処理を記述
    fun process(){
        val trumpBunch = TrumpBunch()
        trumpBunch.init()
        trumpBunch.shuffle()
        val test1 = arrayListOf(trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop())
        val test2 = arrayListOf(trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop())
        val test3 = arrayListOf(trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop())
        test1.forEach {
            println(mapOf("num" to it.num, "mark" to it.mark))
        }
        test2.forEach {
            println(mapOf("num" to it.num, "mark" to it.mark))
        }
        test3.forEach {
            println(mapOf("num" to it.num, "mark" to it.mark))
        }
        val rules = Rules()
        rules.searchHand(test1,test2)

    }

    fun createTestCard2(numberList: ArrayList<Int> = arrayListOf(1,2),markList: ArrayList<Int> = arrayListOf(0,0)):ArrayList<TrumpCard>{
        if(numberList.size != 2 || markList.size != 2){
            println("test error!:2枚カードの指定ができていないため、createTestCard2の引数を確認してください！")
            exitProcess(-1)
        }
        var result = arrayListOf<TrumpCard>()
        var resultNum =  arrayListOf<Int>()
        var resultMark = arrayListOf<Int>()
        for ( cardi in 0 until 2){
            resultNum.add(numberList[cardi])
            resultMark.add(markList[cardi])
            result.add(TrumpCard(numberList[cardi],markList[cardi]))
        }

        println("生成したハンド(数):" + resultNum)
        println("生成したハンド(マーク)" + resultMark)
        return result
    }


    fun createTestCard5(numberList: ArrayList<Int> = arrayListOf(3,4,5,6,7),markList: ArrayList<Int> = arrayListOf(0,0,0,0,0)):ArrayList<TrumpCard>{
        if(numberList.size != 5 || markList.size != 5){
            println("test error! :5枚カードの指定ができていないため、createTestCard5の引数を確認してください！")
            exitProcess(-1)
        }
        var result = arrayListOf<TrumpCard>()
        var resultNum =  arrayListOf<Int>()
        var resultMark = arrayListOf<Int>()
        for ( cardi in 0 until 5){
            resultNum.add(numberList[cardi])
            resultMark.add(markList[cardi])
            result.add(TrumpCard(numberList[cardi],markList[cardi]))
        }

        println("生成した場(数):" + resultNum)
        println("生成した場(マーク)" + resultMark)
        return result
    }

}