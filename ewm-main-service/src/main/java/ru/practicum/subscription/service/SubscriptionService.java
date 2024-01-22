package ru.practicum.subscription.service;

import ru.practicum.subscription.dto.SubscriptionDto;
import ru.practicum.user.dto.UserShortDto;

import java.util.List;

public interface SubscriptionService {
    SubscriptionDto follow(Long userId, Long followerId);

    void unfollow(Long userId, Long followerId);

    List<UserShortDto> getSubscriptions(Long userId, int from, int size);

    List<UserShortDto> getSubscribers(Long userId, int from, int size);
}
