package com.coa.service.impl;

import com.coa.dto.VisitorDTO;
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
    public Visitor findById(Long id)  {
       Optional<Visitor> result=visitorRepository.findById(id);

       return result.orElse(null);
    }

    @Override
    public Optional<VisitorDTO> findAndMapToVisitorDTO(Long id) {
        VisitorDTO visitorDTO;
        Visitor visitor=findById(id);
        visitorDTO=new VisitorDTO(
                visitor.getId(),
                visitor.getName(),
                visitor.getPosition().getName(),
                visitor.getAgency().getName()
        );
        return Optional.of(visitorDTO);

    }

    @Override
    public Visitor findVisitorByName(String name)  {
        Optional<Visitor> result=visitorRepository.findVisitorByName(name);
        return result.orElse(null);
    }

    @Override
    public Visitor findVisitorByName(Long id, String name)  {
        Optional<Visitor> result=visitorRepository.findVisitorByName(id, name);
        return result.orElse(null);
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
