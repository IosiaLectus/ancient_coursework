import java.util.*;
import java.io.*;

public class BTree2 {
	
	public static final int NULL = -1*((int)Math.pow(2,31));
	
	public static int theInt = 0;
	
	BNode root;
	
	public static void main(String[] ary)throws IOException{
		BTree2 B = new BTree2();
		for(int i=0;i<100000;i++){
			int x = (int)(100000*Math.random());
			B.insert(x);
			if(i%1000==0){
				System.out.println(i);
			}
			if(B.find(x).equals("Not found")){
				System.out.println("Could not find item just inserted!!!");
				break;
			}
		}
	}
	
	public BTree2(){
		root = new BNode(true);
	}
	
	public void insert(int x)throws IOException{
		root.insert(x);
		while(!(root.parent == null)){
			root = root.parent;
		}
	}
	
	public String find(int x){
		return root.find(x);
	}
}

class BNode {
	
	public static final int  NULL = -1*((int)Math.pow(2,31));
	
	public int min = NULL;
	
	public BNode parent;
	
	public int[] markers = new int[3];
	
	public BNode[] childrenA = new BNode[4];
	
	public BTFile[] childrenB = new BTFile[4];
	
	public boolean isLeaf;
	
	public BNode(boolean a){
		isLeaf = a;
		for(int i=0;i<3;i++){
			markers[i] = NULL;
		}
	}
	
	public boolean isFull()throws IOException{
		if(isLeaf){
			if(childrenB[3] == null){
				return false;
			}
			return true;
		}else{
			if(childrenA[3] == null){
				return false;
			}
			return true;
		}
	}
	
	public String find(int x){
		if(isLeaf){
			if(childrenB[0] == null)
				return "Not found";
			if(markers[0] == NULL || x<markers[0])
				return childrenB[0].file.getName();
			if(childrenB[1] == null)
				return "Not found";
			if(markers[1] == NULL || x<markers[1])
				return childrenB[1].file.getName();
			if(childrenB[2] == null)
				return "Not found";
			if(markers[2] == NULL || x<markers[2])
				return childrenB[2].file.getName();
			if(childrenB[3] == null)
				return "Not found";
			return childrenB[3].file.getName();
		}
		if(childrenA[0] == null)
			return "Not found";
		if(markers[0] == NULL || x<markers[0])
			return childrenA[0].find(x);
		if(childrenA[1] == null)
			return "Not found";
		if(markers[1] == NULL || x<markers[1])
			return childrenA[1].find(x);
		if(childrenA[2] == null)
			return "Not found";
		if(markers[2] == NULL || x<markers[2])
			return childrenA[2].find(x);
		if(childrenA[3] == null)
			return "Not found";
		return childrenA[3].find(x);
	}
	
	public void insert(int x)throws IOException{
		if(isLeaf){
			leafInsert(x);
		}else{
			internalInsert(x);
		}
	}
	
	private void internalInsert(int x)throws IOException{
		if(min == NULL || x<min){
			min = x;
		}
		if(markers[0] == NULL || x<markers[0]){
			if(childrenA[0].isLeaf){
				childrenA[0].leafInsert(x);
			}else{
				childrenA[0].internalInsert(x);
			}
		}else if(markers[1] == NULL || x<markers[1]){
			if(childrenA[0].isLeaf){
				childrenA[1].leafInsert(x);
			}else{
				childrenA[1].internalInsert(x);
			}
		}else if(markers[2] == NULL || x<markers[2]){
			if(childrenA[0].isLeaf){
				childrenA[2].leafInsert(x);
			}else{
				childrenA[2].internalInsert(x);
			}
		}else{
			if(childrenA[0].isLeaf){
				childrenA[2].leafInsert(x);
			}else{
				childrenA[2].internalInsert(x);
			}
		}
		for(int i=1;i<childrenA.length;i++){
			if(!(childrenA[i] == null)){
				markers[i-1] = childrenA[i].min;
			}
		}
	}
	
