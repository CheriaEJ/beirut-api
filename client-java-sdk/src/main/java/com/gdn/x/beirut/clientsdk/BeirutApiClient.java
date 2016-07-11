package com.gdn.x.beirut.clientsdk;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdn.client_sdk.shade.org.apache.http.client.methods.CloseableHttpResponse;
import com.gdn.client_sdk.shade.org.apache.http.client.methods.HttpPost;
import com.gdn.client_sdk.shade.org.apache.http.entity.mime.HttpMultipartMode;
import com.gdn.client_sdk.shade.org.apache.http.entity.mime.MultipartEntityBuilder;
import com.gdn.client_sdk.shade.org.apache.http.entity.mime.content.ByteArrayBody;
import com.gdn.client_sdk.shade.org.apache.http.impl.client.CloseableHttpClient;
import com.gdn.client_sdk.shade.org.apache.http.util.EntityUtils;
import com.gdn.common.client.GdnRestClientConfiguration;
import com.gdn.common.enums.ErrorCategory;
import com.gdn.common.exception.ApplicationException;
import com.gdn.common.web.client.GdnBaseRestCrudClient;
import com.gdn.common.web.wrapper.response.GdnBaseRestResponse;
import com.gdn.common.web.wrapper.response.GdnRestListResponse;
import com.gdn.common.web.wrapper.response.GdnRestSingleResponse;
import com.gdn.x.beirut.dto.request.ApplyNewPositionModelDTORequest;
import com.gdn.x.beirut.dto.request.CandidateDTORequest;
import com.gdn.x.beirut.dto.request.ListStringRequest;
import com.gdn.x.beirut.dto.request.PositionDTORequest;
import com.gdn.x.beirut.dto.request.UpdateCandidateStatusModelDTORequest;
import com.gdn.x.beirut.dto.request.UpdatePositionModelDTORequest;
import com.gdn.x.beirut.dto.response.CandidateDTOResponse;
import com.gdn.x.beirut.dto.response.CandidateDTOResponseWithoutDetail;
import com.gdn.x.beirut.dto.response.CandidatePositionDTOResponse;
import com.gdn.x.beirut.dto.response.CandidatePositionSolrDTOResponse;
import com.gdn.x.beirut.dto.response.CandidateWithPositionsDTOResponse;
import com.gdn.x.beirut.dto.response.PositionDTOResponse;
import com.gdn.x.beirut.dto.response.PositionDetailDTOResponse;


public class BeirutApiClient extends GdnBaseRestCrudClient {
  private static final String APPLICATION_JSON = "application/json";
  private static final Logger LOG = LoggerFactory.getLogger(BeirutApiClient.class);
  protected TypeReference<GdnBaseRestResponse> typeRef =
      new TypeReference<GdnBaseRestResponse>() {};
  @Autowired
  private ObjectMapper objectMapper;

  public BeirutApiClient(GdnRestClientConfiguration clientConfig, String contextPath) {
    super(clientConfig);
    setContextPath(contextPath);
  }

  public GdnBaseRestResponse applyNewPosition(String requestId, String username,
      ApplyNewPositionModelDTORequest applyNewPositionModelDTORequest) throws Exception {
    URI uri = generateURI("/candidate/applyNewPosition", requestId, username, null);
    return invokePostType(uri, applyNewPositionModelDTORequest,
        ApplyNewPositionModelDTORequest.class, MediaType.APPLICATION_JSON_VALUE, typeRef);
  }

  public GdnBaseRestResponse deleteCandidate(String requestId, String username,
      ListStringRequest idsRequest) throws Exception {

    URI uri = generateURI("/candidate/deleteCandidate", requestId, username, null);
    return invokePostType(uri, idsRequest, ListStringRequest.class,
        MediaType.APPLICATION_JSON_VALUE, typeRef);
  }

  public GdnBaseRestResponse deletePosition(String requestId, String username,
      ListStringRequest idsToDelete) throws Exception {
    URI uri = generateURI("/position/deletePosition", requestId, username, null);
    return invokePostType(uri, idsToDelete, ListStringRequest.class,
        MediaType.APPLICATION_JSON_VALUE, typeRef);
  }

