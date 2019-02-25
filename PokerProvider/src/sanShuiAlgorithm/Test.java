package sanShuiAlgorithm;

public class Test{
	
	public static void outPut(Choice p) {
		for(int i = 0; i < p.head.size(); i++) {
			Card temp = p.head.get(i);
			System.out.print(temp.rank+" ");
		}
		
		for(int i = 0; i < p.head.size(); i++) {
			Card temp = p.head.get(i);
			System.out.print(temp.type+" ");
		}
		
		System.out.println("");
		
		for(int i = 0; i < p.mid.size(); i++) {
			Card temp = p.mid.get(i);
			System.out.print(temp.rank+" ");
		}
		
		for(int i = 0; i < p.mid.size(); i++) {
			Card temp = p.mid.get(i);
			System.out.print(temp.type+" ");
		}

		System.out.println("");
		
		for(int i = 0; i < p.end.size(); i++) {
			Card temp = p.end.get(i);
			System.out.print(temp.rank+" ");
		}
		
		for(int i = 0; i < p.end.size(); i++) {
			Card temp = p.end.get(i);
			System.out.print(temp.type+" ");
		}

		System.out.println("");
		
		System.out.println(p.headType);
		System.out.println(p.midType);
		System.out.println(p.endType);
	}
	
	public static void main(String[] args) {
		Out.preActionPerTime();//每发四个玩家要重置一次
		PlayerByCloudFree p = new PlayerByCloudFree();
		//新建玩家对象， 构造函数自动给玩家分发手牌
		Out.getAllChoice(p);//得到所有排序
		System.out.println(Out.allChoice.size());
		//输出排序种数
		for(int i = 0; i < Out.allChoice.size(); i++) {
			outPut(Out.allChoice.get(i));
			System.out.println("");
		}//输出格式。。先rank ， 后花色
		
	}
}