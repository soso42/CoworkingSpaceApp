package org.example.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    private Long id;
    private WorkSpace workSpace;
    private LocalDate startDate;
    private LocalDate endDate;

    public boolean overlaps(LocalDate startDate, LocalDate endDate) {
        return !this.startDate.isAfter(endDate) && !this.endDate.isBefore(startDate);
    }

}
