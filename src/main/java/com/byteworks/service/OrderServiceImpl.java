package com.byteworks.service;

import com.byteworks.dto.OrderDto;
import com.byteworks.exception.MealNotFoundException;
import com.byteworks.exception.OrderNotFoundException;
import com.byteworks.exception.UserNotFoundException;
import com.byteworks.model.Meal;
import com.byteworks.model.Order;
import com.byteworks.model.User;
import com.byteworks.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @author Kelvin
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

    private static final double USER_LAT = 3.072489;
    private static final double USER_LONG = 1.440987;
    private static final double VENDOR_LAT = 3.071101;
    private static final double VENDOR_LONG = 1.441481;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MealService mealService;

    @Autowired
    private EmailService emailService;

    @Override
    public Order findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);

        if (!order.isPresent()) {
            throw new OrderNotFoundException("Order Not Found for id: " + id);
        }

        return order.get();

    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public String validateOrder(OrderDto orderDto) {

        String validationMessage = null;

        if (orderDto.getMeals().length() < 1) {
            validationMessage = "Order must contain an least one meal";
        }

        String meals = orderDto.getMeals();
        List<Meal> userMeals = new ArrayList<>();
        List<Meal> availableMeals = mealService.findAllAvailableMeals();

        if (availableMeals != null) {
            for (Meal meal: availableMeals) {
                if(meals.trim().toLowerCase().contains(meal.getName().toLowerCase())) {
                    userMeals.add(meal);
                }
            }
        }

        if (userMeals.size() < 1) {
            validationMessage = "Meal does not exist, Please try again.";
        }

        return validationMessage;
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order processOrder(OrderDto orderDto) {

        final double DISCOUNT = 0.025;

        Order newOrder = new Order();

        User user = new User();

        Boolean cardPayment = orderDto.getCardPayment();
        Boolean officeDelivery = orderDto.getOfficeDelivery();

        //List<String> meals = Arrays.asList(orderDto.getMeals().split(","));
        String meals = orderDto.getMeals();
        List<Meal> userMeals = new ArrayList<>();
        List<Meal> availableMeals = mealService.findAllAvailableMeals();

        if (availableMeals != null) {
            for (Meal meal: availableMeals) {
                if(meals.trim().toLowerCase().contains(meal.getName().toLowerCase())) {
                    userMeals.add(meal);
                }
            }
        }

        double total = getTotalMealPrice(userMeals);

        if (cardPayment) {
            total *= 1 - DISCOUNT;
            newOrder.setOrderTotal(total);
        } else {
            newOrder.setOrderTotal(total);
        }

        if (officeDelivery) {
            double distance = BigDecimal.valueOf(getTotalDistance(USER_LAT, VENDOR_LAT, USER_LONG, VENDOR_LONG, 1.0, 1.0)).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
            total += 10 * distance;
            newOrder.setOrderTotal(total);
        }

        newOrder.setUser(userService.findById(1L));
        newOrder.setMeals(userMeals);
        newOrder.setCardPayment(cardPayment);
        newOrder.setOfficeDelivery(officeDelivery);
        newOrder.setShippingAddress(orderDto.getShippingAddress());
        newOrder.setOrderDate(new Date());

        String paymentType = newOrder.getCardPayment() ? "Card Payment" : "Payment on Delivery";
        String deliveryType = newOrder.getOfficeDelivery() ? "Office Delivery: " + newOrder.getShippingAddress() : "Pickup Delivery";

        String emailSubject = "New Meal Order";
        String vendorEmail = "isievwore.kelvin@gmail.com";
        String emailBody = "You have a new meal order from " + newOrder.getUser().getFirstName() + " " + newOrder.getUser().getLastName() +
                "\n Order Details: \n Meals: " + newOrder.getMeals().toString() +
                "\n Payment Type: " + paymentType +
                "\n Delivery Type: " + deliveryType +
                "\n Total Order Cost : #" + newOrder.getOrderTotal();

        emailService.sendEmail(vendorEmail, emailSubject, emailBody);

        return orderRepository.save(newOrder);

    }

    private double getTotalMealPrice(List<Meal> meals) {
        double total = 0.0;
        for (Meal meal: meals) {
            total += meal.getPrice();
        }
        return total;
    }

    private double getTotalDistance(double lat1, double lat2, double lon1, double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

}
