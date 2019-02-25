package AlgorithmByYang;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import java.util.Random;
public class Choice {
  public List<Card> head=new ArrayList<Card>();
  public List<Card> mid=new ArrayList<Card>();
  public List<Card> end=new ArrayList<Card>();
  String headType,midType,endType;
 
   /*���մ���ĺ���*/
   public  void handle(ArrayList<Card> handCard)
  {
	 sort(handCard);//�Ƚ�������
	 
	 Card[] arrayHead=new Card[3];
	 Card[] arrayMid =new Card[5];
	 Card[] arrayEnd =new Card[5];

	 /**/
	  endType=handleEnd(arrayEnd,handCard);

      end=new ArrayList<Card>(Arrays.asList(arrayEnd));
	  
	  clearOriginal(end,handCard);
	 
	  midType=handleMid(arrayMid, handCard);
	  mid=new ArrayList<Card>(Arrays.asList(arrayMid));
	 
	  clearOriginal(mid,handCard);
	  
	 
	 
	  headType=handleHead(arrayHead, handCard);
	  head=new ArrayList<Card>(Arrays.asList(arrayHead));
	 
	  clearOriginal(head,handCard);
	  final int zh=5;
	  final int q=3;
	  sort(handCard);
	/* β������*/  
	  clearArrayListNull(head);
	  clearArrayListNull(mid);
	  clearArrayListNull(end);
	  if(end.size()<=zh) //�������δ�������ʣ�µ�������룬����ʵ���������ŵ�ԭ��
	  {
		  if(end.size()==4)
		  {
			  if(endType=="tiezhi")
			  {
				  end.add(handCard.get(0));
				  handCard.remove(0);
			  }
			  if(endType=="liangdui")
			  {
				  end.add(handCard.get(handCard.size()-1));
				  handCard.remove(handCard.get(handCard.size()-1));
			  }
		  }
		  if(end.size()==3)
		  {
			  Card[] tmp=new Card[2];
			  end.add(handCard.get(handCard.size()-1));
			  handCard.remove(handCard.get(handCard.size()-1));
			  end.add(handCard.get(handCard.size()-1));
			  handCard.remove(handCard.get(handCard.size()-1));
			  endType="santiao";	  
		  }
		  if(end.size()==2)
		  {
			  end.add(handCard.get(handCard.size()-1));
			  handCard.remove(handCard.get(handCard.size()-1));
			  end.add(handCard.get(handCard.size()-1));
			  handCard.remove(handCard.get(handCard.size()-1)); 
			  end.add(handCard.get(handCard.size()-1));
			  handCard.remove(handCard.get(handCard.size()-1));
		  }
	  }
	  
	 /*****�е�����**********/
	  if(mid.size()<=zh) //�������δ�������ʣ�µ��������
	  {
		  if(mid.size()==4)
		  {
			  if(midType=="tiezhi")
			  {
				  mid.add(handCard.get(0));
				  handCard.remove(0);
			  }
			  if(midType=="liangdui")
			  {
				  mid.add(handCard.get(handCard.size()-1));
				  handCard.remove(handCard.get(handCard.size()-1));
			  }
		  }
		  if(mid.size()==3)
		  {	  
			  mid.add(handCard.get(handCard.size()-1));
			  handCard.remove(handCard.get(handCard.size()-1));
			  mid.add(handCard.get(handCard.size()-1));
			  handCard.remove(handCard.get(handCard.size()-1));
			  midType="santiao";
		  }
		  if(mid.size()==2) //�������δ�������ʣ�µ��������
		  {
			  mid.add(handCard.get(handCard.size()-1));
			  handCard.remove(handCard.get(handCard.size()-1));
			  mid.add(handCard.get(handCard.size()-1));
			  handCard.remove(handCard.get(handCard.size()-1)); 
			  mid.add(handCard.get(handCard.size()-1));
			  handCard.remove(handCard.get(handCard.size()-1));
		  }
	  }
	  
	  
	 /*****ͷ������***/
	  if(head.size()==2)
	  {
		  head.add(handCard.get(0));
		  handCard.remove(0);
	  }
	  
	     sort(head);
	     sort(mid);
	     sort(end);
	 /*    System.out.println(headType);
		  System.out.println(head);
		  System.out.println(midType);
		  System.out.println(mid);
		  System.out.println(endType);
		  System.out.println(end);
		  */
  }
   /*�����Ͱ�������С����������ͬ�������ƣ�������*/
   public static void sort(List<Card> handCard)
   {
 	  Collections.sort(handCard,new Comparator<Card>()
 	  {
        @Override
        public int compare(Card x,Card y)
        {
     	   if(x.getRank()==y.getRank())
     		   return (x.getType()-y.getType());
     	   else
     		   return (x.getRank()-y.getRank());
        }
 	  }
 	  );
   }
   //һ����������
   public static void clearArray(Card[] x)
   {
 	  for (int i=0;i<x.length;i++)
 	  {
 		  x[i]=null;
 	  }
   }
    //��������
   public static void clearArrayListNull(List<Card> x)
   {
 	  int i=0;
 	  for(i=0;i<x.size();)
 	  {
 		  if(x.get(i)==null)
 		  {
 			  x.remove(i);
 		  }
 		  else
 			  i++;
 	  }
   }
  // ��������
   public static void  clearOriginal(List<Card> card,List<Card> original)
  {
	  for(int i=0;i<card.size();i++)
	  {
		  original.remove(card.get(i));
	  }
  }
   /*
    * ͬ��˳��������ͬ��ɫ�������ơ� 5
    * ��֧��������ͬ�������ƣ���һ�ŵ����ơ� 4 
    * ��«�����ŵ�����ͬ���Ƽ���������ͬ����  3
    * ͬ�������Ų���������ͬ��ɫ���ơ�5
    * ˳�ӣ����Ų�ͬ��ɫ�������ơ�5
    * ���������ŵ�����ͬ���ƣ��������ŵ�����ͬ���� 3
    * ���ԣ��������ŵ�����ͬ���Ƽ���һ�ŵ�����ͬ���ơ�4
    * һ�ԣ�һ�����ŵ�����ͬ���Ƽ������ŵ�����ͬ��ɢ�ơ�2
    * ���������Ų�ͬ��ɫ�Ҳ������ĵ�����ͬ��ɢ��*/
   //����ͷ����
  public static String handleHead(Card []head,ArrayList<Card> original)
  {
	  String headname=new String();
	  if(isHasThreeSame(original,head))
	  {
		  headname="santiao";
		  return headname;
	  }
	  else
	  {
		  clearArray(head);
	  }
	  if(isHasDuizi(original,head))
	  {
		  headname="duzi";
		  return headname;
	  }
	  else
	  {
		  clearArray(head);
	  }
	  if(wulong(original,head))
	  {
		  headname="wulong";
	  }
	  return headname;
  }
  //�����е��� ��
  public static String handleMid(Card[] mid,ArrayList<Card> original)
  {
	  String midname=new String();
	  if(isHasTongHuaShun(original,mid))
	  {
		  midname="tonghuashun";
		  return midname;
	  }
	  else
	  {
        clearArray(mid);
	  }
	  if(isHasTiezhi(original,mid))
	  {
		  midname="tiezhi";
		  return midname;
	  }
	  else
	  {
		  clearArray(mid);
	  }
	  if(isHasHulu(original,mid))
	  {
		  midname="hulu";
		  return midname;
	  }
	  else
	  {
		  clearArray(mid);
	  }
	  if(isHasTonghua(original,mid))
	  {
		  midname="thonghua";
		  return midname;
	  }
	  else
	  {
		  clearArray(mid);
	  }
	  if(isHasThreeSame(original,mid))
	  {
		  midname="threesame";
		  return midname;
	  }
	  else
	  {
		  clearArray(mid);
	  }
	  
	  if(isHasShunzi(original,mid))
	  {
		  midname="shunzi";
		  return midname;
	  }
	  else
	  {
		  clearArray(mid);
	  }
	  if(isHasLianDui(original,mid))
	  {
		  midname="liangdui";
		  return midname;
	  }
	  else
	  {
		  clearArray(mid);
	  }
	  if(isHasDuizi(original,mid))
	  {
		  midname="duizi";
		  return midname;
	  }
	  else
	  {
		  clearArray(mid);
	  }
	   if(wulong(original,mid))
	   {
		   midname="wulong";
	   }
	  return midname;
  }
  //����β����
  public static String handleEnd(Card []end,ArrayList<Card> original)
  {
	  String endname=new String();
	  if(isHasTongHuaShun(original,end))
	  {
		  endname="tonghuashun";
		  return endname;
	  }
	  else
	  {
        clearArray(end);
	  }
	  if(isHasTiezhi(original,end))
	  {
		  endname="tiezhi";
		  return endname;
	  }
	  else
	  {
		  clearArray(end);
	  }
	  if(isHasHulu(original,end))
	  {
		  endname="hulu";
		  return endname;
	  }
	  else
	  {
		  clearArray(end);
	  }
	  if(isHasTonghua(original,end))
	  {
		  endname="thonghua";
		  return endname;
	  }
	  else
	  {
		  clearArray(end);
	  }
	  if(isHasThreeSame(original,end))
	  {
		  endname="threesame";
		  return endname;
	  }
	  else
	  {
		  clearArray(end);
	  }
	  if(isHasShunzi(original,end))
	  {
		  endname="shunzi";
		  return endname;
	  }
	  else
	  {
		  clearArray(end);
	  }
	  if(isHasLianDui(original,end))
	  {
		  endname="liangdui";
		  return endname;
	  }
	  else
	  {
		  clearArray(end);
	  }
	  if(isHasDuizi(original,end))
	  {
		  endname="duizi";
		  return endname;
	  }
	  else
	  {
		  clearArray(end);
	  }
	   if(wulong(original,end))
	   {
		   endname="wulong";
	   }
	  return endname;
  }
  /*
   * ͬ��˳��������ͬ��ɫ�������ơ� 5
   * ��֧��������ͬ�������ƣ���һ�ŵ����ơ� 4 
   * ��«�����ŵ�����ͬ���Ƽ���������ͬ����  3
   * ͬ�������Ų���������ͬ��ɫ���ơ�5
   * ˳�ӣ����Ų�ͬ��ɫ�������ơ�5
   * ���������ŵ�����ͬ���ƣ��������ŵ�����ͬ���� 3
   * ���ԣ��������ŵ�����ͬ���Ƽ���һ�ŵ�����ͬ���ơ�4
   * һ�ԣ�һ�����ŵ�����ͬ���Ƽ������ŵ�����ͬ��ɢ�ơ�2
   * ���������Ų�ͬ��ɫ�Ҳ������ĵ�����ͬ��ɢ��*/
   //������������ֱ���ͬ�����ͣ��������������͵�ƴ����������ȷ���Ƶ����ͣ�������һ����������
   static boolean isHasTongHuaShun(ArrayList<Card> x,Card[] item)
   {
	  
	  ArrayList<Card> type1=new ArrayList<Card>();
	  ArrayList<Card> type2=new ArrayList<Card>();
	  ArrayList<Card> type3=new ArrayList<Card>();
	  ArrayList<Card> type4=new ArrayList<Card>();
	  
	  int i=0;
	  for ( i=0;i<x.size();i++)
	  {
		 Card y;
		 y=x.get(i);
		 switch(y.getType())
		 {
		 case 1:type1.add(y);break;
		 case 2:type2.add(y);break;
		 case 3:type3.add(y);break;
		 case 4:type4.add(y);break;
		 default: break;
		 }
	  }
	  
	  int[] judge=new int[5];
	  Card [][] tmp=new Card [5][];
	  tmp[1]=new Card[5];
	  tmp[2]=new Card[5];
	  tmp[3]=new Card[5];
	  tmp[4]=new Card[5];

      if(type1.size()>=5&&tonghuashunHelp(type1,tmp[1]))
      {
    	  judge[1]=tmp[1][0].getRank();
      }
       
      if(type2.size()>=5&&tonghuashunHelp(type2,tmp[2]))
      {
    	  judge[2]=tmp[2][0].getRank();
      }
      if(type3.size()>=5&&tonghuashunHelp(type3,tmp[3]))
      {
    	  judge[3]=tmp[3][0].getRank();
      }
      if(type4.size()>=5&&tonghuashunHelp(type4,tmp[4]))
      {
    	  judge[4]=tmp[4][0].getRank();
      }
      int pos=0;
      for(i=1;i<5;i++)
      {
    	  if(pos<judge[i])
    		  pos=i;
      }

      if(pos!=0)
      {
    	  
    	  for(i=0;i<5;i++)
    		  item[i]
    				  =tmp[pos][i];
    	  return true;
      }
	  return false;
   }
   static boolean tonghuashunHelp(ArrayList<Card> x,Card[]tmp)
   {
	      if(x.size()<5)
	    	  return false;
	 
	      final int fundCard=5;
	      int j=0;
	      int i=x.size()-1;
	   //   System.out.println(i);
		   tmp[j]=x.get(i--);
		    while(i>=0&&j<fundCard-1)
			   {  
				   Card y;
	               y=x.get(i--);        
	            	 if(tmp[j].getRank()==(y.getRank()+1))
	            	 {
					   j++;
					   tmp[j]=y;
	            	 }
	            	 else
	            	 {
					  j=0;
					  tmp[j]=y;
	            	 }
	               }
		  if(j==fundCard-1)
			  return true;
	   return false;
   }
   static boolean isHasTiezhi(ArrayList<Card> x,Card[] item)//ȷ����ͬ�Ļ�ɫ
   {
	   int i=x.size()-1;
	   int j=0;
	   final int fundCard=4;
	   item[j]=x.get(i--);
	    while(i>=0&&j<fundCard-1)// i<0����������j=5�Ѿ��ҵ���ͬ��˳
		   {
			  
			   Card y;
			   y=x.get(i--);
			 /*  do //����Խ�������
			   {
				   y=x.get(i--);//����ͬ��
			   } while(item[j].getType()!=y.getType()&&i>0) ;*/
			 
			   if(item[j].getRank()==y.getRank())
			   {
				   j++;
				   item[j]=y;
			   }
			   else
			   {
				  j=0;
				  item[j]=y;
			   }
		   }
	    if(j==fundCard-1)
	    {
	    	return true;
	    }
	   return false;
   }
   static boolean isHasHulu(ArrayList<Card> x,Card[] item) //�Ѿ���֤����������
   {
	   int i=x.size()-1;
	   int j=0;
	   final int fundCard=3;
	   item[j]=x.get(i--);
	    while(i>=0&&j<fundCard-1)// i>=2��������,���ҵ�����ͬ��������
		   {
			  
			   Card y;
			   y=x.get(i--);
			   if(item[j].getRank()==y.getRank())
			   {
				   j++;
				   item[j]=y;
			   }
			   else
			   {
				  j=0;
				  item[j]=y;
			   }
		   }
	    if(j<2)  //������ʵ��
	    	return false; 
	    j++;
	    i=x.size()-1;
	    item[j]=x.get(i--);
	    
	    for(;i>=0;)
        {
	    	   Card y;
	    	   
	    	   if(i>2
	    		  &&x.get(i).getRank()==x.get(i-1).getRank()
	    		  &&x.get(i-1).getRank()==x.get(i-2).getRank())
	    	   {
	    		   i-=3; 
	    	   }
          //    if(i>=0)  
			   y=x.get(i--);
          //    else
           // 	  return false;
			   if(item[j].getRank()==y.getRank()
					   &&item[j].getRank()!=item[0].getRank())
			   {
				   j++;
				   item[j]=y;
			   }
			   else
			   {
				  j=3;
				  item[j]=y;
			   }
			   if(j==4)
				   return true;
        }
	    /*ɾ��������Ҫ����*/
	   return false;
   }
   static boolean isHasTonghua(ArrayList<Card> x,Card[] item)
   {
	   int i=x.size()-1;
	   boolean flag=false;;
	   final int fundCard=5;
	   Card[][] no=new Card[5][];
	   
	   no[0]=new Card[13];
	   no[1]=new Card[13];
	   no[2]=new Card[13];
	   no[3]=new Card[13];
	   no[4]=new Card[13];
	   
	   int [] count ={0,0,0,0,0};
	   int [] big=new int [5];
	   for( i=x.size()-1;i>=0;--i)
	   {
		   Card y;
		   int pos;
		   y=x.get(i);
		  
		   pos=y.getType();  
		   no[pos] [count[pos]++] =y;
		   if(big[pos]<y.getRank())
		     big[pos]=y.getRank();  
	   }
	   int pos=0;
	   /*
	   for(int j=1;j<5;j++)
	   {
	    if(count[j]>4&&big[j]>big[pos])
		   {
             flag=true;
             pos=j;
		   }
	   }
	   */
	  for(int j=1;j<5;j++)
	   {
	    if(count[j]>4)
		   {
             flag=true;
             if(big[j]>big[pos])
                pos=j;
             if(big[j]==big[pos])
                {
                 Card a,b;
                 for(int k=0;k<5;k++)
                 {
                  a=no[j][k];
                  b=no[pos][k];
                  if(a.getRank()>b.getRank())
                     {
                      pos=j;
                      break;
                     }
                     else if(a.getRank()<b.getRank())
                         break;
                 }
                }
		   }
	   }
	   if(count[pos]>4)
	   {
		   for (int j=0;j<fundCard;j++)
		   {
			   item[j]=no[pos][j];
		   }
		   return true;
	   }  
	   return false;
   }
   //�������û������ȷ�����ͣ�����ȷ����������
   static boolean isHasThreeSame(ArrayList<Card> x,Card[] item)//�����ĸ���������ɾ���ĵط�
   {
	   int i=x.size()-1;
	   int j=0;
	   final int fundCard=3;
	   item[j]=x.get(i--);
	    while(i>=0&&j<fundCard-1)// i<0����������j=5�Ѿ��ҵ���ͬ��˳
		   {
			  
			   Card y;
			   y=x.get(i--);
			   if(item[j].getRank()==y.getRank())
			   {
				   j++;
				   item[j]=y;
			   }
			   else
			   {
				  j=0;
				  item[j]=y;
			   }
		   }
	    /*ɾ��������Ҫ����*/
	    if(j==fundCard-1)
	    {
	    	return true;
	    }
	   return false;
   }
   static boolean isHasShunzi(ArrayList<Card> x,Card[] item)
   {/*�ų��˶��ӵ�˳��*/
	   int i=x.size()-1;
	   int j=0;
	   final int fundCard=5;
	   item[j]=x.get(i--);
	   
	   /*�ж��ӵ�˳��*/
	       i=x.size()-1;
	       j=0;
	   item[j]=x.get(i--);
	    while(i>=0&&j<fundCard-1)// i<0����������j=5�Ѿ��ҵ���ͬ��˳
		   {
			  
			   Card y;
			   y=x.get(i--);
			   if(item[j].getRank()==(y.getRank()+1))
			   {
				   j++;
				   item[j]=y;
			   }
			   else if(item[j].getRank()==y.getRank())
			   {
				   ;
			   }
			   else 
			   {
				  j=0;
				  item[j]=y;
			   }
		   }
	   if(j==fundCard-1)
	   {
		   return true;
	   }
	      return false;
   }
   static boolean isHasDuizi(ArrayList<Card> x,Card[] item)
   {
	   int i=x.size()-1;
	   int j=0;
	   final int fundCard=2;
	   item[j]=x.get(i--);
	    while(i>=0&&j<fundCard-1)// i<0����������j=5�Ѿ��ҵ���ͬ��˳
		   {
			  
			   Card y;
			   y=x.get(i--);
			   if(item[j].getRank()==y.getRank())
			   {
				   j++;
				   item[j]=y;
			   }
			   else
			   {
				  j=0;
				  item[j]=y;
			   }
		   }
	    /*ɾ��������Ҫ����*/
	    if(j==fundCard-1)
	    {
	    	return true;
	    }
	   return false;
   }
   static boolean isHasLianDui(ArrayList<Card> x,Card[] item)
   {
	   int i=x.size()-1;
	   int j=0;
	   final int fundCard=4;
	   item[j]=x.get(i--);
	    while(i>=0&&j<1)
		   {
			  
			   Card y;
			   y=x.get(i--);
			   if(item[j].getRank()==y.getRank())
			   {
				   j++;
				   item[j]=y;
			   }
			   else
			   {
				   j=0;
				   item[j]=y;
			   }
		   }
	    if(i>1)
	    	item[++j]=x.get(i--);
	    while(i>=0&&j<fundCard-1)
		   {
			  
			   Card y;
			   y=x.get(i--);
			   if(item[j].getRank()==y.getRank())
			   {
				   j++;
				   item[j]=y;
			   }
			   else
			   {
				   j=2;
				   item[j]=y;
			   }
		   }
	    if(j==fundCard-1)
	    {
	    	
	    	return true;
	    }
	   return false;
   }
   static boolean wulong(ArrayList<Card> x,Card[] item)
   {
	   int i=x.size()-1;
	   int j=0;
	   final int fundCard=item.length;
	   for (j=0;j<fundCard;j++)
	   {
		   Card y;
		   y=x.get(i);
		   item[j]=y;
		   i--;
	   }
	   return true;
   }
   public  String toString()
   {
	   return head+"\n"+mid+"\n"+end+"\n";
   }
 
/*
 public static void main(String [] args)
   {
	   ArrayList<Card> test=new ArrayList();
		  for(int i=0;i<13;++i)
		  {
			  Random rand=new Random();
			  int  r, t;
			  r=rand.nextInt(13)+1;
			  t=rand.nextInt(4)+1;
			  test.add(new Card(r,t));
		  }
		  test.set(0, new Card(1,3));
		  test.set(1, new Card(1,4));
		  test.set(2, new Card(5,3));
		  test.set(3, new Card(10,3));
		  test.set(4, new Card(4,1));
		  test.set(5, new Card(5,4));
		  test.set(6, new Card(6,2));
		  test.set(7, new Card(8,3));
		  test.set(8, new Card(7,1));
		  test.set(9, new Card(8,4));
		  test.set(10, new Card(10,4));
		  test.set(11, new Card(12,3));
		  test.set(12, new Card(12,4));
		  sort(test);
		  System.out.println(test);
		 // Card[] item=new Card[5];
		  Choice choice=new Choice();
	      choice.handle(test);
		 // ArrayList<Card> tmp=new ArrayList<Card>(Arrays.asList(item));
		 // System.out.println(tmp);
	      System.out.println(choice.headType);
		  System.out.println(choice.head);
		  System.out.println(choice.midType);
		  System.out.println(choice.mid);
		  System.out.println(choice.endType);
		  System.out.println(choice.end);
   
   }*/
}
 /*
 boolean specialCard(ArrayList<Card> Card)
 {
	 sort(Card);
	 return false;
 }
 
static boolean hasZhizun(ArrayList<Card> x)
 {
     int j=0;
     Card y,z;
     y=x.get(j);
     for(j=1;j<x.size();j++)
     {
    	 z=y;
    	 y=x.get(j);
    	 if(y.getType()!=z.getType()||y.getRank()!=(z.getRank()+1))
    	 {
    	     return false;
    	 }
     }
       return true;
 }
 static boolean hasyitiaolong(ArrayList<Card> x)
 {
     int j=0;
     Card y,z;
     y=x.get(j);
     for(j=1;j<x.size();j++)
     {
    	 z=y;
    	 y=x.get(j);
    	 if(y.getRank()!=(z.getRank()+1))
    	     return false;
     }
       return true;
 }
 static boolean hasshierhuangzu(ArrayList<Card>x)
 {
	 int j=0;
     Card y;
	 for(j=0;j<x.size();j++)
     {
    	 y=x.get(j);
    	 if(y.getRank()>9)
    	     return false;
     }
	 return true;
 }
  boolean hassantonghua(ArrayList<Card> x)
{
   Card [] item1=new Card[5];
   Card [] item2=new Card[5];
   Card [] item3=new Card[3];
   ArrayList y=(ArrayList<Card>)x.clone();
   int count=0;
   int i=0;
   if(isHasTongHuaShun(y,item1))
   {
	   count++;
	   for(i=0;i<5;i++)
		   y.remove(item1[i]);
   }
   else
	   return false;
   if(isHasTongHuaShun(y,item2))
   {
	   count++;
	   for(i=0;i<5;i++)
		   y.remove(item2[i]);
   }
   else
	   return false;
  if(x.size()!=3)
	  return false;

	  Card a,b,c;
	  a=x.get(0);
	  b=x.get(1);
	  c=x.get(2);
	  if(a.getType()!=b.getType()||b.getType()!=c.getType())
            return false;
	  if((a.getRank()+1)!=b.getRank()||(b.getRank()+1)!=c.getRank())
		  return false;
	  
	 end=(ArrayList<Card>)Arrays.asList(item1);
	 mid=(ArrayList<Card>)Arrays.asList(item2);
	 head.add(a);
	 head.add(b);
	 head.add(c);
     return true; 
}
static boolean hasquanda(ArrayList<Card> x)
 {
     int j=0;
     Card y;
     
     for(j=0;j<x.size();j++)
     {
    	 y=x.get(j);
    	 if(y.getRank()<7)
    	     return false;
     }
       return true;
 }
 static boolean hasquanxiao(ArrayList<Card> x)
 {
     int j=0;
     Card y;
     
     for(j=0;j<x.size();j++)
     {
    	 y=x.get(j);
    	 if(y.getRank()>8)
    	     return false;
     }
       return true;
 }
 static boolean hascuoyise(ArrayList<Card> x)
 {
	 int j=0;
     Card y;
     y=x.get(j);
     int color=y.getType()%2;
     for(j=1;j<x.size();j++)
     {
    	 y=x.get(j);
    	 if((y.getType()%2)!=color)
    	     return false;
     }
       return true;
 }
 
}

*/
