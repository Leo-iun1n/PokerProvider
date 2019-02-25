package AlgorithmByYang;
import java.util.ArrayList;
import java.util.List;

import java.util.Random;

public class Player implements FinalTest{
   
   public Choice choice;
   public void change(List<Card>handCard)
   {
	   choice=new Choice();
	   choice.handle((ArrayList<Card>)handCard);//choice����handle�����Ѿ������ʹ������
	/*      System.out.println(choice.headType);
		  System.out.println(choice.head);
		  System.out.println(choice.midType);
		  System.out.println(choice.mid);
		  System.out.println(choice.endType);
		  System.out.println(choice.end);*/
		 
   
   }
  /*
   public static void main(String [] args)
   {
	   ArrayList<Card> test=new ArrayList<Card>();
		  for(int i=0;i<13;++i)
		  {
			  Random rand=new Random();
			  int  r, t;
			  r=rand.nextInt(13)+1;
			  t=rand.nextInt(4)+1;
			  test.add(new Card(r,t));
		  }
		 Player p=new Player();
		 p.change(test);
   }*/
}