	private void internalInsert(BTFile f)throws IOException{
		if(min == NULL || f.min<min){
			min = f.min;
		}
		if(markers[0] == NULL || f.max<markers[0]){
			if(childrenA[0].isLeaf){
				childrenA[0].leafInsert(f);
			}else{
				childrenA[0].internalInsert(f);
			}
		}else if(markers[1] == NULL || f.max<markers[1]){
			if(childrenA[0].isLeaf){
				childrenA[1].leafInsert(f);
			}else{
				childrenA[1].internalInsert(f);
			}
		}else if(markers[2] == NULL || f.max<markers[2]){
			if(childrenA[0].isLeaf){
				childrenA[2].leafInsert(f);
			}else{
				childrenA[2].internalInsert(f);
			}
		}else{
			if(childrenA[0].isLeaf){
				childrenA[2].leafInsert(f);
			}else{
				childrenA[2].internalInsert(f);
			}
		}
		for(int i=1;i<childrenA.length;i++){
			if(!(childrenA[i] == null)){
				markers[i-1] = childrenA[i].min;
			}
		}
	}
	
	private void leafInsert(int x)throws IOException{
		if(min == NULL || x<min){
			min = x;
		}
		if(markers[0] == NULL || x<markers[0]){
			if(childrenB[0] == null){
				childrenB[0] = new BTFile("file"+Integer.toString(BTree2.theInt)+".txt");
				BTree2.theInt++;
			}
			if(!childrenB[0].isFull()){
				childrenB[0].insert(x);
			}else{
				BTFile f = childrenB[0].split();
				leafInsert(f);
				leafInsert(x);
			}
		}else if(markers[1] == NULL || x<markers[1]){
			if(childrenB[1] == null){
				childrenB[1] = new BTFile("file"+Integer.toString(BTree2.theInt)+".txt");
				BTree2.theInt++;
			}
			if(!childrenB[1].isFull()){
				childrenB[1].insert(x);
			}else{
				BTFile f = childrenB[0].split();
				leafInsert(f);
				leafInsert(x);
			}
		}else if(markers[2] == NULL || x<markers[2]){
			if(childrenB[2] == null){
				childrenB[2] = new BTFile("file"+Integer.toString(BTree2.theInt)+".txt");
				BTree2.theInt++;
			}
			if(!childrenB[2].isFull()){
				childrenB[2].insert(x);
			}else{
				BTFile f = childrenB[0].split();
				leafInsert(f);
				leafInsert(x);
			}
		}else{
			if(childrenB[3] == null){
				childrenB[3] = new BTFile("file"+Integer.toString(BTree2.theInt)+".txt");
				BTree2.theInt++;
			}
			if(!childrenB[3].isFull()){
				childrenB[3].insert(x);
			}else{
				BTFile f = childrenB[0].split();
				leafInsert(f);
				leafInsert(x);
			}
		}
		for(int i=1;i<childrenB.length;i++){
			if(!(childrenB[i]==null)){
				markers[i-1] = childrenB[i].min;
			}
		}
	}
	
	private void leafInsert(BTFile f)throws IOException{
		if(min == NULL || f.min<min){
			min = f.min;
		}
		if(isFull()){
			nodeSplit(f);
		}else{
			if(markers[0] == NULL || f.max<markers[0]){
				childrenB[0] = f;
			}
			else if(markers[1] == NULL || f.max<markers[1]){
				childrenB[1] = f;
			}
			else if(markers[2] == NULL || f.max<markers[2]){
				childrenB[2] = f;
			}else{
				childrenB[2] = f;
			}
		}
		for(int i=1;i<childrenB.length;i++){
			if(!(childrenB[i]==null)){
				markers[i-1] = childrenB[i].min;
			}
		}
	}
	
