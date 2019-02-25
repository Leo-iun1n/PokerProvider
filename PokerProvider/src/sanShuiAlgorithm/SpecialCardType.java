package sanShuiAlgorithm;//Changed
//Changed
import java.util.*;
public class SpecialCardType {



	private static  boolean isSanShunZi = false;
	private static boolean isUsed[] = new boolean[13];
	private static boolean isSanTongHuaShun = false;


	public static int checkIsSpecial(PlayerByCloudFree p) {
		PlayerByCloudFree tempPlayer = p;
		if(checkIsKingDragen(tempPlayer))
			//return 108;
			return 13;
		if(checkIsDragen(tempPlayer))
			//return 36;
			return 12;
		if(checkIsTwelveRoyalty(tempPlayer))
			//return 24;
			return 11;
		if(checkIsThreeFlushType(tempPlayer))
			//return 20;
			return 10;
		if(checkIsDividByThree(tempPlayer))
			//return 20;
			return 9;
		if(checkIsAllBig(tempPlayer))
			//return 10;
			return 8;
		if(checkIsAllSmall(tempPlayer))
			//return 10;
			return 7;
		if(checkIsAlike(tempPlayer))
			//return 10;
			return 6;
		if(checkIsFourThree(tempPlayer))
			//return 6;
			return 5;
		if(checkIsFiveToThree(tempPlayer))
			//return 5;
			return 4;
		if(checkIsThreeShunZi(tempPlayer))
			//return 4;
			return 3;
		if(checkIsSixAndaHalf(tempPlayer))
			//return 4;
			return 2;
		if(checkIsThreeFlush(tempPlayer))
			//return 3;
			return 1;
		return 0;
	}

	//三同花OK
	private static boolean checkIsThreeFlush(PlayerByCloudFree p) {

		PlayerByCloudFree tempPlayer = p;
		tempPlayer.sortByType();
		int kingNums = p.checkNumOfKings();
		if(kingNums==110) kingNums=2;
		else if(kingNums>0) kingNums = 1;
		int[] numOfType = new int[4];
		for(int i = 0; i < tempPlayer.handCard.size(); i++) {
			Card tempCard = new Card();
			tempCard = tempPlayer.handCard.get(i);
			if(tempCard.type == 0) continue;
			numOfType[tempCard.type-1]++;
		}

		int numOfOne = 0;
		int numOfThree = 0;
		int numOfFive = 0;
		int numOfTen = 0;
		int numOfEight = 0;
		int numOfFour = 0;
		int numOfNine = 0;

		for(int i = 0; i < 4; i++) {
			switch(numOfType[i]) {
				case 13:
					return true;
				case 3:
					numOfThree++;
					break;
				case 5:
					numOfFive++;
					break;
				case 8:
					numOfEight++;
					break;
				case 10:
					numOfTen++;
					break;
				case 4:
					numOfFour++;
					break;
				case 9:
					numOfNine++;
					break;
				case 1:
					numOfOne++;
					break;
			}
		}

		if(numOfOne == 1 && kingNums == 2){
			kingNums-=2;
			numOfThree++;
			numOfOne--;
		}

		if (numOfNine == 1 && kingNums!=0) {
			numOfNine--;
			numOfTen++;
			kingNums--;
		}
		if(numOfEight == 1&&kingNums==2){
			numOfEight--;
			kingNums -= 2;
			numOfTen++;
		}
		if(numOfTen==1){
			numOfTen--;
			numOfFive+=2;
		}
		while(numOfFour!=0&&kingNums!=0){
			numOfFour--;
			numOfFive++;
			kingNums--;
		}

		if(numOfThree==2&&kingNums==2){
			numOfFive++;
			numOfThree--;
			kingNums-=2;
		}

		if(numOfThree == 1 && (numOfFive == 2||numOfTen == 1))
			return true;
		else if(numOfFive == 1 && numOfEight == 1)
			return true;

		return false;
	}

	//六对半OK
	private static boolean checkIsSixAndaHalf(PlayerByCloudFree p) {

		PlayerByCloudFree tempPlayer = p;
		tempPlayer.sortByRank();
		int numOfTwo = 0;
		int numOfOne = 0;
		int[] numOfRank = new int[13];
		for(int i = 0; i < tempPlayer.handCard.size(); i++) {
			if(tempPlayer.handCard.get(i).rank > 40) continue;
			numOfRank[tempPlayer.handCard.get(i).rank - 1]++;
		}
		for(int i = 0; i < 13; i++) {
			switch(numOfRank[i]) {
				case 2:
					numOfTwo++;
					break;
				case 1:
					numOfOne++;
					break;
				case 4:
					numOfTwo+=2;
					break;
				case 3:
					numOfTwo+=1;
					numOfOne+=1;
					break;

			}
		}

		int kingNums = p.checkNumOfKings();
		if(kingNums == 110) kingNums = 2;
		else if(kingNums>0) kingNums = 1;

		numOfOne-=kingNums;
		numOfTwo+=kingNums;

        //System.out.println(numOfOne+" fafa "+numOfTwo);

        if(numOfTwo == 6 && numOfOne == 1) return true;
		return false;
	}

	//三顺子

	private static int getPreRank(PlayerByCloudFree p){
		for(int i = 0; i < p.handCard.size(); i++){
			if(!isUsed[i]) return p.handCard.get(i).rank - 1;
		}
		return 0;
	}

	private static int getPre(PlayerByCloudFree p){
		for(int i = 0; i < p.handCard.size(); i++){
			if(!isUsed[i]) return i;
		}
		return 0;
	}

	private static void dfsForShunZi(int kingNums, int now, PlayerByCloudFree p, int preRank,
									 int numOfThree, int numOfFive){

		if(numOfThree==1&&numOfFive==2) {
			isSanShunZi = true;
			return;
		}
		if(isSanShunZi) return;
		for(int i = 0; i < p.handCard.size(); i++){
			Card temp = p.handCard.get(i);
			if(temp.rank>40) continue;
			if(isUsed[i]) continue;
			if(temp.rank - preRank == 1) {
				//makeVisForShunZi(temp, p);
				isUsed[i] = true;
				int ans = getPreRank(p);
				if(now+1 == 3){
					dfsForShunZi(kingNums, now+1, p,preRank+1,  numOfThree, numOfFive);
					dfsForShunZi(kingNums, 0, p, ans, numOfThree+1, numOfFive);
				}else if(now+1 == 5){
					dfsForShunZi(kingNums, 0, p, ans, numOfThree, numOfFive+1);
				}else dfsForShunZi(kingNums, now+1, p, preRank+1, numOfThree, numOfFive);
				isUsed[i] = false;
			}else if(temp.rank - preRank > 1) {
				int ans = getPreRank(p);
				if(kingNums >= 1) {
					if(now+1 == 3){
						dfsForShunZi(kingNums - 1, now+1, p,preRank+1,  numOfThree, numOfFive);
						dfsForShunZi(kingNums - 1, 0, p, ans, numOfThree+1, numOfFive);
					}else if(now+1 == 5){
						dfsForShunZi(kingNums - 1, 0, p, ans, numOfThree, numOfFive+1);
					}else dfsForShunZi(kingNums - 1, now+1, p, preRank+1, numOfThree, numOfFive);
					//dfsForShunZi(kingNums - 1, now + 1, p, preRank + 1, numOfThree, numOfThree);

				}
			}
		}
	}


