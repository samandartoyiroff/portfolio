package uz.tuit.portfolio.domain;


import jakarta.persistence.*;
import lombok.*;
import uz.tuit.portfolio.model.HardSkillDegree;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HardSkill extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private HardSkillDegree degree;


}
