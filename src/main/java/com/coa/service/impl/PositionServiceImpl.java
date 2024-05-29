package com.coa.service.impl;

import com.coa.exceptions.rest.AlreadyExistException;
import com.coa.exceptions.rest.ResourceNotFoundException;
import com.coa.model.Position;
import com.coa.payload.request.PositionRequest;
import com.coa.payload.response.PositionResponse;
import com.coa.repository.PositionRepository;
import com.coa.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<PositionResponse> findAll() {
        return positionRepository.findAll().stream().map(position -> modelMapper.map(position,PositionResponse.class)).toList();
    }

    @Override
    public PositionResponse findById(Long id) {
        Position position = positionRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Position","id",id));
        return modelMapper.map(position,PositionResponse.class);
    }

    @Override
    public PositionResponse findByTitle(String title) {
        Position position = positionRepository.findByTitle(title).orElseThrow(()->new ResourceNotFoundException("Position","title",title));
        return modelMapper.map(position,PositionResponse.class);
    }

    @Override
    public Map<Long, String> findTitles() {
        return positionRepository.findTitles();
    }

    @Override
    public PositionResponse save(PositionRequest positionRequest) {
        Optional<Position> positionOptional = positionRepository.findByTitle(positionRequest.getTitle());

        if(positionOptional.isPresent()){
            throw new AlreadyExistException("Position","title");
        }

        Position position = modelMapper.map(positionRequest,Position.class);
        Position dbPosition = positionRepository.save(position);

        return modelMapper.map(dbPosition,PositionResponse.class);
    }

    @Override
    public PositionResponse update(Long id, PositionRequest positionRequest) {
         Position position = positionRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Position","id",id));

         Optional<Position> positionOptional = positionRepository.findByTitle(positionRequest.getTitle());

         if(positionOptional.isPresent() && !positionOptional.get().getId().equals(id)){
             throw new AlreadyExistException("Position","title");
         }

         position.setTitle(positionRequest.getTitle());
         positionRepository.save(position);

         return modelMapper.map(position,PositionResponse.class);
    }

    @Override
    public void delete(Long id) {
        positionRepository.deleteById(id);
    }
}