	private static boolean checkIsThreeShunZi(PlayerByCloudFree p){
		int kingNums = p.checkNumOfKings();
		if(kingNums == 110) kingNums = 2;
		else if(kingNums>0) kingNums = 1;
		p.handCard.sort(PlayerByCloudFree.cardRankComparator);
		for(int i = 0; i  < p.handCard.size(); i++)
		dfsForShunZi(kingNums, 0, p, p.handCard.get(i).rank - 1, 0, 0);
		if(isSanShunZi) {
			isSanShunZi = false;
			for(int i = 0; i < isUsed.length; i++)
				isUsed[i] = false;
			return true;
		}
		return false;
	}

	//五对三条OK
	private static boolean checkIsFiveToThree(PlayerByCloudFree p) {

		PlayerByCloudFree tempPlayer = p;
		int kingNums = p.checkNumOfKings();
		if(kingNums==110) kingNums = 2;
		else if(kingNums>0) kingNums = 1;
		tempPlayer.sortByRank();
		int numOfTwo = 0;
		int numOfThree = 0;
		int numOfTwoFalse = 0;
		int[] numOfRank = new int[13];
		for(int i = 0; i < tempPlayer.handCard.size(); i++) {
			if(tempPlayer.handCard.get(i).rank - 1 > 40) continue;
			numOfRank[tempPlayer.handCard.get(i).rank - 1]++;
		}
		for(int i = 0; i < 13; i++) {
			switch(numOfRank[i]) {
				case 2:
					numOfTwo++;
					break;
				case 3:
					numOfThree++;
					break;
                case 4:
                    numOfTwoFalse+=2;
                    break;
				case 1:
					if(kingNums!=0){
						kingNums--;
						numOfTwo++;
					}
					break;
			}
		}

		if(numOfTwo+numOfTwoFalse == 6 && kingNums!=0&&numOfTwo>0) {
			numOfTwo--;
			kingNums--;
			numOfThree++;
		}else if(kingNums == 2){
			numOfTwo++;
			kingNums-=2;
		}
		//System.out.println(numOfTwo+" dqfqafq "+numOfThree + " " + numOfTwoFalse);
		if(numOfTwo + numOfTwoFalse == 5 && numOfThree == 1) return true;
		return false;
	}

	//四套三条OK
	private static boolean checkIsFourThree(PlayerByCloudFree p) {

		PlayerByCloudFree tempPlayer = p;
		tempPlayer.sortByRank();
		int kingNums = p.checkNumOfKings();
		if(kingNums==110) kingNums = 2;
		else if(kingNums>0) kingNums = 1;
		int numOfThree = 0;
		int numOfOne = 0;
		int[] numOfRank = new int[13];
		for(int i = 0; i < tempPlayer.handCard.size(); i++) {
			if(tempPlayer.handCard.get(i).rank > 40) continue;
			numOfRank[tempPlayer.handCard.get(i).rank - 1]++;
		}
		for(int i = 0; i < 13; i++) {
			switch(numOfRank[i]) {
				case 3:
					numOfThree++;
					break;
				case 1:
					numOfOne++;
					break;
				case 2:
					if(kingNums!=0){
						numOfThree++;
						kingNums--;
					}
					break;
				case 4:
					numOfThree++;
					numOfOne++;
			}
		}
		if(numOfOne>1){
			if(kingNums==2){
				numOfOne--;
				numOfThree++;
				kingNums-=2;
			}
		}
		if(numOfThree == 4 && numOfOne == 1) return true;
		return false;
	}

	//凑一色OK
	private static boolean checkIsAlike(PlayerByCloudFree p) {

		PlayerByCloudFree tempPlayer = p;
		int kingNums = p.checkNumOfKings();
		if(kingNums==110) kingNums = 2;
		else if(kingNums>0) kingNums = 1;
		tempPlayer.sortByType();
		int[] numOfType = new int[4];
		for(int i = 0; i < tempPlayer.handCard.size(); i++) {
			if(tempPlayer.handCard.get(i).type == 0) continue;
			numOfType[tempPlayer.handCard.get(i).type - 1]++;
		}
		if(numOfType[0]+numOfType[2] + kingNums == 13
				||numOfType[1]+numOfType[3] + kingNums==13)
			return true;
		return false;
	}

	//全小Ok
	private static boolean checkIsAllSmall(PlayerByCloudFree p) {
		PlayerByCloudFree tempPlayer = p;
		tempPlayer.sortByRank();
		//int maxRank = tempPlayer.handCard.get(12).rank;
		int maxRank = 0;
		for(int i = 0; i < tempPlayer.handCard.size(); i++){
			Card temp = p.handCard.get(i);
			if(temp.rank>20) continue;
			maxRank = (maxRank>temp.rank)?maxRank:temp.rank;
		}
		if(maxRank<=7) return true;
		return false;
	}

	//全大OK
	private static boolean checkIsAllBig(PlayerByCloudFree p) {
		PlayerByCloudFree tempPlayer = p;
		tempPlayer.sortByRank();
		int minRank = tempPlayer.handCard.get(0).rank;
		if(minRank>7) return true;
		return false;
	}

	//三分天下
	private static boolean checkIsDividByThree(PlayerByCloudFree p) {
		PlayerByCloudFree tempPlayer = p;
		int kingNums = p.checkNumOfKings();
		if(kingNums==110) kingNums = 2;
		else if(kingNums>0) kingNums = 1;
		int[] numOfRank = new int[13];
		int numOfFour = 0;
		for(int i = 0; i < tempPlayer.handCard.size(); i++) {
			if(tempPlayer.handCard.get(i).rank > 40) continue;
			numOfRank[tempPlayer.handCard.get(i).rank-1]++;
		}

		for(int i = 0; i < 13; i++) {
			if(numOfRank[i] == 4) numOfFour++;
			else if(numOfRank[i] == 3){
				if(kingNums>0){
					kingNums--;
					numOfFour++;
				}
			}else if(numOfRank[i] == 2){
				if(kingNums==2){
					kingNums-=2;
					numOfFour++;
				}
			}
		}

		if(numOfFour == 3) return true;
		return false;
	}


	private static int getPreRankForTongHuaShun(List<Card> list){
		for(int i = 0; i < list.size(); i++){
			if(!isUsed[i]) return list.get(i).rank - 1;
		}
		return 0;
	}


	private static  int getPreType(List<Card> list) {
		for(int i = 0; i < list.size(); i++){
			if(!isUsed[i]) return list.get(i).type;
		}
		return 0;
	}

	private static void dfsForTongHuaShunZi(int kingNums, int now, List<Card> list, int preRank,
									 int numOfThree, int numOfFive, int preColor){

		if(numOfFive == 2 && numOfThree == 1){
			isSanTongHuaShun = true;
			return;
		}

		if(isSanTongHuaShun) return;

		for(int i = 0; i < list.size(); i++){
			Card temp = list.get(i);
			if(temp.rank>40) continue;
			if(isUsed[i]) continue;
			if(temp.rank - preRank == 1&&preColor == temp.type) {
				isUsed[i] = true;
				int ans = getPreRankForTongHuaShun(list);
				int color = getPreType(list);
				if(now+1 == 3){
					dfsForTongHuaShunZi(kingNums, now + 1, list, preRank + 1, numOfThree, numOfFive, preColor);
					dfsForTongHuaShunZi(kingNums, 0, list, ans, numOfThree + 1, numOfFive, color);

				}else if(now+1 == 5){
					dfsForTongHuaShunZi(kingNums, 0, list, ans, numOfThree, numOfFive+1, color);
				}else dfsForTongHuaShunZi(kingNums, now+1, list, preRank+1, numOfThree, numOfFive, preColor);
				isUsed[i] = false;
			}else if(temp.rank - preRank > 1)
			{
				int ans = getPreRankForTongHuaShun(list);
				int color = getPreType(list);
				if(kingNums >= 1) {
					if(now+1 == 3){
						dfsForTongHuaShunZi(kingNums - 1, now+1, list,preRank+1,  numOfThree, numOfFive, preColor);
						dfsForTongHuaShunZi(kingNums - 1, 0, list, ans, numOfThree+1, numOfFive, color);
					}else if(now+1 == 5){
						dfsForTongHuaShunZi(kingNums - 1, 0, list, ans, numOfThree, numOfFive+1, color);
					}else dfsForTongHuaShunZi(kingNums - 1, now+1, list, preRank+1, numOfThree, numOfFive, preColor);
				}
			}
		}
	}


