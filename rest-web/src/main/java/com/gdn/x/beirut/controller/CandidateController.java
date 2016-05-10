package com.gdn.x.beirut.controller;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdn.common.web.wrapper.response.GdnBaseRestResponse;
import com.gdn.common.web.wrapper.response.GdnRestListResponse;
import com.gdn.common.web.wrapper.response.GdnRestSingleResponse;
import com.gdn.common.web.wrapper.response.PageMetaData;
import com.gdn.x.beirut.dto.request.CandidateDTORequest;
import com.gdn.x.beirut.dto.response.CandidateDTOResponse;
import com.gdn.x.beirut.entities.Candidate;
import com.gdn.x.beirut.services.CandidateService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/api/candidate")
@Api(value = "CandidateController", description = "Controller untuk Candidate")
public class CandidateController {

  @Autowired
  private CandidateService candidateService;
  @Autowired
  private Mapper dozerMapper;

  @RequestMapping(value = "/api/candidate/findCandidateById", method = RequestMethod.POST,
      consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "mencari kandidat berdasarkan ID",
      notes = "mengeluarkan kandidat dengan ID tersebut.")
  @ResponseBody
  public GdnRestSingleResponse<CandidateDTOResponse> findCandidateById(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username, @RequestParam String id)
          throws Exception {
    Candidate candidate = this.candidateService.getCandidate(id);
    CandidateDTOResponse candres = new CandidateDTOResponse();
    CandidateMapper.map(candidate, candres, dozerMapper);
    return new GdnRestSingleResponse<CandidateDTOResponse>(candres, requestId);
  }

  @RequestMapping(value = "/api/candidate/findCandidateByPhoneNumber", method = RequestMethod.POST,
      consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
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
      CandidateDTOResponse newCandidateDTORes = new CandidateDTOResponse();
      CandidateMapper.map(candidate, newCandidateDTORes, dozerMapper);
      candidateResponse.add(newCandidateDTORes);
    }
    return new GdnRestListResponse<>(candidateResponse,
        new PageMetaData(50, 0, candidateResponse.size()), requestId);
  }

  @RequestMapping(value = "/api/candidate/getAllCandidate", method = RequestMethod.POST,
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
      CandidateMapper.map(candidate, newCandidateDTORes, dozerMapper);
      candidateResponse.add(newCandidateDTORes);
    }
    return new GdnRestListResponse<>(candidateResponse,
        new PageMetaData(50, 0, candidateResponse.size()), requestId);
  }

  @RequestMapping(value = "/api/position/insertNewCandidate", method = RequestMethod.POST,
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Insert new Candidate", notes = "memasukan kandidat baru.")
  @ResponseBody
  public GdnBaseRestResponse insertNewCandidate(@RequestParam String clientId,
      @RequestParam String storeId, @RequestParam String requestId, @RequestParam String channelId,
      @RequestParam String username, @RequestBody CandidateDTORequest candreq) {
    Candidate temp = new Candidate(storeId);
    CandidateMapper.map(candreq, temp, dozerMapper);
    this.candidateService.save(temp);
    return new GdnBaseRestResponse(true);
  }
}
