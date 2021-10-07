package com.devsuperior.bds02.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class EventService {

	@Autowired
	private EventRepository repository;
	
	@Autowired
	private CityRepository cityRepository;

	@Transactional
	public EventDTO update(Long id, EventDTO dto) {
		try {
			var event = repository.getOne(id);
			var city = cityRepository.getOne(dto.getCityId());
			
			event.setDate(dto.getDate());
			event.setName(dto.getName());
			event.setUrl(dto.getUrl());
			event.setCity(city);
			
			event = repository.save(event);
			return new EventDTO(event);
			
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Entity not found for id " + id);
		}
		
		
		
	}
}
