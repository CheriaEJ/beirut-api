package com.gdn.x.beirut.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdn.common.base.mapper.GdnMapper;
import com.gdn.common.enums.ErrorCategory;
import com.gdn.common.exception.ApplicationException;
import com.gdn.common.web.param.PageableHelper;
import com.gdn.common.web.wrapper.response.GdnBaseRestResponse;
import com.gdn.common.web.wrapper.response.GdnRestListResponse;
import com.gdn.common.web.wrapper.response.GdnRestSingleResponse;
import com.gdn.common.web.wrapper.response.PageMetaData;
import com.gdn.x.beirut.dto.request.ApplyNewPositionModelDTORequest;
import com.gdn.x.beirut.dto.request.CandidateDTORequest;
import com.gdn.x.beirut.dto.request.ListStringRequest;
import com.gdn.x.beirut.dto.request.UpdateCandidateStatusModelDTORequest;
import com.gdn.x.beirut.dto.response.CandidateDTOResponse;
import com.gdn.x.beirut.dto.response.CandidateDTOResponseWithoutDetail;
import com.gdn.x.beirut.dto.response.CandidatePositionDTOResponse;
import com.gdn.x.beirut.dto.response.CandidatePositionSolrDTOResponse;
import com.gdn.x.beirut.dto.response.CandidateWithPositionsDTOResponse;
import com.gdn.x.beirut.entities.Candidate;
import com.gdn.x.beirut.entities.CandidateDetail;
import com.gdn.x.beirut.entities.CandidatePosition;
import com.gdn.x.beirut.entities.Status;
import com.gdn.x.beirut.services.CandidateService;
import com.gdn.x.beirut.solr.entities.CandidatePositionSolr;
import com.gdn.x.beirut.solr.services.CandidatePositionSolrService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/api/candidate/")
@Api(value = "CandidateController", description = "Controller untuk Candidate")
public class CandidateController {

  private static final Logger LOG = LoggerFactory.getLogger(CandidateController.class);
  @Autowired
  private CandidateService candidateService;

  @Autowired
  private GdnMapper gdnMapper;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private CandidatePositionSolrService candidatePositionSolrService;

