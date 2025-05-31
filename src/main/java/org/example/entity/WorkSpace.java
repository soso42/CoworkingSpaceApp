package org.example.entity;

import lombok.*;
import org.example.enums.WorkSpaceType;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkSpace implements Serializable {

    @Serial
    private static final long serialVersionUID = 10101L;

    private Long id;
    private WorkSpaceType type;
    private Double price;
    private Boolean available = true;

    @Override
    public String toString() {
        return "WorkSpace: " +
                "id=" + id +
                ", type=" + type +
                ", price=" + price;
    }
}
