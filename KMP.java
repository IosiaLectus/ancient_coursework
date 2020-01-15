/*
This was written for my Data Structures II class. Other than this comment it has not been altered.
*/



import java.io.*;
import java.util.*;

public class KMP{
	public static void main(String[] args){
		//System.out.println(findPatternB(args[0],args[1]));
		String s = "AXIOMATA, SIVE LEGES MOTUS Leges sol� descripta sunt, commentariis pr�termissis Lex I Corpus omne perseverare in statu suo quiescendi vel movendi uniformiter in directum, nisi quatenus illud a viribus impressis cogitur statum suum mutare. Lex II Mutationem motus proportionalem esse vi motrici impress�, & fieri secundum lineam rectam qua vis illa imprimitur. Lex III Actioni contrariam semper & �qualem esse reactionem: sive corporum duorum actiones in se mutuo semper esse �quales & in partes contrarias dirigi. REGUL� PHILOSOPHANDI Regula I Causas rerum naturalium non plures admitti debere, quam qu� & ver� sint & earum ph�nomenis explicandis sufficiant.";
		String p = "sunt";
		System.out.println("");
		System.out.println(findPattern(p,s));
		System.out.println("");
	}

	/*public static int findPatternB(String p, String file_name){
		int i = -1;
		Scanner sc = new Scanner(file_name);
		if(!sc.hasNext()){
			System.out.println("The given file is empty");
		}
		String s = sc.nextLine();
		if(!sc.hasNext()){
			i = findPattern(p,s);
		}
		while(sc.hasNext() && i == -1){
			i = findPattern(p,s);
			if(s.length()<p.length()){
				s = s + sc.nextLine();
			}else{
				s = s.substring(s.length()-p.length(),s.length()) + sc.nextLine();
			}
		}
		return i;
	}*/

	public static int findPattern(String p, String s){
		int i =0;
		int j =0;
		int[] T = failFunction(p);
		System.out.println("");
		System.out.println("");
		while(i<s.length()){
			if(p.charAt(j)==s.charAt(i)){
				if(j==p.length()-1)
					return i-p.length()+1;
				j++;
				i++;
			}else if(p.charAt(j)!=s.charAt(i)){
				j=T[j];
				i++;
			}
		}
		return -1;
	}

	public static int[] failFunction(String s){
		int[] T = new int[s.length()];
		T[0] = 0;
		int i = 1;
		int j = 0;
		while(i<s.length()){
			if(s.charAt(i)==s.charAt(j)){
				T[i]=j+1;
				i++;
				j++;
			}else if(j>0){
				j=T[j-1];
			}else{
				T[i]=0;
				i++;
			}
		}
		return T;
	}
}
