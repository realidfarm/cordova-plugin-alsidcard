package com.sdses.readcardservice;
interface IReadCardService{
	
	boolean startReadCard();
	boolean stopReadCard();
	boolean readCard();
	String  getSAMID();
	String  getBoardVersion();	
	String  getBoardSN();

}