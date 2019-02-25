package AlgorithmByBai;
import java.util.List;
import java.util.ArrayList;
import AlgorithmByBai.Card;
import AlgorithmByBai.Player;
      
public class reachToptest2 
{
	public static void main(String []args)
	{
		List<Card> handCard = new ArrayList<Card>();
		handCard.add(new Card(7,1));
		handCard.add(new Card(7,3));
		handCard.add(new Card(13,3));
		handCard.add(new Card(11,2));
		handCard.add(new Card(9,3));
		handCard.add(new Card(4,2));
		handCard.add(new Card(8,3));
		handCard.add(new Card(8,1));
		handCard.add(new Card(5,2));
		handCard.add(new Card(6,2));
		handCard.add(new Card(7,2));
		handCard.add(new Card(8,2));
		handCard.add(new Card(9,2));
		Player p=new Player();
		p.change(handCard);
		
		for(int i=0;i<p.choice.end.size();i++)
		{
			System.out.print(p.choice.mid.get(i).rank);
			System.out.print(p.choice.mid.get(i).type);
			System.out.println();
		}
		
	}
}
