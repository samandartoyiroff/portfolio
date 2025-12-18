package uz.tuit.portfolio.domain;

import jakarta.persistence.*;
import lombok.*;
import uz.tuit.portfolio.model.Gender;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String occupation;

    @OneToOne(cascade = CascadeType.ALL)
    private Image portifolioImage;

    @Embedded
    private Address address;

    @Column(columnDefinition = "TEXT")
    private String aboutMe;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "portfolio_hobbies", joinColumns = @JoinColumn(name = "portfolio_id"))
    @Column(name = "hobby")
    private List<String> hobbies;

    @Embedded
    private ContactInfo contactInfo;

    @OneToMany(orphanRemoval = true, mappedBy = "portfolio", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Experience> experiences;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<HardSkill> hardSkills; // max 30

    @OneToMany(fetch = FetchType.EAGER)
    private List<SoftSkill> softSkills;  // Max 10

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Education> educations;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Technology> technologies; // Max 15

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL,  fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Certificate> certificates;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL,  fetch = FetchType.EAGER, orphanRemoval = true)
    private List<LanguageSkill> languageSkills;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL,  fetch = FetchType.EAGER, orphanRemoval = true)
    @ToString.Exclude
    private List<Project> projects;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL,  fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Feedback> feedbacks;

    private String driverLicense;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String template;

}
