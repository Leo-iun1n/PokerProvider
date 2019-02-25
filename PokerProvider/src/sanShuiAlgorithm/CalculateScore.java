package sanShuiAlgorithm;

import java.lang.reflect.Field;
import java.util.*;

public class CalculateScore {
    public static void ScorePlayers(List<Player> in) throws NoSuchFieldException, IllegalAccessException {
        List<Player> validPlayers = new ArrayList<>();
        for (Player i : in) {
            for (int sindex = 0; sindex < 4; ++sindex) {
                i.scoreOfEachOpponent[sindex] = new ScoreMovement();
            }
            if (!i.isSpecial) {    //�����Ʋ��ڱ��ദ��
                generateInfoForHead(i);    //����ͷ�գ�weight������������
                genreateInfoForMidOrEnd(i, "mid");    //�����ж�
                genreateInfoForMidOrEnd(i, "end");    //����β��
                validPlayers.add(i);
            }
        }
        calculateHeadScore(validPlayers);
        calculateMidOrEndScore(validPlayers, "mid");
        calculateMidOrEndScore(validPlayers, "end");
        calculateShootAndFourbaggerScore(validPlayers);
        calculateFinalScore(validPlayers);
    }

    private static void calculateFinalScore(List<Player> validPlayers) {
        for(Player p : validPlayers) {
            int score = 0;
            for(int i = 0; i < p.scoreOfEachOpponent.length; ++i) {
                ScoreMovement t = p.scoreOfEachOpponent[i];
                int tempScore = t.headScore + t.headScoreExtra + t.midScore + t.midScoreExtra + t.endScore + t.endScoreExtra;
                if(t.shootStatus != 0) {
                    tempScore *= 2;
                }
                score += tempScore;
            }
            if(p.fourbaggerStatus == 1) {
                score *= 2;
            }
            p.score += score;
        }
    }

    private static void calculateShootAndFourbaggerScore(List<Player> validPlayers) {
        int opponentCnt = validPlayers.size() - 1;
        for (Player p : validPlayers) {
            int winCnt = 0;
            for (int index = 0; index < p.biggerThanCnts.length; ++index) {
                int t = p.biggerThanCnts[index];
                if (Math.abs(t) == 3) {
                    p.scoreOfEachOpponent[index].shootStatus = t / 3;
                    winCnt += t / 3;
                }
            }
            if (Math.abs(winCnt) == opponentCnt) {
                p.fourbaggerStatus = winCnt / opponentCnt;
                for(Player pp : validPlayers) {
                    if(pp != p) {
                        pp.fourbaggerStatus = -1;
                    }
                }
            }
        }
    }

