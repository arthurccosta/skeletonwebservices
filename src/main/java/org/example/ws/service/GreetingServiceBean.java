package org.example.ws.service;

import java.util.Collection;

import org.example.ws.model.Greeting;
import org.example.ws.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(
		propagation = Propagation.SUPPORTS,
		readOnly = true)
public class GreetingServiceBean implements GreetingService {

	@Autowired
	private GreetingRepository greetingRepository;

	@Autowired
	private CounterService counterService;
	
	@Override
	public Collection<Greeting> findAll() {
		counterService.increment("method.invoke.greetingServiceBean.findAll");
		Collection<Greeting> greetings = greetingRepository.findAll();
		return greetings;
	}

	@Override
	@Cacheable(
			value = "greetings",
			key = "#id")
	public Greeting findOne(Long id) {
		counterService.increment("method.invoke.greetingServiceBean.findOne");
		Greeting greeting = greetingRepository.findOne(id);
		return greeting;
	}

	@Override
	@Transactional(
			propagation = Propagation.REQUIRED,
			readOnly = false)
	@CachePut(
			value = "greetings",
			key = "#result.id")
	public Greeting create(Greeting greeting) {
		counterService.increment("method.invoke.greetingServiceBean.create");
		if(greeting.getId() != null){
			//Não é possível criar com este id
			return null;
		}
		Greeting savedGreeting = greetingRepository.save(greeting);
		if(savedGreeting.getId() == 4L){
			throw new RuntimeException("Roll me back");
		}
		return savedGreeting;
	}

	@Override
	@Transactional(
			propagation = Propagation.REQUIRED,
			readOnly = false)
	@CachePut(
			value = "greetings",
			key = "#greeting.id")
	public Greeting update(Greeting greeting) {
		counterService.increment("method.invoke.greetingServiceBean.update");
		Greeting greetingPersisted = findOne(greeting.getId());
		if(greetingPersisted == null){
			//não é possivel fazer update
			return null;
		}
		Greeting updateGreeting = greetingRepository.save(greeting);
		return updateGreeting;
	}

	@Override
	@Transactional(
			propagation = Propagation.REQUIRED,
			readOnly = false)
	@CacheEvict(
			value = "greetings",
			key = "#id")
	public void delete(Long id) {
		counterService.increment("method.invoke.greetingServiceBean.delete");
		greetingRepository.delete(id);
	}

	@Override
	@CacheEvict(
			value = "greetings",
			allEntries = true)
	public void evictCache() {
	
		
	}
}
