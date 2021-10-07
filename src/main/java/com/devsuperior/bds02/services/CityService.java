package com.devsuperior.bds02.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class CityService {

	@Autowired
	private CityRepository repository;

	@Transactional
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Entity not found for id " + id);
		}
	}

	@Transactional(readOnly = true)
	public List<CityDTO> findAll() {
		var cities = repository.findAll(Sort.by("name"));
		return cities.stream().map(city -> new CityDTO(city)).collect(Collectors.toList());
	}

	@Transactional
	public CityDTO insert(CityDTO dto) {
		City city = new City(null, dto.getName());
		city = repository.save(city);
		return new CityDTO(city);
	}
	
}
