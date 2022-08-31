package com.example.demo.wa1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity()
@Table(name = "usr")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private Integer id;
    private String name;
    private String doc;
    private LocalDateTime createdTimestamp;
    private LocalDateTime updatedTimestamp;

}
