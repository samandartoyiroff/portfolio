package uz.tuit.portfolio.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Project extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "cv_id")
    @ToString.Exclude
    private CV cv;


}
