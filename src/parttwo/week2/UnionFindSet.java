package parttwo.week2;

public class UnionFindSet{
	public static int[] makeUnionFindSet(int[] UFS, int nodeNum){
		UFS = new int[nodeNum];
		for(int i = 0 ; i < UFS.length; i ++){
			UFS[i] = -1;
		}
		return UFS;
	}
	
	public static int find(int x, int[] UFS){
		int t = x; int p;
		while(UFS[t] >= 0)
			t = UFS[t];
		//then t UFS[t] < 0, t is the leader
		//path compression
		while(x != t){
			p = UFS[x];
			UFS[x] = t;
			x = p;
		}
		return t;
	}
	
	/**s1 and s2 must be leader.
	 * merge these two UFS together.
	 * the smaller will be installed as a child of the larger
	 * @param UFS
	 * @param s1 the leader of UFS 1
	 * @param s2 the leader of UFS 2
	 * @return
	 */
	public static int[] union(int[] UFS, int s1, int s2){
		//这种方法表示s1 和s2 是两个普通点，要先求它们的leader
/*		int x = UnionFindSet.find(s1, UFS);
		int y = UnionFindSet.find(s2, UFS);
		if(x == y)
			return UFS;
		if(-UFS[x] >= -UFS[y]){
			UFS[x] += UFS[y];
			UFS[y] = x;
		}
		else{
			UFS[y] += UFS[x];
			UFS[x] = y;
		}
		return UFS;*/
		//下面这种方法，表示参数s1 和s2 本身就是leader
		if(s1 == s2)
			return UFS;
		if(-UFS[s1] >= -UFS[s2]){
			UFS[s1] += UFS[s2];
			UFS[s2] = s1;
		}
		else{
			UFS[s2] += UFS[s1];
			UFS[s1] = s2;
		}
		return UFS;
	}
}