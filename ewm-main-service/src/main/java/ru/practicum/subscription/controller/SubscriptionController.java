package ru.practicum.subscription.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.subscription.dto.SubscriptionDto;
import ru.practicum.subscription.service.SubscriptionService;
import ru.practicum.user.dto.UserShortDto;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/users/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService service;

    @PostMapping
    @ResponseStatus(CREATED)
    public SubscriptionDto follow(@RequestParam Long userId, @RequestParam Long followerId) {
        return service.follow(userId, followerId);
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    public void unfollow(@RequestParam Long userId, @RequestParam Long followerId) {
        service.unfollow(userId, followerId);
    }

    @GetMapping
    public List<UserShortDto> getSubscriptions(@RequestParam Long userId,
                                               @RequestParam(required = false, defaultValue = "0") int from,
                                               @RequestParam(required = false, defaultValue = "10") int size) {
        return service.getSubscriptions(userId, from, size);
    }

    @GetMapping("/{userId}")
    public List<UserShortDto> getSubscribers(@PathVariable Long userId,
                                             @RequestParam(required = false, defaultValue = "0") int from,
                                             @RequestParam(required = false, defaultValue = "10") int size) {
        return service.getSubscribers(userId, from, size);
    }
}
