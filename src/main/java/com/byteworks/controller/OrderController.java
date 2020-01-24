package com.byteworks.controller;

import com.byteworks.response.OrderResponse;
import com.byteworks.dto.OrderDto;
import com.byteworks.model.Meal;
import com.byteworks.model.Order;
import com.byteworks.service.MealService;
import com.byteworks.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kelvin
 *
 */
@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private MealService mealService;


    @ApiOperation(value = "Fetch all Available Meals", response = List.class)
    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public List<Meal> getAllMeals() {

        return mealService.findAllAvailableMeals();

    }

    @ApiOperation(value = "Make an order", response = String.class)
    @RequestMapping(value = "/orders", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> makeOrder(@RequestBody OrderDto orderDto) {

        OrderResponse orderResponse = new OrderResponse();

        String validationMessage = orderService.validateOrder(orderDto);
        if (validationMessage != null) {
            orderResponse.setId(null);
            orderResponse.setResponseMessage(validationMessage);
            orderResponse.setTotalOrderCost(null);
            return new ResponseEntity<>(orderResponse, HttpStatus.BAD_REQUEST);
        }

        Order newOrder = orderService.processOrder(orderDto);

        orderResponse.setId(newOrder.getId());
        orderResponse.setResponseMessage("Your Order Was Successful, Meal Will Arrive in 30 Mins");
        orderResponse.setTotalOrderCost("Your Order Cost is #" + newOrder.getOrderTotal());

        return new ResponseEntity<>(orderResponse, HttpStatus.OK);

    }

    @ApiOperation(value = "Fetch all Orders", response = List.class)
    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public List<Order> getAllOrders() {

        return orderService.findAllOrders();

    }


    @ApiOperation(value = "Fetch Order By ID", response = Order.class)
    @RequestMapping(value = "/orders/{id}", method = RequestMethod.GET)
    public Order getOrderById(@PathVariable Long id) {

        return orderService.findById(id);

    }

}
