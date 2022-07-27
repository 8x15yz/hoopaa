package com.ssafy.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.api.response.JsonRes;
import com.ssafy.api.service.RoomInfoService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.dto.RoomInfoDto;
import com.ssafy.db.entity.RoomInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.kurento.client.internal.server.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@Api(value = "토론방 조회 API", tags = {"Rooms"})
@RestController
@RequestMapping("/api/v1/list")
public class RoomInfoController {

    @Autowired
    RoomInfoService roomInfoService;
    @Autowired
    ObjectMapper mapper;

    @GetMapping("/all")
    @ApiOperation(value = "전체 토론방 조회")
    public ResponseEntity<? extends BaseResponseBody> findAll() throws JsonProcessingException {
        List<RoomInfoDto> findAll=roomInfoService.findAll();
        String findAllString=mapper.writeValueAsString(findAll);
        return ResponseEntity.ok(JsonRes.of(200, "success",findAllString));
    }
    @GetMapping("/search/{keyword}")
    @ApiOperation(value="토론방 검색")
    public ResponseEntity<? extends BaseResponseBody> searchBy(@PathVariable String keyword) throws JsonProcessingException {
        List<RoomInfoDto> searchBy=roomInfoService.searchBy(keyword);
        String searchByString=mapper.writeValueAsString(searchBy);
        return ResponseEntity.ok(JsonRes.of(200, "success",searchByString));
    }

    @GetMapping("/{cate}")
    @ApiOperation(value="카테고리별 검색")
    public ResponseEntity<? extends BaseResponseBody> findByCate(@PathVariable int cate) throws JsonProcessingException {
        List<RoomInfoDto> findByCate=roomInfoService.findByCate(cate);
        String findByCateString=mapper.writeValueAsString(findByCate);
        return ResponseEntity.ok(JsonRes.of(200, "success",findByCateString));
    }
}
