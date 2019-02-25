package sanShuiAlgorithm;

import java.util.*;



public class Choice {
	public List<Card> head = new ArrayList<Card>();
    public List<Card> mid = new ArrayList<Card>();
	public List<Card> end = new ArrayList<Card>();
	public String headType, midType, endType;
	int level;
	public static final Comparator<Choice> choiceComparator = new Comparator<Choice>() {
		@Override
		public int compare(Choice o1, Choice o2) {
			return Integer.compare(o1.level, o2.level);
		}
	};

	public void intoChoice(PlayerByCloudFree p) {
		for(Card i:p.head) {
			head.add(i);
		}
		for(Card i:p.mid)
			mid.add(i);
		for(Card i:p.end)
			end.add(i);
		headType = p.headType;
		midType = p.midType;
		endType = p.endType;
	}
	
	public boolean isSameChoice(Choice b) {
		for(int i = 0; i < 3; i++) {
			//System.out.println("love"+b.head.size());
			//System.out.println("hate"+head.size());
			//System.out.println(b.mid.size());
			//System.out.println(b.end.size());
			//for(int j = 0; j < b.end.size(); j++) {
			//	System.out.print(b.end.get(j).rank+" ");
			//}
			if(!SpecialCardType.isEquals(b.head.get(i), head.get(i)))
				return false;
		}
		for(int i = 0; i < 5; i++) {
			if(!SpecialCardType.isEquals(b.mid.get(i), mid.get(i))) 
				return false;
		}
		for(int i = 0; i < 5; i++) {
			if(!SpecialCardType.isEquals(b.end.get(i), end.get(i))) 
				return false;
		}
		return true;
	}

	public void showForTest() {
		for(Card i:head)
		System.out.print("test:" + i.rank+"  ");
		System.out.println();
		for(Card ii:mid)
			System.out.print("test:" + ii.rank+"  ");
		System.out.println();
		for (Card i :end)
			System.out.print("test:"+i.rank+"  ");
		System.out.println();
	}
}
