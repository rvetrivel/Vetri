package com.qib.qibhr1.Instruction;

import java.util.ArrayList;

public class Instruction {

	private String key1;
	private String key2;
	private String key3;
	private String key4;
	private String key5;
	private String key6;
	private String key7;
	private String key8;
	private String key9;
	private String key10;

	private String key11;
	private String key12;
	private ArrayList<String> key=new ArrayList<>();
	public Instruction() {
		// TODO Auto-generated constructor stub
	}

	public Instruction(String key1, String key2, String key3, String key4,
					   String key5, String key6, String key7, String key8,String key9,String key10,String key11,String key12,ArrayList<String> key) {
		super();
		this.key = key;
		this.key1 = key1;
		this.key2 = key2;
		this.key3 = key3;
		this.key4 = key4;
		this.key5 = key5;
		this.key6 = key6;
		this.key7 = key7;
		this.key8 = key8;
		this.key9 = key9;
		this.key10 = key10;
		this.key11 = key11;
		this.key12 = key12;


	}

	public ArrayList<String> getkey() {
		return key;
	}
	public void setkey(ArrayList<String> key) {
		this.key = key;
	}
	public String getkey1() {
		return key1;
	}

	public void setkey1(String key1) {
		this.key1 = key1;
	}

	public String getkey2() {
		return key2;
	}

	public void setkey2(String key2) {

		this.key2 = key2;
	}

	public String getkey3() {
		return key3;
	}

	public void setkey3(String key3) {
		this.key3 = key3;
	}

	public String getkey4() {
		return key4;
	}

	public void setkey4(String key4) {
		this.key4 = key4;
	}
	public String getkey5() {
		return key5;
	}

	public void setkey5(String key5) {
		this.key5 = key5;
	}

	public String getkey6() {
		return key6;
	}

	public void setkey6(String key6) {
		this.key6 = key6;
	}

	public String getKey7() {
		return key7;
	}

	public void setKey7(String key7) {
		this.key7 = key7;
	}

	public String getKey8() {
		return key8;
	}

	public void setKey8(String key8) {
		this.key8 = key8;
	}
	public String getKey9() {
		return key9;
	}

	public void setKey9(String key9) {
		this.key9 = key9;
	}

	public String getKey10() {
		return key10;
	}

	public void setkey10(String key10) {
		this.key10 = key10;
	}
	public String getKey11() {

		return key11;
	}

	public void setkey11(String key11) {
		this.key11 = key11;
	}
	public String getKey12() {
		return key12;
	}

	public void setkey12(String key12) {
		this.key12 = key12;
	}

}