	public void nodeSplit(BTFile f)throws IOException{
		if(isLeaf){
			if(parent == null){
				parent = new BNode(false);
			}
			BTFile[] list = new BTFile[17];
			int k = 0;
			boolean fInsrtd = false;
			for(int i=0;i<4;i++){
				if(parent.childrenA[i] != null){
					for(int j=0;j<4;j++){
						if(parent.childrenA[i].childrenB[j] != null){
							if(parent.childrenA[i].childrenB[j].min>f.max && !fInsrtd){
								list[k] = f;
								k++;
								fInsrtd = true;
							}
							list[k] = parent.childrenA[i].childrenB[j];
							k++;
						}
					}
				}
			}
			if(k>16){
				int x = k/5;
				int i = 0;
				BNode B1 = new BNode(true);
				BNode B2 = new BNode(true);
				BNode B3 = new BNode(true);
				BNode B4 = new BNode(true);
				BNode B5 = new BNode(true);
				while(i<x){
					B1.childrenB[i] = list[i];
					i++;
				}
				while(i<2*x){
					B2.childrenB[i-x] = list[i];
					i++;
				}
				while(i<3*x){
					B3.childrenB[i-2*x] = list[i];
					i++;
				}
				while(i<4*x){
					B4.childrenB[i-3*x] = list[i];
					i++;
				}
				while(i<k){
					B5.childrenB[i-4*x] = list[i];
					i++;
				}
				
				B1.min = childrenB[0].min;
				B2.min = childrenB[0].min;
				B3.min = childrenB[0].min;
				B4.min = childrenB[0].min;
				B5.min = childrenB[0].min;
				
				for(int j=1;j<B1.childrenB.length;j++){
					if(!(B1.childrenB[j]==null)){
						B1.markers[j-1] = B1.childrenB[j].min;
					}
				}
				for(int j=1;j<B2.childrenB.length;j++){
					if(!(B2.childrenB[j]==null)){
						B2.markers[j-1] = B2.childrenB[j].min;
					}
				}
				for(int j=1;j<B3.childrenB.length;j++){
					if(!(B3.childrenB[j]==null)){
						B3.markers[j-1] = B3.childrenB[j].min;
					}
				}
				for(int j=1;j<B4.childrenB.length;j++){
					if(!(B4.childrenB[j]==null)){
						B4.markers[j-1] = B4.childrenB[j].min;
					}
				}
				for(int j=1;j<B5.childrenB.length;j++){
					if(!(B5.childrenB[j]==null)){
						B5.markers[j-1] = B5.childrenB[j].min;
					}
				}
				B1.parent = parent;
				B2.parent = parent;
				B3.parent = parent;
				B4.parent = parent;
				parent.childrenA[0] = B1;
				parent.childrenA[1] = B2;
				parent.childrenA[2] = B3;
				parent.childrenA[3] = B4;
				parent.nodeSplit(B5);
			}else if(k>8){
				int x = k/4;
				int i = 0;
				BNode B1 = new BNode(true);
				BNode B2 = new BNode(true);
				BNode B3 = new BNode(true);
				BNode B4 = new BNode(true);
				while(i<x){
					B1.childrenB[i] = list[i];
					i++;
				}
				while(i<2*x){
					B2.childrenB[i-x] = list[i];
					i++;
				}
				while(i<3*x){
					B3.childrenB[i-2*x] = list[i];
					i++;
				}
				while(i<k){
					B4.childrenB[i-3*x] = list[i];
					i++;
				}
				
				B1.min = childrenB[0].min;
				B2.min = childrenB[0].min;
				B3.min = childrenB[0].min;
				B4.min = childrenB[0].min;
				
				for(int j=1;j<B1.childrenB.length;j++){
					if(!(B1.childrenB[j]==null)){
						B1.markers[j-1] = B1.childrenB[j].min;
					}
				}
				for(int j=1;j<B2.childrenB.length;j++){
					if(!(B2.childrenB[j]==null)){
						B2.markers[j-1] = B2.childrenB[j].min;
					}
				}
				for(int j=1;j<B3.childrenB.length;j++){
					if(!(B3.childrenB[j]==null)){
						B3.markers[j-1] = B3.childrenB[j].min;
					}
				}
				for(int j=1;j<B4.childrenB.length;j++){
					if(!(B4.childrenB[j]==null)){
						B4.markers[j-1] = B4.childrenB[j].min;
					}
				}
				B1.parent = parent;
				B2.parent = parent;
				B3.parent = parent;
				B4.parent = parent;
				parent.childrenA[0] = B1;
				parent.childrenA[1] = B2;
				parent.childrenA[2] = B3;
				parent.childrenA[3] = B4;
			}else if(k>6){
				int x = k/3;
				int i = 0;
				BNode B1 = new BNode(true);
				BNode B2 = new BNode(true);
				BNode B3 = new BNode(true);
				while(i<x){
					B1.childrenB[i] = list[i];
					i++;
				}
				while(i<2*x){
					B2.childrenB[i-x] = list[i];
					i++;
				}
				while(i<k){
					B3.childrenB[i-2*x] = list[i];
					i++;
				}
				
				B1.min = childrenB[0].min;
				B2.min = childrenB[0].min;
				B3.min = childrenB[0].min;
				
				for(int j=1;j<B1.childrenB.length;j++){
					if(!(B1.childrenB[j]==null)){
						B1.markers[j-1] = B1.childrenB[j].min;
					}
				}
				for(int j=1;j<B2.childrenB.length;j++){
					if(!(B2.childrenB[j]==null)){
						B2.markers[j-1] = B2.childrenB[j].min;
					}
				}
				for(int j=1;j<B3.childrenB.length;j++){
					if(!(B3.childrenB[j]==null)){
						B3.markers[j-1] = B3.childrenB[j].min;
					}
				}
				B1.parent = parent;
				B2.parent = parent;
				B3.parent = parent;
				parent.childrenA[0] = B1;
				parent.childrenA[1] = B2;
				parent.childrenA[2] = B3;
			}else{
				int x = k/2;
				int i = 0;
				BNode B1 = new BNode(true);
				BNode B2 = new BNode(true);
				while(i<x){
					B1.childrenB[i] = list[i];
					i++;
				}
				while(i<2*k){
					B2.childrenB[i-x] = list[i];
					i++;
				}
				
				B1.min = childrenB[0].min;
				B2.min = childrenB[0].min;
				
				for(int j=1;j<B1.childrenB.length;j++){
					if(!(B1.childrenB[j]==null)){
						B1.markers[j-1] = B1.childrenB[j].min;
					}
				}
				for(int j=1;j<B2.childrenB.length;j++){
					if(!(B2.childrenB[j]==null)){
						B2.markers[j-1] = B2.childrenB[j].min;
					}
				}
				B1.parent = parent;
				B2.parent = parent;
				parent.childrenA[0] = B1;
				parent.childrenA[1] = B2;
			}
		}else{
			throw new IOException("nodeSplit can only take a BTFile if the node is a leaf");
		}
	}
	
