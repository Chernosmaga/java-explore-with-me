package ru.practicum.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.category.model.Category;
import ru.practicum.event.model.Event;
import ru.practicum.user.model.User;

public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {
    Page<Event> findAllByInitiator(User initiator, Pageable page);

    boolean existsByCategory(Category category);
}
