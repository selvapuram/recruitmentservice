package com.heavenhr.recruitment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.heavenhr.recruitment.model.dto.OfferResponse;
import com.heavenhr.recruitment.utils.ObjectMapperUtil;

/**
 * The Class OfferControllerTest.
 * This class tests the controls the request for the CRUD operations on the Offer Entity and all
 * its child entities.
 * 
 * @author madhankumar
 * @version 1.0.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OfferControllerTest extends AbstractTest {

  /** The data path. */
  String dataPath = "src/test/resources/data/OfferData.json";

  /** The offerJson json. */
  public static JSONObject offerJson = null;

  /** The base url. */
  private String baseUrl = "/offers";

  @Before
  public void setup() throws FileNotFoundException, IOException, JSONException {
    mvc = MockMvcBuilders.webAppContextSetup(context).build();
    offerJson = new JSONObject(FileUtils.readFileToString(new File(dataPath), "UTF-8"));
  }

  @Test
  public void testCartPing() throws Exception {
    // MVC
    String uri = "/ping";
    ResponseEntity<String> response = template.getForEntity(uri, String.class);
    Assert.assertEquals("Assertion Check", response.getStatusCode().value(), HttpStatus.OK.value());
  }

  @Test
  public void testCreateOffer() throws Exception {

    String uri = baseUrl + "/";
    MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(uri);
    byte[] offer = offerJson.toString().getBytes();
    request.content(offer);

    MvcResult result = mvc.perform(request.contentType(MediaType.APPLICATION_JSON)).andReturn();

    String content = result.getResponse().getContentAsString();
    OfferResponse response = ObjectMapperUtil.convertToObject(content, OfferResponse.class);
    Assert.assertEquals("CreateOfferResponse Code", result.getResponse().getStatus(), HttpStatus.CREATED.value());
    Assert.assertNotNull("Create Offer", response);
  }

  @Test
  public void testViewOffer() throws Exception {

    String uri = baseUrl + "/" + "1";
    MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri);
    MvcResult result = mvc.perform(request.contentType(MediaType.APPLICATION_JSON)).andReturn();

    String content = result.getResponse().getContentAsString();
    OfferResponse response = ObjectMapperUtil.convertToObject(content, OfferResponse.class);
    Assert.assertEquals("ViewOfferResponse Code", result.getResponse().getStatus(), HttpStatus.OK.value());
    Assert.assertNotNull("View Offer", response);
  }

  @Test
  public void testViewAllOfferSuccess() throws Exception {
    String uri = baseUrl + "/";
    MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri);
    MvcResult result = mvc.perform(request.contentType(MediaType.APPLICATION_JSON)).andReturn();

    String content = result.getResponse().getContentAsString();
    OfferResponse response = ObjectMapperUtil.convertToObject(content, OfferResponse.class);
    Assert.assertEquals("ViewOffersResponse Code", result.getResponse().getStatus(), HttpStatus.OK.value());
    Assert.assertNotNull("Find Offer", response);
  }

}
