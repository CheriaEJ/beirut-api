package com.gdn.x.beirut.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdn.common.base.mapper.GdnMapper;
import com.gdn.common.web.param.PageableHelper;
import com.gdn.common.web.wrapper.response.GdnBaseRestResponse;
import com.gdn.common.web.wrapper.response.GdnRestListResponse;
import com.gdn.common.web.wrapper.response.PageMetaData;
import com.gdn.x.beirut.dto.request.ListStringRequest;
import com.gdn.x.beirut.dto.request.PositionDTORequest;
import com.gdn.x.beirut.dto.response.PositionDTOResponse;
import com.gdn.x.beirut.dto.response.PositionDetailDTOResponse;
import com.gdn.x.beirut.entities.Position;
import com.gdn.x.beirut.services.PositionService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/api/position/")
@Api(value = "PositionController")
public class PositionController {

  @Autowired
  private PositionService positionService;

  @Autowired
  private GdnMapper gdnMapper;

  @RequestMapping(value = "deletePosition", method = RequestMethod.POST,
          consumes = {MediaType.APPLICATION_JSON_VALUE},
          produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "delete position", notes = "menghapus posisi.")
  @ResponseBody
  public GdnBaseRestResponse deletePosition(@RequestParam String clientId,
                                            @RequestParam String storeId, @RequestParam String requestId, @RequestParam String channelId,
                                            @RequestParam String username, @RequestBody ListStringRequest idsToDelete) throws Exception {
    this.positionService.markForDeletePosition(storeId, idsToDelete.getValues());
    return new GdnBaseRestResponse(true);
  }

  @RequestMapping(value = "getAllPosition", method = RequestMethod.GET,
          produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "get all position", notes = "mengambil semua posisi.")
  @ResponseBody
  public GdnRestListResponse<PositionDTOResponse> getAllPositionByStoreId(
          @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
          @RequestParam String channelId, @RequestParam String username) {
    List<Position> positions = this.positionService.getAllPositionByStoreId(storeId);
    List<PositionDTOResponse> positionDTOResponses = new ArrayList<PositionDTOResponse>();
    for (Position positiones : positions) {
      PositionDTOResponse positionDTOResponse =
      this.gdnMapper.deepCopy(positiones, PositionDTOResponse.class);
      positionDTOResponses.add(positionDTOResponse);
    }

    return new GdnRestListResponse<PositionDTOResponse>(positionDTOResponses,
            new PageMetaData(5, 5, positions.size()), requestId);
  }

  @RequestMapping(value = "getAllPositionWithPageable", method = RequestMethod.GET,
          produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "get all Candidate restricted with Pagination",
          notes = "mengambil semua posisi with pagination")
  @ResponseBody
  public GdnRestListResponse<PositionDTOResponse> getAllPositionWithPageable(
          @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
          @RequestParam String channelId, @RequestParam String username, @RequestParam int page,
          @RequestParam int size) {
    Page<Position> positions = this.positionService.getAllPositionByStoreIdWithPageable(storeId,
            PageableHelper.generatePageable(page, size));
    List<PositionDTOResponse> res = new ArrayList<>();
    for (Position position : positions) {
      PositionDTOResponse positionDTOResponse =
              this.gdnMapper.deepCopy(position, PositionDTOResponse.class);
      res.add(positionDTOResponse);
    }
    return new GdnRestListResponse<>(res, new PageMetaData(50, 0, res.size()), requestId);
  }

  public GdnMapper getGdnMapper() {
    return gdnMapper;
  }

  @RequestMapping(value = "getPositionByStoreIdAndMarkForDelete", method = RequestMethod.GET,
          produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "get position by storeid and markForDelete",
          notes = "mengambil semua posisi dengan StoreId dengan markForDelete.")
  @ResponseBody
  public GdnRestListResponse<PositionDTOResponse> getPositionByStoreIdAndMarkForDelete(
          @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
          @RequestParam String channelId, @RequestParam String username,
          @RequestParam boolean markForDelete) {

    List<Position> positions =
            this.positionService.getPositionByStoreIdAndMarkForDelete(storeId, markForDelete);
    List<PositionDTOResponse> positionDTOResponses = new ArrayList<>();
    for (Position position : positions) {
      PositionDTOResponse positionDTOResponse =
              this.gdnMapper.deepCopy(position, PositionDTOResponse.class);
      positionDTOResponses.add(positionDTOResponse);
    }
    return new GdnRestListResponse<>(positionDTOResponses,
            new PageMetaData(50, 0, positionDTOResponses.size()), requestId);
  }

