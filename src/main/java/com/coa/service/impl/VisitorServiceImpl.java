package com.coa.service.impl;

import com.coa.exceptions.rest.AlreadyExistException;
import com.coa.model.Agency;
import com.coa.model.Visitor;
import com.coa.model.address.Address;
import com.coa.payload.request.VisitorRequest;
import com.coa.payload.request.address.AddressRequest;
import com.coa.payload.response.AgencyResponse;
import com.coa.payload.response.VisitorResponse;
import com.coa.repository.AgencyRepository;
import com.coa.repository.PositionRepository;
import com.coa.repository.VisitorRepository;
import com.coa.repository.address.AddressRepository;
import com.coa.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitorServiceImpl implements VisitorService {
    private final VisitorRepository visitorRepository;
    private final PositionRepository positionRepository;
    private final AgencyRepository agencyRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<VisitorResponse> findAll() {
        return visitorRepository.findAll().stream().map(visitor -> modelMapper.map(visitor,VisitorResponse.class)).collect(Collectors.toList());
    }

    @Override
    public VisitorResponse findById(Long id) {
        return modelMapper.map(visitorRepository.findById(id),VisitorResponse.class);
    }

    @Override
    public VisitorResponse findByName(String name) {
        return modelMapper.map(visitorRepository.findByName(name),VisitorResponse.class);
    }

    @Override
    public VisitorResponse save(VisitorRequest visitorRequest) {
        Visitor visitor = new Visitor();

        Optional<Visitor> visitorOptional = visitorRepository.findByName(visitorRequest.getName());

        if(visitorOptional.isPresent()){
           throw new AlreadyExistException("Visitor","name");
        }

        visitor.setName(visitorRequest.getName());


        Agency agency = agencyRepository.findById(visitorRequest.getAgency().getId())
                .orElseGet(()->new Agency(visitorRequest.getAgency().getName()));

        agency.addVisitor(visitor);
       // agencyRepository.save(agency);

        AddressRequest addressRequest = visitorRequest.getAddress();
        Address address = addressRepository.findByCodes(addressRequest.getBarangay().getCode(),
                addressRequest.getMunicipality().getCode(),addressRequest.getProvince().getCode(),addressRequest.getRegion().getCode())
                .orElseGet(()->modelMapper.map(addressRequest,Address.class));


        return null;
    }

    @Override
    public VisitorResponse update(Long id, VisitorRequest visitor) {
        return null;
    }

    @Override
    public Map<Long, String> findNames() {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
