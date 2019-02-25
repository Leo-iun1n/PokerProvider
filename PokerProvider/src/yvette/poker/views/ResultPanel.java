package yvette.poker.views;

import yvette.poker.CardManager;
import yvette.poker.PokerCard;
import sanShuiAlgorithm.DataCorrespond;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by windness on 2017/10/30.
 */
public class ResultPanel extends JPanel implements Runnable{
    private static Map<String, String> typeToTextMap = new HashMap<>();
    public static void initTypeMap() {
        typeToTextMap.put("santiao", "三条");
        typeToTextMap.put("duizi", "对子");
        typeToTextMap.put("wulong", "乌龙");
        typeToTextMap.put("tonghuashun", "同花顺");
        typeToTextMap.put("tiezhi", "铁支");
        typeToTextMap.put("hulu", "葫芦");
        typeToTextMap.put("tonghua", "同花");
        typeToTextMap.put("shunzi", "顺子");
        typeToTextMap.put("liangdui", "两对");

        typeToTextMap.put("三同花", "三同花");
        typeToTextMap.put("六对半", "六对半");
        typeToTextMap.put("三顺子", "三顺子");
        typeToTextMap.put("五对三条", "五对三条");
        typeToTextMap.put("四套三条", "四套三条");
        typeToTextMap.put("凑一色", "凑一色");
        typeToTextMap.put("全小", "全小");
        typeToTextMap.put("全大", "全大");
        typeToTextMap.put("三分天下", "三分天下");
        typeToTextMap.put("三同花顺", "三同花顺");
        typeToTextMap.put("十二皇族", "十二皇族");
        typeToTextMap.put("一条龙", "一条龙");
        typeToTextMap.put("至尊青龙", "至尊青龙");
    }

    CardManager cardManager;
    CardFrame frame;
    DataCorrespond dataCorrespond;
    List<DataCorrespond.PokerSolution> solutionList;

    public static final int x_gap_card = CardFrame.x_card_gap;
    public static final int x_gap_group = CardFrame.x_card_gap;
    public static final int x_group1st = CardFrame.x_left + PokerCard.WIDTH * 2;
    public static final int x_group2nd = x_group1st + 2 * x_gap_card + PokerCard.WIDTH + x_gap_group;
    public static final int x_group3rd = x_group2nd + 4 * x_gap_card + PokerCard.WIDTH + x_gap_group;
    public static final int y_top = CardFrame.y_top;
    public static final int y_gap = CardFrame.y_card_gap;

    List<PokerCard[]> resultPokerCards;
    int rowPointer = 0;
    int maxRows = 3;
//    int rows;

    public ResultPanel(CardManager cardManager, CardFrame frame) {
        this.setLayout(null);
        this.cardManager = cardManager;
        this.frame = frame;
        this.setBounds(100, 0, frame.WIDTH, 600);
//        this.setBackground(java.awt.Color.gray);
        this.setOpaque(false);

        resultPokerCards = new ArrayList<>();

        this.setVisible(false);
//        generateCards();
    }

    public void generateCards() {

        PokerCard tempCard;
        PokerCard[] cards;
        /**yvette
         * 获取方案
         */
        dataCorrespond = new DataCorrespond();
        List<DataCorrespond.Poker> pokerList = new ArrayList<>();
        for(int i = 0; i < 13; i++){
            DataCorrespond.Poker algorithmPoker= new DataCorrespond.Poker(cardManager.getSelectedList().get(i));
            pokerList.add(algorithmPoker);
        }
        dataCorrespond.superChange(pokerList);
        solutionList = dataCorrespond.solutionList;
        DataCorrespond.Poker tempPoker;
        for(int i = 0; i < solutionList.size(); i++){
            cards = new PokerCard[13];
            DataCorrespond.PokerSolution tempSolution = solutionList.get(i);

//            for (int j = 0; j < 3; j++) {
//                tempPoker = tempSolution.touDao.get(j);
//                System.out.println("card = ( " + tempPoker.pokerColor + ", " + tempPoker.pokerPoint + " )");
//            }
//
//            for (int j = 0; j < 5; j++) {
//                tempPoker = tempSolution.zhongDao.get(j);
//                System.out.println("card = ( " + tempPoker.pokerColor + ", " + tempPoker.pokerPoint + " )");
//            }
//
//            for (int j = 0; j < 5; j++) {
//                tempPoker = tempSolution.weiDao.get(j);
//                System.out.println("card = ( " + tempPoker.pokerColor + ", " + tempPoker.pokerPoint + " )");
//            }

            for(int j = 0; j < 13; j++) {
                if (j < 3) {
                    tempPoker = tempSolution.touDao.get(j);
                    cards[j] = new PokerCard(cardManager, tempPoker.pokerColor, tempPoker.pokerPoint,
                            CardManager.getProvider().getPokerBufferedImage(tempPoker.pokerColor,tempPoker.pokerPoint));
                } else if (j < 8) {
                    tempPoker = tempSolution.zhongDao.get(j-3);
                    cards[j] = new PokerCard(cardManager, tempPoker.pokerColor, tempPoker.pokerPoint,
                            CardManager.getProvider().getPokerBufferedImage(tempPoker.pokerColor,tempPoker.pokerPoint));
                } else {
                    tempPoker = tempSolution.weiDao.get(j-8);
                    cards[j] = new PokerCard(cardManager, tempPoker.pokerColor, tempPoker.pokerPoint,
                            CardManager.getProvider().getPokerBufferedImage(tempPoker.pokerColor,tempPoker.pokerPoint));
                }

            }
            resultPokerCards.add(cards);
//            System.out.println("cards.length = " + cards.length);
        }
//        System.out.println("results.size() = " + resultPokerCards.size());

    }