	private static boolean checkIsThreeFlushType(PlayerByCloudFree p){

		int kingNums = p.checkNumOfKings();
		if(kingNums == 110) kingNums = 2;
		else if(kingNums>0) kingNums = 1;
		p.handCard.sort(PlayerByCloudFree.cardRankComparator);
		for(int i = 0; i  < p.handCard.size(); i++)
			dfsForTongHuaShunZi(kingNums, 0, p.handCard, p.handCard.get(i).rank - 1, 0, 0, p.handCard.get(i).type);
		if(isSanTongHuaShun) {
			isSanTongHuaShun = false;
			for(int i = 0; i < isUsed.length; i++)
				isUsed[i] = false;
			return true;
		}
		return false;
	}

	//十二皇族
	private static boolean checkIsTwelveRoyalty(PlayerByCloudFree p) {

		PlayerByCloudFree tempPlayer = p;
		tempPlayer.sortByRank();
		int minRank = tempPlayer.handCard.get(0).rank;
		if(minRank>=10) return true;
		return false;
	}

	//一条龙
	private static boolean checkIsDragen(PlayerByCloudFree p) {
		PlayerByCloudFree tempPlayer = p;
		int kingNums = p.checkNumOfKings();
		if(kingNums==110) kingNums = 2;
		else if(kingNums>0) kingNums = 1;
		int[] rankNum = new int[13];
		for(int i = 0; i < tempPlayer.handCard.size(); i++) {
			if(tempPlayer.handCard.get(i).rank>40) continue;
			rankNum[tempPlayer.handCard.get(i).rank-1] ++;
		}
		for(int i = 0; i < 13; i++) {
			if(rankNum[i]>1)
				return false;
			if(rankNum[i]==0){
				if(kingNums>0){
					kingNums--;
				}else return false;
			}
		}
		return true;
	}

	//至尊青龙
	private static boolean checkIsKingDragen(PlayerByCloudFree p) {
		PlayerByCloudFree tempPlayer = p;
		tempPlayer.sortByRank();
		int type = tempPlayer.handCard.get(0).type;
		//System.out.println(type);
		for(int i = 1; i < tempPlayer.handCard.size(); i++) {
			if(tempPlayer.handCard.get(i).type==0) continue;
			if(Integer.compare(type, tempPlayer.handCard.get(i).type)!=0)
				return false;
		}
		if(checkIsDragen(tempPlayer)) return true;
		return false;
	}

	private static int extraDeal(List<Card> l, int kingNums) {
		int[] num = new int[13];
		int backNum = 0;
		List<Card> list = l;
		for(int i = 0; i < list.size(); i++) {
			num[list.get(i).rank-1]++;
		}
		for(int i = 0;i < 13; i++) {
			int cardNum = 0;
			while(num[i]!=0) {
				num[i]--;
				cardNum++;
				for(int j = i+1; j < 13; j++) {
					if(num[j]==0) {
						if(cardNum == 3) {
							backNum += 100;
						}
						cardNum = 0;
						break;
					}else {
						num[j] --;
						cardNum++;
						if(cardNum == 5) {
							backNum += 1;
							cardNum = 0;
							break;
						}
						if(cardNum == 3 && j==12)
							backNum += 100;
					}
				}
			}
		}
		return backNum;
	}

	private static void dealForallType(List<Card> type1, PlayerByCloudFree p) {

		if(type1.size()>=5) {
			for(int a = 0; a < type1.size(); a++) {
				for(int b = 0; b <type1.size(); b++) {
					if(b == a) continue;
					for(int c = 0; c < type1.size(); c++) {
						if(c == b || c == a) continue;
						for(int d = 0; d < type1.size(); d++) {
							if(d == c || d == b ||d == a) continue;
							for(int e = 0; e < type1.size(); e++) {
								if(e == a || e == b || e == c || e == d) continue;
								AllType temp = new AllType();
								temp.card.add(type1.get(a));
								temp.card.add(type1.get(b));
								temp.card.add(type1.get(c));
								temp.card.add(type1.get(d));
								temp.card.add(type1.get(e));
								p.allType.add(temp);
							}
						}
					}
				}
			}
		}

	}

	private static void dealAlltype(PlayerByCloudFree p) {
		PlayerByCloudFree tempPlayer = p;
		tempPlayer.sortByType();
		List<Card> type1 = new ArrayList<Card>();
		List<Card> type2 = new ArrayList<Card>();
		List<Card> type3 = new ArrayList<Card>();
		List<Card> type4 = new ArrayList<Card>();
		for(int i = 0; i < tempPlayer.handCard.size(); i++) {
			switch(tempPlayer.handCard.get(i).type) {
				case 1:
					type1.add(tempPlayer.handCard.get(i));
					break;
				case 2:
					type2.add(tempPlayer.handCard.get(i));
					break;
				case 3:
					type3.add(tempPlayer.handCard.get(i));
					break;
				case 4:
					type4.add(tempPlayer.handCard.get(i));
					break;
				default:
					type1.add(tempPlayer.handCard.get(i));
					type2.add(tempPlayer.handCard.get(i));
					type3.add(tempPlayer.handCard.get(i));
					type4.add(tempPlayer.handCard.get(i));
					break;
			}
			type1.sort(PlayerByCloudFree.cardRankComparator);
			type4.sort(PlayerByCloudFree.cardRankComparator);
			type3.sort(PlayerByCloudFree.cardRankComparator);
			type2.sort(PlayerByCloudFree.cardRankComparator);
			dealForallType(type1, p);
			dealForallType(type2, p);
			dealForallType(type3, p);
			dealForallType(type4, p);
		}
	}

