package sanShuiAlgorithm;

public class TestForShunZi {
	public static void main(String[] args) {
		CardPile.PreDeal();
		PlayerByCloudFree p = new PlayerByCloudFree();
		p.handCard.clear();
		p.handCardCopy.clear();
		Card[] a = new Card[13];
		for(int i = 0;i < 13; i++) {
			a[i] = new Card();
		}
		a[0].rank = 11;
		a[0].type = 1;
		p.handCard.add(a[0]);
		p.handCardCopy.add(a[0]);
		
		a[1].rank = 3;
		a[1].type = 2;
		p.handCard.add(a[1]);
		p.handCardCopy.add(a[1]);
		
		a[2].rank = 1;
		a[2].type = 3;
		p.handCard.add(a[2]);
		p.handCardCopy.add(a[2]);
		
		a[3].rank = 9;
		a[3].type = 3;
		p.handCard.add(a[3]);
		p.handCardCopy.add(a[3]);
		
		a[4].rank = 8;
		a[4].type = 2;
		p.handCard.add(a[4]);
		p.handCardCopy.add(a[4]);
		
		a[5].rank = 7;
		a[5].type = 1;
		p.handCard.add(a[5]);
		p.handCardCopy.add(a[5]);
		
		a[6].rank = 6;
		a[6].type = 2;
		p.handCard.add(a[6]);
		p.handCardCopy.add(a[6]);
		
		
		a[7].rank = 5;
		a[7].type = 3;
		p.handCard.add(a[7]);
		p.handCardCopy.add(a[7]);
		
		a[8].rank = 13;
		a[8].type = 1;
		p.handCard.add(a[8]);
		p.handCardCopy.add(a[8]);
		a[9].rank = 13;
		a[9].type = 3;
		p.handCard.add(a[9]);
		p.handCardCopy.add(a[9]);
		a[10].rank = 13;
		a[10].type = 2;
		p.handCard.add(a[10]);
		p.handCardCopy.add(a[10]);
		a[11].rank = 9;
		a[11].type = 2;
		p.handCard.add(a[11]);
		p.handCardCopy.add(a[11]);
		a[12].rank = 9;
		a[12].type = 4;
		p.handCard.add(a[12]);
		p.handCardCopy.add(a[12]);		
		
		//SpecialCardType.dealShunZi(p);
		//SpecialCardType.analyzeShunZi(p);
		//SpecialCardType.analyzeLeftCard(p);
		SpecialCardType.getCardSortedBack(p);
		for(int i = 0; i < p.shunZi.size(); i++) {
			ShunZi temp = p.shunZi.get(i);
			for(int j = 0 ; j < temp.shunZi.size(); j++) {
				System.out.print(temp.shunZi.get(j).rank+" ");
			}
			System.out.println("");
		}
		System.out.println(p.head.size());
		System.out.println(p.mid.size());
		System.out.println(p.end.size());
	}
}
