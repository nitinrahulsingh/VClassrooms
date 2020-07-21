package com.vclassrooms.Entity;

import com.google.gson.annotations.SerializedName;

public class StudentListItem {

	@SerializedName("firstName")
	private String firstName;

	@SerializedName("lastName")
	private String lastName;

	@SerializedName("photoPath")
	private String photoPath;

	@SerializedName("id")
	private int id;

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setPhotoPath(String photoPath){
		this.photoPath = photoPath;
	}

	public String getPhotoPath(){
		return photoPath;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			 firstName ;
		}
}