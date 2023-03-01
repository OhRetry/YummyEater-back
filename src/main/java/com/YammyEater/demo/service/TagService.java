package com.YammyEater.demo.service;

import com.YammyEater.demo.domain.Tag;
import com.YammyEater.demo.dto.TagDto;
import com.YammyEater.demo.repository.TagRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    @Autowired
    TagRepository tagRepository;

    public List<TagDto> getAllTag() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream().map(TagDto::of).toList();
    }
}
