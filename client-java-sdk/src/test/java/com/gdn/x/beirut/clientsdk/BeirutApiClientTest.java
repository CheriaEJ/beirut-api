package com.gdn.x.beirut.clientsdk;


import static org.mockito.MockitoAnnotations.initMocks;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gdn.common.client.GdnRestClientConfiguration;
import com.gdn.common.util.GdnHttpClientHelper;
import com.gdn.common.web.param.MandatoryRequestParam;
import com.gdn.common.web.wrapper.request.SimpleRequestHolder;
import com.gdn.common.web.wrapper.response.GdnBaseRestResponse;
import com.gdn.common.web.wrapper.response.GdnRestListResponse;
import com.gdn.x.beirut.dto.request.ListStringRequest;
import com.gdn.x.beirut.dto.response.CandidateDTOResponse;



public class BeirutApiClientTest {
  private static final String REQUEST_ID = "REQUEST_ID";
  private static final String USERNAME = "USERNAME";
  private static final String CLIENT_ID = "CLIENT_ID";
  private static final String CHANNEL_ID = "CHANNEL_ID";
  private static final String STORE_ID = "STORE_ID";
  private static final String HOST = "localhost";
  private static final String PASSWORD = "password";
  private static final String CONTEXT_PATH = "beirut/docs/api";
  private static final String CONTEXT_PATH_TEST = "/beirut/docs/api";
  private static final String ID_CANDIDATE = "id_candidate";
  private static final String JSON = MediaType.APPLICATION_JSON_VALUE;
  private static final Integer PORT = 8080;
  private static final int PAGE = 0;
  private static final int SIZE = 5;
  private static final int CONNECTION_TIMEOUT_IN_MS = 6000;

  private MandatoryRequestParam mandatoryRequestParam;
  private GdnBaseRestResponse gdnBaseResponse;
  private GdnRestListResponse<CandidateDTOResponse> gdnRestListCandidate;
  private SimpleRequestHolder simpleRequestHolder;
  private HashMap<String, String> additionalRequestParam;
  private TypeReference<GdnBaseRestResponse> typeRef;
  private final ListStringRequest listPositionIdString = new ListStringRequest();
  private final Long start = new Long(0);
  private final Long end = new Long(99999999);

  @Mock
  private GdnHttpClientHelper httpClientHelper;

  @Mock
  private GdnRestClientConfiguration clientConfig;

  @InjectMocks
  private BeirutApiClient beirutApiClient;


  @Before
  public void initialize() throws Exception {
    initMocks(this);
    this.gdnBaseResponse = new GdnBaseRestResponse(REQUEST_ID);
    List<String> list = new ArrayList<String>();
    list.add("ad");
    this.listPositionIdString.setValues(list);
    this.clientConfig = new GdnRestClientConfiguration(USERNAME, PASSWORD, HOST, PORT, CLIENT_ID,
        CHANNEL_ID, STORE_ID);
    this.clientConfig.setConnectionTimeoutInMs(CONNECTION_TIMEOUT_IN_MS);
    this.beirutApiClient = new BeirutApiClient(this.clientConfig, CONTEXT_PATH);
    this.mandatoryRequestParam = MandatoryRequestParam.generateMandatoryRequestParam(STORE_ID,
        CHANNEL_ID, CLIENT_ID, REQUEST_ID, USERNAME, USERNAME);
    typeRef = new TypeReference<GdnBaseRestResponse>() {};
    this.beirutApiClient.setTypeRef(typeRef);
    ReflectionTestUtils.setField(beirutApiClient, "httpClientHelper", httpClientHelper,
        GdnHttpClientHelper.class);
  }

  @After
  public void noMoreTransaction() {
    Mockito.verifyNoMoreInteractions(this.httpClientHelper);
  }

