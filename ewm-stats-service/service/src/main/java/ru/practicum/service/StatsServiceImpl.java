package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.EndpointHitDto;
import ru.practicum.ViewStatsDto;
import ru.practicum.exception.DateTimeFormatException;
import ru.practicum.mapper.EndpointHitMapper;
import ru.practicum.mapper.ViewStatsMapper;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.ViewStats;
import ru.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;
    private final EndpointHitMapper endpointHitMapper;
    private final ViewStatsMapper viewStatsMapper;

    @Override
    public EndpointHitDto send(EndpointHitDto endpointHit) {
        log.debug("send({})", endpointHit);
        EndpointHit data = endpointHitMapper.toEndpointHit(endpointHit);
        EndpointHit endpoint = statsRepository.save(data);
        log.info("Сохранена информация по запросу в сервис: {}", endpoint);
        return endpointHitMapper.toEndpointHitDto(endpoint);
    }

    @Override
    public List<ViewStatsDto> receive(LocalDateTime start, LocalDateTime end, String[] uris, Boolean isUnique) {
        log.debug("receive({}, {}, {}, {})", start, end, uris, isUnique);
        if (start.isAfter(end)) {
            throw new DateTimeFormatException("Неверно указаны данные даты и времени");
        }
        List<ViewStats> views;
        if (isUnique) {
            if (uris != null) {
                views = statsRepository.getDistinctByUris(uris, start, end);
            } else {
                views = statsRepository.getDistinctByStartAndEnd(start, end);
            }
        } else {
            if (uris != null) {
                views = statsRepository.getByUris(uris, start, end);
            } else {
                views = statsRepository.getByStartAndEnd(start, end);
            }
        }
        List<ViewStatsDto> statistics = views.stream()
                .map(viewStatsMapper::toViewStatsDto).collect(Collectors.toList());
        log.info("Возращена статистика по просмотрам: {}", statistics);
        return statistics;
    }
}
