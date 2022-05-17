package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CommentForNewsDTO;
import com.alkemy.ong.dto.NewsDTO;
import com.alkemy.ong.dto.PageFormatter;
import com.alkemy.ong.services.CommentService;
import com.alkemy.ong.services.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;
    @Autowired
    private CommentService commentService;
    @Operation(summary = "Get pagination News by page number", description = "Return page with 10 news and url to next and previous page", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageFormatter.class))),
            @ApiResponse(responseCode = "404", description = "Page not found", content = @Content)})
    @GetMapping(produces = { "application/json" })
    public ResponseEntity<PageFormatter<NewsDTO>> getAll(@PageableDefault(page=0, size = 10) @Parameter(description = "Number of page, default is 0") Pageable pageable){
        PageFormatter<NewsDTO> newsDTO = newsService.findPageable(pageable);
        return ResponseEntity.ok().body(newsDTO);
    }
    @Operation(summary = "Get all data from a news by id", description = "Return all fields of a news, only for admins", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content=@Content),
            @ApiResponse(responseCode = "404", description = "Page not found", content = @Content)
    })
    @GetMapping(value = "/{id}", produces = { "application/json" })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<NewsDTO> getByID (@Parameter(description = "News id", required = true) @PathVariable UUID id){
        NewsDTO dto = newsService.getDetailsById(id);
        return ResponseEntity.ok().body(dto);
    }
    @Operation(summary = "Save a news", description = "Save a news, only for admins", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid News supplied", content=@Content),
            @ApiResponse(responseCode = "404", description = "Page not found", content = @Content)
    })
    @PostMapping(produces = { "application/json" })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<NewsDTO> save (@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "News to save, required validation", required = true) @Valid @RequestBody NewsDTO newsDTO){
        NewsDTO newsSaved = newsService.save(newsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newsSaved);
    }
    @Operation(summary = "Update a news", description = "Update a news, only for admins", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid id or News supplied", content=@Content),
            @ApiResponse(responseCode = "404", description = "Page not found", content = @Content)
    })
    @PutMapping(value = "/{id}", produces = { "application/json" })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<NewsDTO> update (@Parameter(description = "News id", required = true) @PathVariable UUID id,
                                           @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "News data", required = true) @RequestBody NewsDTO newsDTO){
        NewsDTO newsUpdated = newsService.update(id, newsDTO);
        return ResponseEntity.ok().body(newsUpdated);
    }
    @Operation(summary = "Delete a news", description = "Delete a news, only for admins", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content=@Content),
            @ApiResponse(responseCode = "404", description = "Page not found", content = @Content)
    })
    @DeleteMapping(value= "/{id}", produces = { "application/json" })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete (@Parameter(description = "News id", required = true) @PathVariable UUID id){
        newsService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @Operation(summary = "Get all comments from a news", description = "Return data from all comments in a news", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content=@Content),
            @ApiResponse(responseCode = "404", description = "Page not found", content = @Content)
    })
    @GetMapping(value = "/{id}/comments", produces = { "application/json" })
    public ResponseEntity<List<CommentForNewsDTO>> getAllComments(@Parameter(description = "News id", required = true) @PathVariable UUID id) {
        if(!newsService.existById(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<CommentForNewsDTO> commentForNewsDTOS = commentService.findAllByNewsId(id);
        if(commentForNewsDTOS.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<CommentForNewsDTO>>(commentForNewsDTOS, HttpStatus.OK);
    }
}
