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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.heavenhr.recruitment.model.dto.ApplicationResponse;
import com.heavenhr.recruitment.utils.ObjectMapperUtil;

/**
 * The Class ApplicationControllerTest.
 * This class tests the controls the request for the CRUD operations on the Application Entity and all
 * its child entities.
 * 
 * @author madhankumar
 * @version 1.0.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationControllerTest extends AbstractTest {

  /** The data path. */
  String dataPath = "src/test/resources/data/ApplicationData.json";

  /** The offerJson json. */
  public static JSONObject applicationJson = null;

  private static final int OFFER_SUCCESS = 2;

  /** The base url. */
  private String baseUrl = "/offers/" + OFFER_SUCCESS + "/applications";

  @Before
  public void setup() throws FileNotFoundException, IOException, JSONException {
    mvc = MockMvcBuilders.webAppContextSetup(context).build();
    applicationJson = new JSONObject(FileUtils.readFileToString(new File(dataPath), "UTF-8"));
  }

  @Test
  public void testCreateApplication() throws Exception {

    String uri = baseUrl + "/";
    MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(uri);
    byte[] offer = applicationJson.toString().getBytes();
    request.content(offer);

    MvcResult result = mvc.perform(request.contentType(MediaType.APPLICATION_JSON)).andReturn();

    String content = result.getResponse().getContentAsString();
    ApplicationResponse response = ObjectMapperUtil.convertToObject(content, ApplicationResponse.class);
    Assert.assertEquals("CreateApplicationResponse Code", result.getResponse().getStatus(), HttpStatus.CREATED.value());
    Assert.assertNotNull("Create Application", response);
  }

  @Test
  public void testViewApplication() throws Exception {

    String uri = baseUrl + "/" + "1";
    MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri);
    MvcResult result = mvc.perform(request.contentType(MediaType.APPLICATION_JSON)).andReturn();

    String content = result.getResponse().getContentAsString();
    ApplicationResponse response = ObjectMapperUtil.convertToObject(content, ApplicationResponse.class);
    Assert.assertEquals("ViewApplicationResponse Code", result.getResponse().getStatus(), HttpStatus.OK.value());
    Assert.assertNotNull("View Application", response);
  }

  @Test
  public void testViewAllApplication() throws Exception {
    String uri = baseUrl + "/";
    MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri);
    MvcResult result = mvc.perform(request.contentType(MediaType.APPLICATION_JSON)).andReturn();

    String content = result.getResponse().getContentAsString();
    ApplicationResponse response = ObjectMapperUtil.convertToObject(content, ApplicationResponse.class);
    Assert.assertEquals("ViewApplicationResponse Code", result.getResponse().getStatus(), HttpStatus.OK.value());
    Assert.assertNotNull("Find Applications", response);
  }

  @Test
  public void testPatchApplication() throws Exception {

    String uri = baseUrl + "/" + "1?applicationStatus=HIRED";
    MockHttpServletRequestBuilder request = MockMvcRequestBuilders.patch(uri);
    MvcResult result = mvc.perform(request.contentType(MediaType.APPLICATION_JSON)).andReturn();

    String content = result.getResponse().getContentAsString();
    ApplicationResponse response = ObjectMapperUtil.convertToObject(content, ApplicationResponse.class);
    Assert.assertEquals("PatchApplicationResponse Code", result.getResponse().getStatus(), HttpStatus.OK.value());
    Assert.assertNotNull("Patch Application", response);
  }

}
