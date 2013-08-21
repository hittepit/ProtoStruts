package be.fabrice.proto.model;

public class Isbn {
	private String value;

	public Isbn(String value){
		String cleanValue = cleanValue(value);
		if(!validate(cleanValue)){
			throw new IllegalArgumentException("Isbn not correct: "+value);
		}
		if(cleanValue.length()==10){
			cleanValue = transform10to13(cleanValue);
		}
		this.value = cleanValue;
	}

	public String getValue() {
		return value;
	}

	public static boolean validate(String value){
		if(value==null){
			return false;
		}
		String cleanValue = cleanValue(value);
		//Dans le if et le else if, les lignes en commentaires sont normalement les bonnes et le return est faux
		if(cleanValue.length()==13){
//			int c13 = digitAt(cleanValue,12);
//			int c = control13(cleanValue);
//			return c13==c;
			return cleanValue.startsWith("978");
		} else if(cleanValue.length()==10){
//			String c10 = cleanValue.substring(9,10);
//			String c = control10(cleanValue);
//			return c10.equals(c);
			return true;
		}
		return false;
	}

	private static String cleanValue(String value){
		return value.replace("-","").replace("/","").replace(" ","").trim();
	}

	private String transform10to13(String value10){
		String extended = "978"+ value10.substring(0,9);
		return extended+control13(extended);
	}

	private static int control13(String value13){
		int v1 = digitAt(value13,0)+digitAt(value13,2)+digitAt(value13,4)+digitAt(value13,6)+digitAt(value13,8)+digitAt(value13,10);
		int v2 = digitAt(value13,1)+digitAt(value13,3)+digitAt(value13,5)+digitAt(value13,7)+digitAt(value13,9)+digitAt(value13,11);
		int control = (10-((v1+3*v2)%10))%10;
		return control;
	}

	private static String control10(String value10){
		int total = 0;
		for(int i = 0;i<9;i++){
			int n = digitAt(value10,i);
			total += (n*(10-i));
		}
		int c = total%11;
		if(c>0){
			c=11-c;
		}
		return c!=10? Integer.valueOf(c).toString():"X";
	}

	private static int digitAt(String c, int index){
		return Character.digit(c.charAt(index),10);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Isbn other = (Isbn) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return getValue();
	}
}
