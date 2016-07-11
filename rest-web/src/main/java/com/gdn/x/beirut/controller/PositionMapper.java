package com.gdn.x.beirut.controller;

import java.util.List;

import com.gdn.x.beirut.dto.response.PositionDetailDTOResponse;
import com.gdn.x.beirut.entities.CandidatePosition;
import com.gdn.x.beirut.entities.Position;

public class PositionMapper {


  public static void map(Position position,
      List<PositionDetailDTOResponse> positionDetailDTOResponses) {
    List<CandidatePosition> candidatePositions = position.getCandidatePositions();
    for (CandidatePosition candidatePosition : candidatePositions) {
      PositionDetailDTOResponse positionDetailDTOResponse = new PositionDetailDTOResponse();
      positionDetailDTOResponse.setIdPosition(position.getId());
      positionDetailDTOResponse.setPositionTitle(position.getTitle());
      positionDetailDTOResponse.setCurrentStatus(candidatePosition.getStatus().toString());
      positionDetailDTOResponse
          .setCandidateFirstName(candidatePosition.getCandidate().getFirstName());
      positionDetailDTOResponse
          .setCandidateLastName(candidatePosition.getCandidate().getLastName());
      positionDetailDTOResponses.add(positionDetailDTOResponse);
    }
  }
}
