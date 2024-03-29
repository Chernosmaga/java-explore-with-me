package ru.practicum.statistics.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.EndpointHitDto;
import ru.practicum.ViewStatsDto;
import ru.practicum.statistics.client.BaseClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StatService extends BaseClient {
    private static final String API_PREFIX = "/";

    @Autowired
    public StatService(@Value("${stats-service.url}") String serverUrl,
                       RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public EndpointHitDto post(@RequestBody EndpointHitDto endpoint) {
        ResponseEntity<EndpointHitDto> responseEntity = this.post("/hit", endpoint, new EndpointHitDto());
        return responseEntity.getBody();
    }

    public List<ViewStatsDto> get(LocalDateTime start, LocalDateTime end, String[] uris, String unique) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> map = Map.of(
                "start", start.format(formatter),
                "end", end.format(formatter),
                "uris", uris,
                "unique", unique

        );
        ResponseEntity<ArrayList<ViewStatsDto>> responseEntity =
                this.get("/stats?start={start}&end={end}&uris={uris}&uniq={unique}", map, new ArrayList<>());
        return responseEntity.getBody();
    }
}