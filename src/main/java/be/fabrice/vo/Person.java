package be.fabrice.vo;

public class Person {
	private Long id;
	private String lastname;
	private String firstname;
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public static Builder builder(){
		return new Builder();
	}
	
	public static class Builder{
		private String lastname;
		private String firstname;
		
		private Builder(){}
		
		public Builder lastname(String lastname){
			this.lastname = lastname;
			return this;
		}
		
		public Builder firstname(String firstname){
			this.firstname = firstname;
			return this;
		}
		
		public Person build(){
			Person p = new Person();
			p.setFirstname(this.firstname.trim().toUpperCase());
			p.setLastname(this.lastname.trim().toUpperCase());
			return p;
		}
	}
}
