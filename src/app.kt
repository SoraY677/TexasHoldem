import kotlin.concurrent.timer

fun main(){
    val rules = Rules()
    val trumpBunch = TrumpBunch()
    trumpBunch.shuffle()
    val listA = arrayListOf(trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop())
    val listB = arrayListOf(trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop())
    rules.searchHand(listA,listB)
}
