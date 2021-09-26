package com.iuh.IUHStudent.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String email;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "Image",
            joinColumns = @JoinColumn(name = "user_id")
    )
    private List<Image> images = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private Account account;


}