    private static void calculateMidOrEndScore(List<Player> validPlayers, String midOrEnd)
            throws NoSuchFieldException, IllegalAccessException {
        Class playerClass = validPlayers.get(0).getClass();
        Field cardsField = playerClass.getField(midOrEnd);
        Field weightField = playerClass.getField(midOrEnd + "Weight");
        Field groupRanksField = playerClass.getField(midOrEnd + "GroupRanks");
        Field singleRanksField = playerClass.getField(midOrEnd + "SingleRanks");
        Field biggerThanCntField = playerClass.getField("biggerThanCnts");

        List<Card> cards = (List<Card>) cardsField.get(validPlayers.get(0));
        List<Integer> singleRanks = (List<Integer>) singleRanksField.get(validPlayers.get(0));
        List<Integer> groupRanks = (List<Integer>) groupRanksField.get(validPlayers.get(0));

        Class scoreMovementClass = validPlayers.get(0).scoreOfEachOpponent[0].getClass();
        Field partScoreField = scoreMovementClass.getField(midOrEnd + "Score");
        Field extraScoreField = scoreMovementClass.getField(midOrEnd + "ScoreExtra");

        validPlayers.sort((Player l, Player r) -> {
            try {
                return -Integer.compare((int) weightField.get(l), (int) weightField.get(r));
            } catch (IllegalAccessException e) {
                throw new RuntimeException("IllegalAccessException from lambda expression.");
            }
        });
        for (int i = 0; i < validPlayers.size(); ++i) {
            for (int j = i + 1; j < validPlayers.size(); ++j) {
                Player winner = validPlayers.get(i);
                Player loser = validPlayers.get(j);
                int[] biggerThanCntsOfWinner = (int[]) biggerThanCntField.get(winner);
                int[] biggerThanCntsOfLoser = (int[]) biggerThanCntField.get(loser);
                int duelResult = 123;
                if ((int) weightField.get(winner) != (int) weightField.get(loser)) {
                    partScoreField.set(winner.scoreOfEachOpponent[loser.id], 1);
                    biggerThanCntsOfWinner[loser.id] += 1;
                    partScoreField.set(loser.scoreOfEachOpponent[winner.id], -1);
                    biggerThanCntsOfLoser[winner.id] -= 1;
                } else {
                    duelResult = SameTypeDuel(winner, loser, midOrEnd);        //���� :)  winnerʤ����1
                    partScoreField.set(winner.scoreOfEachOpponent[loser.id], duelResult);
                    biggerThanCntsOfWinner[winner.id] += 1;
                    partScoreField.set(loser.scoreOfEachOpponent[winner.id], -duelResult);
                    biggerThanCntsOfLoser[loser.id] -= 1;
                }
                if (duelResult != 0) {
                    int moveScoreExtra = 0;
                    switch ((int) weightField.get(winner)) {
                        case 9:
                            moveScoreExtra = midOrEnd.equals("mid") ? 8 : 4;    //ͬ��˳
                            break;
                        case 8:
                            moveScoreExtra = midOrEnd.equals("mid") ? 6 : 3;    //��֧
                            break;
                        case 7:
                            moveScoreExtra = midOrEnd.equals("mid") ? 1 : 0;    //��«
                            break;
                    }
                    extraScoreField.set(winner.scoreOfEachOpponent[loser.id], moveScoreExtra);
                    extraScoreField.set(loser.scoreOfEachOpponent[winner.id], -moveScoreExtra);
                }
            }
        }
    }

    private static void calculateHeadScore(List<Player> validPlayers)
            throws NoSuchFieldException, IllegalAccessException {
        validPlayers.sort((Player l, Player r) -> -Integer.compare(l.headWeight, r.headWeight));
        for (int i = 0; i < validPlayers.size(); ++i) {
            for (int j = i + 1; j < validPlayers.size(); ++j) {
                Player winner = validPlayers.get(i);
                Player loser = validPlayers.get(j);
                int duelResult = 123;    //���ⲻΪ0��ֵ
                if (winner.headWeight != loser.headWeight) {
                    winner.scoreOfEachOpponent[loser.id].headScore = 1;
                    winner.biggerThanCnts[loser.id] += 1;
                    loser.scoreOfEachOpponent[winner.id].headScore = -1;
                    loser.biggerThanCnts[winner.id] -= 1;
                } else {
                    duelResult = SameTypeDuel(winner, loser, "head");
                    winner.scoreOfEachOpponent[loser.id].headScore = duelResult;
                    winner.biggerThanCnts[loser.id] += 1;
                    loser.scoreOfEachOpponent[winner.id].headScore = -duelResult;
                    loser.biggerThanCnts[winner.id] -= 1;
                }
                if (duelResult != 0 && winner.headWeight == 3) {    //����
                    winner.scoreOfEachOpponent[loser.id].headScoreExtra = 2;
                    loser.scoreOfEachOpponent[winner.id].headScoreExtra = -2;
                }
            }
        }
    }

