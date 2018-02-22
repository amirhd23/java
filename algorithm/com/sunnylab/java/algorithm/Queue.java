package com.sunnylab.java.algorithm;

import java.util.Iterator;

public class Queue<T> implements Iterable<T> {
	private Node first; // link to least recently added node
	private Node last; // link to most recently added node
	private int N; // number of Ts on the queue

	private class Node { // nested class to define nodes
		T item;
		Node next;
	}

	public boolean isEmpty() {
		return first == null;
	}

	public int size() {
		return N;
	}

	public void enqueue(T item) { // Add item to the end of the list.
		Node oldlast = last;
		last = new Node();
		last.item = item;
		last.next = null;
		if (isEmpty())
			first = last;
		else
			oldlast.next = last;
		N++;
	}

	public T dequeue() { // Remove T from the beginning of the list.
		T item = first.item;
		first = first.next;
		N--;
		if (isEmpty())
			last = null;
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
			Queue<String> queue = new Queue<String>();
			for (String s : values) {
				if (!s.equals("-")) {
					queue.enqueue(s);
				} else if (!queue.isEmpty()) {
					System.out.println(queue.dequeue().toString());
				}
			}
			System.out.println("(" + queue.size() + " left on stack)");
		}

}
