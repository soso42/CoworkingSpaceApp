package org.example.entity;

import lombok.*;
import org.example.enums.WorkSpaceType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkSpace {

    private Long id;
    private WorkSpaceType type;
    private Integer price;
    private Boolean available = true;

    @Override
    public String toString() {
        return "WorkSpace: " +
                "id=" + id +
                ", type=" + type +
                ", price=" + price;
    }
}
