package yvette.poker;

import yvette.poker.views.CardFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by hasee on 2017/10/28.
 */
public class PokerCard extends JLabel {
    public static final int WIDTH = 75;
    public static final int HEIGHT = 120;

    /**
     * 这些属性和 UI 排版相关，不应该是 PokerCard 的属性，应该放在 CardFrame 中
     * 使用时如 CardFrame.x_left
     */
//    public static final int x_left = 20; // 最左边的空白距离
//    public static final int y_top = 20; // 顶端的空白距离
//    public static final int x_card_gap = 25; // 同一花色的牌和牌的距离
//    public static final int y_card_gap = 40; // 不同花色的牌和牌的距离

    private int pokerPoint; //数值
    private int pokerColor;//花色

    private int start_x, start_y; // 每张牌的初始位置（不变）
    private int sx, sy; //动画起始点所在位置
    private int dx, dy;//终点位置
    private long animStartTime;//动画起始时间
    private int animTime;//动画持续时间
    private int sensingxArea; // 每张牌的sensingxArea都为其宽度
    private MoveDis myDis = new MoveDis();
    private CardManager cardManage;

    public enum LOCATION_TYPE {
        START_POINT,
        ABOVE,
        RIGHT,
        SELECTED
    }

    private LOCATION_TYPE location_type;

    public LOCATION_TYPE getLocation_type() {
        return location_type;
    }


    public PokerCard(CardManager cardManager, int pokerColor, int pokerPoint, Image image) {
        this.cardManage = cardManager;
        this.pokerColor = pokerColor;
        this.pokerPoint = pokerPoint;
        /**
         * 默认的 sensingxArea 不再是 x_card_gap
         */
//        this.sensingxArea=x_card_gap;
        this.sensingxArea = WIDTH;

        sx = this.getX();
        sy = this.getY();
        location_type = LOCATION_TYPE.START_POINT;

        Image scaledImage = image.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);

        this.setIcon(new ImageIcon(scaledImage));
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public void setStartLocation(int sx, int sy) {
        this.start_x = sx;
        this.start_y = sy;
        this.setBounds(sx, sy, PokerCard.WIDTH, PokerCard.HEIGHT);
    }
    /*
    reset
     */
    public void resetToStartState(){
        moveTo(start_x, start_y, 200);
        location_type = LOCATION_TYPE.START_POINT;
    }

    public int getPokerPoint() {
        return pokerPoint;
    }

    public int getPokerColor() {
        return pokerColor;
    }

    public int getStart_x() {
        return start_x;
    }

    public int getStart_y() {
        return start_y;
    }

    public int getSensingxArea() {
        return sensingxArea;
    }

//    public void setSensingxArea(){ sensingxArea=WIDTH; }

    public void moveTo(int dx, int dy, int animTime) {
        animStartTime = System.currentTimeMillis();
        this.sx = this.getX();
        this.sy = this.getY();
        this.dx = dx;
        this.dy = dy;
        this.animTime = animTime;
        cardManage.addCardToAnimList(this);
    }


    public void moveAbove(int y_dis) {
        switch (location_type) {
            case START_POINT:
            case RIGHT:
                moveTo(start_x, start_y - y_dis, 200);
                location_type = LOCATION_TYPE.ABOVE;
                break;
            case ABOVE:
                break;
        }

    }

    public void moveRight() {
        switch (location_type) {
            case START_POINT:
            case ABOVE:
                moveTo(start_x + WIDTH, start_y, 300);
                location_type = LOCATION_TYPE.RIGHT;
                break;
            case RIGHT:
                break;
        }

    }

    public void moveToStartPoint() {
        switch (location_type) {
            case START_POINT:
                break;
            case ABOVE:
            case RIGHT:
                moveTo(start_x, start_y, 200);
                location_type = LOCATION_TYPE.START_POINT;
                break;
        }

    }

    /**
     * 从最下方的选中牌序列中移动到原来的位置
     */
    public void moveFromSelectedList() {
        moveTo(start_x, start_y, 400);
        location_type = LOCATION_TYPE.START_POINT;
    }

    /**
     * 移动到最下方的选中的牌序列的第 index 号位置
     */
    public void moveToSelectedList(int index) {
        moveTo(CardFrame.x_left + index * (WIDTH), CardFrame.y_top + 3 * (HEIGHT + CardFrame.y_card_gap), 400);
        location_type = LOCATION_TYPE.SELECTED;
    }

    /**
     * 所有牌移动到相同的左上角位置
     */
    public void moveTogether() {
        switch (location_type) {
            case SELECTED:
//                moveTo(start_x + CardFrame.WIDTH, start_y, 1000);
//                break;
            case START_POINT:
            case RIGHT:
            case ABOVE:
//                moveTo(start_x - CardFrame.WIDTH, start_y, 1000);
                moveTo(CardFrame.x_left, CardFrame.y_top, 500);
                break;
        }

    }

    public void anim() {
        long currentTime = System.currentTimeMillis();
        int costTime = (int) (currentTime - animStartTime);

        if (costTime >= animTime) {
            // 当前使用时间超过了动画时间，应该立刻移动到终点
            this.setLocation(dx, dy);
            cardManage.removeCardFromAnimList(this);
        } else {
            // 还没到动画时间，计算位置
            myDis.getDistance(costTime);
            int x = myDis.getxDistance();//当前X坐标
            int y = myDis.getyDistance();
            this.setLocation(x, y);
        }
    }

    class MoveDis {
        private int xDis;
        private int yDis;

        public MoveDis() {
            xDis = yDis = 0;
        }

        public void getDistance(int costTime) {
            float myxSpeed = (dx - sx) / (float) animTime;
            float myySpeed = (dy - sy) / (float) animTime;
            this.xDis = (int) (PokerCard.this.sx + myxSpeed * costTime);
            this.yDis = (int) (PokerCard.this.sy + myySpeed * costTime);
        }

        public int getxDistance() {
            return xDis;
        }

        public int getyDistance() {
            return yDis;
        }
    }
}


