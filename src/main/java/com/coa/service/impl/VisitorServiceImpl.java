package com.coa.service.impl;

import com.coa.mapper.AddressMapper;
import com.coa.exceptions.rest.AlreadyExistException;
import com.coa.exceptions.rest.ResourceNotFoundException;
import com.coa.model.Agency;
import com.coa.model.Position;
import com.coa.model.Visitor;
import com.coa.model.address.Address;
import com.coa.payload.request.VisitorRequest;
import com.coa.payload.request.address.AddressRequest;
import com.coa.payload.response.VisitorResponse;
import com.coa.repository.AgencyRepository;
import com.coa.repository.PositionRepository;
import com.coa.repository.VisitorRepository;
import com.coa.repository.address.*;
import com.coa.service.VisitorService;
import jakarta.transaction.Transactional;
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
    private final AddressMapper addressMapper;
    private final BarangayRepository barangayRepository;
    private final MunicipalityRepository municipalityRepository;
    private final ProvinceRepository provinceRepository;
    private final RegionRepository regionRepository;


    @Override
    public List<VisitorResponse> findAll() {
        return visitorRepository.findAll().stream().map(visitor -> modelMapper.map(visitor, VisitorResponse.class)).collect(Collectors.toList());
    }

    @Override
    public VisitorResponse findById(Long id) {
        Visitor visitor = visitorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Visitor", "id", id));
        return modelMapper.map(visitor, VisitorResponse.class);
    }

    @Override
    public VisitorResponse findByName(String name) {
        Visitor visitor = visitorRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Visitor", "name", name));
        return modelMapper.map(visitor, VisitorResponse.class);
    }

    @Override
    @Transactional
    public VisitorResponse save(VisitorRequest visitorRequest) {
        Visitor visitor = new Visitor();

        Optional<Visitor> visitorOptional = visitorRepository.findByName(visitorRequest.getName());

        if (visitorOptional.isPresent()) {
            throw new AlreadyExistException("Visitor", "name");
        }

        visitor.setName(visitorRequest.getName());

        Position position = positionRepository.findById(visitorRequest.getPosition().getId())
                .orElseGet(() -> positionRepository.save(new Position(visitorRequest.getPosition().getTitle())));

        position.addVisitor(visitor);

        Agency agency = agencyRepository.findById(visitorRequest.getAgency().getId())
                .orElseGet(() -> agencyRepository.save(new Agency(visitorRequest.getAgency().getName())));

        agency.addVisitor(visitor);


        AddressRequest addressRequest = visitorRequest.getAddress();
        Address address = addressRepository.findByCodes(addressRequest.getBarangay() == null ? null : addressRequest.getBarangay().getCode(),
                        addressRequest.getMunicipality().getCode(), addressRequest.getProvince().getCode(), addressRequest.getRegion().getCode())
                .orElseGet(() -> addressMapper.mapToModel(addressRequest));

        if (address.getBarangay() != null) {
            barangayRepository.save(address.getBarangay());
        }

        municipalityRepository.save(address.getMunicipality());
        provinceRepository.save(address.getProvince());
        regionRepository.save(address.getRegion());
        addressRepository.save(address);

        address.addVisitor(visitor);


        Visitor dbVisitor = visitorRepository.save(visitor);

        return modelMapper.map(dbVisitor, VisitorResponse.class);
    }

    @Override
    @Transactional
    public VisitorResponse update(Long id, VisitorRequest visitorRequest) {
        Visitor visitor = visitorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Visitor", "id", id));

        Optional<Visitor> visitorOptional = visitorRepository.findByName(visitorRequest.getName());

        if (visitorOptional.isPresent()) {
            throw new AlreadyExistException("Visitor", "name");
        }

        visitor.setName(visitorRequest.getName());

        Position position = positionRepository.findById(visitorRequest.getPosition().getId())
                .orElseGet(() -> positionRepository.save(new Position(visitorRequest.getPosition().getTitle())));

        position.addVisitor(visitor);

        Agency agency = agencyRepository.findById(visitorRequest.getAgency().getId())
                .orElseGet(() -> agencyRepository.save(new Agency(visitorRequest.getAgency().getName())));

        agency.addVisitor(visitor);


        AddressRequest addressRequest = visitorRequest.getAddress();
        Address address = addressRepository.findByCodes(addressRequest.getBarangay() == null ? null : addressRequest.getBarangay().getCode(),
                        addressRequest.getMunicipality().getCode(), addressRequest.getProvince().getCode(), addressRequest.getRegion().getCode())
                .orElseGet(() -> addressMapper.mapToModel(addressRequest));

        if (address.getBarangay() != null) {
            barangayRepository.save(address.getBarangay());
        }

        municipalityRepository.save(address.getMunicipality());
        provinceRepository.save(address.getProvince());
        regionRepository.save(address.getRegion());
        addressRepository.save(address);

        address.addVisitor(visitor);


        visitorRepository.save(visitor);

        return modelMapper.map(visitor, VisitorResponse.class);

    }

    @Override
    public Map<Long, String> findNames() {
        return visitorRepository.findNames();
    }

    @Override
    public void delete(Long id) {
        visitorRepository.deleteById(id);
    }
}
