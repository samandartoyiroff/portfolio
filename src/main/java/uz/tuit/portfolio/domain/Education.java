package uz.tuit.portfolio.domain;


import jakarta.persistence.*;
import lombok.*;
import uz.tuit.portfolio.model.EducationType;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Education extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private EducationType educationType;

    @OneToOne(cascade = CascadeType.ALL)
    private File certificate;

    @ManyToOne
    @JoinColumn(name = "cv_id")
    private CV cv;


}