	private static void dealFour(PlayerByCloudFree p) {
		PlayerByCloudFree tempPlayer = p;
		tempPlayer.sortByRank();
		Card tempCard = tempPlayer.handCard.get(0);
		int tempNum = 1;
		TieZhi tempT = new TieZhi();
		tempT.tiezhi.add(tempCard);
		for(int i = 1; i < tempPlayer.handCard.size(); i++) {
			Card temp = tempPlayer.handCard.get(i);
			if(temp.rank==tempCard.rank) {
				tempNum++;
				tempT.tiezhi.add(temp);
			}
			else {
				tempNum = 1;
				tempCard = temp;
				tempT.tiezhi.clear();
				tempT.tiezhi.add(temp);
			}
			if(tempNum == 4) {
				TieZhi in = new TieZhi();
				for(int ii = 0; ii < tempT.tiezhi.size(); ii++) {
					in.tiezhi.add(tempT.tiezhi.get(ii));
				}
				p.quadrupleCard.add(in);
				Three three1 = new Three();
				three1.three.add(new Card(in.tiezhi.get(0).type, in.tiezhi.get(0).rank));
				three1.three.add(new Card(in.tiezhi.get(1).type, in.tiezhi.get(1).rank));
				three1.three.add(new Card(in.tiezhi.get(2).type, in.tiezhi.get(2).rank));
				p.tripleCard.add(three1);


				Three three2 = new Three();
				three2.three.add(new Card(in.tiezhi.get(0).type, in.tiezhi.get(0).rank));
				three2.three.add(new Card(in.tiezhi.get(1).type, in.tiezhi.get(1).rank));
				three2.three.add(new Card(in.tiezhi.get(3).type, in.tiezhi.get(3).rank));
				p.tripleCard.add(three2);


				Three three3 = new Three();
				three3.three.add(new Card(in.tiezhi.get(0).type, in.tiezhi.get(0).rank));
				three3.three.add(new Card(in.tiezhi.get(3).type, in.tiezhi.get(3).rank));
				three3.three.add(new Card(in.tiezhi.get(2).type, in.tiezhi.get(2).rank));
				p.tripleCard.add(three3);


				Three three4 = new Three();
				three4.three.add(new Card(in.tiezhi.get(3).type, in.tiezhi.get(0).rank));
				three4.three.add(new Card(in.tiezhi.get(1).type, in.tiezhi.get(1).rank));
				three4.three.add(new Card(in.tiezhi.get(2).type, in.tiezhi.get(2).rank));
				p.tripleCard.add(three4);

				tempNum = 1;
				tempT.tiezhi.clear();
			}
		}
	}

	private static ShunZi getnewShunZi(ShunZi temp){
		ShunZi tempShun = new ShunZi();
		for(Card i : temp.shunZi){
			Card tempCard = new Card(i.type, i.rank);
			tempShun.shunZi.add(tempCard);
		}
		return tempShun;
	}

	private static int getCardPos(PlayerByCloudFree p, Card temp){
	    p.handCard.sort(PlayerByCloudFree.cardRankComparator);
	    for(int i = 0; i < p.handCard.size(); i++){
	        Card t = p.handCard.get(i);
	        if(isEquals(t, temp))
	            return i;
        }
        return -1;
    }

	private static void dfsForSingleShunZi(int kingNums, int now, PlayerByCloudFree p, int preRank,
                                           ShunZi tempShunZi, int pos){
		for(int i = pos; i < p.handCard.size(); i++) {
			Card temp = p.handCard.get(i);
			if(temp.rank > 40) continue;
			if(isUsed[i]) continue;
			if(temp.rank - preRank == 1) {

				if(now + 1 == 5){
					if(kingNums > 0){
						int Val = kingNums == 10?10:100;
						ShunZi shun = getnewShunZi(tempShunZi);
						if(kingNums == 10) shun.shunZi.add(new Card(0, 44));
						else shun.shunZi.add(new Card(0, 88));
						p.shunZi.add(shun);
					}

					ShunZi shun = getnewShunZi(tempShunZi);
					shun.shunZi.add(temp);
					p.shunZi.add(shun);
					for(int my_susu = 0; my_susu < p.handCard.size(); my_susu++) {
						if(isUsed[my_susu]) continue;
						isUsed[i] = true;
						dfsForSingleShunZi(kingNums, now + 1, p, p.handCard.get(my_susu).rank - 1, shun, my_susu++);
						isUsed[i] = false;
					}
				}else if(now + 1 <= 9){
					ShunZi shun = getnewShunZi(tempShunZi);
					shun.shunZi.add(temp);
					isUsed[i] = true;
					dfsForSingleShunZi(kingNums, now+1, p, preRank+1, shun, i+1);
					isUsed[i] = false;
					if(kingNums > 0){
						int Val = kingNums == 10?10:100;
						ShunZi shunshun = getnewShunZi(tempShunZi);
						if(kingNums == 10) shunshun.shunZi.add(new Card(0, 44));
						else shunshun.shunZi.add(new Card(0, 88));
						//p.shunZi.add(shunshun);
						dfsForSingleShunZi(kingNums-Val, now+1, p, preRank+1, shunshun, i+1);
						//continue;
					}
				}else if(now+1 >= 9) {
					//System.out.println(temp.rank);
                    ShunZi shun = getnewShunZi(tempShunZi);
                    shun.shunZi.add(temp);
                    p.shunZi.add(shun);
                    return;
                }
			}else if(temp.rank - preRank > 1) {
				int ans = getPreRank(p);
				if(kingNums > 0) {

					if(now + 1 == 10){
						int Val = kingNums == 10?10:100;
						ShunZi shun = getnewShunZi(tempShunZi);
						if(kingNums == 10) shun.shunZi.add(new Card(0, 44));
						else shun.shunZi.add(new Card(0, 88));
						p.shunZi.add(shun);
						continue;
					}

					if(now+1 == 5){
						//ShunZi ttt = getnewShunZi(tempShunZi);
						//p.shunZi.add(tempShunZi);
                        int Val = kingNums == 10?10:100;
						ShunZi shun = getnewShunZi(tempShunZi);
						if(kingNums==10) shun.shunZi.add(new Card(0, 44));
						else shun.shunZi.add(new Card(0, 88));
						ShunZi ttt = getnewShunZi(shun);
						p.shunZi.add(ttt);
						for(Card ppp : ttt.shunZi){
							//System.out.println("my_test"+ppp.type);
						}
						for(int wode_susu = 0; wode_susu<p.handCard.size(); wode_susu++) {
							if(isUsed[wode_susu]) continue;
							dfsForSingleShunZi(kingNums - Val, now + 1, p, p.handCard.get(wode_susu).rank-1, shun, wode_susu);
						}
					}else if(now+1 < 10){
						ShunZi shun = getnewShunZi(tempShunZi);
						int Val = 0;
						if(kingNums == 10){
							Val = 10;
							shun.shunZi.add(new Card(0, 44));
						}else{
							Val = 100;
							shun.shunZi.add(new Card(0, 88));
						}
						dfsForSingleShunZi(kingNums - Val, now+1, p, preRank+1, shun, i);
					}else if(now+1 == 10){
                        ShunZi shun = getnewShunZi(tempShunZi);
                        if(kingNums==10) shun.shunZi.add(new Card(0, 44));
                        else shun.shunZi.add(new Card(0, 88));
                        p.shunZi.add(shun);
                        return;
                    }
				}else return;
			}else if(temp.rank == preRank){
				//dfsForSingleShunZi(kingNums, now, p, preRank, getnewShunZi(tempShunZi), i+1);
				ShunZi shun1 = getnewShunZi(tempShunZi);
				Card t = shun1.shunZi.get(shun1.shunZi.size()-1);
				shun1.shunZi.remove(t);
                int ppp = getCardPos(p, t);
				shun1.shunZi.add(temp);
				isUsed[i] = true;
				//dfsForSingleShunZi();
                isUsed[ppp] = false;
				dfsForSingleShunZi(kingNums, now, p, preRank, shun1, i+1);
				isUsed[i] = false;
				isUsed[ppp] = true;
			}
		}
	}

	private static void dealShunZi(PlayerByCloudFree tempPlayer){

		int kingNums = tempPlayer.checkNumOfKings();
		tempPlayer.handCard.sort(PlayerByCloudFree.cardRankComparator);
		for(int i = 0; i < tempPlayer.handCard.size(); i++){
            ShunZi tempShunZi = new ShunZi();
			for(int ii = 0; ii < 13; ii++){
				isUsed[i] = false;
			}
			dfsForSingleShunZi(kingNums, 0, tempPlayer, tempPlayer.handCard.get(i).rank-1, tempShunZi, i);
		}
	}


