package com.gdn.x.beirut.controller;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdn.common.base.mapper.GdnMapper;
import com.gdn.common.web.param.PageableHelper;
import com.gdn.common.web.wrapper.response.GdnRestListResponse;
import com.gdn.x.beirut.dto.request.ListStringRequest;
import com.gdn.x.beirut.dto.request.PositionDTORequest;
import com.gdn.x.beirut.dto.response.PositionDTOResponse;
import com.gdn.x.beirut.entities.Candidate;
import com.gdn.x.beirut.entities.CandidatePosition;
import com.gdn.x.beirut.entities.Position;
import com.gdn.x.beirut.entities.Status;
import com.gdn.x.beirut.services.PositionService;

public class PositionControllerTest {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private static final String ID = "id";

  private static final String STORE_ID = "storeId";

  private static final String CLIENT_ID = "clientId";

  private static final String CHANNEL_ID = "channelId";

  private static final String REQUEST_ID = "requestId";

  private static final String USERNAME = "username";

  private static final String TITLE = "title";

  private final String UriBasePath = "/api/position/";

  @InjectMocks
  PositionController positionController;

  @Mock
  private PositionService positionService;

  private GdnMapper gdnMapper;

  private ObjectMapper objectMapper;

  private MockMvc mockMVC;

  private final PositionDTORequest positionDTORequest = new PositionDTORequest();

  private final List<PositionDTORequest> positionDTORequests = new ArrayList<>();

  private final List<Position> positions = new ArrayList<Position>();

  private final Position position = new Position();

