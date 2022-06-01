package com.viniciuslo66.projetoed1.Util;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

public class MyList<T> {

  public class Node<T> {

    T data;
    Node<T> prev;
    Node<T> next;

    public Node(T data) {
      this.data = data;
      this.prev = null;
      this.next = null;
    }

    public Node(Node<T> node) {
      this.data = node.data;
      this.prev = null;
      this.next = null;
    }
  }

  @JsonIgnore
  private Node<T> head;

  @JsonIgnore
  private Node<T> tail;

  @JsonIgnore
  public int size = 0;

  @JsonValue(value = true)
	@SuppressWarnings("unchecked")
	public T[] getAll() {
		T[] listAll = (T[]) new Object[size];
		
		Node<T> indexNode = head;
		int indexList = 0;
		
		// Percorrer toda a lista
		if(indexNode != null) {
			do {
				listAll[indexList] = indexNode.data;
				indexList++;
				indexNode = indexNode.next;
			} while(indexNode != null);
		}
		
		return listAll;
	}

  public void addTail(T elemento) {
    Node<T> node = new Node<T>(elemento);

    if (tail == null) {
      head = node;
      tail = node;
    } else {
      tail.next = node;
      node.prev = tail;
      tail = node;
    }
    size++;
  }

  public boolean addAll(Collection<? extends T> collection) {
    for (T t : collection) {
      this.addTail(t);
    }
    return true;
  }

}
