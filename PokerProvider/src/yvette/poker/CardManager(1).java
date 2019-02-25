package yvette.poker; /**
 * Created by hasee on 2017/10/28.
 */

import java.lang.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CardManager {
    /**
     * selectedList 的最大容量
     */
    public static final int max_selected = 13;

    private PokerCard[][] allCards = new PokerCard[4][13];
    private PokerCard[] ghostCards = new PokerCard[2];
    private List<PokerCard> animList;
    /**
     * 当前被选中的扑克牌List、当前正在获取焦点的扑克牌
     */
    private List<PokerCard> selectedList;
    private PokerCard focusedCard;

    private MyThread t1;

    private static PokerImageProvider provider = new PokerImageProvider();
    public static PokerImageProvider getProvider() { return provider; }

    public CardManager() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                allCards[i][j] = new PokerCard(this, i, j, provider.getPokerBufferedImage(i, j));
                /**
                 * 现在不需要为其规定 sensingxArea
                 * 现在所有的牌的 sensingxArea 都为 WIDTH
                 * 参见 PokerCard 的构造函数
                 */
//                if(j==12) allCards[i][j].setSensingxArea();
            }
        }

        for (int i = 0; i < 2; i++) {
            ghostCards[i] = new PokerCard(this, 4, i, provider.getPokerBufferedImage(4, i));
//            if(i==1) ghostCards[1].setSensingxArea();
        }

        animList = new ArrayList<PokerCard>();
        selectedList = new ArrayList<PokerCard>();
        focusedCard = null;

        t1 = new MyThread();
        t1.start();
    }

    public void addCardToAnimList(PokerCard tempCard) {
        if (!animList.contains(tempCard)) {
            animList.add(tempCard);
        }
    }

    public void removeCardFromAnimList(PokerCard tempCard) {
        if (animList.contains(tempCard)) {
            animList.remove(tempCard);
        }
    }

    /**
     * 将当前的 focusedCard 加入到 selectedList 中去
     */
    public void addFocusedCardToSelectedList() {
        if (focusedCard == null) return;
        if (selectedList.size() == max_selected) return;

        PokerCard pokerCard = focusedCard;
        if (!selectedList.contains(pokerCard)) {
//            int index = selectedList.size();
//            selectedList.add(pokerCard);
//            pokerCard.moveToSelectedList(index);

            /**
             * 从小到大排序
             *
             * 0 1 2 4 5 add(3)
             * 0 1 2 3 4 5 index = 3
             */
            selectedList.add(pokerCard);
            selectedList.sort(new Comparator<PokerCard>() {
                @Override
                public int compare(PokerCard o1, PokerCard o2) {
                    return o1.getPokerPoint() - o2.getPokerPoint();
                }
            });
            int index = selectedList.indexOf(pokerCard);
            for (int i = index; i < selectedList.size(); i++) {
                pokerCard = selectedList.get(i);
                pokerCard.moveToSelectedList(i);
            }

        }
    }

    /**
     * 将当前的 focusedCard 移出 selectedList
     */
    public void removeFocusedCardFromSelectedList() {
        if (focusedCard == null) return;

        if (selectedList.contains(focusedCard)) {
            int index = selectedList.indexOf(focusedCard);

            /**
             * 移除第 index 个元素，需要将 index + 1 ~ size() - 1 这些元素对应的图片向前移动一格
             */
            for (int i = index + 1; i < selectedList.size(); i++) {
                selectedList.get(i).moveToSelectedList(i-1);
            }

            selectedList.remove(index);
            focusedCard.moveFromSelectedList();
        }
    }

    /**
     * 修改所有牌的状态为收起
     */
    public void addAllCardToPutAwayList(){
        for(int i=0;i<4;i++){
            for(int j=0;j<13;j++){
                allCards[i][j].resetToStartState();
            }
        }
        for(int i=0;i<2;i++){
            ghostCards[i].resetToStartState();
        }
    }

    /**
     * 将当前的 所有card恢复到起始位置
     */
    public void moveAllCardToStartPoint(){
        for(int i=0;i<4;i++){
            for(int j=0;j<13;j++){
                allCards[i][j].moveToStartPoint();
            }
        }
        for(int i=0;i<2;i++){
            ghostCards[i].moveToStartPoint();
        }
    }

    public void resetSelectedInfo() { // 清理selected信息
        selectedList.clear();
        focusedCard = null;
    }

    public void setButtonEnable(){ //设置按钮不可用

    }


    class MyThread extends Thread {
        public void run() {
            while (true) {
                for (int i = animList.size() - 1; i >= 0; i--) {
                    animList.get(i).anim();
                }
                try {
                    Thread.sleep(15);
                } catch (Exception e) {}
            }
        }
    }

    public PokerCard[][] getAllCards() { return allCards; }

    public PokerCard[] getGhostCards() { return ghostCards; }

    public List<PokerCard> getAnimList() { return animList; }

    public List<PokerCard> getSelectedList() { return selectedList; }

    public PokerCard getFocusedCard() { return focusedCard; }

    public void setFocusedCard(PokerCard focusedCard) { this.focusedCard = focusedCard; }
}
