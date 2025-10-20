package uz.tuit.portfolio.domain;

import jakarta.persistence.*;
import lombok.*;
import uz.tuit.portfolio.model.LangLevel;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LanguageSkill extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Language language;

    @Enumerated(EnumType.STRING)
    private LangLevel level;

    @ManyToOne
    @JoinColumn(name = "cv_id")
    private CV cv;

}
