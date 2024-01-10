package ru.practicum.compilation.service;

import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {
    CompilationDto create(NewCompilationDto compilation);

    CompilationDto update(Long compId, UpdateCompilationRequest compilation);

    void delete(Long compId);

    List<CompilationDto> getAll(Boolean pinned, int from, int size);

    CompilationDto getById(Long compId);
}
