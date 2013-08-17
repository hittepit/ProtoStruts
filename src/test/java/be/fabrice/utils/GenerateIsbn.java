package be.fabrice.utils;

import be.fabrice.proto.model.Isbn;

public class GenerateIsbn {
	public static void main(String[] args) {
		GenerateIsbn instance = new GenerateIsbn();
		instance.launch();
	}
	
	private void launch(){
		String s="";
		for(int i = 0; i<9;i++){
			s+=Integer.valueOf((int)(Math.random()*10)).toString();
		}
		
		for(int c=0;c<10;c++){
			if(Isbn.validate("978"+s+c)){
				System.out.println("978"+s+c);
			}
		}
	}
}
