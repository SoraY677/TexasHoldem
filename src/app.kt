import kotlin.concurrent.timer

fun main(){
    val rules = Rules()
    val trumpBunch = TrumpBunch()
//    trumpBunch.shuffle()
//    trumpBunch.drawCardfromTop()
    var trumpEx = TrumpCard(5,0)
    val listA = arrayListOf(trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop())
    val listB = arrayListOf(trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop(),trumpBunch.drawCardfromTop(),trumpEx)
    rules.searchHand(listA,listB)
}