	private static void dealTonghuaShun(PlayerByCloudFree p) {
		for(int i = 0; i < p.shunZi.size(); i++) {
			ShunZi tempShunZi = p.shunZi.get(i);
			boolean flag = false;
			/*if(tempShunZi.shunZi.size() == 10){
				for(int ii = 0; ii < tempShunZi.shunZi.size(); ii++){
					System.out.println("My_susu"+tempShunZi.shunZi.get(ii).type + "  " + tempShunZi.shunZi.get(ii).rank);
				}
			}*/
			ShunZi tt = new ShunZi();
			for(int j = 0; j < tempShunZi.shunZi.size(); j++) {

				if(tempShunZi.shunZi.get(j).rank > 20){
					tt.shunZi.add(tempShunZi.shunZi.get(j));
					if(j==4){
						p.tonghuaShun.add(tt);
					}
					continue;
				}

				if(j<5) {
					if (tempShunZi.shunZi.get(j).type
							!= tempShunZi.shunZi.get(0).type)
						flag = true;
				}
				else{
					if(tempShunZi.shunZi.get(1).type!=tempShunZi.shunZi.get(6).type)
						flag = true;
				}
				if(flag) break;
				if(!flag)
					tt.shunZi.add(tempShunZi.shunZi.get(j));
				if(tt.shunZi.size() == 5) p.tonghuaShun.add(getnewShunZi(tt));
			}
			if(!flag&&tt.shunZi.size() == 10) {
				p.tonghuaShun.add(tt);
			}
		}
	}


	//C(3,2)
	private static void dealThree(PlayerByCloudFree p) {
		PlayerByCloudFree tempPlayer = p;
		tempPlayer.sortByRank();
		Card temp = tempPlayer.handCard.get(0);
		int cardNum = 1;
		for(int i = 1; i < tempPlayer.handCard.size(); i++) {
			Card tempCard = tempPlayer.handCard.get(i);
			if(tempCard.rank==temp.rank) {
				cardNum++;
			}else {
				cardNum = 1;
				temp = tempCard;
			}
			if(cardNum == 3) {
				Three tt = new Three();
				tt.three.add(temp);
				tt.three.add(tempPlayer.handCard.get(i-1));
				tt.three.add(tempCard);
				p.tripleCard.add(tt);
				Two duizi = new Two();
				duizi.two.add(temp);
				duizi.two.add(tempCard);
				p.doubleCard.add(duizi);
				duizi = new Two();
				duizi.two.add(temp);
				duizi.two.add(tempPlayer.handCard.get(i-1));
				p.doubleCard.add(duizi);
				duizi = new Two();
				duizi.two.add(tempCard);
				duizi.two.add(tempPlayer.handCard.get(i-1));
				p.doubleCard.add(duizi);
				cardNum = 0;
			}
		}
	}

	//得到对子
	private static void  dealTwo(PlayerByCloudFree p) {
		PlayerByCloudFree tempPlayer = p;
		Card tempCard = tempPlayer.handCard.get(0);
		int cardNum = 1;
		for(int i = 1; i < tempPlayer.handCard.size(); i++) {
			Card temp = tempPlayer.handCard.get(i);
			if(tempCard.rank==temp.rank) {
				cardNum++;
			}else {
				cardNum = 1;
				tempCard = temp;
			}
			if(cardNum == 2) {
				Two tt = new Two();
				tt.two.add(temp);
				tt.two.add(tempCard);
				cardNum = 0;
				p.doubleCard.add(tt);
			}
		}
	}


	//algorithm


	public static void dealHandCard(PlayerByCloudFree p) {

		dealAlltype(p);
		dealFour(p);
		dealShunZi(p);
		dealTonghuaShun(p);
		dealThree(p);
		dealTwo(p);
	}

	/*
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 */

	public static boolean checkIsUsed(Card checkCard, PlayerByCloudFree p) {
		for(int i = 0; i < p.handCardCopy.size(); i++) {
			Card tempCard = p.handCardCopy.get(i);
			if(checkCard.rank == tempCard.rank
					&&checkCard.type == tempCard.type)
				if(p.vis[i] == true)
					return true;
		}
		return false;
	}

	public static boolean isEquals(Card a, Card b) {
		if(a.rank==b.rank&&a.type==b.type)
			return true;
		return false;
	}

	public static void makeVis(PlayerByCloudFree p, Card tempCard) {
		for(int i = 0; i < p.handCardCopy.size(); i++) {
			Card temp = p.handCardCopy.get(i);
			if(isEquals(tempCard, temp))
				p.vis[i] = true;
		}
	}

	public static void cancleVis(PlayerByCloudFree p, Card tempCard){
		for(int i = 0; i < p.handCardCopy.size(); i++) {
			Card temp = p.handCardCopy.get(i);
			if(isEquals(tempCard, temp))
				p.vis[i] = false;
		}
	}

	public static void addTongHuaShun(List<Card> list, ShunZi begin, PlayerByCloudFree p) {
		for(Card i:begin.shunZi) {
			list.add(i);
			makeVis(p, i);
		}
	}

	private static int checkIsExistInTongHuaShun(PlayerByCloudFree p, Card card){
		int ans = 0;
		for(Three three : p.tripleCard){
			for(Card t : three.three){
				if(card.rank == t.rank && card.type == t.type)
					ans++;
			}
		}
		return ans;
	}

	public static void analyzeCardTongHuaShun(PlayerByCloudFree p) {
		ShunZi temp = new ShunZi();

		for (int i = 0; i < p.tonghuaShun.size(); i++) {
			for (Card card : p.tonghuaShun.get(i).shunZi) {
				p.tonghuaShun.get(i).level += 5*checkIsExistInTongHuaShun(p, card);
					p.tonghuaShun.get(i).level+=checkIsExistInDuiZi(p,card);
					p.tonghuaShun.get(i).level+=2*checkIsExistInSanTiao(p,card);
					p.tonghuaShun.get(i).level+=3*checkIsExistInKing(p,card);
			}
		}
		p.tonghuaShun.sort(PlayerByCloudFree.cardShunZiComparator);
		for (int i = 0; i < p.tonghuaShun.size(); i++) {
			ShunZi tempShunZi = p.tonghuaShun.get(i);
			boolean flag = false;
			for (int j = 0; j < 5; j++) {
				Card tempCard = tempShunZi.shunZi.get(j);
				if (checkIsUsed(tempCard, p)) {
					flag = true;
					break;
				}
			}
			if (flag) continue;
			if (p.end.size() == 0) {
				for (int ii = 0; ii < 5; ii++) {
					Card tt = tempShunZi.shunZi.get(ii);
					makeVis(p, tt);
					p.end.add(tt);
				}
				p.endType = "tonghuashun";
				p.endRank = 10;
			} else if (p.mid.size() == 0) {
				for (int ii = 0; ii < 5; ii++) {
					Card tt = tempShunZi.shunZi.get(ii);
					makeVis(p, tt);
					p.mid.add(tt);
				}
				p.midType = "tonghuashun";
				p.midRank = 10;
			}
		}
	}

