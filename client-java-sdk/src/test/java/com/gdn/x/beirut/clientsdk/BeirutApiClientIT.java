package com.gdn.x.beirut.clientsdk;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gdn.common.client.GdnRestClientConfiguration;
import com.gdn.common.web.wrapper.response.GdnBaseRestResponse;
import com.gdn.common.web.wrapper.response.GdnRestListResponse;
import com.gdn.common.web.wrapper.response.GdnRestSingleResponse;
import com.gdn.x.beirut.dto.request.ApplyNewPositionModelDTORequest;
import com.gdn.x.beirut.dto.request.CandidateDTORequest;
import com.gdn.x.beirut.dto.request.PositionDTORequest;
import com.gdn.x.beirut.dto.request.StatusDTORequest;
import com.gdn.x.beirut.dto.request.UpdateCandidateStatusModelDTORequest;
import com.gdn.x.beirut.dto.request.UpdatePositionModelDTORequest;
import com.gdn.x.beirut.dto.response.CandidateDTOResponse;
import com.gdn.x.beirut.dto.response.CandidateDTOResponseWithoutDetail;
import com.gdn.x.beirut.dto.response.CandidatePositionSolrDTOResponse;
import com.gdn.x.beirut.dto.response.PositionDTOResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfigClientSDK.class})
public class BeirutApiClientIT {

  private static final String CONTEXT_PATH = "/beirut/api";
  private static final String STORE_ID = "20002";
  private static final String CHANNEL_ID = "web";
  private static final String CLIENT_ID = "XBEIRUT";
  private static final Integer PORT = 8180;
  private static final String HOST = "localhost";
  private static final String PASSWORD = "DUMMY";
  private static final String USERNAME = "I_TEST_USER";
  private static final String REQUEST_ID = "ClientAPITest";


  private long timestamp;
  private BeirutApiClient beirutApiClient;
  private List<String> positionIds;
  private List<String> positionIds1;
  private GdnRestListResponse<PositionDTOResponse> resultPosition;
  private GdnRestListResponse<CandidateDTOResponseWithoutDetail> resultCandidate;

