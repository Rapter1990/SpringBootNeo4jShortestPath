package com.springshortpath.app.payload.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PathRequest {

    private String from;
    private String destination;

}