    public void showResults() {
        if (resultPokerCards.size() == 0) return;

        this.removeAll();

        if (rowPointer >= resultPokerCards.size()) { // 从头播放
            rowPointer = 0;
        }

        PokerCard[] cards;
        PokerCard tempCard;

        for (int i = 0; i < maxRows && rowPointer != resultPokerCards.size(); i++, rowPointer++) {
            cards = resultPokerCards.get(rowPointer);



            for (int j = 12; j >= 0; j--) {
                tempCard = cards[j];
//                System.out.println()
                if (j < 3) {
                    tempCard.setStartLocation(x_group1st + j * x_gap_card, y_top + i * (y_gap + PokerCard.HEIGHT));
//                    tempCard.setLocation(x_group1st + j * x_gap_card, y_top + i * (y_gap + PokerCard.HEIGHT) - CardFrame.HEIGHT);
                    tempCard.setLocation(x_group1st + CardFrame.WIDTH + j * x_gap_card, y_top + i * (y_gap + PokerCard.HEIGHT));
                } else if (j < 8) {
                    tempCard.setStartLocation(x_group2nd + (j-3) * x_gap_card, y_top + i * (y_gap + PokerCard.HEIGHT));
//                    tempCard.setLocation(x_group2nd + (j-3) * x_gap_card, y_top + i * (y_gap + PokerCard.HEIGHT) - CardFrame.HEIGHT);
                    tempCard.setLocation(x_group2nd + (j-3) * x_gap_card + CardFrame.WIDTH , y_top + i * (y_gap + PokerCard.HEIGHT));
                } else {
                    tempCard.setStartLocation(x_group3rd + (j-8) * x_gap_card, y_top + i * (y_gap + PokerCard.HEIGHT));
//                    tempCard.setLocation(x_group3rd + (j-8) * x_gap_card, y_top + i * (y_gap + PokerCard.HEIGHT) - CardFrame.HEIGHT);
                    tempCard.setLocation(x_group3rd + (j-8) * x_gap_card + CardFrame.WIDTH , y_top + i * (y_gap + PokerCard.HEIGHT));
                }
                this.add(tempCard);
            }
        }

        int currentPage = (rowPointer - 1) / 3 + 1;
        int allPage = (resultPokerCards.size() % 3 > 0 ? 1 : 0) + resultPokerCards.size() / 3;
        CardInfoTextField numberInfo = new CardInfoTextField(currentPage + "/" + allPage + "页");
        numberInfo.setBounds(CardFrame.x_left, y_top + y_gap + PokerCard.HEIGHT, PokerCard.WIDTH, y_gap);
        this.add(numberInfo);

       // System.out.println("views size = " + this.getComponentCount());

        this.updateUI();
        this.setVisible(true);

        System.out.print("resultPokerCards.size():" + resultPokerCards.size());
        Thread t1 = new Thread(this);
        t1.start();



    }

