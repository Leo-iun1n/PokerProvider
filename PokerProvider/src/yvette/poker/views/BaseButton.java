package yvette.poker.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by windness on 2017/11/2.
 */
public class BaseButton extends JButton {
    private static Font font = new Font("微软雅黑", Font.BOLD, 18);
    private static Color color = Color.white;

    public BaseButton() {
        this.setFont(new Font("微软雅黑", Font.BOLD, 18));
        this.setForeground(Color.white);
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setBorder(BorderFactory.createRaisedBevelBorder());
        this.addMouseListener(new BaseMouseListener());
//        this.setSelectedIcon(new ImageIcon(new byte[]));
    }

    private class BaseMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            BaseButton.this.setBorder(BorderFactory.createLoweredBevelBorder());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            BaseButton.this.setBorder(BorderFactory.createRaisedBevelBorder());
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
