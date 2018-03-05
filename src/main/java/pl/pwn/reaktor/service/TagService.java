package pl.pwn.reaktor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.pwn.reaktor.model.Tag;
import pl.pwn.reaktor.repository.TagRepository;

@Service
public class TagService {
	
	@Autowired
	private TagRepository tagRepository;
	
	public List<Tag>listAllTags(){
		return tagRepository.findAll();
	}

}
