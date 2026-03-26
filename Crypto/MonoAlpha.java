import java.io.*;
import java.util.*;

public class MonoAlpha {
	
	public static void main(String[] args)throws IOException{
		//Scanner sc = new Scanner(new File("abc.txt"));
		PrintWriter p = new PrintWriter(System.out);
		//System.out.print(shift(sc, 7));
		//System.out.println();
		//System.out.println(polyAlpha("WHAT IS YOUR NAME", "hELLOwORLD"));
		//symbol(new PrintWriter("wtc.txt"))
		System.out.println(playfair("HelloWorld", "Josiah"));
		crzy(p);
	}
	
	public static void symbol(PrintWriter p){
		for(int i=0;i<100000;i++){
			p.print(i);
			p.print(",");
			p.println((char)i);
		}
	}
	
	public static String polyAlpha(Scanner sc, String keyword){
		keyword = keyword.toUpperCase();
		int j = 0;
		int i = (int)keyword.charAt(j)-(int)'A';
		String s = "";
		String s2 = "";
		while(sc.hasNext()){
			s2 = sc.next();
			s2.toUpperCase();
			s2 = stringShift(s2,i);
			s = s + s2;
			j = (j+1)%(keyword.length());
			i = (int)keyword.charAt(j)-(int)'A';
		}
		return s;
	}
	
	public static String polyAlpha(String clear, String keyword){
		keyword = keyword.toUpperCase();
		int j = 0;
		int i = (int)keyword.charAt(j)-(int)'A';
		String s = "";
		String s2 = "";
		int i2 = 0;
		while(i2 < clear.length()){
			s2 = Character.toString(clear.charAt(i2));
			s2.toUpperCase();
			s2 = stringShift(s2,i);
			s = s + s2;
			j = (j+1)%(keyword.length());
			i = (int)keyword.charAt(j)-(int)'A';
			i2++;
		}
		return s;
	}

    public static String shift(Scanner sc, int shiftBy) {
	    int i = (int) 'A';
	    int j = (shiftBy)%26;
	    String s = "";
	    while(sc.hasNext()){
		    s = s + sc.next();
	    }
	    s.toUpperCase();
	    String s2 = "";
	    String s3 = "";
	    for(int a=0; a<s.length(); a++){
		    char c = s.charAt(a);
		    int b = (((int) c)-i+j)%26+i;
		    c = (char) b;
		    s3 = Character.toString(c) ;
		    s2 = s2 +s3;
	    }
	    return s2;
    }
    
    public static String stringShift(String s, int shiftBy) {
	    int i = (int) 'A';
	    int j = (shiftBy)%26;
	    s = s.toUpperCase();
	    String s2 = "";
	    String s3 = "";
	    for(int a=0; a<s.length(); a++){
		    char c = s.charAt(a);
		    int b = (((int) c)-i+j)%26+i;
		    c = (char) b;
		    s3 = Character.toString(c) ;
		    s2 = s2 +s3;
	    }
	    return s2;
    }
    
    public static void crzy(PrintWriter p){
	    int i = (int)(Math.random()*300);
	    int j = 34;
	    int a = 2;
	    int b = 4;
	    while(i-1 > 0 || j-1 > 0 || b-1>0 || a-1>0){
		    //System.out.print((char)i);
		    p.print((char)i);
		    i = (int)(Math.random()*300);
		    j = (int)(Math.random()*300);
		    a = (int)(Math.random()*300);
		    b = (int)(Math.random()*300);
	    }
	    System.out.println();
	    
    }
     public static void crzy2(){
	    String s = "";
	    int i = (int)(Math.random()*6);
	    int j = 34;
	    int a = 2;
	    int b = 4;
	    while(i > 0 || j > 0 || b>0 || a>0){
		    if(i == 5){
			    System.out.println(i%2);
		    }else{
			    System.out.print(i%2);
		    }
		    i = (int)(Math.random()*2);
		    j = (int)(Math.random()*300);
		    a = (int)(Math.random()*300);
		    b = (int)(Math.random()*300);
	    }
	    System.out.println();
    }
    
    public static int[] analyzeFrequency(String st){
	    st = st.toUpperCase();
	    int[] list = new int[26];
	    int i = 0;
	    int j = 0;
	    while(i<st.length()){
		    j = (int)st.charAt(i)-(int)'A';
		    if(j>=0 && j<=25)
			    list[j] = list[j] + 1;
		    i++;
	    }
	    return list;
    }
    
    public static int[] analyzeFrequency(Scanner sc){
	    String s = "";
	    while(sc.hasNext()){
		    s = s + sc.next();
	    }
	    return analyzeFrequency(s);
    }
    
    public static void analyzeAndPrint(String s){
	    int[] list = analyzeFrequency(s);
	    for(int i = 0; i<list.length; i++){
		    System.out.printf("%c = %d\n",(char)((int)'A'+i),list[i]);
	    }
    }
    
    public static String playfair(String clear1, String keyword1){
	    
	    String chart = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
	    String clear = clear1;
	    String keyword = keyword1; 
	    
	    keyword = keyword.toUpperCase();
	    clear = clear.toUpperCase();
	    clear = clear.replace('J','I');
	    keyword = keyword.replace('J','I');
	    
	    String s = "";
	    for(int i=1; i<keyword.length(); i++){
		    s = Character.toString(keyword.charAt(0));
		    keyword = keyword.replace(s,"");
		    chart = chart.replace(s,"");
		    chart = s + chart;
	    }
	    
	    int x1 = 0;
	    int y1 = 0;
	    int x2 = 0;
	    int y2 = 0;
	    s = "";
	    if(clear.length()%2 == 1)
		    clear = clear + "X";
	    for(int i=0; i<clear.length(); i=i+2){
		    char c1 = clear.charAt(i);
		    char c2 = clear.charAt(i+1);
		    int a1 = chart.indexOf(c1);
		    int a2 = chart.indexOf(c2);
		    x1 = a1%5;
		    x2 = a2%5;
		    y1 = (a1/5);
		    y2 = (a2/5);
		    int b2 = (y1*5)+x2;
		    int b1 = (y2*5)+x1;
		    /*if(x1 == x2){
			    b2 = ((y2+1)%5)*5+x2;
			    b1 = ((y1+1)%5)*5+x1;
		    }
		    if(y1 == y2){
			    b2 = y2*5+(x2+1)%5;
			    b1 = y1*5+(x1+1)%5;
		    }
		    if(y1==y2 && x1==x2){
			    b1 = y2*5+x2;
			    b1 = b2;
		    }*/
		    s = s+Character.toString(chart.charAt(b1))+Character.toString(chart.charAt(b2));
	    }
	    return s;
    }
}
