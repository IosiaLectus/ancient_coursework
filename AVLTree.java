/*
This was written for my Data Structures II class. Other than this comment it has not been altered.
*/


import java.util.*;
import java.io.*;

public class AVLTree {

	public AVLNode root;

	public static void main(String[] ary){
		AVLTree t = new AVLTree();
		t.insert(87);
		t.insert(58);
		t.insert(02);
		t.insert(46);
		t.insert(86);
		t.insert(65);
		t.insert(123);
		t.insert(03);
	}

	public AVLTree(){
		root = null;
	}

	public AVLTree(AVLNode n){
		root = n;
	}

	public void insert(int x){
		if(root == null){
			root = new AVLNode(x,0);
			return;
		}
		AVLNode n = root;
		while(true){
			if(x >= n.getValue()){
				if(!n.hasRight()){
					n.setRight(new AVLNode(x,0));
					AVLNode m = n;
					while(m.hasPrev()){
						m = m.getPrev();
						m.setHeight(m.getHeight()+1);
					}
					break;
				}
				n = n.getRight();
			}
			if(x < n.getValue()){
				if(!n.hasLeft()){
					n.setLeft(new AVLNode(x,0));
					AVLNode m = n;
					while(m.hasPrev()){
						m = m.getPrev();
						m.setHeight(m.getHeight()+1);
					}
					break;
				}
				n = n.getLeft();
			}
		}
		while(n.hasPrev()){
			n = n.getPrev();
			int d = 0;
			if(n.hasLeft() && n.hasRight()){
				d = n.getLeft().getHeight()-n.getRight().getHeight();
			}else{
				if(n.hasRight()){
					d =-1-n.getRight().getHeight();
				}else{
					d = n.getLeft().getHeight()+1;
				}
			}
			if(!(d<=1 || d>=-1)){
				if(n.getLeft().getHeight()>n.getRight().getHeight()){
					singleRotationRight(n);
					d = n.getLeft().getHeight()-n.getRight().getHeight();
					if(!(d<=1 || d>=-1)){
						doubleRotationLeft(n);
					}
				}else{
					singleRotationLeft(n);
					d = n.getLeft().getHeight()-n.getRight().getHeight();
					if(!(d<=1 || d>=-1)){
						doubleRotationRight(n);
					}
				}
			}
		}
		while(root.hasPrev()){
			root = root.getPrev();
			}
	}


	public void singleRotationRight(AVLNode n){
		AVLNode m = n.getLeft();
		n.setLeft(m.getRight());
		m.getRight().setPrev(n);
		m.setRight(n);
		m.setPrev(n.getPrev());
		n.setPrev(m);
		int x = n.getHeight();
		n.setHeight(m.getHeight());
		m.setHeight(x);
	}

	public void singleRotationLeft(AVLNode n){
		AVLNode m = n.getRight();
		n.setRight(m.getLeft());
		m.getLeft().setPrev(n);
		m.setLeft(n);
		m.setPrev(n.getPrev());
		n.setPrev(m);
		int x = n.getHeight();
		n.setHeight(m.getHeight());
		m.setHeight(x);
	}

	public void doubleRotationRight(AVLNode n){
		singleRotationLeft(n.getRight());
		singleRotationRight(n);
	}

	public void doubleRotationLeft(AVLNode n){
		singleRotationRight(n.getLeft());
		singleRotationLeft(n);
	}

}

class AVLNode {

	private AVLNode right;
	private AVLNode left;
	private AVLNode prev;
	private int value;
	private int height;

	public AVLNode(int x,int h){
		right = null;
		left = null;
		prev = null;
		value = x;
		height = h;
	}

	public AVLNode(AVLNode r, AVLNode l, int x,int h){
		right = r;
		r.prev = this;
		left = l;
		l.prev = this;
		prev = null;
		value = x;
		height = h;
	}

	public AVLNode getRight(){ return right; }

	public AVLNode getLeft(){ return left; }

	public int getValue(){ return value; }

	public void setValue(int x){ value = x; }

	public void setRight(AVLNode r){
		right = r;
		r.prev = this;
	}

	public void setLeft(AVLNode l){
		left = l;
		l.prev = this;
	}

	public AVLNode getPrev(){
		return prev;
	}

	public void setPrev(AVLNode n){prev = n;}

	public boolean hasLeft(){
		if(left == null)
			return false;
		return true;
	}

	public boolean hasRight(){
		if(right == null)
			return false;
		return true;
	}

	public int getHeight(){return height;}

	public void setHeight(int h){height = h;}

	public boolean hasPrev(){
		if(prev == null)
			return false;
		return true;
	}
}
