package com.gdn.x.beirut.controller;

import org.dozer.Mapper;

import com.gdn.x.beirut.dto.request.CandidateDTORequest;
import com.gdn.x.beirut.dto.response.CandidateDTOResponse;
import com.gdn.x.beirut.dto.response.CandidateDetailDTOResponse;
import com.gdn.x.beirut.entities.Candidate;
import com.gdn.x.beirut.entities.CandidateDetail;

public class CandidateMapper {
  public static void map(Candidate source, CandidateDTOResponse dest, Mapper dozerMapper) {
    dozerMapper.map(source, dest);
    CandidateDetailDTOResponse detilDTOres =
        new CandidateDetailDTOResponse(source.getCandidateDetail().getStoreId());
    dozerMapper.map(source.getCandidateDetail(), detilDTOres);
  }

  public static void map(CandidateDTORequest source, Candidate dest, Mapper dozerMapper) {
    dozerMapper.map(source, dest);
    CandidateDetail candDetail = new CandidateDetail();
    dozerMapper.map(source.getCandidateDetail(), candDetail);
    dest.setCandidateDetail(candDetail);
    candDetail.setStoreId(dest.getStoreId());
    candDetail.setCandidate(dest);
  }
}
