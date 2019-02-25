package yvette.poker;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;


/**
 * Created by windness on 2017/10/27.
 */
public class PokerImageProvider {
    public enum POKER_TYPE {
        SPADE, /**
         * 黑桃
         **/
        HEART, /**
         * 红桃
         **/
        CLUB, /**
         * 梅花
         **/
        DIAMOND, /**
         * 方块
         **/
        GHOST, /**
         * 鬼牌
         **/
        BACK
    }

    private String picFilename;

    private final int row = 14;
    /**
     * 图片横向分为14列
     **/
    private final int col = 4;
    /**
     * 图片纵向分为4行
     **/

    private BufferedImage[][] pokers = new BufferedImage[row][col];
    private BufferedImage[] ghosts = new BufferedImage[2];

    public PokerImageProvider() {
//        this.picFilename = CardMain.getExePath() + "/poker3.jpg";
        this.picFilename = "poker3.jpg";
        splitPic();
    }

    public PokerImageProvider(String picFilename) {
        this.picFilename = picFilename;
        splitPic();
    }

    private void splitPic() {
        Image originalImage = null;
        try {
            originalImage = ImageIO.read(new File(picFilename));
        } catch (IOException e) {
        }

        if (null == originalImage) {
            System.out.println("error, originalImage == null");
            return;
        }

        int originalWidth, originalHeight;
        originalWidth = originalImage.getWidth(null);
        originalHeight = originalImage.getHeight(null);

        int gapWidth = 47;
        int gapHeight = 47;

        int fragmentWidth, fragmentHeight;
        fragmentWidth = originalWidth / row - gapWidth;
        fragmentHeight = originalHeight / col - gapHeight;

        BufferedImage eachBufferedImage;
        Graphics2D eachGraphics;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                pokers[i][j] = eachBufferedImage = new BufferedImage(fragmentWidth, fragmentHeight, BufferedImage.TYPE_INT_RGB);
                eachGraphics = (Graphics2D) eachBufferedImage.getGraphics();

                eachGraphics.drawImage(originalImage,
                        0, 0, fragmentWidth, fragmentHeight,
                        (fragmentWidth + gapWidth) * i, (fragmentHeight + gapHeight) * j, (fragmentWidth + gapWidth) * i + fragmentWidth, (fragmentHeight + gapHeight) * j + fragmentHeight,
                        null
                );
            }
        }

        Color color = new Color(255, 255, 255);
//        Font font = new Font();
        String strContents[] = {"KID", "GHOST"};
        for (int i = 0; i < 2; i++) {
            ghosts[i] = new BufferedImage(fragmentWidth, fragmentHeight, BufferedImage.TYPE_INT_RGB);
            eachGraphics = (Graphics2D) ghosts[i].getGraphics();
            eachGraphics.setColor(color);

            eachGraphics.drawString(strContents[i], fragmentWidth / 2, fragmentHeight / 2);
        }

    }

    /**
     * 鬼牌的 pokerPoint 只能为 0 或 1，分别代表 小鬼 和 大鬼
     *
     * @return 每张牌的 BufferedImage 图片
     */

    public BufferedImage getPokerBufferedImage2(POKER_TYPE pokerColor, int pokerPoint) {
        try {
            if (POKER_TYPE.GHOST == pokerColor) {
                if (pokerPoint < 0 || pokerPoint > 1) return null;
                return pokers[13][1 - pokerPoint];
            } else if (POKER_TYPE.BACK == pokerColor) {
                return pokers[13][3];
            } else {
                int y = 1; // 牌在原图上的y坐标
                switch (pokerColor) {
                    case SPADE:
                        y = 1;
                        break;
                    case HEART:
                        y = 0;
                        break;
                    case CLUB:
                        y = 3;
                        break;
                    case DIAMOND:
                        y = 2;
                        break;
                }
                return pokers[pokerPoint][y];
            }
        } catch (Exception e) {
            System.out.println("error: getPokerBufferedImage2(), pokerColor = " + pokerColor + ", pokerPoint = " + pokerPoint);
        }
        return null;
    }

    public BufferedImage getPokerBufferedImage(int pokerColor, int pokerPoint) {
        return getPokerBufferedImage2(POKER_TYPE.values()[pokerColor], pokerPoint);
    }

    public BufferedImage getPokerBufferedImage(POKER_TYPE pokerColor, int pokerPoint) {
        if (pokerColor == POKER_TYPE.GHOST) {
            return ghosts[pokerPoint];
        } else {
            int col = getCol(pokerPoint);
            int row = getRow(pokerColor.ordinal(), col);
            System.out.println("pokerColor = " + pokerColor + ", pokerPoint = " + pokerPoint + ", row = " + row + ", col = " + col);
            return pokers[row][col];

//            return pokers[pokerColor.ordinal()][getCol(pokerPoint)];
        }
    }

    /*
      point 1211100 9 8 7 6 5 4 3  2  1
      col   0 1 2 3 4 5 6 7 8 9 10 11 12
     */
    private int getCol(int pokerPoint) {
        if (pokerPoint == 0) {
            return 3;
        } else if (pokerPoint <= 9) {
            return 13 - pokerPoint;
        } else {
            return 12 - pokerPoint;
        }
    }


    private int row_ColAndColor[][] = {
            {3, 1, 2, 0},  /** K **/
            {3, 2, 1, 0},  /** Q **/
            {3, 2, 1, 0},  /** J **/
            {3, 0, 2, 1},  /** A **/
            {1, 2, 0, 3},  /**10 **/
            {3, 1, 0, 2},  /** 9 **/
            {0, 2, 3, 1},  /** 8 **/
            {3, 1, 0, 2},  /** 7 **/
            {0, 2, 3, 1},  /** 6 **/
            {3, 1, 0, 2},  /** 5 **/
            {0, 2, 3, 1},  /** 4 **/
            {1, 3, 0, 2},  /** 3 **/
            {0, 3, 2, 1},  /** 2 **/
    };

    /**
     * 返回原始图片的第col行中4张牌的pokerColor花色的牌的列号
     *
     * @param pokerColor
     * @param col
     * @return
     */
    private int getRow(int pokerColor, int col) {
        return row_ColAndColor[col][pokerColor];
    }


}

/*
{178,249,24}

422 568
374 521

48

(422,567)
48,47

5894 2269
421

*/