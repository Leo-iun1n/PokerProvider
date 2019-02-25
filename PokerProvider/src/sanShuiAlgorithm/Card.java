package sanShuiAlgorithm;

public class Card {
	public int rank;
	public int type;
	public int king;
	private String srank;
	private String stype;
	public static final String[] types = {"♥", "♦", "♣", "♠"};
	public static final String[] ranks = {
		"2", "3", "4", "5", "6", "7", "8", "9", "10",
		"J", "Q", "K", "A"
	};
	public Card() {
		rank = 0;
		type = 0;
		king = 0;
	}
	
	public Card(int king) {
		rank = king;
		type = 0;
	}

	public Card(int type, int rank) {
		this.type = type;
		this.rank = rank;
	}
}
