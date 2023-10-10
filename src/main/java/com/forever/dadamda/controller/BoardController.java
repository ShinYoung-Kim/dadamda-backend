package com.forever.dadamda.controller;

import com.forever.dadamda.dto.ApiResponse;
import com.forever.dadamda.dto.board.CreateBoardRequest;
import com.forever.dadamda.dto.board.GetBoardCountResponse;
import com.forever.dadamda.dto.board.GetBoardListResponse;
import com.forever.dadamda.dto.board.GetBoardResponse;
import com.forever.dadamda.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "보드 생성", description = "1개의 보드를 생성합니다.")
    @PostMapping("/v1/boards")
    public ApiResponse<String> createBoards(
            @Valid @RequestBody CreateBoardRequest createBoardRequest,
            Authentication authentication) {
        String email = authentication.getName();
        boardService.createBoards(email, createBoardRequest);
        return ApiResponse.success();
    }

    @Operation(summary = "보드 삭제", description = "1개의 보드를 삭제합니다.")
    @DeleteMapping("/v1/boards/{boardId}")
    public ApiResponse<String> createBoards(@PathVariable @Positive @NotNull Long boardId,
            Authentication authentication) {
        String email = authentication.getName();
        boardService.deleteBoards(email, boardId);
        return ApiResponse.success();
    }

    @Operation(summary = "보드 고정", description = "1개의 보드를 보드 카테고리에서 상단에 고정합니다.")
    @PatchMapping("/v1/boards/fixed/{boardId}")
    public ApiResponse<String> fixedBoards(@PathVariable @Positive @NotNull Long boardId,
            Authentication authentication) {
        String email = authentication.getName();
        boardService.fixedBoards(email, boardId);
        return ApiResponse.success();
    }

    @Operation(summary = "보드 목록 조회", description = "여러 개의 보드를 조회합니다")
    @GetMapping("/v1/boards")
    public ApiResponse<Slice<GetBoardListResponse>> getBoards(Pageable pageable,
            Authentication authentication) {
        String email = authentication.getName();
        return ApiResponse.success(boardService.getBoardList(email, pageable));
    }

    @Operation(summary = "전체 보드 개수 조회", description = "전체 보드 개수 정보를 조회할 수 있습니다.")
    @GetMapping("/v1/boards/count")
    public ApiResponse<GetBoardCountResponse> getBoardCount(Authentication authentication) {
        String email = authentication.getName();
        return ApiResponse.success(GetBoardCountResponse.of(boardService.getBoardCount(email)));
    }

    @Operation(summary = "보드 내용 조회", description = "전체 보드 개수 정보를 조회할 수 있습니다.")
    @GetMapping("/v1/boards/{boardId}")
    public ApiResponse<GetBoardResponse> getBoards(
            @PathVariable @Positive @NotNull Long boardId, Authentication authentication) {
        String email = authentication.getName();
        return ApiResponse.success(boardService.getBoard(email, boardId));
    }
}
