package ru.practicum.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.category.model.Category;
import ru.practicum.event.model.Event;
import ru.practicum.user.model.User;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {
    Page<Event> findAllByInitiator(User initiator, Pageable page);

    boolean existsByCategory(Category category);

    @Query(value = "select * from events as e where e.confirmed_requests < e.participant_limit " +
            "or e.participant_limit = 0 and e.user_id in (?1)", nativeQuery = true)
    Page<Event> findAvailableToParticipateEvents(List<Long> users, Pageable page);

    Page<Event> findEventByInitiatorIdIsIn(List<Long> users, Pageable page);
}