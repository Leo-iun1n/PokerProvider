package yvette.poker.views;

import yvette.poker.CardManager;
import yvette.poker.PokerCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by hasee on 2017/10/31.
 */
public class RestartButton extends BaseButton implements ActionListener,Runnable{
    private CardManager cardManager;
    private CardFrame frame;

    public RestartButton(CardManager cardManager, CardFrame frame){
        this.frame = frame;
        this.cardManager = cardManager;
        this.setText("重  置");

        this.addActionListener(this);
    }

//    public void restartButtonEnable(boolean ifEnable){
//        this.setEnabled(ifEnable);
//    }

//    @Override
    public void actionPerformed(ActionEvent e) {
        frame.resultPanel.reset();
        frame.systemState = CardFrame.SystemState.SELECTING;
        cardManager.resetSelectedInfo();
        frame.registerListener();
        Thread t1 = new Thread(this);
        t1.start();
    }

//    @Override
    public void run() {
        frame.backCard.moveTo(-PokerCard.WIDTH, frame.y_top, 300);

//        for (int i = 0; i < cardManager.getSelectedList().size(); i++) {
//            cardManager.getSelectedList().get(i).moveFromSelectedList();
//            try {
//                Thread.sleep(40);
//            } catch (Exception e) {}
//        }

        try {
            Thread.sleep(300);
        } catch (Exception e) {}

        for (int i = 0; i < 54; i++) {
            PokerCard tempCard;
            if (i < 52) {
                tempCard = cardManager.getAllCards()[i%4][i/4];
                tempCard.resetToStartState();
                tempCard.moveToStartPoint();
            } else {
                tempCard = cardManager.getGhostCards()[i-52];
                tempCard.resetToStartState();
                tempCard.moveToStartPoint();
            }
            try {
                Thread.sleep(40);
            } catch (Exception e) {}
        }

        frame.calculateButton.setText("计算给牌方案");
    }
}

