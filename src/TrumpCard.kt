//トランプカード単体のクラス
//記号 ♠:1/♥:2/♣:3/♦:4の各13種　と
//ババ＝＞記号:5
class TrumpCard {
    var num = 0 // 番号
    var mark = 0 //記号 ♠:1/♥:2/♣:3/♦:4

    constructor(num:Int,mark:Int){
        this.num = num
        this.mark = mark
    }

    /**
     * 渡されたカードからカードIDを発行する
     */
    fun publishId():String{
        return "c" + (this.mark+1).toString() + String.format("%02d",(this.num))
    }
}