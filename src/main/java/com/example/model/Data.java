package com.example.model;

import lombok.AllArgsConstructor;

import java.util.List;

@lombok.Data
@AllArgsConstructor
public class Data {

    private int time_get;

    private int time_send;

    private int time_work;

    private List<String> list;
}