  @Before
  public void inititalize() throws Exception {
    beirutApiClient = new BeirutApiClient(new GdnRestClientConfiguration(USERNAME, PASSWORD, HOST,
        PORT, CLIENT_ID, CHANNEL_ID, STORE_ID), CONTEXT_PATH);

    timestamp = System.currentTimeMillis();
    // Generate Position
    List<PositionDTORequest> positionDTORequests = new ArrayList<PositionDTORequest>();
    for (int i = 0; i < 5; i++) {
      PositionDTORequest positionDTORequest = new PositionDTORequest();
      positionDTORequest.setTitle("Software Developer Division " + i + " " + timestamp);
      positionDTORequests.add(positionDTORequest);
    }

    // insert Position
    for (PositionDTORequest positionDTORequest : positionDTORequests) {
      beirutApiClient.insertNewPosition(REQUEST_ID, USERNAME, positionDTORequest);
    }

    // get Position ids
    positionIds = new ArrayList<String>();
    positionIds1 = new ArrayList<String>();
    resultPosition = beirutApiClient.getAllPositionByStoreId(REQUEST_ID, USERNAME);
    for (PositionDTOResponse positionDTOResponse : resultPosition.getContent()) {
      if (positionDTOResponse.getTitle().equals("Software Developer Division 1 " + timestamp)
          || positionDTOResponse.getTitle().equals("Software Developer Division 4 " + timestamp)) {
        positionIds.add(positionDTOResponse.getId());
      }
      if (positionDTOResponse.getTitle().equals("Software Developer Division 2 " + timestamp)
          || positionDTOResponse.getTitle().equals("Software Developer Division 3 " + timestamp)) {
        positionIds1.add(positionDTOResponse.getId());
      }
    }

    // assign positionIds to the new candidate
    String candidateDTORequestString = "{\"emailAddress\": \"asda@egamail.com" + timestamp
        + "\",\"firstName\": \"asducup\",\"lastName\": \"sanusias\",\"phoneNumber\": \"1\",\"positionIds\": [";
    for (String string : positionIds) {
      candidateDTORequestString += "\"" + string + "\",";
    }
    candidateDTORequestString =
        candidateDTORequestString.substring(0, candidateDTORequestString.length() - 1);
    candidateDTORequestString += "]}";

    FileInputStream inputFile =
        new FileInputStream(new File("src/test/resources/JSON/applyNewPositionRequest.json"));

    // inserting new candidate1
    beirutApiClient.insertNewCandidate(REQUEST_ID, USERNAME, candidateDTORequestString,
        "applyNewPositionRequest.json", IOUtils.toByteArray(inputFile));
    // assign positionIds1 to the new candidate1
    String candidateDTORequestString1 = "{\"emailAddress\": \"asda@egamail.com1" + timestamp
        + "\",\"firstName\": \"asducup\",\"lastName\": \"sanusias\",\"phoneNumber\": \"11\",\"positionIds\": [";
    for (String string : positionIds1) {
      candidateDTORequestString1 += "\"" + string + "\",";
    }
    candidateDTORequestString1 =
        candidateDTORequestString1.substring(0, candidateDTORequestString1.length() - 1);
    candidateDTORequestString1 += "]}";

    FileInputStream inputFile1 = new FileInputStream(
        new File("src/test/resources/JSON/updateCandidateStatusRequestJson.json"));

    // inserting new candidate1
    beirutApiClient.insertNewCandidate(REQUEST_ID, USERNAME, candidateDTORequestString1,
        "updateCandidateStatusRequestJson.json", IOUtils.toByteArray(inputFile1));

    // get candidate
    resultCandidate =
        beirutApiClient.getAllCandidateByStoreIdWithPageable(REQUEST_ID, USERNAME, 0, 3);


  }

  // UPDATED IZAL DONE
  @Test
  @Ignore
  public void testApplyNewPosition() throws Exception {
    String idCandidate = resultCandidate.getContent().get(0).getId();
    // ListStringRequest listPositionIdStrings = new ListStringRequest();
    List<String> listString = new ArrayList<String>();
    for (PositionDTOResponse positionDTOResponse : resultPosition.getContent()) {
      if (positionDTOResponse.getTitle().equals("Software Developer Division 5 " + timestamp)) {
        listString.add(positionDTOResponse.getId());
      }
    }
    // listPositionIdStrings.setValues(listString);

    ApplyNewPositionModelDTORequest applyNewPositionModelDTORequest =
        new ApplyNewPositionModelDTORequest();
    applyNewPositionModelDTORequest.setIdCandidate(idCandidate);
    applyNewPositionModelDTORequest.setListPositionIds(listString);

    GdnBaseRestResponse result =
        beirutApiClient.applyNewPosition(REQUEST_ID, USERNAME, applyNewPositionModelDTORequest);
    Assert.assertTrue(result.isSuccess());
    GdnRestListResponse<CandidatePositionSolrDTOResponse> resultCandidatePosition =
        beirutApiClient.getCandidatePositionBySolrQuery(REQUEST_ID, USERNAME,
            "emailAddress:" + this.resultCandidate.getContent().get(0).getEmailAddress()
                + " AND title:Software Developer Division 5 " + timestamp,
            0, 10);
    Assert.assertTrue(resultCandidatePosition.getContent().size() != 0);
  }

