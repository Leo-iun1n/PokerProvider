package sanShuiAlgorithm;
//修改了
import java.util.*;

public class PlayerByCloudFree {
	public int score;//牌型评分
	public boolean[] vis = new boolean[13];
	public String headType = "wulong", midType = "wulong", endType = "wulong";
	public int headRank = 1, midRank = 1, endRank = 1;

	//public String specialName = "not";

	public List<Card> head = new ArrayList<Card>();
	public List<Card> mid = new ArrayList<Card>();
	public List<Card> end = new ArrayList<Card>();
	public List<Card> handCard = new ArrayList<Card>();
	
	//存储最初牌,并要拷贝一份到handcardcopy

	public int id;
	public int fourbaggerStatus;
	public int headWeight, midWeight, endWeight;
	public int headScore, midScore, endScore;
	public int[] biggerThanCnts = new int[4];
	public ScoreMovement[] scoreOfEachOpponent = new ScoreMovement[4];
	public List<Integer> headGroupRanks = new ArrayList<Integer>();
	public List<Integer> headSingleRanks = new ArrayList<Integer>();
	public List<Integer> midGroupRanks = new ArrayList<Integer>();
	public List<Integer> midSingleRanks = new ArrayList<Integer>();
	public List<Integer> endGroupRanks = new ArrayList<Integer>();
	public List<Integer> endSingleRanks = new ArrayList<Integer>();


	public List<Card> handCardCopy = new ArrayList<Card>();
	public List<Two> doubleCard = new ArrayList<Two>();
	public List<Three> tripleCard = new ArrayList<Three>();
	public List<TieZhi> quadrupleCard = new ArrayList<TieZhi>();
	public List<ShunZi> shunZi = new ArrayList<ShunZi>();
	public List<AllType> allType = new ArrayList<AllType>();
	public List<ShunZi> tonghuaShun = new ArrayList<ShunZi>();//压入最后一个元素的rank
	
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
	/*		a.card.sort(cardRankComparator);
			b.card.sort(cardRankComparator);
			for(int i = 4; i >= 0; i--){
				Card temp1 = a.card.get(i);
				Card temp2 = b.card.get(i);
				if(temp1.rank == temp2.rank) continue;
					return Integer.compare(temp1.rank, temp2.rank);
			}
			return 0;*/
			return Integer.compare(a.level, b.level);
		}
	};

	public static final Comparator<ShunZi> cardTongHuaShunComparator = new Comparator<ShunZi>() {
		@Override
		public int compare(ShunZi o1, ShunZi o2) {
			if(o1.shunZi.size() == o2.shunZi.size()){
				if(o1.shunZi.get(0).rank>o2.shunZi.get(0).rank) return 1;
				else if(o1.shunZi.get(0).rank == o2.shunZi.get(0).rank) return 0;
				else return -1;
			}else{
				if(o1.shunZi.size()>o2.shunZi.size()) return -1;
				else if(o1.shunZi.size() == o2.shunZi.size()) return 0;
				else return 1;
			}
		}
	};


	public static final Comparator<Card> cardRankComparatorFromBig = new Comparator<Card>() {
		@Override
		public int compare(Card a, Card b) {
			if(a.rank>b.rank) return -1;
			if(a.rank==b.rank) return 0;
			return 1;
		}
	};

	public static final Comparator<TieZhi> cardTieZhiComparator = new Comparator<TieZhi>() {
		@Override
		public int compare(TieZhi o1, TieZhi o2) {
			Card a = o1.tiezhi.get(0);
			Card b = o2.tiezhi.get(0);
			return Integer.compare(a.rank, b.rank);
		}
	};

	public static final Comparator<ShunZi> cardShunZiComparator = new Comparator<ShunZi>() {
		@Override
		public int compare(ShunZi a, ShunZi b) {
			if(a.level!=b.level)
				return Integer.compare(a.level, b.level);
			return Integer.compare(b.shunZi.get(0).rank, a.shunZi.get(0).rank);
		}
	};

	public static final Comparator<Card> cardRank = new Comparator<Card>() {
		@Override
		public int compare(Card a1, Card a2) {
			if(a1.rank == a2.rank) return 0;
			else if(a1.rank > a2.rank) return -1;
			else return 1;
		}
	};

	public static final Comparator<TongHuaShun> cardTongShunZiComparator = new Comparator<TongHuaShun>() {
		@Override
		public int compare(TongHuaShun a, TongHuaShun b) {
			Card tempCardA = a.tongHuaShun.get(0);
			Card tempCardB = b.tongHuaShun.get(0);
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
	
	public PlayerByCloudFree() {//得到随机牌存储于handCard， 且复制一份到handCardCopy
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
	
	public int checkNumOfKings() {
		int num = 0;
		for(int i = 0; i < handCard.size(); i++) {
			Card tempCard = handCard.get(i);
			if(tempCard.rank == 44)
				num+=10;
			else if(tempCard.rank == 88)
				num+=100;
		}
		return num;
	}
	
	public void allClear() {
		head.clear();
		mid.clear();
		end.clear();
	}

	public void addMyCard(List<Card> cardList) {
		handCard.clear();
		handCardCopy.clear();
		for(Card i:cardList){
			handCardCopy.add(i);
			handCard.add(new Card(i.type, i.rank));
		}
	}
}

