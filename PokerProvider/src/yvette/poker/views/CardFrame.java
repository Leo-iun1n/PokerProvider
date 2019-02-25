package yvette.poker.views;

import yvette.poker.CardManager;
import yvette.poker.PokerCard;

//import javax.smartcardio.Card;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Color;

public class CardFrame extends JFrame implements MouseMotionListener, MouseListener {
    public static final int WIDTH = 1015;
    public static final int HEIGHT = 750;

    public static final int x_left = 20; // 最左边的空白距离
    public static final int y_top = 20; // 顶端的空白距离
    public static final int x_card_gap = 25; // 同一花色的牌和牌的距离
    public static final int y_card_gap = 40; // 不同花色的牌和牌的距离

    public SystemState systemState;//系统状态

    public enum SystemState{
        SELECTING,
        SELECTED
    }

    JPanel mainPanel;
    ResultPanel resultPanel;
    CalculateButton calculateButton;
    RestartButton restartButton;
    PokerCard backCard;

    CardManager cardManager = new CardManager();

    public CardFrame() throws HeadlessException {
        super("poker");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(WIDTH, HEIGHT));
        this.setLayout(null);
        this.systemState = SystemState.SELECTING;

        /**
         * 为了防止感应区域向左上角偏移，不能直接往 Frame 里加控件，需要先在里面放一个 JPanel 作为容器，再往容器里加控件
         */
        mainPanel = new JPanel(null);
        mainPanel.setSize(WIDTH, HEIGHT);
        mainPanel.setBackground(new Color(46,139,97));
        this.add(mainPanel);
        this.registerListener();
        this.setResizable(false);
//        this.addMouseMotionListener(this);

        /**
         * calculateButton
         */

        resultPanel = new ResultPanel(cardManager, this);
        mainPanel.add(resultPanel);

        calculateButton = new CalculateButton(cardManager, this);
        calculateButton.setBounds(336, 650, 140, 40);
        mainPanel.add(calculateButton);

        restartButton = new RestartButton(cardManager, this);
        restartButton.setBounds(536, 650, 140, 40);
        mainPanel.add(restartButton);

        // backCard
        backCard = new PokerCard(cardManager, 5, 0, CardManager.getProvider().getPokerBufferedImage(5, 0));
        backCard.setStartLocation(-backCard.WIDTH, y_top);
        mainPanel.add(backCard);

        PokerCard tempCard;
        int x, y;
        for (int i = 0; i < 5; i++) {
            y = (PokerCard.HEIGHT + y_card_gap) * (i / 2) + y_top;

            if (i < 4) { // 四行四种花色的牌
                for (int j = 12; j >= 0; j--) {
                    x = x_left + x_card_gap * j + (i % 2) * (13 * x_card_gap + PokerCard.WIDTH * 2);
                    tempCard = cardManager.getAllCards()[i][j];
                    tempCard.setStartLocation(x, y);
//                    tempCard.setBounds(x, y, PokerCard.WIDTH, PokerCard.HEIGHT);
                    mainPanel.add(tempCard);
                }
            } else { // 鬼牌
                for (int j = 1; j >= 0; j--) {
                    x = x_left + x_card_gap * j;
                    tempCard = cardManager.getGhostCards()[j];
                    tempCard.setStartLocation(x, y);
//                    tempCard.setBounds(x, y, PokerCard.WIDTH, PokerCard.HEIGHT);
                    mainPanel.add(tempCard);
                }
            }
        }

        this.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * 鼠标单击后释放会调用的响应函数
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (cardManager.getFocusedCard() == null) return;

        switch (cardManager.getFocusedCard().getLocation_type()) {
            case ABOVE:
                cardManager.addFocusedCardToSelectedList();
                break;
            case SELECTED:
                cardManager.removeFocusedCardFromSelectedList();
                break;
        }

