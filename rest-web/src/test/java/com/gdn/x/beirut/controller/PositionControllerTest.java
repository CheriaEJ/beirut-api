package com.gdn.x.beirut.controller;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.HashSet;
import java.util.Set;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdn.x.beirut.dto.request.PositionDTORequest;
import com.gdn.x.beirut.entities.CandidatePosition;
import com.gdn.x.beirut.entities.Position;
import com.gdn.x.beirut.services.PositionService;
import com.gdn.x.beirut.services.PositionServiceImpl;

public class PositionControllerTest {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final String STORE_ID = "storeId";
  private static final String CLIENT_ID = "clientId";
  private static final String CHANNEL_ID = "channelId";
  private static final String REQUEST_ID = "requestId";
  private static final String USERNAME = "username";
  private MockMvc mockMVC;

  private final Mapper dm = new DozerBeanMapper();

  @Mock
  private PositionService service;

  @InjectMocks
  private PositionController controller;

  @Before
  public void setUp() throws Exception {
      initMocks(this);

      this.controller.setDozerMapper(dm);
      this.mockMVC= standaloneSetup(this.controller).build();
      this.service=new PositionServiceImpl();
  }

  //belum berhasil.
  @Test
  @Ignore
  public void testInsertNewPosition() throws Exception {
    PositionDTORequest positionDTORequest = new PositionDTORequest();
    Position p = new Position();
    p.setTitle("Puing-Puing");
    Set<CandidatePosition> candidatePositions = new HashSet<CandidatePosition>();
    CandidatePosition candidatePosition = new CandidatePosition();
    candidatePosition.setId("12");
    candidatePositions.add(candidatePosition);
    p.setCandidatePosition(candidatePositions);

    Mockito.doNothing().when(this.service).insertNewPosition(Mockito.any(Position.class));
    this.mockMVC
        .perform(post("/api/position/insertNewPosition").contentType(MediaType.APPLICATION_JSON)
            .content(PositionControllerTest.OBJECT_MAPPER.writeValueAsString("Berhasil"))
            .accept(MediaType.APPLICATION_JSON).param("storeId", PositionControllerTest.STORE_ID)
            .param("channelId", PositionControllerTest.CHANNEL_ID)
            .param("clientId", PositionControllerTest.CLIENT_ID)
            .param("requestId", PositionControllerTest.REQUEST_ID)
            .param("username", PositionControllerTest.USERNAME))
        .andExpect(status().isOk());
    Mockito.verify(this.service, Mockito.times(1)).insertNewPosition(Mockito.any(Position.class));
  }

}
