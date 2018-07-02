package parttwo.week4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;

public class TraceProcess{
	
	public void getData() throws Exception{
		String fileName = "E:\\dataset\\trace.txt";
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String outFile = "E:\\dataset\\traceData.csv";
		PrintWriter out = new PrintWriter(outFile);
		String header = "time,scanMac,posX,posY,posZ,orientation,mac,signal,channel,type";
		
		String s = in.readLine();
//		out.println(s);
		s = in.readLine();
//		out.println(s);
		s = in.readLine();
//		out.println(s);
		out.println(header);
		int lineNum = 4;
		
		while((s = in.readLine()) != null){
			while(s.startsWith("#")){
//				out.println(s);
				s = in.readLine();
			}
			System.out.println(lineNum);
			lineNum ++;
			String[] sarry = s.split(";");
			int slen = sarry.length;
			String row = "";
			//time
			row = row + "\"" + getTimeStamp(sarry[0]) + "\"";
			row += ",";
			//id
			row = row + "\"" + getId(sarry[1]) + "\"";
			row += ",";
			//posX posY posZ
			float[] pos = getPos(sarry[2]);
			row += pos[0];
			row += ",";
			row += pos[1];
			row += ",";
			row += pos[2];
			row += ",";
			//degree
			float degree = getDegree(sarry[3]);
			row += degree;
			row += ",";
			//deal with peer data
			for(int i = 4; i < slen; i ++){
				String row1 = new String(row);
				String[] peerData = getPeerData(sarry[i]);
				row1 = row1 + "\"" + peerData[0] + "\"";//peer id.
				row1 += ",";
				row1 += peerData[1];//power
				row1 += ",";
				row1 += peerData[2];//frequency
				row1 += ",";
				row1 += peerData[3];//mode
				out.println(row1);//write a row
			}
		}
		out.flush();
		in.close();
		out.close();
	}
	
	public String getTimeStamp(String t){
		String[] sarry = t.split("=");
		long time = Long.parseLong(sarry[1]);
		Date date = new Date(time);
		return date.toGMTString();
	}
	
	public String getId(String s){
		String[] sarry = s.split("=");
		return sarry[1];
	}
	
	public float[] getPos(String s){
		String[] sarry = s.split("=");
		String[] poss = sarry[1].split(",");
		float[] pos = new float[3];
		pos[0] = Float.parseFloat(poss[0]);
		pos[0] = (float)(Math.round(pos[0]*10))/10;
		pos[1] = Float.parseFloat(poss[1]);
		pos[1] = (float)(Math.round(pos[1]*10))/10;
		pos[2] = Float.parseFloat(poss[2]);
		pos[2] = (float)(Math.round(pos[2]*10))/10;
		return pos;
	}
	
	public float getDegree(String s){
		String[] sarry = s.split("=");
		float degree = Float.parseFloat(sarry[1]);
		degree = (float)(Math.round(degree*10))/10;
		return degree;
	}
	
	public String[] getPeerData(String s){
		String[] sarry = s.split(",");
		String[] res = new String[4];
		String[] ids = sarry[0].split("=");
		res[0] = ids[0];//id
		res[1] = ids[1];//power
		res[2] = sarry[1];//frequency
		res[3] = sarry[2];//mode
		return res;
	}
	
	public static void main(String[] args) throws Exception{
/*		Long time = new Long(1139643118358L);
		Date date = new Date(time);
		float f = 0.3400000f;
		System.out.println((float)(Math.round(f*10))/10);*/
		TraceProcess tp = new TraceProcess();
		tp.getData();
		String s = "t=113954595985";
		String[] sarry = s.split("=");
		System.out.println(sarry.length);
	}
}