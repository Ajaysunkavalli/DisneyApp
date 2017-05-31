package com.disneyapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.disneyapi.model.Dog;

@Repository
public interface DogRepository extends JpaRepository<Dog, String> {
	List<Dog> findByBreed(String breed);
	
	Dog findByPictureurl(String url);
	
	@Query("SELECT DISTINCT breed FROM Dog")
	List<String> findDistinctBreeds();
}
