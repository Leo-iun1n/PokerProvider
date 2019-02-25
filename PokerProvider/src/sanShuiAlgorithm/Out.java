package sanShuiAlgorithm;//Changed

import java.util.*;
public class Out {
	public static List<Choice> allChoice = new ArrayList<>();
	
	public static int In(PlayerByCloudFree p, int kingNums) {
		Card oneIn = new Card();
		if(kingNums==0) return 1000;
		oneIn.rank = (kingNums==10?44:88);
		kingNums = (kingNums==10?10:100);

		//System.out.println("afafafaf");
		
		for(int i = 0; i < p.tripleCard.size(); i++) {
			Three tempThree = p.tripleCard.get(i);
			TieZhi tempTie = new TieZhi();
			tempTie.tiezhi.add(tempThree.three.get(0));
			tempTie.tiezhi.add(tempThree.three.get(1));
			tempTie.tiezhi.add(tempThree.three.get(2));
			tempTie.tiezhi.add(oneIn);
			p.quadrupleCard.add(tempTie);
		}
		
		for(int i = 0; i < p.doubleCard.size(); i++) {
			Two tempTwo = p.doubleCard.get(i);
			Three tempThree = new Three();
			tempThree.three.add(tempTwo.two.get(0));
			tempThree.three.add(tempTwo.two.get(1));
			tempThree.three.add(oneIn);
			p.tripleCard.add(tempThree);
		}
		
		for(int i = 0; i < p.handCardCopy.size(); i++) {
			if(p.vis[i]) continue;
			if(p.handCardCopy.get(i).rank>=44) continue;
			Card tempCard = p.handCardCopy.get(i);
			Two temp = new Two();
			temp.two.add(tempCard);
			temp.two.add(oneIn);
			p.doubleCard.add(temp);
		}
		return kingNums;
	}
	
	public static void preActionPerTime() {
		CardPile.PreDeal();
	}

	private static void swapPosition(Choice choice){
		/*if(choice.endType == "tiezhi"){
			if(choice.headType!="santiao") {
				if (choice.head.get(2).rank < choice.end.get(4).rank) {
					Card hehe = choice.head.get(2);
					Card xixi = choice.end.get(4);
					System.out.println("hehe" + choice.end.get(4).rank);
					Card temp = new Card(choice.end.get(4).type, choice.end.get(4).rank);
					xixi.rank = hehe.rank;xixi.type = hehe.type;
					hehe.rank = temp.rank; hehe.type = temp.type;
					System.out.println("fafa" + choice.head.get(2).rank);
				}
			}
		}*/
	}

	public static void myAdd(Choice tempChoice, PlayerByCloudFree p) {
		tempChoice.intoChoice(p);
		boolean flag = false;
		sortForWuLong(tempChoice);
		for(int i = 0; i < allChoice.size(); i++) {
			if(tempChoice.isSameChoice(allChoice.get(i))) {
				flag = true; 
				break;
			}
		}
		swapPosition(tempChoice);
		if(!flag) allChoice.add(tempChoice);
	}

	private static void sortForWuLong(Choice p){
		if(p.headType == "wulong")
			p.head.sort(PlayerByCloudFree.cardRankComparatorFromBig);
		if(p.midType == "wulong")
			p.mid.sort(PlayerByCloudFree.cardRankComparatorFromBig);
		if(p.endType == "wulong")
			p.end.sort(PlayerByCloudFree.cardRankComparatorFromBig);
	}

	public static void testOut(PlayerByCloudFree p){
		for(int i = 0; i < p.head.size(); i++)
			System.out.print(p.head.get(i).rank+" ");
		System.out.println("");
		for(int j = 0; j < p.mid.size(); j++)
			System.out.print(p.mid.get(j).rank+" ");
		System.out.println("");
		for(int j = 0; j < p.end.size(); j++)
			System.out.print(p.end.get(j).rank+" ");
		System.out.println("");
	}

