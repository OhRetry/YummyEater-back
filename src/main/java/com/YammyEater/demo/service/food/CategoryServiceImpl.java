package com.YammyEater.demo.service.food;

import com.YammyEater.demo.domain.food.Category;
import com.YammyEater.demo.dto.food.CategoryDto;
import com.YammyEater.demo.repository.food.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryDto::of).toList();
    }
}
