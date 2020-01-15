/*
This was written for my Data Structures II class. Other than this comment it has not been altered. As indicated by the name,
this was my third pass at a solution. Unfortunately in those days I did not know about version control.
*/


import java.io.*;
import java.util.*;

public class BoyerMoore3{

	public static void main(String[] args){
		//System.out.println(findPatternB(args[0],args[1]));
		String s2 = " statum suum mutare. Lex II Mutationem motus proportionalem esse vi uniformiter in directum, nisi cogitur I suo quiescendi vel movendi Corpus omne perseverare in statu motrici  lineam rectam qua vis illa imprimitur. Lex III Actioni contrariam semper & �qualem esse reactionem: sive corporum duorum actiones in se mutuo semper esse �quales & in partes contrarias dirigi. REGULAE PHILOSOPHANDI Regula I Causas rerum naturalium non pluresscripta sunt, commentariis pr�termissis Lex  admitti debere, AXIOMATA, SIVE LEGES MOTUS Leges solae dequam qu� & quatenus illud a viribus impress�, & fieri secundum impressis  ver� sint & earum phaenomenis explicandis sufficiant.";
		String p = "sunt";
		String s = "aaaasuntaaaaaaaaaaaaaaaaaaaaaa";
		System.out.println(""+s2.length());
		System.out.println(findPattern(p,s));
		System.out.println("");
	}

	public static int findPattern(String p, String s){

		int[] T = skipTable(p);
		int L = p.length();
		int i = L-1;
		int j = 0;
		if(p.length()>s.length())
			return -1;
		while(i<s.length())
			{
			j = L-1;
		//	while(p.charAt(j)!=s.charAt(i) && i+L<s.length() && T[i]==0){
		//		i+=L;
		//	}
			while(i+L<s.length()&& p.charAt(j)!=s.charAt(i)){   //always do your array-boubds check BEFORE the access
				i+=T[s.charAt(i)];   //advance by the number of positions specified in the table
			}

			if (p.charAt(j)!=s.charAt(i))   //exited loop with failure
			   return -1;

			//System.out.println(i);
			//System.out.println(j);
			int backCheck = i;   //Don't destroy the original position of i, you will need it later
			while(p.charAt(j) == s.charAt(backCheck)){   //backward check
				if(j==0)
					return backCheck;
				backCheck-=1;
				j-=1;
			}

			//A full backward match failed. Move i forward by number of postions specified at T[s.charAt(i+1)]
			i+= T[s.charAt(i+1)];

	/*		System.out.println(i);
			System.out.println(j);
			if(i == s.length()-1){
				i++;
				return -1;
			}*/

		/*	System.out.println(i);
			System.out.println(j);
			if(T[p.charAt(j)] !=0 && i+T[p.charAt(j)] < s.length()){
				i+=T[p.charAt(j)];
				j = L-1;
			}
			System.out.println(i);
			System.out.println(j);
			if(i+T[p.charAt(j)] >= s.length()){
				i = s.length()-1;
				j = L-1;
			}
			System.out.println(i);
			System.out.println(j);  */
		}
		return -1;
	}

	public static int[] skipTable(String s){
		int[] T = new int[128];
		for(int i=0;i<128;i++){
			T[i]=s.length();
		}
		for(int i=0;i<s.length();i++){
			T[s.charAt(i)] = s.length()-i-1;
		}
		return T;
	}
}