  @RequestMapping(value = "getPositionByTitle", method = RequestMethod.GET,
          produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "get position by title", notes = "mengambil semua posisi dengan nama.")
  @ResponseBody
  public GdnRestListResponse<PositionDTOResponse> getPositionByTitle(@RequestParam String clientId,
                                                                     @RequestParam String storeId, @RequestParam String requestId, @RequestParam String channelId,
                                                                     @RequestParam String username, @RequestParam String title) {
    List<Position> positions = this.positionService.getPositionByTitle(title, storeId);
    List<PositionDTOResponse> positionDTOResponses = new ArrayList<PositionDTOResponse>();

    for (Position positiones : positions) {
      PositionDTOResponse positionDTOResponse =
      this.gdnMapper.deepCopy(positiones, PositionDTOResponse.class);
      positionDTOResponses.add(positionDTOResponse);
    }

    return new GdnRestListResponse<PositionDTOResponse>(positionDTOResponses,
            new PageMetaData(5, 5, positions.size()), requestId);
  }

  @RequestMapping(value = "getPositionDetail", method = RequestMethod.GET,
          produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Mendapatkan semua posisi dengan detil",
          notes = "Menampilkan Candidate-kandidate yang mendaftare posisi-posisi tersebut termulti-tenant dengan masing2 storeID")
  @ResponseBody
  public GdnRestListResponse<PositionDetailDTOResponse> getPositionDetailById(
          @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
          @RequestParam String channelId, @RequestParam String username, @RequestParam String id)
          throws Exception {
    try {
      Position result = this.positionService.getPositionDetailByIdAndStoreId(id, storeId);
      List<PositionDetailDTOResponse> positionDetailDTOResponses = new ArrayList<>();
      PositionMapper.map(result, positionDetailDTOResponses);
      return new GdnRestListResponse<>(positionDetailDTOResponses,

      new PageMetaData(50, 0, positionDetailDTOResponses.size()), requestId);
    } catch (Exception e) {
      return new GdnRestListResponse<>(e.getMessage(), "", false, requestId);
    }
  }

  @RequestMapping(value = "insertNewPosition", method = RequestMethod.POST,
          consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
          produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "insert new position", notes = "memasukan posisi baru.")
  @ResponseBody
  public GdnBaseRestResponse insertNewPosition(@RequestParam String clientId,
                                               @RequestParam String storeId, @RequestParam String requestId, @RequestParam String channelId,
                                               @RequestParam String username, @RequestBody PositionDTORequest positionDTORequest) {
    Position temp = this.gdnMapper.deepCopy(positionDTORequest, Position.class);
    temp.setStoreId(storeId);
    Position result = this.positionService.insertNewPosition(temp);
    if (result.getId() == null) {
      return new GdnBaseRestResponse(false);
    }
    return new GdnBaseRestResponse(requestId);
  }

  public void setGdnMapper(GdnMapper gdnMapper) {
    this.gdnMapper = gdnMapper;
  }

  @RequestMapping(value = "updatePosition", method = RequestMethod.POST,
          consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "update position", notes = "mengganti posisi.")
  @ResponseBody
  public GdnBaseRestResponse updatePosition(@RequestParam String clientId,
                                            @RequestParam String storeId, @RequestParam String requestId, @RequestParam String channelId,
                                            @RequestParam String username, @RequestBody PositionDTORequest positionDTORequest)
          throws Exception {
    return new GdnBaseRestResponse(this.positionService.updatePositionTitle(storeId,
            positionDTORequest.getId(), positionDTORequest.getTitle()));
  }
}