    /*public static void analyzeCardTongHuaShun(PlayerByCloudFree p){
	    p.tonghuaShun.sort(PlayerByCloudFree.cardTongHuaShunComparator);

	    for(int i = 0; i < p.tonghuaShun.size(); i++){
	        ShunZi temp = p.tonghuaShun.get(i);
	        if(temp.shunZi.size() == 10){
	        	if(temp.shunZi.get(0).rank>=temp.shunZi.get(5).rank) {
					for (int ii = 5; ii < 10; ii++) {
						Card tt = temp.shunZi.get(ii);
						p.mid.add(tt);
						makeVis(p, tt);
					}

					p.midType = "tonghuashun";
					p.midRank = 10;
					p.endRank = 10;
					p.endType = "tonghuashun";
					for (int ii = 0; ii < 5; ii++) {
						Card tt = temp.shunZi.get(ii);
						p.end.add(tt);
						makeVis(p, tt);
					}
					break;
				}else{
					for (int ii = 0; ii < 5; ii++) {
						Card tt = temp.shunZi.get(ii);
						p.mid.add(tt);
						makeVis(p, tt);
					}

					p.midType = "tonghuashun";
					p.midRank = 10;
					p.endRank = 10;
					p.endType = "tonghuashun";
					for (int ii = 5; ii < 10; ii++) {
						Card tt = temp.shunZi.get(ii);
						p.end.add(tt);
						makeVis(p, tt);
					}
					break;
				}
            }else{
                p.endRank = 10;
                p.endType = "tonghuashun";
                for(int ii = 0; ii < 5; ii++){
                    Card tt = temp.shunZi.get(ii);
                    p.end.add(tt);
                    makeVis(p, tt);
                }
                break;
            }
        }
    }*/


	public static void analyzeFour(PlayerByCloudFree p) {
		for(int i = 0; i < p.quadrupleCard.size(); i++) {
			int endSize = p.end.size();
			TieZhi tempCard = p.quadrupleCard.get(i);
			boolean flag = false;
			for(int j = 0; j < 4; j++) {
				if(checkIsUsed(tempCard.tiezhi.get(j), p)) flag = true;
				if(flag==true) {
					break;
				}
			}

			if(flag == false)
				for(int j = 0; j < 4; j++) {
					if(endSize == 0) {
						makeVis(p, tempCard.tiezhi.get(j));
						p.endType = "tiezhi";
						p.endRank = 9;
						p.end.add(tempCard.tiezhi.get(j));
					}
					else  {
						makeVis(p, tempCard.tiezhi.get(j));
						p.midType = "tiezhi";
						p.midRank = 9;
						p.mid.add(tempCard.tiezhi.get(j));
					}
				}
		}
	}

	private static void changeCard(Card a, Card b){
		Card temp = new Card(a.type, a.rank);
		a.type = b.type;
		a.rank = b.rank;
		b.type = temp.type;
		b.rank = temp.rank;
	}

	public static void analyzeHulu(PlayerByCloudFree p) {
		for(int i = p.tripleCard.size() - 1; i >= 0; i--) {
			Three tempThree = p.tripleCard.get(i);
			boolean flag = false;
			for(int j = 0; j < tempThree.three.size(); j++)
				if(checkIsUsed(tempThree.three.get(j), p)==true){
					flag = true;
					break;
				}
			if(flag == true) continue;
			for(int j = 0; j < p.doubleCard.size(); j++) {
				flag = false;
				Two tempTwo = p.doubleCard.get(j);

				for(int jj = 0; jj < tempTwo.two.size(); jj++)
					if(checkIsUsed(tempTwo.two.get(jj), p)==true){
						flag = true;
						break;
					}
				if(flag) continue;

					for(Card card_three:tempThree.three){
						for(Card card_two:tempTwo.two){
							if(card_three.rank == card_two.rank)
							{
								flag = true;
								break;
							}
						}
						if(flag) break;
					}
				if(flag) continue;
				else {
					if(p.end.size()==0) {
						for(int ii = 0; ii < 3; ii++) {
							p.end.add(tempThree.three.get(ii));
							makeVis(p, tempThree.three.get(ii));
						}
						for(int jj = 0; jj < 2; jj++) {
							p.end.add(tempTwo.two.get(jj));
							makeVis(p,tempTwo.two.get(jj));
						}
						p.endType = "hulu";
						p.endRank = 8;
						break;
					}
					else if(p.mid.size()==0) {
						for(int ii = 0; ii < 3; ii++) {
							p.mid.add(tempThree.three.get(ii));
							makeVis(p, tempThree.three.get(ii));
						}
						for(int jj = 0; jj < 2; jj++) {
							p.mid.add(tempTwo.two.get(jj));
							makeVis(p,tempTwo.two.get(jj));
						}
						p.midType = "hulu";
						p.midRank = 8;
						break;
					}
				}
			}
		}
	}

	private static int checkIsExistInShunZi(PlayerByCloudFree p, Card card){
		int time = 0;
		for(ShunZi shunzi:p.shunZi){
			for(Card c:shunzi.shunZi){
				if(card.rank == c.rank && card.type == c.type)
					time++;
			}
		}
		return time;
	}

	public static void analyzeAllType(PlayerByCloudFree p) {
		//System.out.print(p.allType.size());

		for(int i = 0; i < p.allType.size(); i++){
			for(Card card : p.allType.get(i).card){
					p.allType.get(i).level += 5*checkIsExistInKing(p, card);
					p.allType.get(i).level += 100*checkIsExistInSanTiao(p, card);
					p.allType.get(i).level += checkIsExistInDuiZi(p, card);
					p.allType.get(i).level+=4*checkIsExistInShunZi(p,card);
			}
		}

		p.allType.sort(PlayerByCloudFree.cardAllTypeComparator);
		for(int i = 0; i < p.allType.size(); i++) {
			AllType tempAllType = p.allType.get(i);
			boolean flag = false;
			for(int j = 0; j < tempAllType.card.size(); j++) {
				Card tempCard = tempAllType.card.get(j);
				if(checkIsUsed(tempCard, p)) {
					flag = true;
					break;
				}
			}
			if(flag) continue;
			if(p.end.size()==0) {
				for(int ii = 0; ii < tempAllType.card.size(); ii++) {
					Card tt = tempAllType.card.get(ii);
					makeVis(p, tt);
					p.end.add(tt);
				}
				p.endType = "tonghua";
				p.endRank = 7;
			}else if(p.mid.size() == 0){
				for(int ii = 0; ii < tempAllType.card.size(); ii++) {
					Card tt = tempAllType.card.get(ii);
					makeVis(p, tt);
					p.mid.add(tt);
				}
				p.midType = "tonghua";
				p.midRank = 7;
			}
		}

	}

	private static int checkIsExistInKing(PlayerByCloudFree p, Card card){
		int time = 0;
		if(card.rank > 20) time++;
		return time;
	}

	private static int checkIsExistInSanTiao(PlayerByCloudFree p, Card card){
		int time = 0;
		for(Three three : p.tripleCard){
			for(Card t : three.three){
				if(card.rank == t.rank && card.type == t.type)
					time++;
			}
		}
		return time > 0?1:0;
	}

	private static int checkIsExistInDuiZi(PlayerByCloudFree p, Card card){
		int time = 0;
		for(Two three : p.doubleCard){
			for(Card t : three.two){
				if(card.rank == t.rank && card.type == t.type)
					time ++;
			}
		}
		return time > 0?1:0;
	}

