package com.gdn.x.beirut.controller;

import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;

import com.gdn.common.base.mapper.GdnMapper;
import com.gdn.x.beirut.dto.request.CandidateDTORequest;
import com.gdn.x.beirut.dto.request.StatusDTORequest;
import com.gdn.x.beirut.dto.response.CandidateDTOResponse;
import com.gdn.x.beirut.dto.response.CandidatePositionDTOResponse;
import com.gdn.x.beirut.dto.response.CandidateWithPositionsDTOResponse;
import com.gdn.x.beirut.dto.response.StatusDTOResponse;
import com.gdn.x.beirut.dto.response.StatusLogDTOResponse;
import com.gdn.x.beirut.entities.Candidate;
import com.gdn.x.beirut.entities.CandidatePosition;
import com.gdn.x.beirut.entities.Status;
import com.gdn.x.beirut.entities.StatusLog;

public class CandidateMapper {
  // public static void map(Candidate candidate, CandidateDTOResponse candidateDTOResponse,
  // Mapper dozerMapper) {
  // dozerMapper.map(candidate, candidateDTOResponse);
  // CandidateDetailDTOResponse detilDTOres =
  // new CandidateDetailDTOResponse(candidate.getCandidateDetail().getId());
  // dozerMapper.map(candidate.getCandidateDetail(), detilDTOres);
  // }

  public static void map(Candidate candidate,
      CandidateWithPositionsDTOResponse candidateWithPositionDTO, Mapper dozerMapper) {
    dozerMapper.map(candidate, candidateWithPositionDTO);
    // BeanUtils.copyProperties(,"candidateDetail");
  }

  // public static void map(List<String> idCandidates, String idPosition,
  // CandidatesPositionDTOWrapper objWrapper, Mapper dozerMapper) {
  // for (String id : objWrapper.getIdCandidates()) {
  // idCandidates.add(id);
  // }
  // dozerMapper.map(objWrapper.getPosition(), position);
  // }

  public static void map(CandidateDTORequest candidateDTORequest, Candidate candidate,
      Mapper dozerMapper) {
    dozerMapper.map(candidateDTORequest, candidate);
  }

  public static void map(CandidatePosition candidatePosition,
      CandidatePositionDTOResponse candidatePositionResponse, GdnMapper gdnMapper) {

    for (StatusLog statusLog : candidatePosition.getStatusLogs()) {
      StatusLogDTOResponse statusLogDTO = gdnMapper.deepCopy(statusLog, StatusLogDTOResponse.class);
      switch (statusLog.getStatus()) {
        case APPLY:
          statusLogDTO.setStatus(StatusDTOResponse.APPLY);
          break;
        case CALL_CANDIDATE:
          statusLogDTO.setStatus(StatusDTOResponse.CALL_CANDIDATE);
          break;
        case PSIKOTES:
          statusLogDTO.setStatus(StatusDTOResponse.PSIKOTES);
          break;
        case TECHNICAL_TEST:
          statusLogDTO.setStatus(StatusDTOResponse.TECHNICAL_TEST);
          break;
        case USER:
          statusLogDTO.setStatus(StatusDTOResponse.USER);
          break;
        case HRD:
          statusLogDTO.setStatus(StatusDTOResponse.HRD);
          break;
        case ON_HOLD:
          statusLogDTO.setStatus(StatusDTOResponse.ON_HOLD);
          break;
        case HEAD:
          statusLogDTO.setStatus(StatusDTOResponse.HEAD);
          break;
        case CEO:
          statusLogDTO.setStatus(StatusDTOResponse.CEO);
          break;
        case MEDICAL:
          statusLogDTO.setStatus(StatusDTOResponse.MEDICAL);
          break;
        case OFFERING:
          statusLogDTO.setStatus(StatusDTOResponse.OFFERING);
          break;
        case DECLINED:
          statusLogDTO.setStatus(StatusDTOResponse.DECLINED);
          break;
        case JOIN:
          statusLogDTO.setStatus(StatusDTOResponse.JOIN);
          break;
        case WITHDRAWL:
          statusLogDTO.setStatus(StatusDTOResponse.WITHDRAWL);
          break;
        default:
          statusLogDTO.setStatus(StatusDTOResponse.APPLY);
          break;
      }
      candidatePositionResponse.getStatusLogs().add(statusLogDTO);
    }
  }

  @Deprecated
  public static void mapLazy(Candidate candidate, CandidateDTOResponse candidateDTOResponse) {
    BeanUtils.copyProperties(candidate, candidateDTOResponse, "candidateDetail");
  }

  public static void statusEnumMap(StatusDTORequest source, Status status) {
    switch (source) {
      case APPLY:
        status = Status.APPLY;
        break;
      case CALL_CANDIDATE:
        status = Status.CALL_CANDIDATE;
        break;
      case PSIKOTES:
        status = Status.PSIKOTES;
        break;
      case TECHNICAL_TEST:
        status = Status.TECHNICAL_TEST;
        break;
      case USER:
        status = Status.USER;
        break;
      case HRD:
        status = Status.HRD;
        break;
      case ON_HOLD:
        status = Status.ON_HOLD;
        break;
      case HEAD:
        status = Status.HEAD;
        break;
      case CEO:
        status = Status.CEO;
        break;
      case MEDICAL:
        status = Status.MEDICAL;
        break;
      case OFFERING:
        status = Status.OFFERING;
        break;
      case DECLINED:
        status = Status.DECLINED;
        break;
      case JOIN:
        status = Status.JOIN;
        break;
      case WITHDRAWL:
        status = Status.WITHDRAWL;
        break;
      default:
        status = Status.APPLY;
        break;
    }
  }

}
