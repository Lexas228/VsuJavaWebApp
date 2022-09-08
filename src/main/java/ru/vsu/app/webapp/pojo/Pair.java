package ru.vsu.app.webapp.pojo;

import lombok.Data;

@Data
public class Pair <K, V>{
    private final K first;
    private final V second;
}
