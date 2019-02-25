package sanShuiAlgorithm;

import yvette.poker.PokerCard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by hasee on 2017/11/1.
 */



public class copy {
    /**
     * solutionList 元素类型为PokerSolution
     *
     */
    public List<PokerSolution> solutionList = new ArrayList<>();

    //public static List<Card> specialList= new ArrayList<>();

    public static Card[] specialList = new Card[13];
    public static String specialName = "not";

    public static class PokerSolution{
        //public Poker[] touDao = new Poker[3];
        //public Poker[] zhongDao = new Poker[5];
        //public Poker[] weiDao = new Poker[5];
        public List<Poker> touDao = new ArrayList<>();
        public List<Poker> zhongDao = new ArrayList<>();
        public List<Poker> weiDao = new ArrayList<>();
        public String touDaoInfo = null;
        public String zhongDaoInfo = null;
        public String weiDaoInfo = null;
        public String allInfo = null;

        public static void myListSort(List<Poker> tempList){
            tempList.sort(new Comparator<Poker>() {
                @Override
                public int compare(Poker o1, Poker o2) {
                    int cardPoint1,cardPoint2;
                    cardPoint1 = o1.pokerPoint;
                    cardPoint2 = o2.pokerPoint;
                    if(o1.pokerColor == 4) cardPoint1 += 100;
                    if(o2.pokerColor == 4) cardPoint2 += 100;
                    if(o1.pokerPoint ==0 && o1.pokerColor != 4) cardPoint1 += 50;
                    if(o2.pokerPoint ==0 && o2.pokerColor != 4) cardPoint2 +=50;
                    if(cardPoint1 > cardPoint2){
                        return 1;
                    }else if(cardPoint1 < cardPoint2){
                        return -1;
                    }else{
                        if(o1.pokerColor < o2.pokerColor){
                            return 1;
                        }else{
                            return -1;
                        }
                    }
                }
            });
        }
    }

    public static class Poker {
        public int pokerColor;
        public int pokerPoint;

        public Poker(){}

        public Poker(PokerCard tempPokerCard){
            this.pokerPoint = tempPokerCard.getPokerPoint();
            this.pokerColor = tempPokerCard.getPokerColor();
        }
    }

    private static void exchangeToCardStyle(Poker tempPoker) {
        if (tempPoker.pokerColor == 4) {
            tempPoker.pokerColor = 0;
            if (tempPoker.pokerPoint == 0)
                tempPoker.pokerPoint = 44;
            else if (tempPoker.pokerPoint == 1)
                tempPoker.pokerPoint = 88;
            return;
        }

        if (tempPoker.pokerColor == 0)
            tempPoker.pokerColor = 4;

        if (tempPoker.pokerPoint == 0)
            tempPoker.pokerPoint = 13;
    }

    private static void exchangeToPokerStyle(Poker tempPoker) {
        if (tempPoker.pokerPoint == 44) { //小鬼
            tempPoker.pokerPoint = 0;
            tempPoker.pokerColor = 4;
            return;
        } else if (tempPoker.pokerPoint == 88) { //大鬼
            tempPoker.pokerPoint = 1;
            tempPoker.pokerColor = 4;
            return;
        }

        if (tempPoker.pokerColor == 4)
            tempPoker.pokerColor = 0;

        if (tempPoker.pokerPoint == 13)
            tempPoker.pokerPoint = 0;
    }

    private static void exchange(Poker tempPoker) {
        switch(tempPoker.pokerColor) {
            case 0:
                tempPoker.pokerColor = 4;
                break;
            case 4:
                tempPoker.pokerColor = 0;
                break;
        }

        if(tempPoker.pokerPoint == 44){
            tempPoker.pokerPoint = 0;
            return;
        }

        if(tempPoker.pokerPoint == 88){
            tempPoker.pokerPoint = 1;
            return;
        }

        if(tempPoker.pokerColor == 0
                &&tempPoker.pokerPoint ==0){
            tempPoker.pokerPoint = 44;
            return;
        }

        if(tempPoker.pokerColor == 0
                &&tempPoker.pokerPoint == 1){
            tempPoker.pokerPoint = 88;
            return;
        }

        if(tempPoker.pokerPoint ==0){
            tempPoker.pokerPoint = 13;
            return;
        }

        if(tempPoker.pokerPoint ==13){
            tempPoker.pokerPoint=0;
            return;
        }

    }

