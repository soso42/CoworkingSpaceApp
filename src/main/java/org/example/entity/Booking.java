package org.example.entity;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Booking implements Serializable {

    @Serial
    private static final long serialVersionUID = 12345L;

    private Long id;
    private Long workSpaceId;
    private LocalDate startDate;
    private LocalDate endDate;

}
