package yvette.poker.views;

import yvette.poker.CardManager;
import yvette.poker.PokerCard;
import sanShuiAlgorithm.DataCorrespond;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by windness on 2017/10/30.
 */
public class CalculateButton extends BaseButton implements ActionListener, Runnable{
    private CardManager cardManager;
    private CardFrame frame;
//    private DataCorrespond dataCorrespond;
//    private boolean ifClicked;

    public CalculateButton(CardManager cardManager, CardFrame frame) {
        this.frame = frame;
        this.cardManager = cardManager;
//        this.ifClicked = false;
//        this.frame.systemState = CardFrame.SystemState.SELECTING;
        this.setText("计算给牌方案");

        this.addActionListener(this);
    }

    private void calculate() {
        frame.resultPanel.generateCards();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.frame.systemState == CardFrame.SystemState.SELECTING) {
            if (cardManager.getSelectedList().size() == CardManager.max_selected) {
                calculate();
                this.frame.systemState = CardFrame.SystemState.SELECTED;

                frame.unregisterListener();
//                frame.mainPanel.removeMouseListener(frame);
//                frame.mainPanel.removeMouseMotionListener(frame);

                Thread t1 = new Thread(this);
                t1.start();
            }
        } else { // SELECTED
            System.out.println("SELECTED");
            if (frame.resultPanel.isVisible()) { // resultPanel可见，说明正在显示一页结果，此时按钮为跳转下一页
                System.out.println("jump");

                //将两个按钮设置为不可用
                frame.setCalculateButtonEnable(false);
                frame.setRestartButtonEnable(false);

                frame.resultPanel.showResults();
            }
        }
    }


    /**
     * 点击后的动画效果
     */
    @Override
    public void run() {
//        for (int i = 0; i < cardManager.getSelectedList().size(); i++) {
//            cardManager.getSelectedList().get(i).moveFromSelectedList();
//            try {
//                Thread.sleep(40);
//            } catch (Exception e) {}
//        }

        for (int i = 0; i < 54; i++) {

            PokerCard tempCard;
            if (i < 52) {
                tempCard = cardManager.getAllCards()[i%4][i/4];
                if (tempCard.getLocation_type() != PokerCard.LOCATION_TYPE.SELECTED) {
                    tempCard.moveTogether();
                }
            } else {
                tempCard = cardManager.getGhostCards()[i-52];
                if  (tempCard.getLocation_type() != PokerCard.LOCATION_TYPE.SELECTED) {
                    tempCard.moveTogether();
                }
            }
            try {
                Thread.sleep(20);
            } catch (Exception e) {}
        }

        try {
            Thread.sleep(400);
        } catch (Exception e) {}

        frame.backCard.moveTo(frame.x_left, frame.y_top, 300);

        try {
            Thread.sleep(300);
        } catch (Exception e) {}

        frame.resultPanel.showResults();
        this.setText("下一页");

    }
}
