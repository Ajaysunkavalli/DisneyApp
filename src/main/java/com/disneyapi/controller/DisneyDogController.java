package com.disneyapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.disneyapi.exceptions.DogServiceBaseException;
import com.disneyapi.model.Dog;
import com.disneyapi.service.DogService;
import com.disneyapi.utils.MappingsUtil;

@RestController
@RequestMapping("/dogservice")
public class DisneyDogController {

	@Autowired
	DogService dogService;
	
	
	private final Logger logger = LoggerFactory.getLogger(DisneyDogController.class);

	/**
	 * Requiremnet : List all of the available dog pictures grouped by breed
	 * 
	 * @returne
	 * 
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Map<String, List<String>>> get() {

		logger.info("getAllDogPictures method invoked ");
		Map<String, List<String>> alldogpics = dogService.getAllDogPictures();
		return new ResponseEntity<Map<String, List<String>>>(alldogpics, HttpStatus.OK);
	}

	/**
	 * Returns all the Dogs for given breed
	 * 
	 * @param request
	 * @param response
	 * @param breed
	 * @return
	 */
	@RequestMapping(value = "/{breed}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, List<String>>> getDogPicturesByBreed(HttpServletRequest request,
			HttpServletResponse response, @PathVariable("breed") String breed) {
		logger.info("getDogPicturesByBreed method with path variable {breed} : " + breed);
		Map<String, List<String>> picurlbybreed = new HashMap<String, List<String>>();
		List<String> pictures = null;
		try {
			pictures = dogService.getPicsByBreed(breed);
			picurlbybreed.put(breed, pictures);
			logger.debug("No of pictures with " + breed + " name are " + pictures.size());
		} catch (Exception e) {
			logger.error("Error occured while getting pictures of dogs by breed " + e.getMessage());
			return new ResponseEntity<Map<String, List<String>>>(picurlbybreed, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Map<String, List<String>>>(picurlbybreed, HttpStatus.OK);
	}

	@RequestMapping(value = "/like", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> voteup(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("url") String url) {
		logger.info("/like endpoint called with input {url} : " + url);

		String resp = null;
		try {
			Dog dog = dogService.vote(url, 1);
			int likeCount = dog.getLiked();
			Map<String, String> map = new HashMap<String, String>();
			map.put("likeCount", String.valueOf(likeCount));
			resp = MappingsUtil.getJSONString(map);
		} catch (DogServiceBaseException de) {

			logger.error("error while getting like count " + de.getMessage());
			return new ResponseEntity<String>(resp, HttpStatus.NOT_FOUND);
		} catch (Exception e) {

			logger.error("error while getting like count " + e.getMessage());
			return new ResponseEntity<String>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/unlike", method = RequestMethod.POST)
	public ResponseEntity<String> votedown(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("url") String url) {
		logger.info("/unlike endpoint called with input {url} : " + url);

		String resp = null;
		try {
			Dog dog = dogService.vote(url, 0);
			int unlikeCount = dog.getUnliked();
			Map<String, String> map = new HashMap<String, String>();
			map.put("unlikeCount", String.valueOf(unlikeCount));
			resp = MappingsUtil.getJSONString(map);
		} catch (DogServiceBaseException de) {
			logger.error("error while getting unlike count " + de.getMessage());
			return new ResponseEntity<String>(resp, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			logger.error("error while getting unlike count " + e.getMessage());
			return new ResponseEntity<String>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ResponseEntity<Dog> description(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("url") String url) {
		logger.info("/details endpoint called with input {picurl}" + url);
		Dog details = null;
		try {
			details = dogService.getDogDetails(url);
			logger.debug("dog details : " + details);
		} catch (DogServiceBaseException de) {
			logger.error("error occured while getting dog details " + de.getMessage());
			return new ResponseEntity<Dog>(details, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			logger.error("error occured while getting dog details " + e.getMessage());
			return new ResponseEntity<Dog>(details, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return new ResponseEntity<Dog>(details, HttpStatus.OK);
	}

	@RequestMapping(value = "/totallikes", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> likeCount(HttpServletRequest request, HttpServletResponse response, @RequestParam("url") String url) {
		logger.info("/totallikes endpoint called with input {picurl}" + url);
		String resp = null;
		try {
			int likeCount = dogService.getLikes(url);
			logger.debug("like count : " + likeCount);
			Map<String, String> map = new HashMap<String, String>();
			map.put("likeCount", String.valueOf(likeCount));
			resp = MappingsUtil.getJSONString(map);

		} catch (DogServiceBaseException de) {
			return new ResponseEntity<String>(resp,HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>(resp,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(resp,HttpStatus.OK);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = "Application/json")
	public ResponseEntity<Dog> save(HttpServletRequest request, HttpServletResponse response, Dog dog) {
		Dog resp_dog = null;
		try {
			resp_dog = dogService.addDog(dog);
		} catch (Exception e) {
			logger.error("Error occured whie saving the dog "+e.getMessage());
			return new ResponseEntity<Dog>(resp_dog,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Dog>(resp_dog,HttpStatus.OK);
	}

}