    private static int SameTypeDuel(Player winner, Player loser, String headMidOrEnd)
            throws NoSuchFieldException, IllegalAccessException {
        Field groupRanksField = winner.getClass().getField(headMidOrEnd + "GroupRanks");
        Field singleRanksField = winner.getClass().getField(headMidOrEnd + "SingleRanks");
        List<Integer> groupRanksOfWinner = (List<Integer>) groupRanksField.get(winner);
        List<Integer> groupRanksOfLoser = (List<Integer>) groupRanksField.get(loser);
        List<Integer> singleRanksOfWinner = (List<Integer>) singleRanksField.get(winner);
        List<Integer> singleRanksOfLoser = (List<Integer>) singleRanksField.get(loser);

        for (int i = 0; i < groupRanksOfWinner.size(); ++i) {
            if (!groupRanksOfWinner.get(i).equals(groupRanksOfLoser.get(i))) {
                return Integer.compare(groupRanksOfWinner.get(i), groupRanksOfLoser.get(i));
            }
        }

        for (int i = 0; i < singleRanksOfWinner.size(); ++i) {
            if (!singleRanksOfWinner.get(i).equals(singleRanksOfLoser.get(i))) {
                return Integer.compare(singleRanksOfWinner.get(i), singleRanksOfLoser.get(i));
            }
        }

        return 0;
    }

    private static void generateInfoForHead(Player p) {
        p.head.sort((Card l, Card r) -> Integer.compare(l.rank, r.rank));
        if (p.head.get(0).rank == p.head.get(1).rank
                && p.head.get(1).rank == p.head.get(2).rank) {
            p.headWeight = 3;    //����
            p.headGroupRanks.add(p.head.get(0).rank);
        } else if (p.head.get(0).rank == p.head.get(1).rank) {
            p.headWeight = 2;    //һ��
            p.headGroupRanks.add(p.head.get(0).rank);
            p.headSingleRanks.add(p.head.get(2).rank);
        } else if (p.head.get(1).rank == p.head.get(2).rank) {
            p.headWeight = 2;    //һ��
            p.headGroupRanks.add(p.head.get(1).rank);
            p.headSingleRanks.add(p.head.get(0).rank);
        } else if (p.head.get(0).rank == p.head.get(2).rank) {
            p.headWeight = 2;    //һ��
            p.headGroupRanks.add(p.head.get(0).rank);
            p.headSingleRanks.add(p.head.get(1).rank);
        } else {
            p.headWeight = 1;    //����
            p.headSingleRanks.addAll(generateRankList(p.head));
        }
        p.headSingleRanks.sort(Comparator.reverseOrder());
    }

    private static void genreateInfoForMidOrEnd(Player p, String midOrEnd)
            throws NoSuchFieldException, IllegalAccessException {
        Class playerClass = p.getClass();
        Field cardsField = playerClass.getField(midOrEnd);
        Field weightField = playerClass.getField(midOrEnd + "Weight");
        Field groupRanksField = playerClass.getField(midOrEnd + "GroupRanks");
        Field singleRanksField = playerClass.getField(midOrEnd + "SingleRanks");

        List<Card> cards = (List<Card>) cardsField.get(p);
        List<Integer> singleRanks = (List<Integer>) singleRanksField.get(p);
        List<Integer> groupRanks = (List<Integer>) groupRanksField.get(p);

        cards.sort((Card l, Card r) -> Integer.compare(l.rank, r.rank));
        boolean sameType = checkForSameType(cards);
        boolean increaseOneRank = checkForIncreaseOneRank(cards);
        if (sameType && increaseOneRank) {
            weightField.set(p, 9);    //ͬ��˳
            singleRanks.addAll(generateRankList(cards));
        } else if (sameType) {
            weightField.set(p, 6);    //ͬ��
            singleRanks.addAll(generateRankList(cards));
        } else if (increaseOneRank) {
            weightField.set(p, 5);    //˳��
            singleRanks.addAll(generateRankList(cards));
        }
        singleRanks.sort(Comparator.reverseOrder());

        if ((int) weightField.get(p) != 0) {
            return;
        }

        SameRankDistribution sameRankDistribution = checkForSameRankDistribution(cards);
        List<Integer> sameRankCnts = sameRankDistribution.cntDistribution;
        if (sameRankCnts.equals(Arrays.asList(1, 4))) {
            weightField.set(p, 8);    //��֧
        } else if (sameRankCnts.equals(Arrays.asList(2, 3))) {
            weightField.set(p, 7);    //��«
        } else if (sameRankCnts.equals(Arrays.asList(1, 1, 3))) {
            weightField.set(p, 4);    //����
        } else if (sameRankCnts.equals(Arrays.asList(1, 2, 2))) {
            weightField.set(p, 3);    //����
        } else if (sameRankCnts.equals(Arrays.asList(1, 1, 1, 2))) {
            weightField.set(p, 2);    //һ��
        } else {
            weightField.set(p, 1);    //����
        }

        groupRanks.addAll(sameRankDistribution.groupRanks);
        singleRanks.addAll(sameRankDistribution.singleRanks);
    }

