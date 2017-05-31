package com.disneyapi.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.disneyapi.exceptions.DogServiceBaseException;
import com.disneyapi.model.Dog;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface DogService {

	public List<String> getPicsByBreed(String breed);
	
	public Dog vote(String picurl, int voteType) throws DogServiceBaseException;
	
	public Dog getDogDetails(String picurl)  throws DogServiceBaseException, JsonParseException, JsonMappingException, IOException;
	
	public int getLikes(String picurl) throws DogServiceBaseException;
	
	public Dog addDog(Dog dog);
	
	public Map<String, List<String>> getAllDogPictures();
	
	public void deleteDog(Dog dog) throws DogServiceBaseException;
}