    public void getSolution(Choice tempChoice) {
        PokerSolution solution = new PokerSolution();
        for(int i = 0; i < tempChoice.head.size();i++) {
            Poker tempPoker = new Poker();
            tempPoker.pokerPoint = tempChoice.head.get(i).rank;
            tempPoker.pokerColor = tempChoice.head.get(i).type;
//            exchange(tempPoker);
            exchangeToPokerStyle(tempPoker);
            solution.touDao.add(tempPoker);
            //solution.myListSort();
        }

        for(int i = 0; i < tempChoice.mid.size();i++) {
            Poker tempPoker = new Poker();
            tempPoker.pokerPoint = tempChoice.mid.get(i).rank;
            tempPoker.pokerColor = tempChoice.mid.get(i).type;
//            exchange(tempPoker);
            exchangeToPokerStyle(tempPoker);
            solution.zhongDao.add(tempPoker);
            //solution.myListSort(solution.zhongDao);
        }

        for(int i = 0; i < tempChoice.end.size();i++) {
            Poker tempPoker = new Poker();
            tempPoker.pokerPoint = tempChoice.end.get(i).rank;
            tempPoker.pokerColor = tempChoice.end.get(i).type;
//            exchange(tempPoker);
            exchangeToPokerStyle(tempPoker);
            solution.weiDao.add(tempPoker);
            //solution.myListSort(solution.weiDao);
        }
        solution.touDaoInfo = tempChoice.headType;
        solution.zhongDaoInfo = tempChoice.midType;
        solution.weiDaoInfo = tempChoice.endType;
        solutionList.add(solution);
    }

    public void superChange(List<Poker> poker) {
        Out.allChoice.clear();

        for(int i = 0; i < poker.size(); i++)
//            exchange(poker.get(i));
            exchangeToCardStyle(poker.get(i));
        CardPile.PreDeal();
        PlayerByCloudFree p = new PlayerByCloudFree();
        p.handCard.clear();
        p.handCardCopy.clear();
        for(int i = 0; i < poker.size(); i++) {
            Card tempCard = new Card(poker.get(i).pokerColor, poker.get(i).pokerPoint);
//            Card tempCard = new Card(poker.get(i).getPokerColor(), poker.get(i).getPokerPoint());
            p.handCard.add(tempCard);
            p.handCardCopy.add(tempCard);
            //System.out.println(tempCard.type+"  "+tempCard.rank);
        }
        p.handCardCopy.sort(PlayerByCloudFree.cardRankComparator);
        addSpecial(p);
        p.quadrupleCard.clear();
        p.shunZi.clear();
        p.doubleCard.clear();
        p.tonghuaShun.clear();
        p.tripleCard.clear();
        p.allType.clear();
        if(specialName!="not")
        {
            PokerSolution solution = new PokerSolution();
            solutionList.clear();
            solution.allInfo = specialName;
            solution.touDao.clear();
            solution.weiDao.clear();
            solution.zhongDao.clear();
            int num = 0;
            for(Card ii:specialList){
                Card tt = new Card(ii.type, ii.rank);
                if(num<5){
                    Poker tempPoker = new Poker();
                    tempPoker.pokerPoint = tt.rank;
                    tempPoker.pokerColor = tt.type;
                    exchangeToPokerStyle(tempPoker);
                    /**
                     * 为了保证从小到大的顺序，每次都将元素插入链表头部
                     */
//                    solution.weiDao.add(tempPoker);
                    solution.weiDao.add(0, tempPoker);
                }else if(num<10){
                    Poker tempPoker = new Poker();
                    tempPoker.pokerPoint = tt.rank;
                    tempPoker.pokerColor = tt.type;
                    exchangeToPokerStyle(tempPoker);
//                    solution.zhongDao.add(tempPoker);
                    solution.zhongDao.add(0, tempPoker);
                }else{
                    Poker tempPoker = new Poker();
                    tempPoker.pokerPoint = tt.rank;
                    tempPoker.pokerColor = tt.type;
                    exchangeToPokerStyle(tempPoker);
//                    solution.touDao.add(tempPoker);
                    solution.touDao.add(0, tempPoker);
                }
                num++;
                specialName = "not";
            }
            solutionList.add(solution);
        }else {
            Out.getAllChoice(p);

            for (int i = 0; i < Out.allChoice.size(); i++) {

                //testOut(Out.allChoice.get(i));

                getSolution(Out.allChoice.get(i));
            }
        }
    }

    public static void testOut(Choice c){
        for(int i = 0; i < c.head.size(); i++)
            System.out.print(c.head.get(i).rank+" ");
        System.out.println("");
        for(int j = 0; j < c.mid.size(); j++)
            System.out.print(c.mid.get(j).rank+" ");
        System.out.println("");
        for(int j = 0; j < c.end.size(); j++)
            System.out.print(c.end.get(j).rank+" ");
        System.out.println("");
    }

