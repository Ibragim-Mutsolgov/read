package com.example.read.model;

import lombok.AllArgsConstructor;

import java.util.List;

@lombok.Data
@AllArgsConstructor
public class Data {

    private long time_get;

    private long time_send;

    private long time_work;

    private List<String> list;
}