	public static Choice getRandomChoice(Choice choice) {
		Choice c = new Choice();
		for(Card card:choice.head){
			c.head.add(new Card(card.type, card.rank));
		}
		for(Card card:choice.mid){
			c.mid.add(new Card(card.type, card.rank));
		}
		for(Card card:choice.end){
			c.end.add(new Card(card.type, card.rank));
		}
		c.headType = choice.headType;
		c.midType = choice.midType;
		c.endType = choice.endType;
		if(c.headType != "santiao"){
			c.head.sort(PlayerByCloudFree.cardRank);
			Card temp = new Card(c.head.get(0).type, c.head.get(0).rank);
			Card temp1 = new Card(c.head.get(1).type, c.head.get(1).rank);
			if(c.endType == "duizi" || c.endType == "santiao" || c.endType == "tiezhi"){
				c.head.get(0).rank = c.end.get(4).rank;
				c.head.get(0).type = c.end.get(4).type;
				c.end.get(4).rank = temp.rank;
				c.end.get(4).type = temp.type;
			}
			if(c.midType == "duizi" || c.midType == "santiao" || c.midType == "tiezhi"){
				c.head.get(1).rank = c.mid.get(4).rank;
				c.head.get(1).type = c.mid.get(4).type;
				c.mid.get(4).rank = temp1.rank;
				c.mid.get(4).type = temp1.type;
			}
		}
		int random = (int)(Math.random()*2+1);
		return (random == 1) ? c : choice;
	}