  @RequestMapping(value = "applyNewPosition", method = RequestMethod.POST,
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Apply new Position", notes = "melamar posisi baru.")
  @ResponseBody
  public GdnBaseRestResponse applyNewPosition(@RequestParam String clientId,
      @RequestParam String storeId, @RequestParam String requestId, @RequestParam String channelId,
      @RequestParam String username, @RequestBody ApplyNewPositionModelDTORequest content)
          throws Exception {
    try {
      this.candidateService.applyNewPosition(content.getIdCandidate(),
          content.getListPositionIds());
      return new GdnBaseRestResponse(true);
    } catch (Exception e) {
      return new GdnBaseRestResponse(e.getMessage(), "", false, requestId);
    }
  }

  @RequestMapping(value = "deleteCandidate", method = RequestMethod.POST,
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Delete Id yang ada", notes = "Semua yang ada di list akan di hapus")
  @ResponseBody
  public GdnBaseRestResponse deleteCandidate(@RequestParam String clientId,
      @RequestParam String storeId, @RequestParam String requestId, @RequestParam String channelId,
      @RequestParam String username, @RequestBody ListStringRequest idsRequest) throws Exception {
    try {
      this.candidateService.markForDelete(idsRequest.getValues());
      return new GdnBaseRestResponse(requestId);
    } catch (Exception e) {
      return new GdnBaseRestResponse(e.getMessage(), "", false, requestId);
    }

  }

  @RequestMapping(value = "findCandidateByCreatedDateBetweenAndStoreId", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Find candidate which created between day x and day y",
      notes = "mencari kandidat yang dibuat pada periode tertentu.")
  @ResponseBody
  public GdnRestListResponse<CandidateDTOResponse> findCandidateByCreatedDateBetweenAndStoreId(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username, @RequestParam Long start,
      @RequestParam Long end, @RequestParam int page, @RequestParam int size) throws Exception {
    Date startDate = new Date(start);
    Date endDate = new Date(end);
    Page<Candidate> candidates = this.candidateService.searchByCreatedDateBetweenAndStoreId(
        startDate, endDate, storeId, PageableHelper.generatePageable(page, size));
    List<CandidateDTOResponse> res = new ArrayList<CandidateDTOResponse>();
    for (Candidate candidate : candidates.getContent()) {
      CandidateDTOResponse candidateDTOResponse =
          getGdnMapper().deepCopy(candidate, CandidateDTOResponse.class);
      res.add(candidateDTOResponse);
    }
    return new GdnRestListResponse<CandidateDTOResponse>(res, new PageMetaData(50, 0, res.size()),
        requestId);
  }

  @RequestMapping(value = "findCandidateByEmailAddressAndStoreId", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Find candidate by their email address", notes = "")
  @ResponseBody
  public GdnRestSingleResponse<CandidateDTOResponse> findCandidateByEmailAddressAndStoreId(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username,
      @RequestParam String emailAddress) throws Exception {
    Candidate candidates =
        this.candidateService.searchCandidateByEmailAddressAndStoreId(emailAddress, storeId);
    CandidateDTOResponse candidateDTOResponse =
        getGdnMapper().deepCopy(candidates, CandidateDTOResponse.class);
    return new GdnRestSingleResponse<CandidateDTOResponse>(candidateDTOResponse, requestId);
  }

  @RequestMapping(value = "findCandidateByFirstNameContainAndStoreId", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Find candidate by their first name", notes = "")
  @ResponseBody
  public GdnRestListResponse<CandidateDTOResponse> findCandidateByFirstNameContainAndStoreId(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username, @RequestParam String firstName,
      @RequestParam int page, @RequestParam int size) throws Exception {
    Page<Candidate> candidates = this.candidateService.searchByFirstNameContainAndStoreId(firstName,
        storeId, PageableHelper.generatePageable(page, size));
    List<CandidateDTOResponse> candidateResponse = new ArrayList<>();
    for (Candidate candidate : candidates.getContent()) {
      CandidateDTOResponse newCandidateDTORes =
          getGdnMapper().deepCopy(candidate, CandidateDTOResponse.class);
      candidateResponse.add(newCandidateDTORes);
    }
    return new GdnRestListResponse<CandidateDTOResponse>(candidateResponse,
        new PageMetaData(50, 0, candidateResponse.size()), requestId);
  }

  // DEPRECATED : Udah diganti sama findCandidateByIdAndStoreIdEager / Lazy
  @Deprecated
  @RequestMapping(value = "findCandidateByIdDeprecated", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "mencari kandidat berdasarkan ID",
      notes = "mengeluarkan kandidat dengan ID tersebut.")
  @ResponseBody
  public GdnRestSingleResponse<CandidateDTOResponse> findCandidateById(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username, @RequestParam String id)
          throws Exception {
    Candidate candidate = this.candidateService.getCandidate(id);
    CandidateDTOResponse candres = getGdnMapper().deepCopy(candidate, CandidateDTOResponse.class);
    return new GdnRestSingleResponse<CandidateDTOResponse>(candres, requestId);
  }

  @RequestMapping(value = "findCandidateByIdAndStoreIdEager", method = RequestMethod.GET,
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "get candidate by id and store id eager with candidate positions",
      notes = "mengambil candidate berdasarkan id dan store id yang diberikan dengan relasi candidatePosition nya")
  @ResponseBody
  public GdnRestSingleResponse<CandidateWithPositionsDTOResponse> findCandidateByIdAndStoreIdEager(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username,
      @RequestParam String idCandidate) throws Exception {
    Candidate candidate =
        this.candidateService.getCandidateByIdAndStoreIdEager(idCandidate, storeId);
    // System.out.println("ENTITY : " + candidate.toStringz());
    CandidateWithPositionsDTOResponse candResponse =
        getGdnMapper().deepCopy(candidate, CandidateWithPositionsDTOResponse.class);
    // System.out.println(" RESPONSE ADA statusnya GAGAGA : " + candResponse.toStringz());
    return new GdnRestSingleResponse<CandidateWithPositionsDTOResponse>(candResponse, requestId);
  }

  @RequestMapping(value = "findCandidateByIdAndStoreIdLazy", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "get candidate by id and store id lazy",
      notes = "mengambil candidate berdasarkan id dan store id yang diberikan tanpa megambil child/relasi dengan objek lainnya (hanya summary-nya)")
  @ResponseBody
  public GdnRestSingleResponse<CandidateDTOResponse> findCandidateByIdAndStoreIdLazy(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username,
      @RequestParam String idCandidate) throws Exception {
    Candidate candidate =
        this.candidateService.getCandidateByIdAndStoreIdLazy(idCandidate, storeId);
    CandidateDTOResponse candidateDTOResponse = new CandidateDTOResponse();
    BeanUtils.copyProperties(candidate, candidateDTOResponse, "candidateDetail");
    return new GdnRestSingleResponse<CandidateDTOResponse>(candidateDTOResponse, requestId);
  }

  @RequestMapping(value = "findCandidateByLastNameContainAndStoreId", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Find candidate by their last name", notes = "")
  @ResponseBody
  public GdnRestListResponse<CandidateDTOResponse> findCandidateByLastNameContainAndStoreId(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username, @RequestParam String lastName,
      @RequestParam int page, @RequestParam int size) throws Exception {
    Page<Candidate> candidates = this.candidateService.searchByLastNameContainAndStoreId(lastName,
        storeId, PageableHelper.generatePageable(page, size));
    List<CandidateDTOResponse> candidateResponse = new ArrayList<>();
    for (Candidate candidate : candidates.getContent()) {
      CandidateDTOResponse newCandidateDTORes =
          getGdnMapper().deepCopy(candidate, CandidateDTOResponse.class);
      candidateResponse.add(newCandidateDTORes);
    }
    return new GdnRestListResponse<CandidateDTOResponse>(candidateResponse,
        new PageMetaData(50, 0, candidateResponse.size()), requestId);
  }

  // DEPRECATED : Diganti sama findCandidateByPhoneNumberContainAndStoreId
  @Deprecated
  @RequestMapping(value = "findCandidateByPhoneNumber", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "mencari kandidat berdasarkan nomor telepon",
      notes = "mengeluarkan kandidat dengan nomor telepon tersebut.")
  @ResponseBody
  public GdnRestListResponse<CandidateDTOResponse> findCandidateByPhoneNumber(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username,
      @RequestParam String phoneNumber) throws Exception {
    List<Candidate> candidates = this.candidateService.searchCandidateByPhoneNumber(phoneNumber);
    List<CandidateDTOResponse> candidateResponse = new ArrayList<>();
    for (Candidate candidate : candidates) {
      CandidateDTOResponse newCandidateDTORes =
          getGdnMapper().deepCopy(candidate, CandidateDTOResponse.class);
      candidateResponse.add(newCandidateDTORes);
    }
    return new GdnRestListResponse<CandidateDTOResponse>(candidateResponse,
        new PageMetaData(50, 0, candidateResponse.size()), requestId);
  }

  @RequestMapping(value = "findCandidateByPhoneNumberContainAndStoreId", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Find candidate by their phone number that much alike", notes = "")
  @ResponseBody
  public GdnRestListResponse<CandidateDTOResponse> findCandidateByPhoneNumberContainAndStoreId(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username,
      @RequestParam String phoneNumber, @RequestParam int page, @RequestParam int size)
          throws Exception {
    Page<Candidate> candidates =
        this.candidateService.searchCandidateByPhoneNumberContainAndStoreId(phoneNumber, storeId,
            PageableHelper.generatePageable(page, size));
    List<CandidateDTOResponse> candidateResponse = new ArrayList<>();
    for (Candidate candidate : candidates.getContent()) {
      CandidateDTOResponse newCandidateDTORes =
          getGdnMapper().deepCopy(candidate, CandidateDTOResponse.class);
      candidateResponse.add(newCandidateDTORes);
    }
    return new GdnRestListResponse<CandidateDTOResponse>(candidateResponse,
        new PageMetaData(50, 0, candidateResponse.size()), requestId);
  }

  @RequestMapping(value = "findCandidateDetailAndStoreId", method = RequestMethod.GET,
      produces = {"application/pdf", "application/msword", "image/jpeg", "text/plain"})
  @ApiOperation(value = "Mencari detail kandidat", notes = "")
  @ResponseBody
  public byte[] findCandidateDetailAndStoreId(@RequestParam String clientId,
      @RequestParam String storeId, @RequestParam String requestId, @RequestParam String channelId,
      @RequestParam String username, @RequestParam String id) throws Exception {
    CandidateDetail candidate = this.candidateService.getCandidateDetailAndStoreId(id, storeId);
    return candidate.getContent();
  }

  // DEPRECATED udah diganti pake getAllCandidateByStoreIdWithPageable
  @Deprecated
  @RequestMapping(value = "getAllCandidateDepr", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Get all Candidates", notes = "Mengambil semua kandidat")
  @ResponseBody
  public GdnRestListResponse<CandidateDTOResponse> getAllCandidate(@RequestParam String clientId,
      @RequestParam String storeId, @RequestParam String requestId, @RequestParam String channelId,
      @RequestParam String username) {
    List<Candidate> candidates = this.candidateService.getAllCandidates();
    List<CandidateDTOResponse> candidateResponse = new ArrayList<>();
    for (Candidate candidate : candidates) {
      CandidateDTOResponse newCandidateDTORes = new CandidateDTOResponse();

      BeanUtils.copyProperties(candidate, newCandidateDTORes, "candidateDetail",
          "candidatePositions");
      candidateResponse.add(newCandidateDTORes);
    }
    return new GdnRestListResponse<CandidateDTOResponse>(candidateResponse,
        new PageMetaData(50, 0, candidateResponse.size()), requestId);
  }

  @RequestMapping(value = "getAllCandidatesByStoreIdAndMarkForDeleteWithPageable",
      method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Getting all candidates And MarkForDelete with pageable", notes = "")

  @ResponseBody
  public GdnRestListResponse<CandidateDTOResponseWithoutDetail> getAllCandidateByStoreIdAndMarkForDeleteWithPageable(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username,
      @RequestParam boolean markForDelete, @RequestParam int page, @RequestParam int size)
          throws Exception {
    Pageable pageable = PageableHelper.generatePageable(page, size);
    Page<Candidate> pages = this.candidateService
        .getAllCandidatesByStoreIdAndMarkForDeletePageable(storeId, markForDelete, pageable);
    List<CandidateDTOResponseWithoutDetail> toShow = new ArrayList<>();
    for (Candidate candidate : pages.getContent()) {
      CandidateDTOResponseWithoutDetail newCandidateDTOResponse =
          getGdnMapper().deepCopy(candidate, CandidateDTOResponseWithoutDetail.class);
      toShow.add(newCandidateDTOResponse);
    }
    return new GdnRestListResponse<CandidateDTOResponseWithoutDetail>(toShow,
        new PageMetaData(50, 0, toShow.size()), requestId);
  }

  @RequestMapping(value = "getAllCandidatesByStoreIdWithPageable", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Getting all candidates with pageable", notes = "")

  @ResponseBody
  public GdnRestListResponse<CandidateDTOResponseWithoutDetail> getAllCandidateByStoreIdWithPageable(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username, @RequestParam int page,
      @RequestParam int size) throws Exception {
    Pageable pageable = PageableHelper.generatePageable(page, size);
    Page<Candidate> pages =
        this.candidateService.getAllCandidatesByStoreIdPageable(storeId, pageable);
    List<CandidateDTOResponseWithoutDetail> toShow = new ArrayList<>();
    for (Candidate candidate : pages.getContent()) {
      CandidateDTOResponseWithoutDetail newCandidateDTOResponse =
          getGdnMapper().deepCopy(candidate, CandidateDTOResponseWithoutDetail.class);
      toShow.add(newCandidateDTOResponse);
    }
    return new GdnRestListResponse<CandidateDTOResponseWithoutDetail>(toShow,
        new PageMetaData(50, 0, toShow.size()), requestId);
  }

  @RequestMapping(value = "getCandidatePositionBySolrQuery", method = RequestMethod.GET,
      consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "Mendapatkan candidatePosition dari data yang ada id Solr",
      notes = "contoh Query = \"firstName:values1 AND lastName:values2")
  @ResponseBody
  public GdnRestListResponse<CandidatePositionSolrDTOResponse> getCandidatePositionBySolrQuery(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username, @RequestParam String query,
      @RequestParam int page, @RequestParam int size) {
    Page<CandidatePositionSolr> result = this.candidatePositionSolrService.executeSolrQuery(query,
        storeId, PageableHelper.generatePageable(page, size));
    List<CandidatePositionSolrDTOResponse> candidatePositionSolrDTOResponses = new ArrayList<>();
    for (CandidatePositionSolr candidatePositionSolr : result.getContent()) {
      CandidatePositionSolrDTOResponse candidatePositionSolrDTOResponse =
          gdnMapper.deepCopy(candidatePositionSolr, CandidatePositionSolrDTOResponse.class);
      candidatePositionSolrDTOResponses.add(candidatePositionSolrDTOResponse);
    }
    return new GdnRestListResponse<>(candidatePositionSolrDTOResponses,
        new PageMetaData(50, 0, candidatePositionSolrDTOResponses.size()), requestId);
  }

  @RequestMapping(value = "getCandidatePositionDetailByStoreIdWithLogs", method = RequestMethod.GET,
      consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "get candidateposition with logs",
      notes = "Get candidate tertentu dengan history logs nya")
  @ResponseBody
  public GdnRestSingleResponse<CandidatePositionDTOResponse> getCandidatePositionDetailByStoreIdWithLogs(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username,
      @RequestParam String idCandidate, @RequestParam String idPosition) throws Exception {
    CandidatePosition candidatePosition = this.candidateService
        .getCandidatePositionByStoreIdWithLogs(idCandidate, idPosition, storeId);
    CandidatePositionDTOResponse candidatePositionResponse =
        this.gdnMapper.deepCopy(candidatePosition, CandidatePositionDTOResponse.class);
    CandidateMapper.map(candidatePosition, candidatePositionResponse, this.gdnMapper);
    return new GdnRestSingleResponse<CandidatePositionDTOResponse>(candidatePositionResponse,
        requestId);
  }

  public GdnMapper getGdnMapper() {
    return gdnMapper;
  }

  public ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  @RequestMapping(value = "insertNewCandidate", method = RequestMethod.POST,
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Insert new Candidate", notes = "memasukan kandidat baru.")
  @ResponseBody
  public GdnBaseRestResponse insertNewCandidate(@RequestParam String clientId,
      @RequestParam String storeId, @RequestParam String requestId, @RequestParam String channelId,
      @RequestParam String username, @RequestParam String candidateDTORequestString,
      @RequestPart MultipartFile file) throws Exception {
    if (file == null || file.getBytes().length == 0) {
      throw new ApplicationException(ErrorCategory.REQUIRED_PARAMETER,
          "file content mustbe present");
    }
    CandidateDTORequest candidateDTORequest =
        objectMapper.readValue(candidateDTORequestString, CandidateDTORequest.class);
    Candidate newCandidate = getGdnMapper().deepCopy(candidateDTORequest, Candidate.class);
    CandidateDetail candidateDetail = new CandidateDetail();
    candidateDetail.setContent(file.getBytes());
    candidateDetail.setCandidate(newCandidate);
    candidateDetail.setFilename(file.getOriginalFilename());
    candidateDetail.setMediaType(file.getContentType());
    newCandidate.setCandidateDetail(candidateDetail);
    newCandidate.setStoreId(storeId);
    Candidate existingCandidate =
        this.candidateService.createNew(newCandidate, candidateDTORequest.getPositionIds());
    if (existingCandidate.getId() == null) {
      return new GdnBaseRestResponse(false);
    }
    return new GdnBaseRestResponse(requestId);
  }

  public void setGdnMapper(GdnMapper gdnMapper) {
    this.gdnMapper = gdnMapper;
  }

  public void setObjectMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @RequestMapping(value = "updateCandidateDetail", method = RequestMethod.POST,
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Update candidate detail", notes = "")
  @ResponseBody
  public GdnBaseRestResponse updateCandidateDetail(@RequestParam String clientId,
      @RequestParam String storeId, @RequestParam String requestId, @RequestParam String channelId,
      @RequestParam String username, @RequestParam String idCandidate,
      @RequestPart MultipartFile file) throws Exception {
    if (file == null || file.getBytes().length == 0) {
      throw new ApplicationException(ErrorCategory.REQUIRED_PARAMETER,
          "file content mustbe present");
    }
    Candidate candidate = this.candidateService.getCandidate(idCandidate);
    candidate.getCandidateDetail().setContent(file.getBytes());
    try {
      this.candidateService.updateCandidateDetail(storeId, candidate);
      return new GdnBaseRestResponse(true);
    } catch (Exception e) {
      return new GdnBaseRestResponse(requestId);
    }
  }

  @RequestMapping(value = "updateCandidateInformation", method = RequestMethod.POST,
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Update Informasi kandidat", notes = "")
  @ResponseBody
  public GdnBaseRestResponse updateCandidateInformation(@RequestParam String clientId,
      @RequestParam String storeId, @RequestParam String requestId, @RequestParam String channelId,
      @RequestParam String username, @RequestBody CandidateDTORequest candidateDTORequest)
          throws Exception {
    Candidate updatedCandidate = gdnMapper.deepCopy(candidateDTORequest, Candidate.class);
    LOG.info(candidateDTORequest.toString());
    updatedCandidate.setStoreId(storeId);
    LOG.info(updatedCandidate.toString());
    try {
      boolean result = this.candidateService.updateCandidateInformation(updatedCandidate);
      return new GdnBaseRestResponse(result);
    } catch (Exception e) {
      throw e;
    }

  }

  @RequestMapping(value = "updateCandidateStatus", method = RequestMethod.POST,
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "update candidate status",
      notes = "Update satu atau lebih Status Candidate dengan Position yang diberikan (jika punya) menjadi status yang diberikan")
  @ResponseBody
  public GdnBaseRestResponse updateCandidatesStatus(@RequestParam String clientId,
      @RequestParam String storeId, @RequestParam String requestId, @RequestParam String channelId,
      @RequestParam String username, @RequestBody UpdateCandidateStatusModelDTORequest content)
          throws Exception {
    Status status = Status.valueOf(content.getStatusDTORequest());
    // CandidateMapper.statusEnumMap(status, _status);
    this.candidateService.updateCandidateStatusBulk(storeId, content.getIdCandidates(),
        content.getIdPosition(), status);
    return new GdnBaseRestResponse(true);
  }
}
