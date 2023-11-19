package com.coa.service.impl;

import com.coa.exception.VisitorNotFoundException;
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
    public List<Visitor> listAll() {
        return visitorRepository.findAll();
    }

    @Override
    public Visitor findById(Long id) throws VisitorNotFoundException {
       Optional<Visitor> result=visitorRepository.findById(id);

       return result.isPresent() ? result.get() :
               result.orElseThrow(()->new VisitorNotFoundException("Visitor with id no. " + id + " not found!"));
    }

    @Override
    public Visitor findVisitorByName(String name) throws VisitorNotFoundException {
        Optional<Visitor> result=visitorRepository.findVisitorByName(name);

        return result.isPresent() ? result.get() :
                result.orElseThrow(() -> new VisitorNotFoundException(name + " not found!"));
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