	public void nodeSplit(BNode bn)throws IOException{
		if(!isLeaf){
			if(parent == null){
				parent = new BNode(false);
			}
			BNode[] list = new BNode[17];
			int k = 0;
			boolean bnInsrtd = false;
			for(int i=0;i<4;i++){
				if(parent.childrenA[i] != null){
					for(int j=0;j<4;j++){
						if(parent.childrenA[i].childrenA[j] != null){
							if(parent.childrenA[i].childrenA[j].markers[0]>bn.markers[0] && !bnInsrtd){
								list[k] = bn;
								k++;
								bnInsrtd = true;
							}
							list[k] = parent.childrenA[i].childrenA[j];
							k++;
						}
					}
				}
			}
			if(k>16){
				int x = k/5;
				int i = 0;
				BNode B1 = new BNode(false);
				BNode B2 = new BNode(false);
				BNode B3 = new BNode(false);
				BNode B4 = new BNode(false);
				BNode B5 = new BNode(false);
				while(i<x){
					B1.childrenA[i] = list[i];
					i++;
				}
				while(i<2*x){
					B2.childrenA[i-x] = list[i];
					i++;
				}
				while(i<3*x){
					B3.childrenA[i-2*x] = list[i];
					i++;
				}
				while(i<4*x){
					B4.childrenA[i-3*x] = list[i];
					i++;
				}
				while(i<k){
					B5.childrenA[i-4*x] = list[i];
					i++;
				}
				
				B1.min = childrenA[0].min;
				B2.min = childrenA[0].min;
				B3.min = childrenA[0].min;
				B4.min = childrenA[0].min;
				B5.min = childrenA[0].min;
				
				for(int j=1;j<B1.childrenA.length;j++){
					if(!(B1.childrenA[j] == null)){
						B1.markers[j-1] = B1.childrenA[j].min;
					}
				}
				for(int j=1;j<B2.childrenA.length;j++){
					if(!(B2.childrenA[j] == null)){
						B2.markers[j-1] = B2.childrenA[j].min;
					}
				}
				for(int j=1;j<B3.childrenA.length;j++){
					if(!(B3.childrenA[j] == null)){
						B3.markers[j-1] = B3.childrenA[j].min;
					}
				}
				for(int j=1;j<B4.childrenA.length;j++){
					if(!(B4.childrenA[j] == null)){
						B4.markers[j-1] = B4.childrenA[j].min;
					}
				}
				for(int j=1;j<B5.childrenA.length;j++){
					if(!(B5.childrenA[j] == null)){
						B5.markers[j-1] = B5.childrenA[j].min;
					}
				}
				B1.parent = parent;
				B2.parent = parent;
				B3.parent = parent;
				B4.parent = parent;
				parent.childrenA[0] = B1;
				parent.childrenA[1] = B2;
				parent.childrenA[2] = B3;
				parent.childrenA[3] = B4;
				parent.nodeSplit(B5);
			}else if(k>8){
				int x = k/4;
				int i = 0;
				BNode B1 = new BNode(false);
				BNode B2 = new BNode(false);
				BNode B3 = new BNode(false);
				BNode B4 = new BNode(false);
				while(i<x){
					B1.childrenA[i] = list[i];
					i++;
				}
				while(i<2*x){
					B2.childrenA[i-x] = list[i];
					i++;
				}
				while(i<3*x){
					B3.childrenA[i-2*x] = list[i];
					i++;
				}
				while(i<k){
					B4.childrenA[i-3*x] = list[i];
					i++;
				}
				
				B1.min = childrenA[0].min;
				B2.min = childrenA[0].min;
				B3.min = childrenA[0].min;
				B4.min = childrenA[0].min;
				
				for(int j=1;j<B1.childrenA.length;j++){
					if(!(B1.childrenA[j] == null)){
						B1.markers[j-1] = B1.childrenA[j].min;
					}
				}
				for(int j=1;j<B2.childrenA.length;j++){
					if(!(B2.childrenA[j] == null)){
						B2.markers[j-1] = B2.childrenA[j].min;
					}
				}
				for(int j=1;j<B3.childrenA.length;j++){
					if(!(B3.childrenA[j] == null)){
						B3.markers[j-1] = B3.childrenA[j].min;
					}
				}
				for(int j=1;j<B4.childrenA.length;j++){
					if(!(B4.childrenA[j] == null)){
						B4.markers[j-1] = B4.childrenA[j].min;
					}
				}
				B1.parent = parent;
				B2.parent = parent;
				B3.parent = parent;
				B4.parent = parent;
				parent.childrenA[0] = B1;
				parent.childrenA[1] = B2;
				parent.childrenA[2] = B3;
				parent.childrenA[3] = B4;
			}else if(k>6){
				int x = k/3;
				int i = 0;
				BNode B1 = new BNode(false);
				BNode B2 = new BNode(false);
				BNode B3 = new BNode(false);
				while(i<x){
					B1.childrenA[i] = list[i];
					i++;
				}
				while(i<2*x){
					B2.childrenA[i-x] = list[i];
					i++;
				}
				while(i<k){
					B3.childrenA[i-2*x] = list[i];
					i++;
				}
				
				B1.min = childrenA[0].min;
				B2.min = childrenA[0].min;
				B3.min = childrenA[0].min;
				
				for(int j=1;j<B1.childrenA.length;j++){
					if(!(B1.childrenA[j] == null)){
						B1.markers[j-1] = B1.childrenA[j].min;
					}
				}
				for(int j=1;j<B2.childrenA.length;j++){
					if(!(B2.childrenA[j] == null)){
						B2.markers[j-1] = B2.childrenA[j].min;
					}
				}
				for(int j=1;j<B3.childrenA.length;j++){
					if(!(B3.childrenA[j] == null)){
						B3.markers[j-1] = B3.childrenA[j].min;
					}
				}
				B1.parent = parent;
				B2.parent = parent;
				B3.parent = parent;
				parent.childrenA[0] = B1;
				parent.childrenA[1] = B2;
				parent.childrenA[2] = B3;
			}else{
				int x = k/2;
				int i = 0;
				BNode B1 = new BNode(false);
				BNode B2 = new BNode(false);
				while(i<x){
					B1.childrenA[i] = list[i];
					i++;
				}
				while(i<2*k){
					B2.childrenA[i-x] = list[i];
					i++;
				}
				
				B1.min = childrenA[0].min;
				B2.min = childrenA[0].min;
				
				for(int j=1;j<B1.childrenA.length;j++){
					if(!(B1.childrenA[j] == null)){
						B1.markers[j-1] = B1.childrenA[j].min;
					}
				}
				for(int j=1;j<B2.childrenA.length;j++){
					if(!(B2.childrenA[j] == null)){
						B2.markers[j-1] = B2.childrenA[j].min;
					}
				}
				B1.parent = parent;
				B2.parent = parent;
				parent.childrenA[0] = B1;
				parent.childrenA[1] = B2;
			}
		}else{
			throw new IOException("nodeSplit can only take a BNode if the node is not a leaf");
		}
	}
	
}

