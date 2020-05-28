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
        var trumpCardList = arrayListOf<TrumpCard>()
        var numListSortedByCardNum = mutableListOf<Int>()
        var markListSortedByCardMark = mutableListOf<Int>()
        origin7Card.forEach{
            // AはKよりも高いので、14扱いにします
            var numtmp = it.num
            if(numtmp == 1) numtmp = 14

            trumpCardList.add(TrumpCard(numtmp,it.mark))
            numListSortedByCardNum.add(numtmp)
            markListSortedByCardMark.add(it.mark)

        }

        //それぞれの配列を昇順にソート
        numListSortedByCardNum = ArrayList(numListSortedByCardNum)
        markListSortedByCardMark = ArrayList(markListSortedByCardMark)
        numListSortedByCardNum.sortBy{ it }
        markListSortedByCardMark.sortBy{ it }


        var test = judgeStraitFlush(trumpCardList)

        var sortedTrumpList = sortTrumpCardList(trumpCardList,false)
        var markNumList = arrayListOf(0,-1,-1,-1,-1)
        var prevMark = -1

        //各マークの登場するindexを算出
        //登場しない場合は -1
        sortedTrumpList.forEach {
            //マークが切り替わったとき
            if(prevMark != it.mark){
                //これまでの登場マークすべてを加算し、マーク切り替えを登録
                markNumList[it.mark + 1] = markNumList[it.mark]
                prevMark = it.mark
            }
            markNumList[it.mark + 1]++
        }


        for(marki in 1 until markNumList.size) {
            for(previ in 1..marki ){
                if(markNumList[previ] != -1){
                    sortedTrumpList = sortTrumpCardList(sortedTrumpList,true,markNumList[marki-previ],markNumList[marki]-1)
                    break
                }
            }
        }

        sortedTrumpList.forEach {
            print(it.num)
            println(it.mark)
        }

        return mutableMapOf()

    }


    /**
     * ペア・ツーペア・スリーカード・フォーカード・ノーペアのハンドを探索する
     * @return ワンペア存在：ワンペアのあったindex+1 / ワンペア不在：-1
     */
    fun judgeHandOverPair(sortedNumList:MutableList<Int>):Int{
        var NumCount = mutableListOf<Int>(0,0,0) //各組の数
        var prevNum = 0 // ひとつ前の探索対象
        var prevprevNum = -1 //ふたつ前の探索対象
        var countIndex = 0 //組配列のindex
        for( cardi in 0 until sortedNumList.size) {
            //ひとつ前の探索対象 = 今の探索対象
            if(prevNum == sortedNumList[cardi]){
                NumCount[countIndex]++
                prevprevNum = prevNum
            }
            else{
                // ひとつ前まで同じ数が連続していたら切り替え
                if(prevprevNum == prevNum) countIndex++
            }
            prevNum = sortedNumList[cardi]
        }


        NumCount.sortBy { it * -1}//降順にソート

        // フォーカード
        if(NumCount[0] == 3) return FOR_OF_A_KIND
        // スリーカード or フルハウス
        else if(NumCount[0] == 2){
            if(NumCount[1] == 1) return FULL_HOUSE
            else return THREE_OF_A_KIND
        }
        else if(NumCount[0] == 1){
            if(NumCount[1] == 1)return TWO_PAIR
            return ONE_PAIR
        }

        return NO_PAIR
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
        //一番登場したマークが5枚以上ならばフラッシュ
        if(numerousMarkCount >= 5){
            return true;
        }
        //引っかからなければだめ。
        return false
    }


    /**
     * ストレートフラッシュ
     * @return ストレートフラッシュ？ : true/false
     */
    fun judgeStraitFlush(trumpList:ArrayList<TrumpCard>):Boolean{
        var trumpListtmp = trumpList
        trumpListtmp.sortedBy { it.num * -1 }
        trumpListtmp.forEach {
            print(it.num)
            println(" " + it.mark)
        }
        return false
    }

    /**
     * トランプカードを昇順にクイックソート
     * 参考【http://www.ics.kagoshima-u.ac.jp/~fuchida/edu/algorithm/sort-algorithm/quick-sort.html】
     */
    fun sortTrumpCardList(trumpList:ArrayList<TrumpCard>,isNumorMark:Boolean,lefti:Int = 0,righti:Int = (trumpList.size-1)) : ArrayList<TrumpCard>{

        var sortTrumpList = trumpList
        var compTrumpList = arrayListOf<Int>()
        if(isNumorMark) {
            sortTrumpList.forEach {
                compTrumpList.add(it.num)
            }
        }
        else{
            sortTrumpList.forEach {
                compTrumpList.add(it.mark)
            }
        }
        //探索終了時には受け取ったtrumpListと同じものを返す
        if(lefti == righti)return trumpList
        //順に見て、異なる値を２つ見つけ,、大きいほうを探索の対象とする
        var pivot = fun():Int {

            for (i in lefti+1 until righti){
                if(compTrumpList[lefti] != compTrumpList[i]){
                    return max(compTrumpList[lefti],compTrumpList[i])
                }
            }
            //異なる値が見つからない場合は -1
            return -1
        }

        // pivotで求めた探索対象をもとにして分割
        // 左から求めた大きいほうの開始番号を返却
        var partition = fun():Int{
            var li = lefti
            var ri = righti
            val targetNumIndex = pivot()
            //探索が交差するまで繰り返す
            while(li <= ri){

                //左から右へ、探索対象以上の数値を探索
                while(li < righti) {
                    if (compTrumpList[li] >= targetNumIndex) break
                    li++
                }

                //右から左へ、探索対象未満の数値を探索
                while(ri > lefti){
                    if(compTrumpList[ri] < targetNumIndex) break
                    ri--
                }

                if(li>ri) break;
                // 探索対象たちをswap
                val swaptmp = sortTrumpList[li]
                sortTrumpList[li] = sortTrumpList[ri]
                sortTrumpList[ri] = swaptmp
                li++
                ri--
            }
            return li
        }

        val partitionIndex = partition()
        sortTrumpList = sortTrumpCardList(sortTrumpList,isNumorMark,lefti,partitionIndex-1)
        sortTrumpList = sortTrumpCardList(sortTrumpList,isNumorMark,partitionIndex,righti)

        return sortTrumpList
    }





}