import java.io.*;
import java.util.*;

public class CryptoPak{
	
	public static void main(String[] args)throws IOException{
		String[] sAry = {"QWE:RTYUI OPA/SDFGH?JK\nLZXCVBNM"};
		String sl = readFile("MexicoFlu.txt");
		sl = sl.toUpperCase();
		printTable(analyzeFrequency(sl));
		printToFile(simpleSubCipher(sAry,sl),"encrypted.txt");
		String cipher = readFile("encrypted.txt");
		printToFile(inverseSSCipher(sAry,sl),"decrypted.txt");
	}
	
	public static String readFile(String filename)throws IOException{
		File f = new File(filename);
		Scanner sc = new Scanner(f);
		String s = "";
		while(sc.hasNext()){
			s = s+sc.nextLine()+"\n";
		}
		return s;
	}
	
	public static void printToFile(String printMe, String filename)throws IOException{
		PrintWriter prn = new PrintWriter(filename);
		prn.println(printMe);
		prn.close();
	}
	
	public static int[] analyzeFrequency(String s){
		int[] ret = new int[9000];
		for(int i=0;i<s.length();i++){
			char c = s.charAt(i);
			ret[c]++;
		}
		return ret;
	}
	
	public static void printTable(int[] ary){
		for(int i=0;i<ary.length;i++){
			if(ary[i] != 0)
				System.out.printf("%c: %d\n",(char) i, ary[i]);
		}
	}
	
	public static String simpleSubCipher(String s, String clear){
		String[] sAry = {s};
		return simpleSubCipher(sAry,clear);
	}
	
	public static String simpleSubCipher(String[] sAry, String clear){
		Kcycle kc = new Kcycle(sAry);
		return simpleSubCipher(kc,clear);
	}
	
	public static String simpleSubCipher(Kcycle kc, String clear){
		String encrypted = "";
		for(int i=0;i<clear.length();i++){
			encrypted = encrypted + Character.toString((char)kc.table[clear.charAt(i)]);
		}
		return encrypted;
	}
	
	public static String inverseSSCipher(String s, String cipher){
		String[] sAry = {s};
		Kcycle kc = new Kcycle(sAry);
		kc = kc.invert();
		return simpleSubCipher(kc,cipher);
	}
	
	public static String inverseSSCipher(String[] sAry, String cipher){
		Kcycle kc = new Kcycle(sAry);
		kc = kc.invert();
		return simpleSubCipher(kc,cipher);
	}
	
	public static String inverseSSCipher(Kcycle kc, String cipher){
		kc = kc.invert();
		return simpleSubCipher(kc,cipher);
	}
	
}

class Kcycle{
	
	public int[] table = new int[9000];
	public String[] Strings;
	
	public Kcycle(String[] s2){
		Strings = s2;
		for(int i=0;i<128;i++){
			table[i] = i;
		}
		for(String s: Strings){
			for(int i=0;i<s.length();i++){
				char c = s.charAt(i);
				char c2 = s.charAt((i+1)%s.length());
				table[c] = c2;
			}
		}
	}
	
	public Kcycle invert(){
		String[] ary = {"aa"};
		Kcycle kc = new Kcycle(ary);
		for(int i=0;i<table.length;i++)
			kc.table[table[i]] = i;
		return kc;
	}
}
