package com.text.highlighter;

public class SelectedWordPOJO {

	String word;
	int start;
	int end;
	
	public SelectedWordPOJO(String word, int start, int end){
		this.word = word;
		this.start = start;
		this.end = end;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
	
}