	public static void getAllChoice(PlayerByCloudFree p) {

		SpecialCardType.dealHandCard(p);
		int kingNums = p.checkNumOfKings();
		while(kingNums > 0) {
			kingNums -= In(p, kingNums);
		}
		if(p.midType == "tiezhi" ) p.midRank = 9;
		if(p.endType == "tiezhi") p.endRank = 9;
		SpecialCardType.getCardSortedBack(p);
		//testOut(p);
		//SpecialCardType.dealForDaoShui(p);

		Choice tempChoice = new Choice();
		//tempChoice.intoChoice(p);
		//tempChoice.showForTest();
		//sortForWuLong(tempChoice);
		//tempChoice.showForTest();
		myAdd(tempChoice, p);
		//tempChoice.showForTest();
		//allChoice.add(tempChoice);

		//testOut(p);
		//DataCorrespond.testOut(tempChoice);
		
		p.allClear();
		Choice tempChoice1 = new Choice();
		SpecialCardType.dealForAnotherChoice1(p);
		myAdd(tempChoice1, p);


		p.allClear();
		Choice tempChoice2 = new Choice();
		SpecialCardType.dealForAnotherChoice2(p);
		myAdd(tempChoice2, p);
		
		p.allClear();
		Choice tempChoice3 = new Choice();
		SpecialCardType.dealForAnotherChoice3(p);
		myAdd(tempChoice3, p);
		
		p.allClear();
		Choice tempChoice4 = new Choice();
		SpecialCardType.dealForAnotherChoice4(p);
		myAdd(tempChoice4, p);
		
		p.allClear();
		Choice tempChoice5 = new Choice();
		SpecialCardType.dealForAnotherChoice5(p);
		myAdd(tempChoice5, p);
		
		p.allClear();
		Choice tempChoice6 = new Choice();
		SpecialCardType.dealForAnotherChoice6(p);
		myAdd(tempChoice6, p);

		p.allClear();
		Choice tempChoice7 = new Choice();
		SpecialCardType.dealForAnotherChoice7(p);
		myAdd(tempChoice7, p);

		for(Choice choice : allChoice){
			switch (choice.headType){
				case "wulong":
					choice.level += 1;
					break;
				case "duizi":
					choice.level -= 1;
					break;
				case "liangdui":
					choice.level -= 2;
					break;
				case "santiao":
					choice.level -= 5;
			}

			switch (choice.midType){
				case "wulong":
					choice.level += 1;
					break;
				case "duizi":
					choice.level -= 1;
					break;
				case "liangdui":
					choice.level -= 2;
					break;
				case "santiao":
					choice.level -= 3;
					break;
				case "shunzi":
					choice.level -= 4;
					break;
				case "tonghua":
					choice.level -= 5;
					break;
				case "hulu":
					choice.level -= 10;
					break;
				case "tiezhi":
					choice.level -= 16;
					break;
				case "tonghuashun":
					choice.level -= 24;
					break;
			}

			switch (choice.endType){
				case "wulong":
					choice.level += 1;
					break;
				case "duizi":
					choice.level -= 1;
					break;
				case "liangdui":
					choice.level -= 2;
					break;
				case "santiao":
					choice.level -= 3;
					break;
				case "shunzi":
					choice.level -= 4;
					break;
				case "tonghua":
					choice.level -= 5;
					break;
				case "hulu":
					choice.level -= 6;
					break;
				case "tiezhi":
					choice.level -= 12;
					break;
				case "tonghuashun":
					choice.level -= 16;
					break;
			}
		}
		allChoice.sort(Choice.choiceComparator);
		Choice temp_choice  = new Choice();
		AlgorithmByBai.Player pp = new AlgorithmByBai.Player();
		List<AlgorithmByBai.Card> list = new ArrayList<>();
		for(int i = 0; i < 13; i++){
			AlgorithmByBai.Card card = new AlgorithmByBai.Card();
			card.rank = p.handCardCopy.get(i).rank;
			card.type = p.handCardCopy.get(i).type;
			list.add(card);
		}
		pp.change(list);
		for(int i=0;i < pp.choice.head.size(); i++){
			Card c = new Card();
			c.rank	=pp.choice.head.get(i).rank;
			c.type = pp.choice.head.get(i).type;
			temp_choice.head.add(c);
		}
		for(int i=0;i < pp.choice.mid.size(); i++){
			Card c = new Card();
			c.rank	=pp.choice.mid.get(i).rank;
			c.type = pp.choice.mid.get(i).type;
			temp_choice.mid.add(c);
		}
		for(int i=0;i < pp.choice.end.size(); i++){
			Card c = new Card();
			c.rank	= pp.choice.end.get(i).rank;
			c.type = pp.choice.end.get(i).type;
			temp_choice.end.add(c);
		}
		while(allChoice.size()>=2) allChoice.remove(allChoice.size()-1);
		allChoice.add(temp_choice);








		Choice temp_choice1  = new Choice();
		AlgorithmByYang.Player ppp = new AlgorithmByYang.Player();
		List<AlgorithmByYang.Card> list0 = new ArrayList<>();
		for(int i = 0; i < 13; i++){
			AlgorithmByYang.Card card = new AlgorithmByYang.Card();
			card.rank = p.handCardCopy.get(i).rank;
			card.type = p.handCardCopy.get(i).type;
			list0.add(card);
		}
		ppp.change(list0);
		for(int i=0;i < ppp.choice.head.size(); i++){
			Card c = new Card();
			c.rank	=ppp.choice.head.get(i).rank;
			c.type = ppp.choice.head.get(i).type;
			temp_choice1.head.add(c);
		}
		for(int i=0;i < pp.choice.mid.size(); i++){
			Card c = new Card();
			c.rank	=ppp.choice.mid.get(i).rank;
			c.type = ppp.choice.mid.get(i).type;
			temp_choice1.mid.add(c);
		}
		for(int i=0;i < pp.choice.end.size(); i++){
			Card c = new Card();
			c.rank	= ppp.choice.end.get(i).rank;
			c.type = ppp.choice.end.get(i).type;
			temp_choice1.end.add(c);
		}

		allChoice.add(temp_choice1);





		/*Choice tempChoiceForRandom = getRandomChoice(allChoice.get(0));
		for(int i = 0; i < 3; i++){
			allChoice.get(0).head.get(i).rank = tempChoiceForRandom.head.get(i).rank;
			allChoice.get(0).head.get(i).type = tempChoiceForRandom.head.get(i).type;
		}
		for(int i = 0; i < 5; i++){
			allChoice.get(0).mid.get(i).rank = tempChoiceForRandom.mid.get(i).rank;
			allChoice.get(0).mid.get(i).type = tempChoiceForRandom.mid.get(i).type;
		}
		for(int i = 0; i < 5; i++){
			allChoice.get(0).end.get(i).rank = tempChoiceForRandom.end.get(i).rank;
			allChoice.get(0).end.get(i).type = tempChoiceForRandom.end.get(i).type;
		}
		allChoice.get(0).head.sort(PlayerByCloudFree.cardRank);*/
	}
}