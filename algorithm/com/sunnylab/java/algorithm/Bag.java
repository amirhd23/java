package com.sunnylab.java.algorithm;

import java.util.Iterator;

public class Bag<T> implements Iterable<T> {
	private Node first; // first node in list

	private class Node {
		T item;
		Node next;
	}

	public void add(T item) { // same as push() in Stack
		Node oldfirst = first;
		first = new Node();
		first.item = item;
		first.next = oldfirst;
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
}
