package com.coa.service.impl;

import com.coa.dto.AppearanceDTO;
import com.coa.exceptions.rest.ResourceNotFoundException;
import com.coa.model.Appearance;
import com.coa.model.Purpose;
import com.coa.model.Visitor;
import com.coa.payload.request.AppearanceRequest;
import com.coa.payload.response.AppearanceResponse;
import com.coa.repository.AppearanceRepository;
import com.coa.repository.PurposeRepository;
import com.coa.repository.VisitorRepository;
import com.coa.service.AppearanceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppearanceServiceImpl implements AppearanceService {
    private final AppearanceRepository appearanceRepository;

    private final VisitorRepository visitorRepository;
    private final ModelMapper modelMapper;
    private final PurposeRepository purposeRepository;





    @Override
    public AppearanceResponse findById(Long id) {
        Appearance appearance = appearanceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Appearance", "id", id));
        return modelMapper.map(appearance, AppearanceResponse.class);
    }

    @Override
    public List<AppearanceResponse> findAll() {
        return appearanceRepository.findAll().stream()
                .map(appearance -> modelMapper.map(appearance, AppearanceResponse.class)).toList();
    }

    @Override
    public List<AppearanceResponse> findByVisitor(Long id) {

//        Visitor visitor = visitorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Visitor", "id", id));
//        return visitor.getAppearances().stream().map(appearance -> modelMapper.map(appearance, AppearanceResponse.class)).toList();

        return appearanceRepository.findByVisitor(id)
                .stream()
                .map(appearance -> modelMapper.map(appearance,AppearanceResponse.class)).toList();
    }

    @Override
    public List<AppearanceResponse> findByVisitorAndDateIssued(Long id, String strDateIssued) {
//        Visitor visitor = visitorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Visitor", "id", id));
//
//        return visitor.getAppearances()
//                .stream()
//                .filter(appearance -> appearance.getDateIssued().equals(LocalDate.parse(strDateIssued)))
//                .map(appearance -> modelMapper.map(appearance, AppearanceResponse.class))
//                .toList();

        return appearanceRepository.findByVisitorAndDateIssued(id, LocalDate.parse(strDateIssued))
                .stream().map(appearance -> modelMapper.map(appearance,AppearanceResponse.class)).toList();
    }

    @Override
    public Optional<AppearanceResponse> findByVisitorAndDateFrom(Long id, String strDateFrom) {

        return appearanceRepository.findByVisitorAndDateFrom(id, LocalDate.parse(strDateFrom))
                .map(appearance -> modelMapper.map(appearance,AppearanceResponse.class));

    }

    @Override
    public List<AppearanceResponse> findByPurpose(String description) {
//        return appearanceRepository.findByPurpose(description).stream().map(appearance -> modelMapper.map(appearance,AppearanceResponse.class)).toList();
        Purpose purpose = purposeRepository.findByDescription(description).orElseThrow(() -> new ResourceNotFoundException("Purpose", "description", description));
        return purpose.getAppearances().stream().map(appearance -> modelMapper.map(appearance, AppearanceResponse.class)).toList();
    }

    @Override
    public List<AppearanceResponse> findByDateIssued(String strDateIssued) {
        return appearanceRepository.findByDateIssued(LocalDate.parse(strDateIssued)).stream()
                .map(appearance -> modelMapper.map(appearance, AppearanceResponse.class)).toList();
    }

    @Override
    public List<AppearanceResponse> findByMonth(Integer month) {
        return appearanceRepository.findByMonth(month).stream().map(appearance -> modelMapper.map(appearance, AppearanceResponse.class)).toList();
    }

    @Override
    public List<AppearanceResponse> findByYear(Integer year) {
        return appearanceRepository.findByYear(year).stream().map(appearance -> modelMapper.map(appearance, AppearanceResponse.class)).toList();
    }

    @Override
    public AppearanceResponse findByReference(String reference) {
        Appearance appearance = appearanceRepository.findByReference(reference).orElseThrow(() -> new ResourceNotFoundException("Appearance", "reference", reference));
        return modelMapper.map(appearance, AppearanceResponse.class);
    }

    @Override
    @Transactional
    public AppearanceResponse save(AppearanceRequest appearanceRequest) {


        Appearance appearance = new Appearance();

        map(appearance, appearanceRequest);

        Appearance dbAppearance = appearanceRepository.save(appearance);

        return modelMapper.map(dbAppearance, AppearanceResponse.class);


    }

    @Override
    public List<AppearanceResponse> saveAll(List<AppearanceRequest> appearanceRequests) {
        List<Appearance> appearances = new ArrayList<>();

        for (AppearanceRequest appearanceRequest : appearanceRequests) {
            Appearance appearance = new Appearance();


            map(appearance, appearanceRequest);
            appearances.add(appearance);
        }

        appearanceRepository.saveAll(appearances);

        return appearances.stream().map(appearance -> modelMapper.map(appearance, AppearanceResponse.class)).toList();

    }

    @Override
    @Transactional
    public AppearanceResponse update(Long id, AppearanceRequest appearanceRequest) {
        Appearance appearance = appearanceRepository.findById(appearanceRequest.getId()).orElseThrow(() -> new ResourceNotFoundException("Visitor", "id", id));

        map(appearance, appearanceRequest);

        appearanceRepository.save(appearance);

        return modelMapper.map(appearance, AppearanceResponse.class);
    }

    @Override
    public void delete(Long id) {
        appearanceRepository.deleteById(id);
    }


    public void map(Appearance appearance, AppearanceRequest appearanceRequest) {
        Visitor visitor = modelMapper.map(appearanceRequest.getVisitor(),Visitor.class);
        appearance.setVisitor(visitor);
        appearance.setDateIssued(LocalDate.parse(appearanceRequest.getDateIssued()));
        appearance.setDateFrom(LocalDate.parse(appearanceRequest.getDateFrom()));
        appearance.setDateTo(LocalDate.parse(appearanceRequest.getDateTo()));

        Purpose purpose = purposeRepository.findByDescription(appearanceRequest.getPurpose().getDescription())
                .orElse(new Purpose(appearanceRequest.getPurpose().getDescription()));

        purpose.addAppearance(appearance);
        purposeRepository.save(purpose);

        appearance.setReference(appearanceRequest.getReference());
    }
}