    private static SameRankDistribution checkForSameRankDistribution(List<Card> cards) {
        SameRankDistribution ret = new SameRankDistribution();
        List<Integer>[] tmpRanksStorge = (List<Integer>[]) new ArrayList[5];
        tmpRanksStorge[2] = new ArrayList<>();
        tmpRanksStorge[3] = new ArrayList<>();
        tmpRanksStorge[4] = new ArrayList<>();

        int maxCnt = 0;
        int currentRank = cards.get(0).rank;
        for (int i = 0; i <= cards.size(); ++i) {
            if (i != cards.size() && cards.get(i).rank == currentRank) {
                ++maxCnt;
            } else {
                ret.cntDistribution.add(maxCnt);
                if (2 <= maxCnt && maxCnt <= 4) {
                    tmpRanksStorge[maxCnt].add(cards.get(i - 1).rank);
                } else {
                    ret.singleRanks.add(cards.get(i - 1).rank);
                }
                if (i != cards.size()) {
                    maxCnt = 1;
                    currentRank = cards.get(i).rank;
                }
            }
        }

        if (tmpRanksStorge[4].size() == 1) {
            ret.groupRanks = tmpRanksStorge[4];
        } else if (tmpRanksStorge[3].size() == 1 && tmpRanksStorge[2].size() == 1) {
            ret.groupRanks.add(tmpRanksStorge[3].get(0));    //˳����Ҫ��ȷ���ȱ�������ͬ��С�ƵĴ�С
            ret.groupRanks.add(tmpRanksStorge[2].get(0));
        } else if (tmpRanksStorge[3].size() == 1) {
            ret.groupRanks.add(tmpRanksStorge[3].get(0));
        } else {
            ret.groupRanks.addAll(tmpRanksStorge[2]);
            ret.groupRanks.sort(Comparator.reverseOrder());
        }

        Collections.sort(ret.cntDistribution);
        ret.singleRanks.sort(Comparator.reverseOrder());
        return ret;
    }

    private static List<Integer> generateRankList(List<Card> cards) {
        List<Integer> ret = new ArrayList<>();
        for (Card c : cards) {
            ret.add(c.rank);
        }
        return ret;
    }

    private static boolean checkForSameType(List<Card> cards) {
        int type = cards.get(0).type;
        for (Card i : cards) {
            if (i.type != type) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkForIncreaseOneRank(List<Card> cards) {
        for (int index = 0; index < cards.size() - 1; ++index) {
            if (cards.get(index).rank + 1 != cards.get(index + 1).rank) {
                return false;
            }
        }
        return true;
    }

    private static class SameRankDistribution {
        List<Integer> cntDistribution = new ArrayList<>();
        List<Integer> groupRanks = new ArrayList<>();
        List<Integer> singleRanks = new ArrayList<>();
    }
}