  // UPDATED IZAL DONE
  @Test
  public void testUpdateCandidateInformation() throws Exception {
    CandidateDTORequest updatedCandidate = new CandidateDTORequest();
    updatedCandidate.setId(resultCandidate.getContent().get(0).getId());
    String UPDATED = "Updated";
    updatedCandidate
        .setEmailAddress(resultCandidate.getContent().get(0).getEmailAddress() + UPDATED);
    updatedCandidate.setFirstName(resultCandidate.getContent().get(0).getFirstName() + UPDATED);
    updatedCandidate.setLastName(resultCandidate.getContent().get(0).getLastName() + UPDATED);
    GdnBaseRestResponse response =
        this.beirutApiClient.updateCandidateInformation(REQUEST_ID, USERNAME, updatedCandidate);
    Assert.assertTrue(response.isSuccess());
    GdnRestSingleResponse<CandidateDTOResponse> result =
        this.beirutApiClient.findCandidateByEmailAddressAndStoreId(REQUEST_ID, USERNAME,
            resultCandidate.getContent().get(0).getEmailAddress() + UPDATED);
    Assert
        .assertTrue(result.getValue().getId().equals(resultCandidate.getContent().get(0).getId()));
  }

  // UPDATED IZAL DONE
  @Test
  @Ignore
  public void testUpdateCandidatesStatus() throws Exception {

    String idPosition = "";
    for (PositionDTOResponse positionDTOResponse : resultPosition.getContent()) {
      if (positionDTOResponse.getTitle().equals("Software Developer Division 1 " + timestamp)) {
        idPosition = positionDTOResponse.getId();
        break;
      }
    }
    StatusDTORequest status = StatusDTORequest.MEDICAL;
    // ListStringRequest listCandidateIdStrings = new ListStringRequest();
    List<String> listString = new ArrayList<String>();
    System.out.println("SIZE RESULTCANDIDATENYA WOI : " + resultCandidate.getContent().size());
    for (CandidateDTOResponseWithoutDetail candidateDTOResponseWithoutDetail : resultCandidate
        .getContent()) {
      if (candidateDTOResponseWithoutDetail.getEmailAddress()
          .equals("asda@egamail.com1" + timestamp)) {
        listString.add(candidateDTOResponseWithoutDetail.getId());
      }
    }
    // listCandidateIdStrings.setValues(listString);

    UpdateCandidateStatusModelDTORequest updateCandidateStatusModelDTORequest =
        new UpdateCandidateStatusModelDTORequest();
    updateCandidateStatusModelDTORequest.setIdPosition(idPosition);
    updateCandidateStatusModelDTORequest.setStatusDTORequest(status.name());
    updateCandidateStatusModelDTORequest.setIdCandidates(listString);

    // System.out.println("ini idCandidatenya : " + idCandidate);

    GdnBaseRestResponse response = beirutApiClient.updateCandidatesStatus(REQUEST_ID, USERNAME,
        updateCandidateStatusModelDTORequest);
    Assert.assertTrue(response.isSuccess());

    GdnRestListResponse<CandidatePositionSolrDTOResponse> result =
        beirutApiClient.getCandidatePositionBySolrQuery(REQUEST_ID, USERNAME,
            "status:" + StatusDTORequest.MEDICAL.toString() + " AND STORE_ID:" + STORE_ID, 0, 10);

    Assert.assertTrue(result.getContent().size() == 1);
  }

  @Test
  @Ignore
  public void testUpdatePosition() throws Exception {
    // PositionDTORequest newPosition = new PositionDTORequest();
    // newPosition.setTitle("New Title" + timestamp);

    UpdatePositionModelDTORequest updatePositionModelDTORequest =
        new UpdatePositionModelDTORequest();
    updatePositionModelDTORequest.setIdPositionTarget(positionIds.get(0));
    updatePositionModelDTORequest.setTitle("New Title" + timestamp);

    beirutApiClient.updatePosition(REQUEST_ID, USERNAME, updatePositionModelDTORequest);
    GdnRestListResponse<PositionDTOResponse> result =
        beirutApiClient.getPositionByTitle(REQUEST_ID, USERNAME, "New Title" + timestamp);
    Assert.assertTrue(result.getContent().size() == 1);
  }
}
