package com.example.usgviewer;

public class Grid2D<T extends Number> {
	
	private T[] numArray; 
	private int height;
	private int width;
	
	public Grid2D(T[] arr, int height, int width) {
		numArray = arr;
		this.height = height;
		this.width = width;
	}
	
	public int getX(int index) {
		return index % width;
	}
	
	public int getY(int index) {
		int value = (int) ((index / width) % 1 == 0 ?
				(index / width) : Math.ceil(index / width));
		
		return value;
	}
	
	public int getIndex(int xPos, int yPos) {
		return yPos * width + xPos;
	}

	public T[] getNumArray() {
		return numArray;
	}

	public void setNumArray(T[] numArray) {
		this.numArray = numArray;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

}
