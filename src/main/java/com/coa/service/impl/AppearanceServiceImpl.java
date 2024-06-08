package com.coa.service.impl;

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
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppearanceServiceImpl implements AppearanceService {
    private final AppearanceRepository appearanceRepository;

    private final VisitorRepository visitorRepository;
    private final ModelMapper modelMapper;

    private final PurposeRepository purposeRepository;


    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");


    @Override
    public AppearanceResponse findById(Long id) {
        Appearance appearance = appearanceRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Appearance","id",id));
        return modelMapper.map(appearance,AppearanceResponse.class);
    }

    @Override
    public List<AppearanceResponse> findByVisitor(Long id) {

        Visitor visitor = visitorRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Visitor","id",id));
        return visitor.getAppearances().stream().map(appearance -> modelMapper.map(appearance,AppearanceResponse.class)).toList();
    }

    @Override
    public List<AppearanceResponse> findByVisitorAndDateIssued(Long id, String strDateIssued) {
        Visitor visitor = visitorRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Visitor","id",id));

        return visitor.getAppearances()
                .stream()
                .filter(appearance -> appearance.getDateIssued().equals(LocalDate.parse(strDateIssued,dateTimeFormatter)))
                .map(appearance -> modelMapper.map(appearance,AppearanceResponse.class))
                .toList();
    }

    @Override
    public List<AppearanceResponse> findByPurpose(String description) {
        return appearanceRepository.findByPurpose(description).stream().map(appearance -> modelMapper.map(appearance,AppearanceResponse.class)).toList();
    }

    @Override
    public List<AppearanceResponse> findByDateIssued(String strDateIssued) {
        return appearanceRepository.findByDateIssued(LocalDate.parse(strDateIssued,dateTimeFormatter)).stream()
                .map(appearance -> modelMapper.map(appearance,AppearanceResponse.class)).toList();
    }

    @Override
    public List<AppearanceResponse> findByMonth(Integer month) {
        return appearanceRepository.findByMonth(month).stream().map(appearance -> modelMapper.map(appearance,AppearanceResponse.class)).toList();
    }

    @Override
    public List<AppearanceResponse> findByYear(Integer year){
        return appearanceRepository.findByYear(year).stream().map(appearance -> modelMapper.map(appearance,AppearanceResponse.class)).toList();
    }

    @Override
    public AppearanceResponse findByReference(String reference) {
        Appearance appearance = appearanceRepository.findByReference(reference).orElseThrow(()->new ResourceNotFoundException("Appearance","reference",reference));
        return modelMapper.map(appearance,AppearanceResponse.class);
    }

    @Override
    public AppearanceResponse save(Long id, AppearanceRequest appearanceRequest) {

        Visitor visitor = visitorRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Visitor","id",id));

        Appearance appearance = new Appearance();
        appearance.setVisitor(visitor);

        appearance.setDateIssued(LocalDate.parse(appearanceRequest.getDateIssued(),dateTimeFormatter));
        appearance.setDateFrom(LocalDate.parse(appearanceRequest.getDateFrom(),dateTimeFormatter));
        appearance.setDateTo(LocalDate.parse(appearanceRequest.getDateTo(),dateTimeFormatter));

        Purpose purpose = purposeRepository.findByDescription(appearanceRequest.getPurpose().getDescription())
                .orElse(new Purpose(appearanceRequest.getPurpose().getDescription()));

        purpose.addAppearance(appearance);

        appearance.setReference(appearanceRequest.getReference());

        Appearance dbAppearance = appearanceRepository.save(appearance);

        return modelMapper.map(dbAppearance,AppearanceResponse.class);


    }

    @Override
    public AppearanceResponse update(Long id, AppearanceRequest appearanceRequest) {
        Appearance appearance = appearanceRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Appearance","id",id));

        appearance.setDateIssued(LocalDate.parse(appearanceRequest.getDateIssued(),dateTimeFormatter));
        appearance.setDateFrom(LocalDate.parse(appearanceRequest.getDateFrom(),dateTimeFormatter));
        appearance.setDateTo(LocalDate.parse(appearanceRequest.getDateTo(),dateTimeFormatter));

        Purpose purpose = purposeRepository.findByDescription(appearanceRequest.getPurpose().getDescription())
                .orElse(new Purpose(appearanceRequest.getPurpose().getDescription()));

        purpose.addAppearance(appearance);

        appearance.setReference(appearanceRequest.getReference());

        appearanceRepository.save(appearance);

        return  modelMapper.map(appearance, AppearanceResponse.class);
    }

    @Override
    public void delete(Long id) {
        appearanceRepository.deleteById(id);
    }
}
