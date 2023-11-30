package com.coa.service.impl;

import com.coa.dto.VisitorDTO;
import com.coa.exceptions.VisitorNotFoundException;
import com.coa.model.Visitor;
import com.coa.repository.VisitorRepository;
import com.coa.service.VisitorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepository;

    @Override
    public Page<Visitor> findByNameContainingIgnoreCase(String name, Pageable pageable) {
        return visitorRepository.findByNameContainingIgnoreCase(name,pageable);

    }

    @Override
    public Page<Visitor> findAll(Pageable pageable) {
        return visitorRepository.findAll(pageable);
    }

    @Override
    public List<Visitor> findAll() {
        return visitorRepository.findAll();
    }

    @Override
    public Optional<Visitor> findById(Long id) throws VisitorNotFoundException {
        return visitorRepository.findById(id);


    }

    @Override
    public Optional<VisitorDTO> findAndMapToVisitorDTO(Long id) throws VisitorNotFoundException {
        VisitorDTO visitorDTO = null;
        Optional<Visitor> optionalVisitor=findById(id);

        if(optionalVisitor.isPresent()){
            Visitor visitor=optionalVisitor.get();
            visitorDTO=new VisitorDTO(
                    visitor.getId(),
                    visitor.getName(),
                    visitor.getPosition().getName(),
                    visitor.getAgency().getName()
            );
        }

        return Optional.ofNullable(visitorDTO);

    }

    @Override
    public Optional<Visitor> findVisitorByName(String name) throws VisitorNotFoundException {
        Optional<Visitor> result=visitorRepository.findVisitorByName(name);
        return Optional.ofNullable(result.orElseThrow(() -> new VisitorNotFoundException(name + " does not exist!")));
    }

    @Override
    public Optional<Visitor> findVisitorByName(Long id, String name) throws VisitorNotFoundException {
        Optional<Visitor> result=visitorRepository.findVisitorByName(id, name);
        return Optional.ofNullable(result.orElseThrow(() -> new VisitorNotFoundException(name + " does not exist!")));
    }


    @Override
    @Transactional
    public void save(Visitor visitor) {
        visitorRepository.save(visitor);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        visitorRepository.deleteById(id);
    }
}
