package sanShuiAlgorithm;

import java.util.*;

class ScoreMovement {
	public int headScore, midScore, endScore;
	public int headScoreExtra, midScoreExtra, endScoreExtra;
	public int shootStatus;
}

public class Player {
	public int score;
	public boolean[] vis = new boolean[13]; 
	public boolean isSpecial;
	
	public List<Card> head = new ArrayList<Card>();
	public List<Card> mid = new ArrayList<Card>();
	public List<Card> end = new ArrayList<Card>();
	public List<Card> handCard = new ArrayList<Card>();
	public List<Card> handCardCopy = new ArrayList<Card>();
	public List<Two> doubleCard = new ArrayList<Two>();
	public List<Three> tripleCard = new ArrayList<Three>();
	public List<Card> quadrupleCard = new ArrayList<Card>();
	public List<ShunZi> shunZi = new ArrayList<ShunZi>();
	public List<AllType> allType = new ArrayList<AllType>();
	public List<Card> tonghuaShun = new ArrayList<Card>();//ѹ�����һ��Ԫ�ص�rank
	
	int id;
	public int fourbaggerStatus;
	public int headWeight, midWeight, endWeight;
	public int[] biggerThanCnts = new int[4];
	public ScoreMovement[] scoreOfEachOpponent = new ScoreMovement[4];
	public List<Integer> headGroupRanks = new ArrayList<>();
	public List<Integer> headSingleRanks = new ArrayList<>();
	public List<Integer> midGroupRanks = new ArrayList<>();
	public List<Integer> midSingleRanks = new ArrayList<>();
	public List<Integer> endGroupRanks = new ArrayList<>();
	public List<Integer> endSingleRanks = new ArrayList<>();
	
	public static final Comparator<Card> cardRankComparator = new Comparator<Card>() {
		@Override
		public int compare(Card a, Card b) {
			if(a.rank>b.rank) return 1;
			if(a.rank==b.rank) return 0;
			return -1;
		}
	};
	
	public static final Comparator<Card> cardTypeComparator = new Comparator<Card>() {
		@Override
		public int compare(Card a, Card b) {
			return Integer.compare(a.type, b.type);
		}
	};

	public static final Comparator<Three> cardThreeComparator = new Comparator<Three>() {
		@Override
		public int compare(Three a, Three b) {
			Card tempCardA = a.three.get(0);
			Card tempCardB = b.three.get(0);
			return Integer.compare(tempCardA.rank, tempCardB.rank);
		}
	};
	
	public static final Comparator<AllType> cardAllTypeComparator = new Comparator<AllType>() {
		@Override
		public int compare(AllType a, AllType b) {
			a.card.sort(cardRankComparator);
			Card tempCardA = a.card.get(4);
			Card tempCardB = b.card.get(4);
			return Integer.compare(tempCardA.rank, tempCardB.rank);
		}
	};
	
	public static final Comparator<ShunZi> cardShunZiComparator = new Comparator<ShunZi>() {
		@Override
		public int compare(ShunZi a, ShunZi b) {
			Card tempCardA = a.shunZi.get(0);
			Card tempCardB = b.shunZi.get(0);
			return Integer.compare(tempCardA.rank, tempCardB.rank);
		}
	};
	
	public static final Comparator<Two> cardTwoComparator = new Comparator<Two>() {
		@Override
		public int compare(Two a, Two b) {
			Card tempCardA = a.two.get(0);
			Card tempCardB = b.two.get(0);
			return Integer.compare(tempCardA.rank, tempCardB.rank);
		}
	};
	
	public Player() {
		score = 0;
		List<Object> tempCard = new ArrayList<Object>();
		tempCard = CardPile.getCard();
		for(int i = 0; i < tempCard.size(); i++) {
			String tempStr = (String)tempCard.get(i);
			//System.out.println(tempStr);
			int type = (int)tempStr.charAt(0)-'0';
			int rank = (int) (tempStr.charAt(1)-'0')*10
					+tempStr.charAt(2)-'0';
			//System.out.println(type);
			Card card = new Card();
			card.rank=rank;
			card.type=type;
			//System.out.println(card.rank);
			handCard.add(card);
		}
		tempCard.clear();
		for(int i = 0; i < handCard.size(); i++)
			handCardCopy.add(handCard.get(i));
		handCardCopy.sort(cardRankComparator);
	}
	
	public void sortByType(){
		handCard.sort(cardTypeComparator);
	}
	
	public void sortByRank() {
		handCard.sort(cardRankComparator);
	}
}
