package com.company.clinic.controller;

import com.company.clinic.command.CreateVisitCommand;
import com.company.clinic.dto.VisitDto;
import com.company.clinic.model.visit.Visit;
import com.company.clinic.service.VisitService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/visits")
public class VisitController {

    private final VisitService visitService;
    private final ModelMapper modelMapper;

    public VisitController(VisitService visitService, ModelMapper modelMapper) {
        this.visitService = visitService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<VisitDto> createVisit(@Valid @RequestBody CreateVisitCommand createVisitCommand) {
        Visit visit = visitService.createVisit(createVisitCommand);
        VisitDto visitDto = modelMapper.map(visit, VisitDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(visitDto);
    }

    @GetMapping("/confirm")
    public ResponseEntity<VisitDto> confirmVisit(@RequestParam String token) {
        Visit visit = visitService.confirm(token);
        VisitDto visitDto = modelMapper.map(visit, VisitDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(visitDto);
    }

    @GetMapping("/cancel")
    public ResponseEntity<VisitDto> cancelVisit(@RequestParam String token) {
        Visit visit = visitService.cancel(token);
        VisitDto visitDto = modelMapper.map(visit, VisitDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(visitDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitDto> getVisitById(@PathVariable(name = "id") long id) {
        Visit visit = visitService.getStatusOfVisit(id);
        VisitDto visitDto = modelMapper.map(visit, VisitDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(visitDto);
    }
}
