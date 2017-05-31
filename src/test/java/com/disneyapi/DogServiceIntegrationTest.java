package com.disneyapi;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@IntegrationTest("server.port:0")
public class DogServiceIntegrationTest {
	@Autowired
	private TestRestTemplate restTemplate;

	// Will contain the random free port number
	@Value("${local.server.port}")
	private int port;

	// private String getBaseUrl() {
	// return "http://localhost:" + port;
	// }

	private static final String URL = "/dogservice";

	// @Test
	// public void testGetAllDogsPics() throws Exception {
	// ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL,
	// String.class);
	//
	// int status = responseEntity.getStatusCodeValue();
	// // verify
	// Assert.assertEquals("Incorrect Response Status", HttpStatus.OK.value(),
	// status);
	// String res = responseEntity.getBody();
	// Assert.assertNotNull(res);
	// }

	@Test
	public void testGetDogs() throws Exception {
		final HttpEntity<String> requestEntity = new HttpEntity<String>(new HttpHeaders());
		try {
			Object[] obj = new Object[] {};
			// Retrieve list
			final ResponseEntity<Map> entity = restTemplate.exchange(URL+"/", HttpMethod.GET, requestEntity, Map.class,
					obj);
			final Map<String, String> entries = entity.getBody();
			Assert.assertNotNull(entries);
			final Set<String> breeds = entries.keySet();
			Assert.assertNotNull(breeds);
			Assert.assertEquals("Total number of breeds must be ", 4, breeds.size());
			Assert.assertTrue("Breed by name Yorkie must exists ", breeds.contains("Yorkie"));

		} catch (final Exception ex) {
			ex.printStackTrace();
		}

	}

	@Test
	public void testGetDogPicturesByBreed() throws Exception {
		final ObjectMapper mapper = new ObjectMapper();
		final HttpEntity<String> requestEntity = new HttpEntity<String>(new HttpHeaders());
		try {
			Object[] obj = new Object[] {};
			// Retrieve list
			final ResponseEntity<Map> entity = restTemplate.exchange(URL + "/Yorkie", HttpMethod.GET, requestEntity,
					Map.class, obj);
			final Map<String, List<String>> entries = entity.getBody();
			Assert.assertNotNull(entries);
			List<String> pics = entries.get("Yorkie");
			Assert.assertNotNull(pics);
			Assert.assertEquals("Total count of dog pics must be ", 3, pics.size());
			Assert.assertTrue("the pic belongs to yorkie breed ", pics.contains("http://i.imgur.com/qWLKy8a.jpg"));

		} catch (final Exception ex) {
			ex.printStackTrace();
		}

	}

	@Test
	public void testLikes() throws Exception {
		final ObjectMapper mapper = new ObjectMapper();
		final HttpEntity<String> requestEntity = new HttpEntity<String>(new HttpHeaders());
		try {
			Object[] obj = new Object[] {};
			// Retrieve list
		    ResponseEntity<String> entity = restTemplate.exchange(
					URL + "/totallikes?url=http://i.imgur.com/VzFTsGg.png", HttpMethod.GET, requestEntity, String.class,
					obj);
		    String count = entity.getBody();
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(count);
			String temp_count = (String) json.get("likeCount");
			int countvalue = Integer.parseInt(temp_count);

			entity = restTemplate.exchange(
					URL + "/like?url=http://i.imgur.com/VzFTsGg.png", HttpMethod.POST, requestEntity, String.class,
					obj);
			 count = entity.getBody();
			 parser = new JSONParser();
			 json = (JSONObject) parser.parse(count);
			 temp_count = (String) json.get("likeCount");
			int newcountvalue = Integer.parseInt(temp_count);
			
			Assert.assertEquals(countvalue=countvalue+1, newcountvalue);
		} catch (final Exception ex) {
			ex.printStackTrace();
		}

	}

}
