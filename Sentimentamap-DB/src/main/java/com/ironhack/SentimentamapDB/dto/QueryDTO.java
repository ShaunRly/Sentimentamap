package com.ironhack.SentimentamapDB.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class QueryDTO {

    public String rule;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    public LocalDateTime dateTimeStart;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    public LocalDateTime dateTimeEnd;


    public QueryDTO(String rule, String dateTimeStart, String dateTimeEnd) {
        this.rule = rule;
        this.dateTimeStart = LocalDateTime.parse(dateTimeStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.dateTimeEnd = LocalDateTime.parse(dateTimeEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public QueryDTO(String dateTimeStart, String dateTimeEnd) {
        this.rule = "NAN";
        this.dateTimeStart = LocalDateTime.parse(dateTimeStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.dateTimeEnd = LocalDateTime.parse(dateTimeEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
