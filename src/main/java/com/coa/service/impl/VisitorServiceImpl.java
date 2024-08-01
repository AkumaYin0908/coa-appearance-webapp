package com.coa.service.impl;

import com.coa.mapper.AddressMapper;
import com.coa.exceptions.rest.AlreadyExistException;
import com.coa.exceptions.rest.ResourceNotFoundException;
import com.coa.model.Agency;
import com.coa.model.CourtesyTitle;
import com.coa.model.Position;
import com.coa.model.Visitor;
import com.coa.model.address.Address;
import com.coa.payload.request.VisitorRequest;
import com.coa.payload.request.address.AddressRequest;
import com.coa.payload.request.address.BarangayRequest;
import com.coa.payload.response.VisitorResponse;
import com.coa.repository.AgencyRepository;
import com.coa.repository.CourtesyTitleRepository;
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
    private final CourtesyTitleRepository courtesyTitleRepository;


    @Override
    public List<VisitorResponse> findAll() {
        return visitorRepository.findAll().stream().map(visitor -> modelMapper.map(visitor, VisitorResponse.class)).toList();
    }

    @Override
    public VisitorResponse findById(Long id) {
        Visitor visitor = visitorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Visitor", "id", id));
        return modelMapper.map(visitor, VisitorResponse.class);
    }

    @Override
    public VisitorResponse findByName(String firstName, String middleInitial, String lastName) {
        Visitor visitor = visitorRepository.findByName(firstName,middleInitial,lastName).orElseThrow(() ->
                new ResourceNotFoundException("Visitor", "first name, middle initial, last name",
                        String.format("%s%s%s", firstName,middleInitial.equalsIgnoreCase("n/a") ? " " : middleInitial,lastName)));
        return modelMapper.map(visitor, VisitorResponse.class);
    }

    @Override
    @Transactional
    public VisitorResponse save(VisitorRequest visitorRequest) {
        Visitor visitor = new Visitor();

        Optional<Visitor> visitorOptional = visitorRepository.findByName(visitorRequest.getFirstName(),
                visitorRequest.getMiddleInitial(), visitorRequest.getLastName());

        if (visitorOptional.isPresent()) {
            throw new AlreadyExistException("Visitor", "first name, middle initial, last name");
        }

        CourtesyTitle courtesyTitle = courtesyTitleRepository.findByTitle(visitorRequest.getCourtesyTitle().getTitle())
                .orElseGet(()->courtesyTitleRepository.save(new CourtesyTitle(visitorRequest.getCourtesyTitle().getTitle())));

        courtesyTitle.addVisitor(visitor);

        visitor.setFirstName(visitorRequest.getFirstName());
        visitor.setMiddleInitial(visitorRequest.getMiddleInitial());
        visitor.setLastName(visitorRequest.getLastName());

        Position position = positionRepository.findByTitle(visitorRequest.getPosition().getTitle())
                .orElseGet(() -> positionRepository.save(new Position(visitorRequest.getPosition().getTitle())));

        position.addVisitor(visitor);

        Agency agency = agencyRepository.findByName(visitorRequest.getAgency().getName())
                .orElseGet(() -> agencyRepository.save(new Agency(visitorRequest.getAgency().getName())));

        agency.addVisitor(visitor);


//        AddressRequest addressRequest = visitorRequest.getAddress();
//        Address address = addressRepository.findByCodes(addressRequest.getBarangay() == null ? null : addressRequest.getBarangay().getCode(),
//                        addressRequest.getMunicipality().getCode(), addressRequest.getProvince().getCode(), addressRequest.getRegion().getCode())
//                .orElseGet(() -> addressMapper.mapToModel(addressRequest));
//
//        if (address.getBarangay() != null) {
//            barangayRepository.save(address.getBarangay());
//        }
//
//        municipalityRepository.save(address.getMunicipality());
//        provinceRepository.save(address.getProvince());
//        regionRepository.save(address.getRegion());
//        addressRepository.save(address);
//
//        address.addVisitor(visitor);

        AddressRequest addressRequest = visitorRequest.getAddress();
        Address address= null;
        Optional<Address> addressOptional = addressRepository.findByCodes(addressRequest.getBarangay() == null ? null : addressRequest.getBarangay().getCode(),
                addressRequest.getMunicipality().getCode(), addressRequest.getProvince().getCode(), addressRequest.getRegion().getCode());

        if(addressOptional.isPresent()){
            address = addressMapper.mapToModel(addressRequest);
        }else{
            address = addressMapper.mapToModel(addressRequest);
            if (address.getBarangay() != null) {
                barangayRepository.save(address.getBarangay());
            }

            municipalityRepository.save(address.getMunicipality());
            provinceRepository.save(address.getProvince());
            regionRepository.save(address.getRegion());
            addressRepository.save(address);
        }

        visitor.setAddress(address);


        Visitor dbVisitor = visitorRepository.save(visitor);

        return modelMapper.map(dbVisitor, VisitorResponse.class);
    }

    @Override
    @Transactional
    public VisitorResponse update(Long id, VisitorRequest visitorRequest) {
        Visitor visitor = visitorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Visitor", "id", id));

        Optional<Visitor> visitorOptional = visitorRepository.findByName(visitorRequest.getFirstName(),
                visitorRequest.getMiddleInitial(), visitorRequest.getLastName());

        if (visitorOptional.isPresent() && !visitorOptional.get().getId().equals(id)) {
            throw new AlreadyExistException("Visitor", "first name, middle initial, last name");
        }

        CourtesyTitle courtesyTitle = courtesyTitleRepository.findByTitle(visitorRequest.getCourtesyTitle().getTitle())
                .orElseGet(()->courtesyTitleRepository.save(new CourtesyTitle(visitorRequest.getCourtesyTitle().getTitle())));

        courtesyTitle.addVisitor(visitor);

        visitor.setFirstName(visitorRequest.getFirstName());
        visitor.setMiddleInitial(visitorRequest.getMiddleInitial());
        visitor.setLastName(visitorRequest.getLastName());

        Position position = positionRepository.findByTitle(visitorRequest.getPosition().getTitle())
                .orElseGet(() -> positionRepository.save(new Position(visitorRequest.getPosition().getTitle())));

        position.addVisitor(visitor);

        Agency agency = agencyRepository.findByName(visitorRequest.getAgency().getName())
                .orElseGet(() -> agencyRepository.save(new Agency(visitorRequest.getAgency().getName())));

        agency.addVisitor(visitor);

        AddressRequest addressRequest = visitorRequest.getAddress();
        Address address= null;
        Optional<Address> addressOptional = addressRepository.findByCodes(addressRequest.getBarangay() == null ? null : addressRequest.getBarangay().getCode(),
                        addressRequest.getMunicipality().getCode(), addressRequest.getProvince().getCode(), addressRequest.getRegion().getCode());

        if(addressOptional.isPresent()){
            address = addressMapper.mapToModel(addressRequest);
        }else{
            address = addressMapper.mapToModel(addressRequest);
            if (address.getBarangay() != null) {
                barangayRepository.save(address.getBarangay());
            }

            municipalityRepository.save(address.getMunicipality());
            provinceRepository.save(address.getProvince());
            regionRepository.save(address.getRegion());
            addressRepository.save(address);
        }


        visitor.setAddress(address);

        visitorRepository.save(visitor);

        return modelMapper.map(visitor, VisitorResponse.class);

    }

    @Override
    public List<Map<Long, String>> findNames() {
        return visitorRepository.findNames();
    }

    @Override
    public void delete(Long id) {
        visitorRepository.deleteById(id);
    }
}
