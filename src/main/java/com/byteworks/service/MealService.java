package com.byteworks.service;

import com.byteworks.model.Meal;

import java.util.List;

public interface MealService {

    List<Meal> findAllAvailableMeals();
}
