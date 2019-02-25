package AlgorithmByYang;

public class Card {
    public int rank;
    public int type;
    public Card()
    {
    }
    public Card(int r,int t)
    {
    	rank=r;
        type=t;
    }
    public String toString()  //���أ�����ʱʹ��
    {
    	return "\t花色"+type+"\t点数"+rank;
    }
    public int  getRank()  //����ֵ
    {
    	return rank;
    }
    public int getType()  //��������
    {
    	return type;
    }
}
