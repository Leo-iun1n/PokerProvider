package yvette.poker.views;

import javax.swing.*;
import java.awt.*;

/**
 * Created by windness on 2017/11/2.
 */
public class CardInfoTextField extends JTextField {
    private static Font font = new Font("微软雅黑", Font.BOLD, 15);
    private static Color color = Color.white;

    public CardInfoTextField(String text) {
        super(text);
        setFont(font);
        setForeground(color);
        setBorder(null);
        setOpaque(false);
        setEditable(false);
    }

    public CardInfoTextField(String text, Font font, Color color) {
        this(text);
        setFont(font);
        setForeground(color);
    }
}