        /**
         * 如果不作处理，且点击后鼠标不移动，则当前的 focusedCard 信息不会更新
         * 因此手动调用 鼠标移动 的响应函数
         */
        mouseMoved(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        PokerCard focusedCard = getFocusedCard(x, y);

        if (null != focusedCard) { //获取到了一张牌的焦点

            if (focusedCard.getLocation_type() == PokerCard.LOCATION_TYPE.SELECTED) { // 获取到了状态为 SELECTED 的牌的焦点
                cardManager.setFocusedCard(focusedCard);
                return;
            }

            // 以下为非 SELECTED 状态的牌的情况

            if (focusedCard != cardManager.getFocusedCard()) {//该牌之前没有获取焦点

                focusedCard.moveAbove(y_card_gap / 2);
                cardManager.setFocusedCard(focusedCard);

                if (focusedCard.getPokerColor() != 4) {//该牌不是鬼牌
                    PokerCard tempCard;
                    for (int i = focusedCard.getPokerPoint() + 1; i < 13; i++) {
                        tempCard = cardManager.getAllCards()[focusedCard.getPokerColor()][i];
                        tempCard.moveRight();
                    }
                    for (int i = focusedCard.getPokerPoint() - 1; i >= 0; i--) {
                        tempCard = cardManager.getAllCards()[focusedCard.getPokerColor()][i];
                        tempCard.moveToStartPoint();
                    }
                } else { //该牌为鬼牌
                    PokerCard tempCard;
                    if (focusedCard.getPokerPoint() == 0) {
                        tempCard = cardManager.getGhostCards()[1];
                        tempCard.moveRight();
                    } else {
                        tempCard = cardManager.getGhostCards()[0];
                        tempCard.moveToStartPoint();
                    }
                }
            }
        } else { // 没有进入任何一张牌的感应区域
            cardManager.setFocusedCard(null);

            PokerCard tempCard;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 13; j++) {
                    tempCard = cardManager.getAllCards()[i][j];
                    tempCard.moveToStartPoint();
                }
            }
            for (int i = 0; i < 2; i++) {
                tempCard = cardManager.getGhostCards()[i];
                tempCard.moveToStartPoint();
            }
        }
    }

    /**
     * 现在这个函数不仅会检测每张牌的感应区域，最后还会检测selectedList的感应区域了
     * 因此在检测每张牌的普通感应区域时，如果一张牌符合普通感应的要求，但它的状态为 SELECTED，此时应该判定为未检测
     */
    private PokerCard getFocusedCard(int x, int y) {
        for (int i = 0; i < 4; i++) {
            //if (y >= y_top + i * (y_card_gap + PokerCard.HEIGHT) && y < y_top + i * (y_card_gap + PokerCard.HEIGHT) + PokerCard.HEIGHT) {
            if (y >= cardManager.getAllCards()[i][0].getStart_y() && y < cardManager.getAllCards()[i][0].getStart_y() + PokerCard.HEIGHT) {

                /**
                 * 原本只有最右边一张牌的 sensingxArea = PokerCard.WIDTH ，现在所有牌都为牌的宽度
                 * 这么做的原因是，在判断当前鼠标位置处于横向的哪一张牌的时候，从右往左判断 (下面的循环 j 从 12 到 0)
                 * 这样可以让右边的（看上去更顶层的）牌优先被判断是否被选中
                 * 同时如果有牌被添加到了 selectedList 中，则它即使处于判定区域也会被无视 (下面增加了对它的 Location_type 的判断)
                 * 使它左边的牌获得更大的判定区域
                 */

                for (int j = 12; j >= 0; j--) {
                    PokerCard tempCard = cardManager.getAllCards()[i][j];
                    if (x >= tempCard.getStart_x() && x < tempCard.getStart_x() + tempCard.getSensingxArea()) {
                        if (tempCard.getLocation_type() != PokerCard.LOCATION_TYPE.SELECTED) // 找到的扑克牌不是 selected
                            return tempCard;
                    }
                }
            } else continue;
        }

        int i = 4;
        if (y >= cardManager.getGhostCards()[0].getStart_y() && y < cardManager.getGhostCards()[0].getStart_y() + PokerCard.HEIGHT) {
            for (int j = 1; j >= 0; j--) {
                PokerCard tempCard = cardManager.getGhostCards()[j];
                if (x >= tempCard.getStart_x() && x < tempCard.getStart_x() + tempCard.getSensingxArea()) {
                    if (tempCard.getLocation_type() != PokerCard.LOCATION_TYPE.SELECTED)
                        return tempCard;
                }
            }
        }

        /**
         * 新增的检测selectedList的感应部分
         */
        if (y >= y_top + 3 * (PokerCard.HEIGHT + y_card_gap) && y < y_top + 3 * (PokerCard.HEIGHT + y_card_gap) + PokerCard.HEIGHT) {
            for (int j = 0; j < cardManager.getSelectedList().size(); j++) {
                PokerCard tempCard = cardManager.getSelectedList().get(j);
                if (x >= x_left + PokerCard.WIDTH * j && x < x_left + PokerCard.WIDTH * (j + 1)) {
                    if (tempCard.getLocation_type() == PokerCard.LOCATION_TYPE.SELECTED) {
                        return tempCard;
                    } else
                        return null;
                }
            }
        }
        return null;
    }

    public void registerListener() {
        unregisterListener();
        this.mainPanel.addMouseListener(this);
        this.mainPanel.addMouseMotionListener(this);
    }

    public void unregisterListener() {
        this.mainPanel.removeMouseListener(this);
        this.mainPanel.removeMouseMotionListener(this);
    }

    public void setCalculateButtonEnable(boolean ifEnable){
        calculateButton.setEnabled(ifEnable);
    }

    public void setRestartButtonEnable(boolean ifEnable){
        restartButton.setEnabled(ifEnable);
    }

}