  public GdnRestListResponse<CandidateDTOResponse> findCandidateByCreatedDateBetweenAndStoreId(
      String requestId, String username, Long start, Long end, int page, int size)
          throws Exception {
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("start", String.valueOf(start));
    map.put("end", String.valueOf(end));
    map.put("page", String.valueOf(page));
    map.put("size", String.valueOf(size));
    URI uri = generateURI("/candidate/findCandidateByCreatedDateBetweenAndStoreId", requestId,
        username, map);
    return invokeGetSummary(uri, CandidateDTOResponse.class, MediaType.APPLICATION_JSON_VALUE);
  }

  public GdnRestSingleResponse<CandidateDTOResponse> findCandidateByEmailAddressAndStoreId(
      String requestId, String username, String emailAddress) throws Exception {
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("emailAddress", emailAddress);
    URI uri =
        generateURI("/candidate/findCandidateByEmailAddressAndStoreId", requestId, username, map);
    return invokeGetSingle(uri, CandidateDTOResponse.class, MediaType.APPLICATION_JSON_VALUE);
  }

  public GdnRestListResponse<CandidateDTOResponse> findCandidateByFirstNameContainAndStoreId(
      String requestId, String username, String firstName, int page, int size) throws Exception {
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("firstName", firstName);
    map.put("page", String.valueOf(page));
    map.put("size", String.valueOf(size));
    URI uri = generateURI("/candidate/findCandidateByFirstNameContainAndStoreId", requestId,
        username, map);
    return invokeGetSummary(uri, CandidateDTOResponse.class, MediaType.APPLICATION_JSON_VALUE);
  }

  public GdnRestSingleResponse<CandidateWithPositionsDTOResponse> findCandidateByIdAndStoreIdEager(
      String requestId, String username, String idCandidate) throws Exception {
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("idCandidate", String.valueOf(idCandidate));
    URI uri = generateURI("/candidate/findCandidateByIdAndStoreIdEager", requestId, username, map);
    return invokeGetSingle(uri, CandidateWithPositionsDTOResponse.class,
        MediaType.APPLICATION_JSON_VALUE);
  }

  public GdnRestSingleResponse<CandidateDTOResponse> findCandidateByIdAndStoreIdLazy(
      String requestId, String username, String idCandidate) throws Exception {
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("idCandidate", String.valueOf(idCandidate));
    URI uri = generateURI("/candidate/findCandidateByIdAndStoreIdLazy", requestId, username, map);
    return invokeGetSingle(uri, CandidateDTOResponse.class, MediaType.APPLICATION_JSON_VALUE);
  }

  public GdnRestListResponse<CandidateDTOResponse> findCandidateByLastNameContainAndStoreId(
      String requestId, String username, String lastName, int page, int size) throws Exception {
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("lastName", lastName);
    map.put("page", String.valueOf(page));
    map.put("size", String.valueOf(size));
    URI uri = generateURI("/candidate/findCandidateByLastNameContainAndStoreId", requestId,
        username, map);
    return invokeGetSummary(uri, CandidateDTOResponse.class, MediaType.APPLICATION_JSON_VALUE);
  }

  public GdnRestListResponse<CandidateDTOResponse> findCandidateByPhoneNumberContainAndStoreId(
      String requestId, String username, String phoneNumber, int page, int size) throws Exception {
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("phoneNumber", phoneNumber);
    map.put("page", String.valueOf(page));
    map.put("size", String.valueOf(size));
    URI uri = generateURI("/candidate/findCandidateByPhoneNumberContainAndStoreId", requestId,
        username, map);
    return invokeGetSummary(uri, CandidateDTOResponse.class, MediaType.APPLICATION_JSON_VALUE);
  }

