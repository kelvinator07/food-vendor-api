package com.byteworks.service;

import com.byteworks.model.Meal;
import com.byteworks.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository mealRepository;

    @Override
    public List<Meal> findAllAvailableMeals() {
        return mealRepository.findAll();
    }
}
