package org.example.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Booking {

    private Long id;
    private Long workSpaceId;
    private LocalDate startDate;
    private LocalDate endDate;

}
