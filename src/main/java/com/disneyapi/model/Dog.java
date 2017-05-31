package com.disneyapi.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Manoj kumar chitabathina This class represents a Dog entity class.
 *
 */

@Entity
@Table
public class Dog implements Serializable{

	/**
	 * pictureurl is used to store unique picture url
	 */
	@Id
	private String pictureurl;

	/**
	 * breed is used to hold the breed of the dog
	 */
	private String breed;

	/**
	 * gender is used to hold the gender of the particular dog
	 */
	private String gender;
	/**
	 * description is used to hold the details of the dog
	 */
	private String description;
	/**
	 * like is used to hold the count of likes
	 */
	private int liked;

	/**
	 * dislike is used to hold the count of dislikes
	 */
	private int unliked;

	/**
	 * 
	 */
	Dog() {

	}

	
	public Dog(String pictureurl, String breed, String gender, String description, int liked, int unliked) {
		super();
		this.pictureurl = pictureurl;
		this.breed = breed;
		this.gender = gender;
		this.description = description;
		this.liked = liked;
		this.unliked = unliked;
	}


	/**
	 * @return the pictureurl
	 */
	public String getPictureurl() {
		return pictureurl;
	}

	/**
	 * @param pictureurl the pictureurl to set
	 */
	public void setPictureurl(String pictureurl) {
		this.pictureurl = pictureurl;
	}

	/**
	 * @return the breed
	 */
	public String getBreed() {
		return breed;
	}

	/**
	 * @param breed the breed to set
	 */
	public void setBreed(String breed) {
		this.breed = breed;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the liked
	 */
	public int getLiked() {
		return liked;
	}

	/**
	 * @param liked the liked to set
	 */
	public void setLiked(int liked) {
		this.liked = liked;
	}

	/**
	 * @return the unliked
	 */
	public int getUnliked() {
		return unliked;
	}

	/**
	 * @param unliked the unliked to set
	 */
	public void setUnliked(int unliked) {
		this.unliked = unliked;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Dog [pictureurl=" + pictureurl + ", breed=" + breed + ", gender=" + gender + ", description="
				+ description + ", liked=" + liked + ", unliked=" + unliked + "]";
	}

	
}
