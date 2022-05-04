package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.Entities;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public abstract class Controllers<T extends Entities> {
    protected final Map<Integer, T> someMap = new HashMap<>();
    int id = 0;

    @PostMapping()
    public void create(@Valid @RequestBody T entity) {
        someMap.put(entity.getId(), entity);
        log.info("field with type {} and id={} added.", entity.getClass(), entity.getId());
    }

    @PutMapping()
    public void update(@Valid @RequestBody T entity) {
        someMap.put(entity.getId(), entity);
        log.info("field  with id={} updated: {}", entity.getId(), entity.getClass());
    }

    @GetMapping()
    public Collection<T> list() {
        log.info("current number of {}:", someMap.size());
        return someMap.values();
    }
}
