package ru.practicum.subscription.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.subscription.dto.SubscriptionDto;
import ru.practicum.subscription.model.Subscription;
import ru.practicum.user.mapper.UserMapper;

@Component
@RequiredArgsConstructor
public class SubscriptionMapper {
    private final UserMapper userMapper;

    public SubscriptionDto toSubscriptionDto(Subscription subscription) {
        return new SubscriptionDto(
                subscription.getId(),
                userMapper.toUserShortDto(subscription.getUser()),
                userMapper.toUserShortDto(subscription.getFollower()));
    }
}
