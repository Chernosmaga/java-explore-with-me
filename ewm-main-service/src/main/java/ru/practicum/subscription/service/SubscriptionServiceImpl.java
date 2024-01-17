package ru.practicum.subscription.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.exception.DataViolationException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.subscription.dto.SubscriptionDto;
import ru.practicum.subscription.mapper.SubscriptionMapper;
import ru.practicum.subscription.model.Subscription;
import ru.practicum.subscription.repository.SubscriptionRepository;
import ru.practicum.user.dto.UserShortDto;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final UserMapper userMapper;

    @Override
    public SubscriptionDto follow(Long userId, Long followerId) {
        log.debug("follow({}, {})", userId, followerId);
        User user = userSearch(userId);
        User follower = userSearch(followerId);
        if (subscriptionRepository.existsByUserAndFollower(user, follower)) {
            throw new DataViolationException("Вы уже подписаны на пользователя");
        }
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setFollower(follower);
        subscription.setDate(LocalDateTime.now());
        Subscription savedSubscription = subscriptionRepository.save(subscription);
        log.info("Пользователь: {} подписался на пользователя: {}", follower, user);
        return subscriptionMapper.toSubscriptionDto(savedSubscription);
    }

    @Override
    public void unfollow(Long userId, Long followerId) {
        log.debug("unfollow({}, {})", userId, followerId);
        User user = userSearch(userId);
        User follower = userSearch(followerId);
        if (!subscriptionRepository.existsByUserAndFollower(user, follower)) {
            throw new DataViolationException("Вы не подписаны на пользователя");
        }
        Subscription subscription = subscriptionRepository.findByUserAndFollower(user, follower);
        subscriptionRepository.delete(subscription);
        log.info("Пользователь: {} отписался от пользователя: {}", follower, user);
    }

    @Override
    public List<UserShortDto> getSubscriptions(Long userId, int from, int size) {
        log.debug("getSubscriptions({}, {}, {})", userId, from, size);
        User user = userSearch(userId);
        PageRequest page = PageRequest.of(from, size, DESC, "date");
        List<Subscription> subscriptions = subscriptionRepository.findAllByFollower(user, page)
                .stream().collect(Collectors.toList());
        List<User> users = new LinkedList<>();
        for (Subscription subscription: subscriptions) {
            users.add(subscription.getUser());
        }
        log.info("По запросу пользователя, возвращён список подписок: {}", users);
        return users.stream().map(userMapper::toUserShortDto).collect(Collectors.toList());
    }

    @Override
    public List<UserShortDto> getSubscribers(Long userId, int from, int size) {
        log.debug("getSubscribers({}, {}, {})", userId, from, size);
        User user = userSearch(userId);
        PageRequest page = PageRequest.of(from, size, DESC, "date");
        List<Subscription> subscriptions = subscriptionRepository.findAllByUser(user, page)
                .stream().collect(Collectors.toList());
        List<User> users = new LinkedList<>();
        for (Subscription subscription: subscriptions) {
            users.add(subscription.getFollower());
        }
        log.info("По запросу пользователя возвращён список подписчиков: {}", users);
        return users.stream().map(userMapper::toUserShortDto).collect(Collectors.toList());
    }

    private User userSearch(Long userId) {
        log.debug("userSearch({})", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        log.info("Пользователь найден: {}", user);
        return user;
    }
}