  @Test
  public void testApplyNewPosition() throws Exception {
    this.additionalRequestParam = new HashMap<String, String>();
    this.additionalRequestParam.put("idCandidate", ID_CANDIDATE);
    URI uriApplyNewPosition = new URI("/candidate/applyNewPosition");
    Mockito.when(this.httpClientHelper.getURI(HOST, PORT,
        CONTEXT_PATH_TEST + BeirutApiPath.APPLY_NEW_POSITION, this.mandatoryRequestParam,
        this.additionalRequestParam)).thenReturn(uriApplyNewPosition);
    // System.out.println("getURI(" + " " + HOST + "," + PORT + "," + CONTEXT_PATH_TEST
    // + BeirutApiPath.APPLY_NEW_POSITION + "," + this.mandatoryRequestParam + ","
    // + this.additionalRequestParam); DEBUG
    Mockito
        .when(this.httpClientHelper.invokePostType(uriApplyNewPosition, this.listPositionIdString,
            ListStringRequest.class, typeRef, JSON, CONNECTION_TIMEOUT_IN_MS))
        .thenReturn(this.gdnBaseResponse);

    GdnBaseRestResponse response = this.beirutApiClient.applyNewPosition(REQUEST_ID, USERNAME,
        ID_CANDIDATE, listPositionIdString);

    Mockito.verify(this.httpClientHelper).getURI(HOST, PORT,
        CONTEXT_PATH_TEST + BeirutApiPath.APPLY_NEW_POSITION, this.mandatoryRequestParam,
        this.additionalRequestParam);
    Mockito.verify(this.httpClientHelper).invokePostType(uriApplyNewPosition,
        this.listPositionIdString, ListStringRequest.class, typeRef, JSON,
        CONNECTION_TIMEOUT_IN_MS);
    Assert.assertNotNull(gdnBaseResponse);
    Assert.assertEquals(gdnBaseResponse, response);
  }

  @Test
  public void testDeleteCandidate() throws Exception {
    URI uriDeleteCandidate = new URI("/candidate/deleteCandidate");
    Mockito.when(
        this.httpClientHelper.getURI(HOST, PORT, CONTEXT_PATH_TEST + BeirutApiPath.DELETE_CANDIDATE,
            this.mandatoryRequestParam, this.additionalRequestParam))
        .thenReturn(uriDeleteCandidate);
    Mockito
        .when(this.httpClientHelper.invokePostType(uriDeleteCandidate, this.listPositionIdString,
            ListStringRequest.class, typeRef, JSON, CONNECTION_TIMEOUT_IN_MS))
        .thenReturn(this.gdnBaseResponse);

    GdnBaseRestResponse response =
        this.beirutApiClient.deleteCandidate(REQUEST_ID, USERNAME, listPositionIdString);

    Mockito.verify(this.httpClientHelper).getURI(HOST, PORT,
        CONTEXT_PATH_TEST + BeirutApiPath.DELETE_CANDIDATE, this.mandatoryRequestParam,
        this.additionalRequestParam);
    Mockito.verify(this.httpClientHelper).invokePostType(uriDeleteCandidate,
        this.listPositionIdString, ListStringRequest.class, typeRef, JSON,
        CONNECTION_TIMEOUT_IN_MS);
    Assert.assertNotNull(gdnBaseResponse);
    Assert.assertEquals(gdnBaseResponse, response);
  }

  @Test
  public void testDeletePosition() throws Exception {
    URI uriDeletePosition = new URI("/position/deletePosition");
    Mockito.when(
        this.httpClientHelper.getURI(HOST, PORT, CONTEXT_PATH_TEST + BeirutApiPath.DELETE_POSITION,
            this.mandatoryRequestParam, this.additionalRequestParam))
        .thenReturn(uriDeletePosition);
    Mockito
        .when(this.httpClientHelper.invokePostType(uriDeletePosition, this.listPositionIdString,
            ListStringRequest.class, typeRef, JSON, CONNECTION_TIMEOUT_IN_MS))
        .thenReturn(this.gdnBaseResponse);

    GdnBaseRestResponse response =
        this.beirutApiClient.deletePosition(REQUEST_ID, USERNAME, listPositionIdString);

    Mockito.verify(this.httpClientHelper).getURI(HOST, PORT,
        CONTEXT_PATH_TEST + BeirutApiPath.DELETE_POSITION, this.mandatoryRequestParam,
        this.additionalRequestParam);
    Mockito.verify(this.httpClientHelper).invokePostType(uriDeletePosition,
        this.listPositionIdString, ListStringRequest.class, typeRef, JSON,
        CONNECTION_TIMEOUT_IN_MS);
    Assert.assertNotNull(gdnBaseResponse);
    Assert.assertEquals(gdnBaseResponse, response);
  }

}