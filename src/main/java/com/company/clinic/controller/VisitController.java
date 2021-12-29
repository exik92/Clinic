package com.company.clinic.controller;

import com.company.clinic.command.CreateVisitCommand;
import com.company.clinic.command.VisitActionCommand;
import com.company.clinic.dto.VisitDto;
import com.company.clinic.model.visit.Visit;
import com.company.clinic.model.visit.VisitAction;
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

    @PutMapping("/confirm")
    public ResponseEntity<VisitDto> confirmVisit(@Valid @RequestBody VisitActionCommand visitActionCommand) {
        visitActionCommand.setActionType(VisitAction.CONFIRM);
        Visit visit = visitService.confirm(visitActionCommand);
        VisitDto visitDto = modelMapper.map(visit, VisitDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(visitDto);
    }

    @PutMapping("/cancel")
    public ResponseEntity<VisitDto> cancelVisit(@Valid @RequestBody VisitActionCommand visitActionCommand) {
        visitActionCommand.setActionType(VisitAction.CANCEL);
        Visit visit = visitService.cancel(visitActionCommand);
        VisitDto visitDto = modelMapper.map(visit, VisitDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(visitDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitDto> getVisitById(@PathVariable(name = "id") long id) {
        Visit visit = visitService.getVisitById(id);
        VisitDto visitDto = modelMapper.map(visit, VisitDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(visitDto);
    }
}