class BTFile{
	
	public static final int NULL = -1*((int)Math.pow(2,31));
	
	public int max;
	
	public int min;
	
	public File file;
	
	public BTFile(String s)throws IOException{
		file = new File(s);
		PrintWriter prn = new PrintWriter(file);
		prn.close();
		max = NULL;
		min = NULL;
	}
	
	public void insert(int x)throws IOException{
		if(isFull()){
			throw new IOException("File overflow error");
		}
		String s = "";
		Scanner sc = new Scanner(file);
		boolean b = true;
		while(sc.hasNext()){
			String str = sc.nextLine();
			int i = Integer.parseInt(str);
			if(i>x && b){
				s = s + Integer.toString(x) + "\n";
				b = false;
			}
			s = s+str+"\n";
		}
		if(b){
			s = s + Integer.toString(x) + "\n";
		}
		PrintWriter prn = new PrintWriter(file);
		prn.println(s);
		prn.close();
		if(max == NULL){
			max = x;
		}
		if(max < x){
			max = x;
		}
		if(min == NULL){
			min = x;
		}
		if(min > x){
			min = x;
		}
	}
	
	public BTFile split()throws IOException{
		Scanner sc = new Scanner(file);
		int i = 0;
		BTFile f = new BTFile("file"+Integer.toString(BTree2.theInt)+".txt");
		BTree2.theInt++;
		String[] s = new String[126];
		PrintWriter prn = new PrintWriter(f.file);
		while(sc.hasNext()){
			String str = sc.nextLine();
			if(i>125){
				prn.println(str);
			}else{
				s[i] = str;
			}
			f.max = max;
			if(i == 125){
				max = Integer.parseInt(str);
			}
			if(i == 126){
				f.min = Integer.parseInt(str);
			}
			i++;
		}
		prn.close();
		prn = new PrintWriter(file);
		for(int j=0;j<126;j++){
			prn.println(s[j]);
		}
		prn.close();
		return f;
	}
	
	public boolean isFull()throws IOException{
		int i = 0;
		Scanner sc = new Scanner(file);
		while(sc.hasNext()){
			i++;
			sc.nextLine();
		}
		if(i<256){
			return false;
		}
		return true;
	}
	
}

