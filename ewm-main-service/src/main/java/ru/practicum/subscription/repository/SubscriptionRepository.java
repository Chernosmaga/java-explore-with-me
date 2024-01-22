package ru.practicum.subscription.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.subscription.model.Subscription;
import ru.practicum.user.model.User;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    boolean existsByUserAndFollower(User user, User follower);

    Subscription findByUserAndFollower(User user, User follower);

    Page<Subscription> findAllByFollower(User follower, Pageable page);

    Page<Subscription> findAllByUser(User user, Pageable page);
}