  @SuppressWarnings("deprecation")
  private HttpPost generateMultipartHttpPost(String path, byte[] content, String requestId,
      String filename, String username, Map<String, String> additionalParameterMap)
          throws Exception {
    HttpPost httpPost = getHttpClientHelper().createNewHttpPost(
        generateURI(path, requestId, username, additionalParameterMap),
        getClientConfig().getConnectionTimeoutInMs());
    MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
    entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE).addPart("content",
        new ByteArrayBody(content, filename));
    httpPost.setEntity(entityBuilder.build());
    httpPost.setHeader("Accept", APPLICATION_JSON);
    return httpPost;
  }

  /*
   * // dipertanyakan // harusnya ngeluarin byte [] public byte[]
   * findCandidateDetailAndStoreId(String requestId, String username, String id) throws Exception {
   * SimpleRequestHolder request = new SimpleRequestHolder(id); URI uri =
   * generateURI("/candidate/findCandidateDetailAndStoreId", requestId, username, null); return
   * invokeGetSingle(uri, byte[].class, request); }
   */
  @SuppressWarnings("deprecation")
  private URI generateURI(String path, String requestId, String username,
      Map<String, String> additionalParameterMap) throws Exception {
    String location = getContextPath() + path;
    return getHttpClientHelper().getURI(getClientConfig().getHost(), getClientConfig().getPort(),
        location, getMandatoryParameter(requestId, username), additionalParameterMap);
  }

  public GdnRestListResponse<CandidateDTOResponseWithoutDetail> getAllCandidateByStoreIdAndMarkForDeleteWithPageable(
      String requestId, String username, boolean markForDelete, int page, int size)
          throws Exception {
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("markForDelete", String.valueOf(markForDelete));
    map.put("page", String.valueOf(page));
    map.put("size", String.valueOf(size));
    URI uri = generateURI("/candidate/getAllCandidatesByStoreIdAndMarkForDeleteWithPageable",
        requestId, username, map);
    return invokeGetSummary(uri, CandidateDTOResponseWithoutDetail.class,
        MediaType.APPLICATION_JSON_VALUE);
  }

  public GdnRestListResponse<CandidateDTOResponseWithoutDetail> getAllCandidateByStoreIdWithPageable(
      String requestId, String username, int page, int size) throws Exception {
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("page", String.valueOf(page));
    map.put("size", String.valueOf(size));
    URI uri =
        generateURI("/candidate/getAllCandidatesByStoreIdWithPageable", requestId, username, map);
    return invokeGetSummary(uri, CandidateDTOResponseWithoutDetail.class,
        MediaType.APPLICATION_JSON_VALUE);
  }

  public GdnRestListResponse<PositionDTOResponse> getAllPositionByStoreId(String requestId,
      String username) throws Exception {
    URI uri = generateURI("/position/getAllPosition", requestId, username, null);
    return invokeGetSummary(uri, PositionDTOResponse.class, MediaType.APPLICATION_JSON_VALUE);
  }

  public GdnRestListResponse<PositionDTOResponse> getAllPositionWithPageable(String requestId,
      String username, int page, int size) throws Exception {
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("page", String.valueOf(page));
    map.put("size", String.valueOf(size));
    URI uri = generateURI("/position/getAllPositionWithPageable", requestId, username, map);
    return invokeGetSummary(uri, PositionDTOResponse.class, MediaType.APPLICATION_JSON_VALUE);
  }

  public GdnRestListResponse<CandidatePositionSolrDTOResponse> getCandidatePositionBySolrQuery(
      String requestId, String username, String query, int page, int size) throws Exception {
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("query", query);
    map.put("page", String.valueOf(page));
    map.put("size", String.valueOf(size));
    URI uri = generateURI("/candidate/getCandidatePositionBySolrQuery", requestId, username, map);
    return invokeGetSummary(uri, CandidatePositionSolrDTOResponse.class,
        MediaType.APPLICATION_JSON_VALUE);
  }

  public GdnRestSingleResponse<CandidatePositionDTOResponse> getCandidatePositionDetailByStoreIdWithLogs(
      String requestId, String username, String query, String idCandidate, String idPosition)
          throws Exception {
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("query", query);
    map.put("idCandidate", idCandidate);
    map.put("idPosition", idPosition);
    URI uri = generateURI("/candidate/getCandidatePositionDetailByStoreIdWithLogs", requestId,
        username, map);
    return invokeGetSingle(uri, CandidatePositionDTOResponse.class,
        MediaType.APPLICATION_JSON_VALUE);
  }

  private CloseableHttpClient getHttpClient() {
    return getHttpClientHelper().getClosableHttpConnectionSingleton();
  }

  public ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  public GdnRestListResponse<PositionDTOResponse> getPositionByStoreIdAndMarkForDelete(
      String requestId, String username, boolean markForDelete) throws Exception {
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("markForDelete", String.valueOf(markForDelete));
    URI uri =
        generateURI("/position/getPositionByStoreIdAndMarkForDelete", requestId, username, map);
    return invokeGetSummary(uri, PositionDTOResponse.class, MediaType.APPLICATION_JSON_VALUE);
  }

  public GdnRestListResponse<PositionDTOResponse> getPositionByTitle(String requestId,
      String username, String title) throws Exception {
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("title", title);
    URI uri = generateURI("/position/getPositionByTitle", requestId, username, map);
    return invokeGetSummary(uri, PositionDTOResponse.class, MediaType.APPLICATION_JSON_VALUE);
  }

  public GdnRestListResponse<PositionDetailDTOResponse> getPositionDetailById(String requestId,
      String username, String id) throws Exception {
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("id", id);
    URI uri = generateURI("/position/getPositionDetail", requestId, username, map);
    return invokeGetSummary(uri, PositionDetailDTOResponse.class, MediaType.APPLICATION_JSON_VALUE);
  }

  public TypeReference<GdnBaseRestResponse> getTypeRef() {
    return typeRef;
  }

  @SuppressWarnings("rawtypes")
  public GdnBaseRestResponse insertNewCandidate(String requestId, String username,
      String candidateDTORequestString, String filename, byte[] content) throws Exception {
    HashMap<String, String> additionalParameterMap = new HashMap<String, String>();
    additionalParameterMap.put("candidateDTORequestString",
        objectMapper.writeValueAsString(candidateDTORequestString));
    HttpPost httpPost = generateMultipartHttpPost("/candidate/insertNewCandidate", content,
        requestId, filename, username, additionalParameterMap);
    CloseableHttpResponse response = getHttpClient().execute(httpPost);
    if (response.getStatusLine().getStatusCode() == 200) {
      return objectMapper.readValue(EntityUtils.toString(response.getEntity()),
          new TypeReference<GdnRestListResponse>() {});
    } else {
      String responseText = null;
      if (response.getEntity() != null) {
        responseText = EntityUtils.toString(response.getEntity());
      }
      LOG.error("server give bad response code, code : {}, message : {}, body: {}",
          new Object[] {response.getStatusLine().getStatusCode(),
              response.getStatusLine().getReasonPhrase(), responseText});
      throw new ApplicationException(ErrorCategory.UNSPECIFIED, "check the log");
    }
  }

  public GdnBaseRestResponse insertNewPosition(String requestId, String username,
      PositionDTORequest positionDTORequest) throws Exception {
    URI uri = generateURI("/position/insertNewPosition", requestId, username, null);
    // System.out.println("INI PATHNYAAA :" + uri.toString());
    return invokePostType(uri, positionDTORequest, PositionDTORequest.class,
        MediaType.APPLICATION_JSON_VALUE, typeRef);
  }

  public void setObjectMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public void setTypeRef(TypeReference<GdnBaseRestResponse> typeRef) {
    this.typeRef = typeRef;
  }

  public GdnBaseRestResponse updateCandidateDetail(String requestId, String username,
      String idCandidate, MultipartFile file) throws Exception {
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("idCandidate", idCandidate);
    URI uri = generateURI("/candidate/updateCandidateDetail", requestId, username, map);
    return invokePostType(uri, file, MultipartFile.class, MediaType.APPLICATION_JSON_VALUE,
        typeRef);
  }

  public GdnBaseRestResponse updateCandidateInformation(String requestId, String username,
      CandidateDTORequest updatedCandidate) throws Exception {
    URI uri = generateURI("/candidate/updateCandidateInformation", requestId, username, null);
    return invokePostType(uri, updatedCandidate, CandidateDTORequest.class,
        MediaType.APPLICATION_JSON_VALUE, new TypeReference<GdnBaseRestResponse>() {});
  }

  public GdnBaseRestResponse updateCandidatesStatus(String requestId, String username,
      UpdateCandidateStatusModelDTORequest updateCandidateStatusModelDTORequest) throws Exception {
    URI uri = generateURI("/candidate/updateCandidateStatus", requestId, username, null);
    return invokePostType(uri, updateCandidateStatusModelDTORequest,
        UpdateCandidateStatusModelDTORequest.class, MediaType.APPLICATION_JSON_VALUE, typeRef);
  }

  public GdnBaseRestResponse updatePosition(String requestId, String username,
      UpdatePositionModelDTORequest updatePositionModelDTORequest) throws Exception {
    URI uri = generateURI("/position/updatePosition", requestId, username, null);
    return invokePostType(uri, updatePositionModelDTORequest, UpdatePositionModelDTORequest.class,
        MediaType.APPLICATION_JSON_VALUE, typeRef);
  }
}
