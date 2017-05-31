package com.disneyapi.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.disneyapi.exceptions.DogServiceBaseException;
import com.disneyapi.model.Dog;
import com.disneyapi.repositories.DogRepository;
import com.disneyapi.utils.MappingsUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DogServiceImpl implements DogService {

	@Autowired
	DogRepository dogRepository;
	
	@Autowired
	ObjectMapper mapper;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<String> getPicsByBreed(String breed) {
		// TODO Auto-generated method stub
		Comparator<Dog> likeCountComparator = new Comparator<Dog>() {

			@Override
			public int compare(Dog d1, Dog d2) {
				// TODO Auto-generated method stub
				return Integer.compare(d2.getLiked(), d1.getLiked());
			}
		};
		List<Dog> dogs = dogRepository.findByBreed(breed);
		List<String> picurl = new ArrayList<String>();
			  Collections.sort(dogs,likeCountComparator );
		for(Dog dog:dogs){
			String url = dog.getPictureurl();
			picurl.add(url);
		}
		return picurl;
	}

	public Dog vote(String picurl, int voteType) throws DogServiceBaseException {
		Dog dog = dogRepository.findOne(picurl);
		logger.debug("dog details " + dog);
		
		logger.info("vote type " + voteType);
		if (dog == null) {
			logger.error("Dog with pic " + picurl + " not found");
			throw new DogServiceBaseException("Dog with pic " + picurl + " not found");
		} else {
			if (voteType == 1) {
				int likeCount = dog.getLiked();
				dog.setLiked(likeCount + 1);
			} else {
				int unlikeCount = dog.getUnliked();
				dog.setUnliked(unlikeCount + 1);
			}
			Dog ret_dog = dogRepository.save(dog);
			return ret_dog;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.disney.service.DogService#getDogDetails(java.lang.String)
	 */
	@Override
	public Dog getDogDetails(String picurl) throws DogServiceBaseException {
		Dog dog = dogRepository.findByPictureurl(picurl);
		logger.debug("dog details " + dog);
		if (dog == null) {
			logger.error("Dog with pic " + picurl + " not found");
			throw new DogServiceBaseException("Dog with pic " + picurl + " not found");
		} else {
			return dog;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.disney.service.DogService#getLikes(java.lang.String)
	 */
	@Override
	public int getLikes(String picurl) throws DogServiceBaseException {
		Dog dog = dogRepository.findOne(picurl);
		logger.debug("dog details " + dog);
		if (dog == null) {
			logger.error("Dog with pic " + picurl + " not found");
			throw new DogServiceBaseException("Dog with pic " + picurl + " not found");
		} else {
			int likes = dog.getLiked();
			return likes;
		}
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.disney.service.DogService#addDog(com.disney.model.Dog)
	 */
	@Override
	public Dog addDog(Dog dog) {
		logger.debug("adding dog " + dog);
		Dog d = dogRepository.save(dog);
		logger.info("dog added " + dog.getPictureurl());
		return d;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.disney.service.DogService#deleteDog(com.disney.model.Dog)
	 */
	@Override
	public void deleteDog(Dog dog) throws DogServiceBaseException {

		Dog d = dogRepository.findOne(dog.getPictureurl());
		logger.debug("dog details " + dog);
		if (d == null) {
			logger.error("Dog with pic " + dog.getPictureurl() + " not found");
			throw new DogServiceBaseException("Dog with pic " + dog.getPictureurl() + " not found");
		} else {
			dogRepository.delete(dog);
		}
	}

	@Override
	public Map<String, List<String>> getAllDogPictures() {
         
		Comparator<Dog> likeCountComparator = new Comparator<Dog>() {

			@Override
			public int compare(Dog d1, Dog d2) {
				// TODO Auto-generated method stub
				return Integer.compare(d2.getLiked(), d1.getLiked());
			}
			
		};
		
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        List<String> breeds= dogRepository.findDistinctBreeds();
		for(String breed : breeds)
		    {
			List<Dog> dogs = dogRepository.findByBreed(breed);
			if (breeds.size() > 0) {
				  Collections.sort(dogs,likeCountComparator );
				}
		     List<String> imgs = new ArrayList<String>();
			 for(Dog dog : dogs)
		               {
		                  String dogpic = dog.getPictureurl();
		                  imgs.add(dogpic);
		               }
			 
		        map.put(breed,imgs);
		   } 
		
		return map;
	}

}
