package com.company.clinic.controller;

import com.company.clinic.command.CreatePatientCommand;
import com.company.clinic.dto.PatientDto;
import com.company.clinic.model.patient.Patient;
import com.company.clinic.service.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    private final ModelMapper modelMapper;

    public PatientController(PatientService patientService, ModelMapper modelMapper) {
        this.patientService = patientService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<PatientDto> addPatient(@Valid @RequestBody CreatePatientCommand createPatientCommand) {
        Patient patient = patientService.addPatient(createPatientCommand);
        PatientDto dto = modelMapper.map(patient, PatientDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("{id}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable(name = "id") long id) {
        Patient patient = patientService.getPatientById(id);
        return ResponseEntity.ok(modelMapper.map(patient, PatientDto.class));
    }

    @GetMapping
    public Page<PatientDto> getAllPatients(@PageableDefault(size = 10, page = 0, sort = "nameOfAnimal") Pageable pageable) {
        return patientService.findAll(pageable)
                .map(patient -> modelMapper.map(patient, PatientDto.class));
    }
}
