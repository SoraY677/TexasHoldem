import com.sun.org.apache.xpath.internal.operations.Bool
import sun.security.ec.point.ProjectivePoint
import kotlin.math.max

//役の参考サイト
//https://bright777.com/texasholdem2

class Rules {

    //役一覧リスト
    //↑強い
    val ROYAL_FLASH = 9      // ロイヤルストレートフラッシュ / マーク統一で[10,J,Q,K,A]
    val STRAIT_FLASH = 8     // ストレートフラッシュ / マーク統一で順番 (ex)♣6~10
    val FOR_OF_A_KIND = 7    // フォーカード　/数字4つ同じ  (ex)[♥7,♣7,♠7,♦7] + any
    val FULL_HOUSE = 6       // フルハウス / 数字3つ同じ＋残りもペア (ex)[♥7,♣7,♠7,♠3,♦3]
    val FLUSH = 5            // フラッシュ /マーク統一　(ex)[♥7,♥2,♥Q,♥5,♥1]
    val STRAIT = 4           // ストレート /順番 (ex)[♥3,♣4,♠5,♠6,♦7]
    val THREE_OF_A_KIND = 3  // スリーカード / 数字3つ同じ (ex)[♥3,♣3,♠3] + any
    val TWO_PAIR = 2         // ツーペア / ペア*2 (ex)[♥3,♣3,♠6,♠6] + any
    val ONE_PAIR = 1         // ワンペア / ペア*1 (ex)[♥3,♣3] + any
    val NO_PAIR = 0          // ノーペア / 上記以外
    //↓弱い

    /**
     * ハンドとフロップのカードに対して役の弱いほうから順に役が成り立っているか探索する
     * @return {handPower:Number,highestNum:Number} 役の強さと手札のうち最も高いカードの数値
     *
     */
    fun searchHand(handCard:ArrayList<TrumpCard>, flopCard:ArrayList<TrumpCard>):MutableMap<String,Number>{

        //配列を結合する
        val origin7Card = handCard.plus(flopCard)
        var numListSortedByCardNum = mutableListOf<Int>()
        var markListSortedByCardMark = mutableListOf<Int>()
        origin7Card.forEach{
            numListSortedByCardNum.add(it.num)
            markListSortedByCardMark.add(it.mark)
        }
        numListSortedByCardNum = ArrayList(numListSortedByCardNum)
        markListSortedByCardMark = ArrayList(markListSortedByCardMark)
        numListSortedByCardNum.sortBy{ it }
        markListSortedByCardMark.sortBy{ it }

        println(numListSortedByCardNum)
        println(markListSortedByCardMark)

        println(judgeStrait(numListSortedByCardNum))

        return mutableMapOf()

    }

    /**
     * ノーペア探索
     */
    fun judgeNoPair(cardList:ArrayList<TrumpCard>){

    }

    /**
     * ワンペア
     */
    fun judgeOnePair(){

    }

    /**
     *  ツーペア
     */
    fun judgeTwoPair(){

    }

    /**
     * スリーカード
     */
    fun judgeThreeOfaKind(){

    }

    /**
     * ストレート
     */
    fun judgeStrait(sortedNumList:MutableList<Int>):Boolean{

        var cardNumDiff = 0
        var prevNum = 0
        var targetCount = 0
        sortedNumList.forEach{
            cardNumDiff= it - prevNum
            //差が1の時、階段状なのでカウントする
            if(cardNumDiff == 1)targetCount++
            else {
                targetCount = 1
            }

            if(targetCount == 5){
                return true
            }

            prevNum = it //更新
        }
        return false
    }

    /**
     * フラッシュ
     * @return あり：true/なし:false
     */
    fun judgeFlush(sortedMarkList:MutableList<Int>):Boolean{
        var prevMark = 0 // ひとつ前のマーク
        var targetCount = 0; //現在探索中のマークの出現数
        var numerousMarkCount = 0; //登場したマークの中で最も一番出てきたマーク数
        sortedMarkList.forEach{

            // ひとつ前のマーク ＝　今のマーク の時、
            // マークの出現数を加算
            if(prevMark === it){
                targetCount++;
            }
            // マークが切り替わったとき
            // マークの出現率をリセット => 1にする
            else{
                targetCount = 1
            }
            //一番登場したマークが更新されたら変更される
            numerousMarkCount = max(targetCount,numerousMarkCount)
            prevMark = it // 次へ切り替え
        }
        //一番登場したマークが5枚以上ならばフラッシュ！！！
        if(numerousMarkCount >= 5){
            return true;
        }
        //引っかからなければだめ。
        return false
    }

    /**
     * フルハウス
     */
    fun judgeFullHouse(){

    }

    /**
     * フォーカード
     */
    fun judgeFourOfaKind(){

    }

    /**
     * ストレートフラッシュ
     */
    fun judgeStraitFlush(){

    }



}