    @Override
    public void run() {
        PokerCard[] cards;
        PokerCard tempCard;
        int pageNum = 0;
        int tempPoint = rowPointer;
        if(rowPointer > 3){
            pageNum = rowPointer/3;
            tempPoint = rowPointer - 3 * pageNum;
        }
        for (int i = 0; i < tempPoint; i++) {
            int tempCardNum = i;
            tempCardNum = i + 3 * pageNum;
            cards = resultPokerCards.get(tempCardNum);

            DataCorrespond.PokerSolution solution = solutionList.get(tempCardNum);

            if (solution.allInfo == null) {
                for (int j = 0; j <= 12; j++) {
                    tempCard = cards[j];
                    if (j < 3) {
                        tempCard.moveTo(x_group1st + j * x_gap_card,y_top + i * (y_gap + PokerCard.HEIGHT),60);
                    } else if (j < 8) {
                        tempCard.moveTo(x_group2nd + (j-3) * x_gap_card, y_top + i * (y_gap + PokerCard.HEIGHT),60);
                    } else {
                        tempCard.moveTo(x_group3rd + (j-8) * x_gap_card, y_top + i * (y_gap + PokerCard.HEIGHT),60);
                    }
                    try {
                        Thread.sleep(90);
                    } catch (Exception e) {}
                }
            } else {
                for (int j = 0; j <= 12; j++) {
                    tempCard = cards[j];
                    tempCard.moveTo(x_group1st + PokerCard.WIDTH + j * x_gap_card, y_top + i * (y_gap + PokerCard.HEIGHT),60);
                }
                try {
                    Thread.sleep(90);
                } catch (Exception e) {}
            }

//            for (int j = 0; j <= 12; j++) {
//                tempCard = cards[j];
//                if (j < 3) {
//                    tempCard.moveTo(x_group1st + j * x_gap_card,y_top + i * (y_gap + PokerCard.HEIGHT),60);
//                } else if (j < 8) {
//                    tempCard.moveTo(x_group2nd + (j-3) * x_gap_card, y_top + i * (y_gap + PokerCard.HEIGHT),60);
//                } else {
//                    tempCard.moveTo(x_group3rd + (j-8) * x_gap_card, y_top + i * (y_gap + PokerCard.HEIGHT),60);
//                }
//                try {
//                    Thread.sleep(90);
//                } catch (Exception e) {}
//            }


//            if (solution.touDaoInfo != null)
//                System.out.println("1: " + solution.touDaoInfo);
//            if (solution.zhongDaoInfo != null)
//                System.out.println("2: " + solution.zhongDaoInfo);
//            if (solution.weiDaoInfo != null)
//                System.out.println("3: " + solution.weiDaoInfo);
//            if (solution.allInfo != null)
//                System.out.println("3: " + solution.allInfo);
            if (solution.allInfo == null) {
                CardInfoTextField touDaoText = new CardInfoTextField(typeToTextMap.get(solution.touDaoInfo));
                CardInfoTextField zhongDaoText = new CardInfoTextField(typeToTextMap.get(solution.zhongDaoInfo));
                CardInfoTextField weiDaoText = new CardInfoTextField(typeToTextMap.get(solution.weiDaoInfo));

                touDaoText.setBounds(x_group1st, y_top + i * (y_gap + PokerCard.HEIGHT) + PokerCard.HEIGHT, PokerCard.WIDTH, y_gap);
                zhongDaoText.setBounds(x_group2nd, y_top + i * (y_gap + PokerCard.HEIGHT) + PokerCard.HEIGHT, PokerCard.WIDTH, y_gap);
                weiDaoText.setBounds(x_group3rd, y_top + i * (y_gap + PokerCard.HEIGHT) + PokerCard.HEIGHT, PokerCard.WIDTH, y_gap);

                this.add(touDaoText);
                this.add(zhongDaoText);
                this.add(weiDaoText);
            } else { // allInfo
                Font font = new Font("微软雅黑", Font.BOLD, 18);
                Color color = new Color(255, 255, 100);
                CardInfoTextField allInfoText = new CardInfoTextField(typeToTextMap.get(solution.allInfo), font, color);

                allInfoText.setBounds(x_group2nd, y_top + i * (y_gap + PokerCard.HEIGHT) + PokerCard.HEIGHT, PokerCard.WIDTH * 2, y_gap);

                this.add(allInfoText);
            }
            this.updateUI();

        }

        frame.setCalculateButtonEnable(true);
        frame.setRestartButtonEnable(true);
    }

    public void reset() {
        this.setVisible(false);
        resultPokerCards.clear();
        rowPointer = 0;
    }
}
