package com.disneyapi;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.disneyapi.exceptions.DogServiceBaseException;
import com.disneyapi.model.Dog;
import com.disneyapi.repositories.DogRepository;
import com.disneyapi.service.DogServiceImpl;

public class DogAppUnitTest {

	@InjectMocks
	private DogServiceImpl dogService;

	@Mock
	private DogRepository dogRepository;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetPicsByBreed() throws Exception {
		List<Dog> returnDogs = getDogsforBreed("Yorkie");
		Mockito.when(dogRepository.findByBreed(Mockito.anyString())).thenReturn(returnDogs);
		List<String> picsByBreed = dogService.getPicsByBreed("Yorkie");
		Assert.assertEquals("the size of the list must be ", 3, picsByBreed.size());
		Assert.assertEquals("the first elsemnt in the list must be ", "http://i.imgur.com/oSieVUO.png",
				picsByBreed.get(0));
	}

	@Test
	public void testInvalidBreed() throws Exception {
		List<Dog> returnDogs = getDogsforBreed("abcd");
		Mockito.when(dogRepository.findByBreed(Mockito.anyString())).thenReturn(returnDogs);
		List<String> picsByBreed = dogService.getPicsByBreed("abcd");
		Assert.assertEquals("the size of the list must be ", 0, picsByBreed.size());
	}

	@Test
	public void testVoteLike() throws Exception {
		Dog d = getDog("http://i.imgur.com/oSieVUO.png", "Yorkie", "Dog with breed Yorkie", "F", 10, 0);

		Mockito.when(dogRepository.findOne("http://i.imgur.com/oSieVUO.png")).thenReturn(d);
		Mockito.when(dogRepository.save(d)).thenReturn(d);
		Dog votedDog = dogService.vote("http://i.imgur.com/oSieVUO.png", 1);
		Assert.assertEquals("the like count of the dog must be ",11 , votedDog.getLiked());
	}
	@Test
	public void testGetLikes() throws Exception {
		Dog d = getDog("http://i.imgur.com/oSieVUO.png", "Yorkie", "Dog with breed Yorkie", "F", 10, 0);
		Mockito.when(dogRepository.findOne("http://i.imgur.com/oSieVUO.png")).thenReturn(d);
		int likeCount = dogService.getLikes("http://i.imgur.com/oSieVUO.png");
		Assert.assertEquals("the total like count of the dog must be ",10 , likeCount);
	}
	@Test(expected = DogServiceBaseException.class)
	public void testGetLikesForANonExistingDog() throws Exception {
		Mockito.when(dogRepository.findOne("http://i.imgur.com/wrong.png")).thenReturn(null);
		dogService.getLikes("http://i.imgur.com/wrong.png");
	}
	
	@Test(expected = DogServiceBaseException.class)
	public void testVoteLikeForANonExistingDog() throws Exception {
		Mockito.when(dogRepository.findOne("http://i.imgur.com/wrong.png")).thenReturn(null);
		Dog votedDog = dogService.vote("http://i.imgur.com/wrong.png", 1);
		Assert.assertNull(votedDog);
	}
	@Test
	public void testVoteUnLike() throws Exception {
		Dog d = getDog("http://i.imgur.com/oSieVUO.png", "Yorkie", "Dog with breed Yorkie", "F", 10, 6);

		Mockito.when(dogRepository.findOne("http://i.imgur.com/oSieVUO.png")).thenReturn(d);
		Mockito.when(dogRepository.save(d)).thenReturn(d);
		Dog votedDog = dogService.vote("http://i.imgur.com/oSieVUO.png", 0);
		Assert.assertEquals("the dislike count of the dog must be ", 7, votedDog.getUnliked());
	}
	@Test
	public void testGetDogDetails() throws Exception {
		Dog d = getDog("http://i.imgur.com/oSieVUO.png", "Yorkie", "Dog with breed Yorkie", "F", 10, 6);
		Mockito.when(dogRepository.findByPictureurl("http://i.imgur.com/oSieVUO.png")).thenReturn(d);
		dogService.getDogDetails("http://i.imgur.com/oSieVUO.png");
		Assert.assertEquals("the picture url in the dog must be ", "http://i.imgur.com/oSieVUO.png",
				d.getPictureurl());
	}
	@Test(expected = DogServiceBaseException.class)
	public void testGetEmptyDogDetails() throws Exception {
		Mockito.when(dogRepository.findByPictureurl("http://i.imgur.com/.png")).thenReturn(null);
		Dog dog = dogService.getDogDetails("http://i.imgur.com/.png");
		Assert.assertNull(dog);
	}
	@Test
	public void testGDogDetails() throws Exception {
		Dog d = getDog("http://i.imgur.com/oSieVUO.png", "Yorkie", "Dog with breed Yorkie", "F", 10, 6);
		Mockito.when(dogRepository.findByPictureurl("http://i.imgur.com/oSieVUO.png")).thenReturn(d);
		dogService.getDogDetails("http://i.imgur.com/oSieVUO.png");
		Assert.assertEquals("the picture url in the dog must be ", "http://i.imgur.com/oSieVUO.png",
				d.getPictureurl());
	}

	public Dog getDog(String pic, String breed, String desc, String gender, int like, int unlike) {

		return new Dog(pic, breed, desc, gender, like, unlike);
	}

	private List<Dog> getDogsforBreed(String breed) {
		List<Dog> dogs = new ArrayList<Dog>();
		switch (breed) {
		case "Yorkie": {
			dogs.add(new Dog("http://i.imgur.com/oSieVUO.png", "Yorkie", "Dog with breed Yorkie", "F", 1, 1));
			dogs.add(new Dog("http://i.imgur.com/qWLKy8a.jpg", "Yorkie", "Dog with breed Yorkie", "M", 0, 0));
			dogs.add(new Dog("http://i.imgur.com/oSieVUO.png", "Yorkie", "Dog with breed Yorkie", "F", 10, 0));
			break;
		}
		case "abc": {
		}
		}
		return dogs;
	}

	private List<String> getDogPicsforBreed(String breed) {
		List<String> pics = new ArrayList<String>();
		switch (breed) {
		case "Yorkie": {
			pics.add("http://i.imgur.com/oSieVUO.png");
			pics.add("http://i.imgur.com/qWLKy8a.jpg");
			pics.add("http://i.imgur.com/oSieVUO.png");
			break;
		}
		case "abc": {
		}
		}
		return pics;
	}
}
