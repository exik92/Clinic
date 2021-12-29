package com.company.clinic.controller;

import com.company.clinic.command.CreateDoctorCommand;
import com.company.clinic.dto.DoctorDto;
import com.company.clinic.model.doctor.Doctor;
import com.company.clinic.service.DoctorService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    private final ModelMapper modelMapper;

    public DoctorController(DoctorService doctorService, ModelMapper modelMapper) {
        this.doctorService = doctorService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<DoctorDto> addDoctor(@RequestBody @Valid CreateDoctorCommand createDoctorCommand) {
        Doctor doctor = doctorService.addDoctor(createDoctorCommand);
        DoctorDto dto = modelMapper.map(doctor, DoctorDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("{id}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable(name = "id") long id) {
        Doctor doctor = doctorService.getDoctorById(id);
        DoctorDto doctorDto = modelMapper.map(doctor, DoctorDto.class);
        return ResponseEntity.ok(doctorDto);
    }

    @GetMapping
    public Page<DoctorDto> getAllDoctors(@PageableDefault(size = 10, page = 0, sort = "lastName") Pageable pageable) {
        return doctorService.findAll(pageable)
                .map(doctor -> modelMapper.map(doctor, DoctorDto.class));
    }

    @PutMapping("/fire/{id}")
    public void fireDoctor(@PathVariable(name = "id") long id) {
        doctorService.fireDoctor(id);
    }

//    @GetMapping("{id}")
//    public ResponseEntity<List<DoctorDto>> findDoctorBySpecialization(
//            @PathVariable(name = "specialization") MedicalSpecialization specialization) {
//        return ResponseEntity.ok(Collections.emptyList());
//    }
}
