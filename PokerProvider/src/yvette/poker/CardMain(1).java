package yvette.poker;

import yvette.poker.views.CardFrame;
import yvette.poker.views.ResultPanel;

import java.net.URL;

/**
 * Created by windness on 2017/10/29.
 */



public class CardMain {

    public static String getExePath() {
        return System.getProperty("exe.path");
    }

    public static void main(String args[]) {

        ResultPanel.initTypeMap();
        CardFrame frame = new CardFrame();
    }
}
