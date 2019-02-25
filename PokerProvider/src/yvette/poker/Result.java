package yvette.poker;

import java.util.List;

public class Result {
    /**
     * solutionList 元素类型为PokerSolution
     *
     */
    List<PokerSolution> solutionList;

    static class PokerSolution{
        public Poker[] touDao = new Poker[3];
        public Poker[] zhongDao = new Poker[5];
        public Poker[] weiDao = new Poker[5];

        /**
         * 如果不是特殊牌型，设置前3个变量分别表示3道牌的名称，最后一个为null
         * 如果是特殊牌型，前3个变量为null，最后一个变量设置整体牌型名称
         */
        public String touDaoInfo = null;
        public String zhongDaoInfo = null;
        public String weiDaoInfo = null;
        public String allInfo = null;
    }

    static class Poker {
        /**
         * pokerColor表示花色：
         * 0 黑桃
         * 1 红桃
         * 2 梅花
         * 3 方块
         * 4 鬼牌 （pokerPoint: 0 小鬼， 1 大鬼）
         *
         * pokerPoint表示点数,0~12 分别表示 A~K
         */
        public int pokerColor;
        public int pokerPoint;
    }

}

/**
 * 我传给你的参数是Poker类型的长度为13的一个数组
 */


