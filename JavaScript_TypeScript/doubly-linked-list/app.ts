class ListNode<T> {
	public value: T | null;
	public next: ListNode<T> | null;
	public prev: ListNode<T> | null;

	constructor() {
		this.value = null;
		this.next = null;
		this.prev = null;
	}
}

class DoubleLinkedList<T> {
	private head: ListNode<T> | null;
	private tail: ListNode<T> | null;
	private length: number;

	constructor() {
		this.head = null;
		this.tail = null;
		this.length = 0;
	}

	public push(value: T) {
		let newNode = new ListNode<T>();
		newNode.value = value;
		if (!this.head) {
			this.head = newNode;
			this.tail = newNode;
		} else {
			newNode.next = null;
			newNode.prev = this.tail;
			this.tail!.next = newNode;
			this.tail = newNode;
		}
		this.length++;
	}

	public pop(): T {
		if (this.length == 0) return null as T;

		if (this.length == 1) {
			let val = this.head!.value!;
			this.head = null;
			this.tail = null;
			this.length--;
			return val;
		} else {
			let val = this.tail!.value!;
			this.tail = this.tail!.prev;
			this.tail!.next = null;
			this.length--;
			return val;
		}
	}

	public shift(): T {
		if (this.length == 0) return null as T;

		if (this.length == 1) {
			let val = this.head!.value!;
			this.head = null;
			this.tail = null;
			this.length--;
			return val;
		} else {
			let val = this.head!.value!;
			this.head = this.head!.next;
			this.head!.prev = null;
			this.length--;
			return val;
		}
	}

	public unshift(value: T) {
		let newNode = new ListNode<T>();
		newNode.value = value;
		if(this.length == 0) {
			this.head = newNode;
			this.tail = newNode;
		} else {
			newNode.next = this.head;
			newNode.prev = null;

			this.head!.prev = newNode;
			this.head = newNode;
		}
		this.length++;
		return newNode.value;
	}

	public getRoot(): T {
		return this.head!.value!;
	}

	public getLast(): T {
		return this.tail!.value!;
	}
}