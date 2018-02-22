package com.sunnylab.java.algorithm;

import java.util.Iterator;
import java.util.Scanner;

/**
 * 
 * implementation of Stack API that resizes the array, 
 * allows clients to make stacks for any type of data, 
 * and supports client use of foreach to iterate through the stack items in LIFO order.
 *
 * @param <T>
 */
public class FixedCapacityStack<T> implements Iterable<T> {
	
	private T[] a;//stack entries
	private int N;//size
	
	@SuppressWarnings("unchecked")
	public FixedCapacityStack(int cap) {
		a = (T[]) new Object[cap];
	}
	
	public boolean isEmpty() {
		return N == 0;
	}
	
	public int size() {
		return N;
	}
	
	public void push(T t) {
		if (N == a.length) {
			resize(2*a.length);
		}
		a[N++] = t;
	}
	
	public T pop() {
		T item = a[--N];
		a[N] = null;
		if (N > 0 && a.length == 4*N) {
			resize(a.length/2);
		}
		return item;
	}
	
	// Move stack of size N <= max to a new array of size max. 
	@SuppressWarnings("unchecked")
	private void resize(int max) {
		T[] temp = (T[]) new Object[max];
		for (int i = 0; i < N ; i++) {
			temp[i] = a[i];
		}
		a = temp;
	}
	
	//test
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		FixedCapacityStack<String> stack = new FixedCapacityStack<>(100);
		String line;
		while (!(line = scanner.nextLine()).isEmpty()) {
			Scanner scanner2 = new Scanner(line);
			while (scanner2.hasNext()) {
				String s = scanner2.next();
				if (!s.equals("-")) {
					stack.push(s);
				} else if (!s.isEmpty()) {
					stack.pop();
				}
			}
			scanner2.close();
			System.out.println("(" + stack.size() + " left on stack)"); 
		}
		scanner.close();
	}

	@Override
	public Iterator<T> iterator() {
		return new ReverseArrayIterator();
	}
	
	private class ReverseArrayIterator implements Iterator<T> {
		private int i = N;
		
		public boolean hasNext() {return i > 0;}
		public T next() {return a[--N];}
		public void remove() {throw new UnsupportedOperationException();}
	}
}