    private void addSpecial(PlayerByCloudFree p){
        int returnVal = SpecialCardType.checkIsSpecial(p);
        for(int i = 0; i < 13; i++){
            specialList[i] = new Card();
        }
        int kingNums = p.checkNumOfKings();
        while(kingNums>0){
            kingNums -= Out.In(p, kingNums);
        }
        SpecialCardType.dealHandCard(p);
        for(int i = 0; i < p.doubleCard.size(); i++){
            for(int j = 0; j < p.doubleCard.get(i).two.size(); j++){
                System.out.println(p.doubleCard.get(i).two.get(j).rank);
            }
        }
        switch (returnVal){
            case 0:
                return;
            case 1://
                SpecialCardType.analyzeAllType(p);
                SpecialCardType.analyzeLeftCard(p);
                int laz = 0;
                for(Card ii:p.head){
                    Card temp = new Card(ii.type, ii.rank);
                    specialList[laz++] = temp;
                }
                for(Card ii:p.mid){
                    Card temp = new Card(ii.type, ii.rank);
                    specialList[laz++] = temp;
                }
                for(Card ii:p.end){
                    Card temp = new Card(ii.type, ii.rank);
                    //specialList.add(temp);
                    specialList[laz++] = temp;
                }
                specialName = "三同花";
                p.allClear();
                SpecialCardType.reZero(p);
                break;
            case 2:
                SpecialCardType.analyzeTwoDuiZi(p);
                SpecialCardType.analyzeDuiZi(p);
                int laz2 = 0;
                for(Card ii:p.end){
                    Card temp = new Card(ii.type, ii.rank);
                    specialList[laz2++] = temp;
                }
                for(Card ii:p.mid){
                    Card temp = new Card(ii.type, ii.rank);
                    specialList[laz2++] = temp;
                }
                for(Card ii:p.head){
                    Card temp = new Card(ii.type, ii.rank);
                    specialList[laz2++] = temp;
                }
                specialName = "六对半";
                p.allClear();
                SpecialCardType.analyzeDuiZi(p);
                SpecialCardType.analyzeLeftCard(p);
                for(Card ii:p.end) {
                    Card temp = new Card(ii.type, ii.rank);
                    specialList[laz2++] = temp;
                }
                p.allClear();
                SpecialCardType.reZero(p);
                break;
            case 3:
                SpecialCardType.analyzeShunZi(p);
                SpecialCardType.analyzeLeftCard(p);
                specialName = "三顺子";
                int laz3=0;
                for(Card ii:p.end){
                    Card temp = new Card(ii.type, ii.rank);
                    specialList[laz3++] = temp;
                }

                for(Card ii:p.mid){
                    Card temp = new Card(ii.type, ii.rank);
                    specialList[laz3++] = temp;
                }

                for(Card ii:p.head){
                    Card temp = new Card(ii.type, ii.rank);
                    specialList[laz3++] = temp;
                }
                p.allClear();
                SpecialCardType.reZero(p);
                break;
            case 4:
                SpecialCardType.analyzeFour(p);
                //Out.In(p, p.checkNumOfKings());
                SpecialCardType.analyzeSanTiao(p);
                int laz4 = 0;
                if(p.end.size() == 3)
                    for(Card ii:p.end){
                        Card temp = new Card(ii.type, ii.rank);
                        specialList[laz4++] = temp;
                        //System.out.println(ii.type+"  "+ii.rank);
                    }
                else if(p.mid.size()==3){
                    for(Card pp:p.end){
                        SpecialCardType.cancleVis(p, pp);
                    }
                    for(Card ii:p.mid){
                        Card temp = new Card(ii.type, ii.rank);
                        specialList[laz4++] = temp;
                        //System.out.println(ii.type+"  "+ii.rank);
                    }
                }else if(p.head.size() == 3){

                    for(Card pp:p.end){
                        //System.out.println("afafafa"+pp.type+"  "+pp.rank);
                        SpecialCardType.cancleVis(p, pp);
                    }

                    for(Card pp:p.mid){
                        SpecialCardType.cancleVis(p, pp);
                    }
                    for(Card ii:p.head){
                        Card temp = new Card(ii.type, ii.rank);
                        specialList[laz4++] = temp;
                        //System.out.println(ii.type+"  "+ii.rank);
                    }
                }
                p.end.clear();
                p.head.clear();
                p.mid.clear();
                SpecialCardType.analyzeDuiZi(p);
                SpecialCardType.analyzeLeftCard(p);
                specialName = "五对三条";

                for(Card ii:p.end){
                    Card temp = new Card(ii.type, ii.rank);
                    specialList[laz4++] = temp;
                }

                for(Card ii:p.mid){
                    Card temp = new Card(ii.type, ii.rank);
                    specialList[laz4++] = temp;
                }

                for(Card ii:p.head){
                    Card temp = new Card(ii.type, ii.rank);
                    specialList[laz4++] = temp;
                }


                for(Card pp : specialList){
                    System.out.println(pp.type+"  "+pp.rank);
                }


                p.allClear();
                SpecialCardType.reZero(p);
                break;
            case 5:
                specialName = "四套三条";
                //Out.In(p, p.checkNumOfKings());
                SpecialCardType.analyzeSanTiao(p);
                int laz5 = 0;
                for(Card ii:p.end){
                    Card temp = new Card(ii.type, ii.rank);
                    specialList[laz5++] = temp;
                }

                for(Card ii:p.mid){
                    Card temp = new Card(ii.type, ii.rank);
                    specialList[laz5++] = temp;
                }

                for(Card ii:p.head){
                    Card temp = new Card(ii.type, ii.rank);
                    specialList[laz5++] = temp;
                }

                p.allClear();
                SpecialCardType.analyzeSanTiao(p);
                for(Card ii:p.end){
                    Card temp = new Card(ii.type, ii.rank);
                    specialList[laz5++] = temp;
                }
                p.end.clear();
                SpecialCardType.analyzeLeftCard(p);
                for(Card ii:p.end){
                    Card temp = new Card(ii.type, ii.rank);
                    specialList[laz5++] = temp;
                }
                p.allClear();
                SpecialCardType.reZero(p);
                break;
            case 6:
            case 7:
            case 8:
                p.handCard.sort(PlayerByCloudFree.cardRankComparatorFromBig);
                int laz6 = 0;
                for(Card ii:p.handCard){
                    Card temp = new Card(ii.type, ii.rank);
                    specialList[laz6++] = temp;
                }
                if(returnVal == 6) specialName = "凑一色";
                else if(returnVal == 7) specialName = "全小";
                else specialName = "全大";
                p.allClear();
                SpecialCardType.reZero(p);
                break;
            case 9:
                int laz9 = 0;
                SpecialCardType.analyzeFour(p);
                for(Card ii:p.end){
                    Card temp = new Card(ii.type, ii.rank);
                    specialList[laz9++] = temp;
                }
                for(Card ii:p.mid){
                    Card temp = new Card(ii.type, ii.rank);
                    specialList[laz9++] = temp;
                }
                p.allClear();
                SpecialCardType.analyzeFour(p);
                SpecialCardType.analyzeLeftCard(p);
                for(Card ii:p.end){
                    Card temp = new Card(ii.type, ii.rank);
                    specialList[laz9++] = temp;
                }
                p.allClear();
                specialName = "三分天下";
                SpecialCardType.reZero(p);
                break;
            case 10:
                specialName = "三同花顺";
                SpecialCardType.analyzeShunZi(p);
                SpecialCardType.analyzeLeftCard(p);
                int laz10 = 0;
                for(Card ii:p.end){
                    Card temp = new Card(ii.type, ii.rank);
                    specialList[laz10++] = temp;
                }

                for(Card ii:p.mid){
                    Card temp = new Card(ii.type, ii.rank);
                    specialList[laz10++] = temp;
                }

                for(Card ii:p.head){
                    Card temp = new Card(ii.type, ii.rank);
                    specialList[laz10++] = temp;
                }
                p.allClear();
                SpecialCardType.reZero(p);
                break;
            case 11:
                specialName = "十二皇族";
                int laz11 = 0;
                p.handCard.sort(PlayerByCloudFree.cardRankComparatorFromBig);
                for(Card ii:p.handCard){
                    Card temp = new Card(ii.type, ii.rank);
                    specialList[laz11++] = temp;
                }
                p.allClear();
                SpecialCardType.reZero(p);
                break;
            case 12:
                specialName = "一条龙";
                int laz12 = 0;
                p.handCard.sort(PlayerByCloudFree.cardRankComparatorFromBig);
                for(Card ii:p.handCard){
                    Card temp = new Card(ii.type, ii.rank);
                    specialList[laz12++] = temp;
                }
                p.allClear();
                SpecialCardType.reZero(p);
                break;
            case 13:
                specialName = "至尊青龙";
                int laz13 = 0;
                p.handCard.sort(PlayerByCloudFree.cardRankComparatorFromBig);
                for(Card ii:p.handCard){
                    Card temp = new Card(ii.type, ii.rank);
                    specialList[laz13++] = temp;
                }
                p.allClear();
                SpecialCardType.reZero(p);
                break;
        }
    }
}

