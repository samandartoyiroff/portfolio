package uz.tuit.portfolio.domain;


import jakarta.persistence.*;
import lombok.*;
import uz.tuit.portfolio.model.JobType;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;

    private String position;

    private String address;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @ManyToOne
    @JoinColumn(name = "cv_id")
    private CV cv;

}
