package sanShuiAlgorithm;
//1---13->2---A
import java.util.*;

public class CardPile {
	private static final String[] type = {"1", "2", "3", "4"};
	private static final String[] rank = {"01","02","03","04","05","06","07",
			"08","09","10","11","12","13"};

	private static List<Object> deck = new ArrayList<Object>();
	
	public static void PreDeal() {
		deck.clear();
		for(int i = 0; i < type.length; i++)
			for(int j = 0; j < rank.length; j++)
				deck.add(type[i]+rank[j]);
		deck.add("044");
		deck.add("088");
		Collections.shuffle(deck);
	}

	public static List<Object> getCard() {
		List<Object> cardToHand = dealCard(deck, 13);
		return cardToHand;
	}
	
	private static List<Object> dealCard(List<Object> deck, int num){
		int deckSize = deck.size();
		List<Object> handView = deck.subList(deckSize-num, deckSize);
		deck = deck.subList(0, deckSize-num);
		List<Object> hand = new ArrayList<Object>(handView);
		handView.clear();
		return hand;
	}
}