	public static void analyzeShunZi(PlayerByCloudFree p) {

		ShunZi temp = new ShunZi();

		for (int i = 0; i < p.shunZi.size(); i++) {
			for (Card card : p.shunZi.get(i).shunZi) {
					p.shunZi.get(i).level += 3*checkIsExistInKing(p, card);
					p.shunZi.get(i).level += 2*checkIsExistInSanTiao(p, card);
					p.shunZi.get(i).level += checkIsExistInDuiZi(p, card);
			}
		}
		p.shunZi.sort(PlayerByCloudFree.cardShunZiComparator);
		for (int i = 0; i < p.shunZi.size(); i++) {
			ShunZi tempShunZi = p.shunZi.get(i);
			boolean flag = false;
			for (int j = 0; j < 5; j++) {
				Card tempCard = tempShunZi.shunZi.get(j);
				if (checkIsUsed(tempCard, p)) {
					flag = true;
					break;
				}
			}
			if (flag) continue;
			if (p.end.size() == 0) {
				for (int ii = 0; ii < 5; ii++) {
					Card tt = tempShunZi.shunZi.get(ii);
					makeVis(p, tt);
					p.end.add(tt);
				}
				p.endType = "shunzi";
				p.endRank = 6;
			} else if (p.mid.size() == 0) {
				for (int ii = 0; ii < 5; ii++) {
					Card tt = tempShunZi.shunZi.get(ii);
					makeVis(p, tt);
					p.mid.add(tt);
				}
				p.midType = "shunzi";
				p.midRank = 6;
			}
		}
	}

	public static void analyzeSanTiao(PlayerByCloudFree p) {
		for(int i = p.tripleCard.size() - 1; i >=  0; i--) {

			if(p.tripleCard.get(i).three.get(0).rank!=p.tripleCard.get(i).three.get(1).rank
					&&p.tripleCard.get(i).three.get(1).rank <40) continue;


			Three tempThree = p.tripleCard.get(i);
			boolean flag = false;
			for(int j = 0; j < tempThree.three.size(); j++) {
				Card tempCard = tempThree.three.get(j);
				if(checkIsUsed(tempCard, p)) {
					flag = true;
					//System.out.println("cao"+tempCard.rank);
					break;
				}
			}
			if(flag == true) continue;
			if(p.end.size()==0) {
				for(int ii = 0; ii < tempThree.three.size(); ii++) {
					Card cardIn = tempThree.three.get(ii);
					makeVis(p, cardIn);
					p.end.add(cardIn);
				}
				p.endType = "santiao";
				p.endRank = 5;
			}else if(p.mid.size()==0){
				for(int ii = 0; ii < tempThree.three.size(); ii++) {
					Card cardIn = tempThree.three.get(ii);
					makeVis(p, cardIn);
					p.mid.add(cardIn);
				}
				p.midType = "santiao";
				p.endRank = 5;
			}else if(p.head.size()==0) {
				for(int ii = 0; ii < tempThree.three.size(); ii++) {
					Card cardIn = tempThree.three.get(ii);
					makeVis(p, cardIn);
					p.head.add(cardIn);
				}
				p.headType = "santiao";
				p.headRank = 5;
			}
		}
	}

	public static void analyzeTwoDuiZi(PlayerByCloudFree p) {

		Two duiZi1 = new Two();
		Two duiZi2 = new Two();
		int tempVis = 0;
		for(int i = p.doubleCard.size()-1; i >= 0; i--) {
			Two tempTwo = p.doubleCard.get(i);
			boolean flag = false;
			for(int j = 0; j < tempTwo.two.size(); j++) {
				Card tempCard = tempTwo.two.get(j);
				if(checkIsUsed(tempCard, p)) {
					flag = true;
					break;
				}
			}
			if(flag) continue;
			if(tempVis == 0) {
				tempVis++;
				duiZi1 = tempTwo;
			}
			else {
				duiZi2 = tempTwo;
				boolean sun = false;
				for(int ii = 0; ii < duiZi1.two.size(); ii++){
					for(int jj = 0; jj < duiZi2.two.size(); jj++){
						if(isEquals(duiZi1.two.get(ii), duiZi2.two.get(jj)))
						{
							sun = true;
							break;
						}
					}
				}
				if(sun){
					continue;
				}

				tempVis = 0;
				if(p.end.size() == 0) {
					p.end.add(duiZi1.two.get(0));
					p.end.add(duiZi1.two.get(1));
					p.end.add(duiZi2.two.get(0));
					p.end.add(duiZi2.two.get(1));
					flag = true;
					p.endType = "liangdui";
					p.endRank = 4;
				}else if(p.mid.size() == 0) {
					p.mid.add(duiZi1.two.get(0));
					p.mid.add(duiZi1.two.get(1));
					p.mid.add(duiZi2.two.get(0));
					p.mid.add(duiZi2.two.get(1));
					flag = true;
					p.midType = "liangdui";
					p.midRank = 4;
				}
				if(flag) {
					makeVis(p, duiZi1.two.get(0));
					makeVis(p, duiZi1.two.get(1));
					makeVis(p, duiZi2.two.get(0));
					makeVis(p, duiZi2.two.get(1));
				}
			}
		}
	}

	public static void analyzeDuiZi(PlayerByCloudFree p) {
		for(int i = p.doubleCard.size() - 1; i >= 0; i--) {
			Two tempTwo = p.doubleCard.get(i);
			boolean flag = false;
			for(int j =0; j < tempTwo.two.size(); j++) {
				Card tempCard = tempTwo.two.get(j);
				if(checkIsUsed(tempCard, p)) {
					flag = true;
					break;
				}
			}
			if(flag) continue;
			if(p.end.size()<=3&&(!(p.end.size()>0&&p.mid.size()==0))) {
				for(int ii = 0; ii < tempTwo.two.size(); ii++) {
					Card temp = tempTwo.two.get(ii);
					p.end.add(temp);
					makeVis(p, temp);
				}
				p.endType = "duizi";
				p.endRank = 3;
				if(p.end.size() == 5) {
					p.endRank = 8;
					p.endType = "hulu";
				}else if(p.end.size() == 4) {
					p.endRank = 4;
					p.endType = "liangdui";
				}
			}else if(p.mid.size()<=3&&(!(p.mid.size()>0&&p.head.size()==0))) {
				for(int ii = 0; ii < tempTwo.two.size(); ii++) {
					Card temp = tempTwo.two.get(ii);
					p.mid.add(temp);
					makeVis(p, temp);
				}
				p.midType = "duizi";
				p.midRank = 3;
				if(p.mid.size() == 5) {
					p.midRank = 8;
					p.midType = "hulu";
				}else if(p.mid.size() == 4) {
					p.midRank = 4;
					p.midType = "liangdui";
				}
			}else if(p.head.size()<=1) {
				for(int ii = 0; ii < tempTwo.two.size(); ii++) {
					Card temp = tempTwo.two.get(ii);
					p.head.add(temp);
					makeVis(p, temp);
				}
				p.headType = "duizi";
				p.headRank = 3;
			}
		}
	}

	public static void analyzeLeftCard(PlayerByCloudFree p) {
		for(int i = 0; i < p.handCardCopy.size(); i++) {
			if(p.vis[i]) continue;

			Card tempCard = p.handCardCopy.get(i);
			if(p.end.size() < 5){
				p.end.add(tempCard);
			}
			else if(p.mid.size() < 5)p.mid.add(tempCard);
			else if(p.head.size() < 3) p.head.add(tempCard);
		}
	}

	public static void getCardSortedBack(PlayerByCloudFree p) {
		//dealHandCard(p);
		p.tonghuaShun.sort(PlayerByCloudFree.cardTongHuaShunComparator);
		p.tripleCard.sort(PlayerByCloudFree.cardThreeComparator);
		p.shunZi.sort(PlayerByCloudFree.cardShunZiComparator);
		p.allType.sort(PlayerByCloudFree.cardAllTypeComparator);
		p.doubleCard.sort(PlayerByCloudFree.cardTwoComparator);
		p.quadrupleCard.sort(PlayerByCloudFree.cardTieZhiComparator);
		analyzeCardTongHuaShun(p);
		analyzeFour(p);
		analyzeHulu(p);
		analyzeAllType(p);
		analyzeShunZi(p);
		analyzeSanTiao(p);
		analyzeTwoDuiZi(p);
		analyzeDuiZi(p);
		analyzeLeftCard(p);
		finalActionForOneChoice(p);
		dealForDaoShui(p);
	}

