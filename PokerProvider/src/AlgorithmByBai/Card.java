package AlgorithmByBai;

public class Card {
	public int rank,
	type;
	public Card(){}
	public Card(int r,int t)
	{
		  rank=r;
		  type=t;
	}
}
	// 大王花色0，rank 88；
	// 小王花色0，rank 44；
	// 花色1-4代表四种花色（♥，♣，♦，♠）
	// Rank 1-13表示牌的大小（2，3，4……，K,  A）
	//可以拓展相关功能。
