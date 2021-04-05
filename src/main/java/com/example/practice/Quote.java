package com.example.practice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {

    private String type;
    private Value value;

    public Quote() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "type='" + type + '\'' +
                ", value=" + value +
                '}';
    }
}

// To directly bind your data to your custom types, you need to specify the variable name to be exactly the same as the
// key in the JSON document returned by hte API. If you need to be sure, or don't know specifically,
// you can use @JsonProperty annotaion to specify the exact key of the JSON document.