	private static void exchange(List<Card> a, List<Card> b) {
		for(int i = a.size() - 1, j = b.size() - 1; i >= 0; i--, j--) {
			Card temp1 = a.get(i);
			Card temp2 = b.get(j);
			a.remove(temp1);
			a.add(temp2);
			b.remove(temp2);
			b.add(temp1);
		}
	}

	public static void dealForDaoShui(PlayerByCloudFree p) {
		if(p.endType == "tiezhi") p.endRank = 9;
		if(p.midType == "tiezhi") p.midRank = 9;

		if(p.endType == "tonghuashun") p.endRank = 10;
		if(p.midType == "tonghuashun") p.midRank = 10;

		if(p.endType == "hulu") p.endRank = 8;
		if(p.midType == "hulu") p.midRank = 8;

		if(p.endType == "tonghua") p.endRank = 7;
		if(p.midType == "tonghua") p.midRank = 7;

		if(p.endType == "shunzi") p.endRank = 6;
		if(p.midType == "shunzi") p.midRank = 6;

		if(p.endType == "santiao") p.endRank = 5;
		if(p.midType == "santiao") p.midRank = 5;

		if(p.endType == "liangdui") p.endRank = 4;
		if(p.midType == "liangdui") p.midRank = 4;

		if(p.endType == "duizi") p.endRank = 3;
		if(p.midType == "duizi") p.midRank = 3;

		if(p.endType == "wulong") p.endRank = 2;
		if(p.midType == "wulong") p.midRank = 2;
		if(p.midRank > p.endRank)
		{
			exchange(p.mid, p.end);
			int k = p.midRank;
			p.midRank = p.endRank;
			p.endRank = k;
			String temp = p.midType;
			p.midType = p.endType;
			p.endType = temp;
		}
		else if(p.midRank == p.endRank){
			if(p.endType != p.midType) return;
			if(p.midType == "tonghua" || p.midType == "wulong") {
				p.mid.sort(PlayerByCloudFree.cardRank);
				p.end.sort(PlayerByCloudFree.cardRank);
			}
			int laz = 0;
			while(p.mid.get(laz).rank == p.end.get(laz).rank||p.mid.get(laz).rank + p.end.get(laz).rank> 40) {
				laz++;
				if (laz >= 5) return;
			}
			if(p.mid.get(laz).rank > p.end.get(laz).rank)
			{
				//System.out.println("cccccccccccccccccccccccccccccccc"+p.mid.get(laz).rank + "dfsfafafa"+p.end.get(laz).rank);
				exchange(p.mid, p.end);
				String temp = p.midType;
				p.midType = p.endType;
				p.endType = temp;
				int k = p.midRank;
				p.midRank = p.endRank;
				p.endRank = k;
			}
		}
	}

	private static void finalActionForOneChoice(PlayerByCloudFree p) {
		if(p.headRank == p.midRank&& p.headRank == 1) {
			Card temp = p.mid.get(3);
			Card temp2 = p.head.get(2);
			p.head.remove(temp2);
			p.mid.remove(temp);
			p.head.add(temp);
			p.mid.add(temp2);
		}
	}

	public static void reZero(PlayerByCloudFree p) {
		for(int i = 0; i < p.vis.length; i++) {
			p.vis[i] = false;
		}
		p.head.clear();
		p.mid.clear();
		p.end.clear();
		p.headRank = 1;
		p.midRank = 1;
		p.endRank = 1;
		p.headType = "wulong";
		p.midType = "wulong";
		p.endType = "wulong";
	}

	public static void dealForAnotherChoice1(PlayerByCloudFree p) {
		reZero(p);
		analyzeCardTongHuaShun(p);
		analyzeFour(p);
		//analyzeHulu(p);
		analyzeAllType(p);
		analyzeShunZi(p);
		analyzeSanTiao(p);
		analyzeTwoDuiZi(p);
		analyzeDuiZi(p);
		analyzeLeftCard(p);
		//dealForDaoShui(p);
		finalActionForOneChoice(p);
		dealForDaoShui(p);
	}

	public static void dealForAnotherChoice2(PlayerByCloudFree p) {
		reZero(p);
		analyzeCardTongHuaShun(p);
		analyzeFour(p);
		//analyzeHulu(p);
		analyzeAllType(p);
		//analyzeShunZi(p);
		analyzeSanTiao(p);
		analyzeTwoDuiZi(p);
		analyzeDuiZi(p);
		analyzeLeftCard(p);
		//dealForDaoShui(p);
		finalActionForOneChoice(p);
		dealForDaoShui(p);
	}

	public static void dealForAnotherChoice3(PlayerByCloudFree p) {
		reZero(p);
		analyzeCardTongHuaShun(p);
		analyzeFour(p);
		//analyzeHulu(p);
		//analyzeAllType(p);
		//analyzeShunZi(p);
		analyzeSanTiao(p);
		analyzeTwoDuiZi(p);
		analyzeDuiZi(p);
		analyzeLeftCard(p);
		//dealForDaoShui(p);
		finalActionForOneChoice(p);
		dealForDaoShui(p);
	}

	public static void dealForAnotherChoice4(PlayerByCloudFree p) {
		reZero(p);
		analyzeCardTongHuaShun(p);
		analyzeFour(p);
		//analyzeHulu(p);
		//analyzeAllType(p);
		//analyzeShunZi(p);
		analyzeSanTiao(p);
		//analyzeTwoDuiZi(p);
		analyzeDuiZi(p);
		analyzeLeftCard(p);
		//dealForDaoShui(p);
		finalActionForOneChoice(p);
		dealForDaoShui(p);
	}

	public static void dealForAnotherChoice5(PlayerByCloudFree p) {
		reZero(p);
		analyzeCardTongHuaShun(p);
		analyzeFour(p);
		//analyzeHulu(p);
		analyzeAllType(p);
		analyzeShunZi(p);
		analyzeSanTiao(p);
		//analyzeTwoDuiZi(p);
		analyzeDuiZi(p);
		analyzeLeftCard(p);
		//dealForDaoShui(p);
		finalActionForOneChoice(p);
		dealForDaoShui(p);
	}
	public static void dealForAnotherChoice6(PlayerByCloudFree p) {
		reZero(p);
		analyzeCardTongHuaShun(p);
		analyzeFour(p);
		//analyzeHulu(p);
		analyzeAllType(p);
		analyzeShunZi(p);
		analyzeSanTiao(p);
		//analyzeTwoDuiZi(p);
		analyzeDuiZi(p);
		analyzeLeftCard(p);
		//dealForDaoShui(p);
		finalActionForOneChoice(p);
		dealForDaoShui(p);
	}

	public static void dealForAnotherChoice7(PlayerByCloudFree p) {
		reZero(p);
		analyzeCardTongHuaShun(p);
		analyzeFour(p);
		//analyzeHulu(p);
		//analyzeAllType(p);
		analyzeShunZi(p);
		analyzeSanTiao(p);
		//analyzeTwoDuiZi(p);
		analyzeDuiZi(p);
		analyzeLeftCard(p);
		//dealForDaoShui(p);
		finalActionForOneChoice(p);
		dealForDaoShui(p);
	}
}