  @Before
  public void initialize() throws Exception {
    initMocks(this);
    this.mockMVC = standaloneSetup(this.positionController).build();
    this.gdnMapper = new GdnMapper() {
      @SuppressWarnings("unchecked")
      @Override
      public <T> T deepCopy(Object source, Class<T> destinationClass) {
        Mapper mapper = new DozerBeanMapper();
        T destination;
        try {
          destination = destinationClass.newInstance();
        } catch (InstantiationException e) {
          return (T) source;
        } catch (IllegalAccessException e) {
          return (T) source;
        }
        mapper.map(source, destination);
        return destination;
      }
    };
    this.positionController.setGdnMapper(this.gdnMapper);
    this.position.setId(ID);
    this.position.setTitle(TITLE);
    this.positions.add(this.position);
    this.positionDTORequest.setId(ID);
    this.positionDTORequest.setTitle("title");
    this.positionDTORequests.add(positionDTORequest);
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testDeletePosition() throws Exception {
    String uri = "deletePosition";
    String json =
        FileUtils.readFileToString(new File("src/test/resources/JSON/markForDeleteJSON.json"));
    Mockito.doNothing().when(this.positionService).markForDeletePosition(Mockito.matches(STORE_ID),
        Mockito.anyList());
    ListStringRequest listStringIds = OBJECT_MAPPER.readValue(json, ListStringRequest.class);

    this.mockMVC
        .perform(MockMvcRequestBuilders.post(UriBasePath + uri).param("clientId", CLIENT_ID)
            .param("storeId", STORE_ID).param("requestId", REQUEST_ID)
            .param("channelId", CHANNEL_ID).param("username", USERNAME).content(json)
            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    this.positionController.deletePosition(CLIENT_ID, STORE_ID, REQUEST_ID, CHANNEL_ID, USERNAME,
        listStringIds);
    Mockito.verify(this.positionService, Mockito.times(2))
        .markForDeletePosition(Mockito.matches(STORE_ID), Mockito.anyList());
  }

  @Test
  public void testGetAllPosition() throws Exception {
    String uri = "getAllPosition";
    Mockito.when(this.positionService.getAllPositionByStoreId(STORE_ID)).thenReturn(this.positions);
    this.mockMVC
        .perform(MockMvcRequestBuilders.get(UriBasePath + uri).param("clientId", CLIENT_ID)
            .param("storeId", STORE_ID).param("requestId", REQUEST_ID)
            .param("channelId", CHANNEL_ID).param("username", USERNAME))
        .andExpect(MockMvcResultMatchers.status().isOk());
    this.positionController.getAllPositionByStoreId(CLIENT_ID, STORE_ID, REQUEST_ID, CHANNEL_ID,
        USERNAME);
    Mockito.verify(this.positionService, Mockito.times(2)).getAllPositionByStoreId(STORE_ID);
  }

  @Test
  public void testGetAllPositionWithPageable() throws Exception {
    List<Position> content = new ArrayList<>();
    content.add(position);
    Page<Position> shouldBeReturned =
        new PageImpl<>(content, PageableHelper.generatePageable(0, 2), content.size());
    String uri = "getAllPositionWithPageable";
    Mockito.when(this.positionService.getAllPositionByStoreIdWithPageable(STORE_ID,
        PageableHelper.generatePageable(0, 2))).thenReturn(shouldBeReturned);
    this.mockMVC.perform(MockMvcRequestBuilders.get(UriBasePath + uri).param("clientId", CLIENT_ID)
        .param("storeId", STORE_ID).param("requestId", REQUEST_ID).param("channelId", CHANNEL_ID)
        .param("username", USERNAME).param("page", "0").param("size", "2"))
        .andExpect(MockMvcResultMatchers.status().isOk());
    this.positionController.getAllPositionWithPageable(CLIENT_ID, STORE_ID, REQUEST_ID, CHANNEL_ID,
        USERNAME, 0, 2);
    Mockito.verify(this.positionService, Mockito.times(2))
        .getAllPositionByStoreIdWithPageable(STORE_ID, PageableHelper.generatePageable(0, 2));

  }

  @Test
  public void testGetPositionByStoreIdAndMarkForDelete() throws Exception {
    String uri = "getPositionByStoreIdAndMarkForDelete";
    Mockito.when(this.positionService.getPositionByStoreIdAndMarkForDelete(STORE_ID, false))
        .thenReturn(positions);
    this.mockMVC.perform(MockMvcRequestBuilders.get(UriBasePath + uri).param("clientId", CLIENT_ID)
        .param("storeId", STORE_ID).param("requestId", REQUEST_ID).param("channelId", CHANNEL_ID)
        .param("username", USERNAME).param("markForDelete", "false")).andExpect(status().isOk());
    GdnRestListResponse<PositionDTOResponse> res =
        this.positionController.getPositionByStoreIdAndMarkForDelete(CLIENT_ID, STORE_ID,
            REQUEST_ID, CHANNEL_ID, USERNAME, false);
    Mockito.verify(this.positionService, Mockito.times(2))
        .getPositionByStoreIdAndMarkForDelete(STORE_ID, false);
    Assert.assertTrue(res.getContent().get(0).getId().equals(positions.get(0).getId()));
  }

  @Test
  public void testGetPositionByTitle() throws Exception {
    String uri = "getPositionByTitle";
    Mockito.when(this.positionService.getPositionByTitle(TITLE, STORE_ID))
        .thenReturn(this.positions);
    this.mockMVC
        .perform(MockMvcRequestBuilders.get(UriBasePath + uri).accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).param("clientId", CLIENT_ID)
            .param("storeId", STORE_ID).param("requestId", REQUEST_ID)
            .param("channelId", CHANNEL_ID).param("username", USERNAME).param("title", TITLE))
        .andExpect(MockMvcResultMatchers.status().isOk());
    this.positionController.getPositionByTitle(CLIENT_ID, STORE_ID, REQUEST_ID, CHANNEL_ID,
        USERNAME, TITLE);
    Mockito.verify(this.positionService, Mockito.times(2)).getPositionByTitle(TITLE, STORE_ID);
  }

  @Test
  public void testGetPositionDetailById() throws Exception {
    Candidate candidate = new Candidate();
    candidate.setFirstName("John Doe");
    candidate.setLastName("John Doe");
    CandidatePosition candidatePosition = new CandidatePosition();
    candidatePosition.setCandidate(candidate);
    candidatePosition.setStatus(Status.APPLY);
    Position shouldBeReturned = new Position();
    shouldBeReturned.setId(ID);
    shouldBeReturned.setStoreId(STORE_ID);
    shouldBeReturned.setCreatedBy("dummy");
    shouldBeReturned.setCandidatePositions(new ArrayList<CandidatePosition>());
    shouldBeReturned.getCandidatePositions().add(candidatePosition);
    shouldBeReturned.setMarkForDelete(false);
    shouldBeReturned.setTitle("This is a dummy");
    String uri = "getPositionDetail";
    Mockito.when(this.positionService.getPositionDetailByIdAndStoreId(ID, STORE_ID))
        .thenReturn(shouldBeReturned);
    this.mockMVC
        .perform(MockMvcRequestBuilders.get(UriBasePath + uri).param("clientId", CLIENT_ID)
            .param("storeId", STORE_ID).param("requestId", REQUEST_ID)
            .param("channelId", CHANNEL_ID).param("username", USERNAME).param("id", ID))
        .andExpect(status().isOk());
    this.positionController.getPositionDetailById(CLIENT_ID, STORE_ID, REQUEST_ID, CHANNEL_ID,
        USERNAME, ID);
    Mockito.verify(positionService, Mockito.times(2)).getPositionDetailByIdAndStoreId(ID, STORE_ID);
  }

  @Test
  public void testGetPositionDetailByIdAndThrowException() throws Exception {
    String uri = "getPositionDetail";
    Mockito.when(this.positionService.getPositionDetailByIdAndStoreId(ID, STORE_ID))
        .thenThrow(new Exception());
    this.mockMVC
        .perform(MockMvcRequestBuilders.get(UriBasePath + uri).param("clientId", CLIENT_ID)
            .param("storeId", STORE_ID).param("requestId", REQUEST_ID)
            .param("channelId", CHANNEL_ID).param("username", USERNAME).param("id", ID))
        .andExpect(status().isOk());
    this.positionController.getPositionDetailById(CLIENT_ID, STORE_ID, REQUEST_ID, CHANNEL_ID,
        USERNAME, ID);
    Mockito.verify(positionService, Mockito.times(2)).getPositionDetailByIdAndStoreId(ID, STORE_ID);
  }

  @Test
  public void testInsertNewPosition() throws Exception {
    String uri = "insertNewPosition";
    String positionDTORequestJson = "{\"id\":\"id\",\"title\":\"title\"}";
    Position temp = this.gdnMapper.deepCopy(this.positionDTORequest, Position.class);
    Mockito.when(this.positionService.insertNewPosition(temp)).thenReturn(temp);
    this.mockMVC.perform(MockMvcRequestBuilders.post(UriBasePath + uri)
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
        .param("clientId", CLIENT_ID).param("storeId", STORE_ID).param("requestId", REQUEST_ID)
        .param("channelId", CHANNEL_ID).param("username", USERNAME).content(positionDTORequestJson))
        .andExpect(MockMvcResultMatchers.status().isOk());
    this.positionController.insertNewPosition(CLIENT_ID, STORE_ID, REQUEST_ID, CHANNEL_ID, USERNAME,
        this.positionDTORequest);
    Mockito.verify(this.positionService, Mockito.times(2)).insertNewPosition(temp);
  }

  @Test
  public void testUpdatePosition() throws Exception {
    objectMapper = new ObjectMapper();

    String uri = "updatePosition";
    String updatePositionRequestJson = "{\"id\":\"id\",\"title\":\"title\"}";
    PositionDTORequest updatePositionModelDTORequest =
        objectMapper.readValue(updatePositionRequestJson, PositionDTORequest.class);

    Mockito
        .when(this.positionService.updatePositionTitle(STORE_ID,
            updatePositionModelDTORequest.getId(), updatePositionModelDTORequest.getTitle()))
        .thenReturn(true);
    this.mockMVC.perform(MockMvcRequestBuilders.post(UriBasePath + uri)
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
        .param("clientId", CLIENT_ID).param("storeId", STORE_ID).param("requestId", REQUEST_ID)
        .param("channelId", CHANNEL_ID).param("username", USERNAME)
        .content(updatePositionRequestJson)).andExpect(MockMvcResultMatchers.status().isOk());
    this.positionController.updatePosition(CLIENT_ID, STORE_ID, REQUEST_ID, CHANNEL_ID, USERNAME,
        positionDTORequest);
    Mockito.verify(this.positionService, Mockito.times(2)).updatePositionTitle(STORE_ID,
        updatePositionModelDTORequest.getId(), updatePositionModelDTORequest.getTitle());
  }
}
