package com.sunnylab.java.algorithm;

import java.util.Iterator;

/**
 * 
 * Pushdown stack (linked-list implementation)
 *
 */
public class Stack<T> implements Iterable<T> {
	private Node first;// top of stack (most recently added node)
	private int N;// number of items

	private class Node {// nested class for nodes
		private T item;
		private Node next;
	}

	public boolean isEmpty() {
		return first == null;
	}

	public int size() {
		return N;
	}

	public void push(T item) {
		// add item to top of stack
		Node oldFirst = first;
		first = new Node();
		first.item = item;
		first.next = oldFirst;
		N++;
	}

	public T pop() {
		// remove item from top of stack
		T item = first.item;
		first = first.next;
		N--;
		return item;
	}

	public Iterator<T> iterator() {
		return new ListIterator();
	}

	private class ListIterator implements Iterator<T> {
		private Node current = first;

		public boolean hasNext() {
			return current != null;
		}

		public void remove() {
			throw new UnsupportedOperationException("remove() not supported");
		}

		public T next() {
			T item = current.item;
			current = current.next;
			return item;
		}
	}

	// test
	public static void main(String[] args) {
		String input = "to be or not to - be - - that - - - is";
		String[] values = input.split("\\s");
		Stack<String> stack = new Stack<String>();
		for (String s : values) {
			if (!s.equals("-")) {
				stack.push(s);
			} else if (!stack.isEmpty()) {
				System.out.println(stack.pop().toString());
			}
		}
		System.out.println("(" + stack.size() + " left on stack)");
	}
}
