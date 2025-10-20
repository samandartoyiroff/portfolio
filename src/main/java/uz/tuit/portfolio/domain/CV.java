package uz.tuit.portfolio.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CV extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String email;

    private String phoneNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    private Occupation occupation;

    @OneToOne(cascade = CascadeType.ALL)
    private Image cvPhoto;

    @Embedded
    private Address address;

    @Column(columnDefinition = "TEXT")
    private String aboutMe;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_hobbies", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "hobbies")
    private List<String> hobbies;

    @Embedded
    private ContactInfo contactInfo;

    @OneToMany(orphanRemoval = true, mappedBy = "cv", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Experience> experiences;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<HardSkill> hardSkills; // max 30

    @ManyToMany(fetch = FetchType.EAGER)
    private List<SoftSkill> softSkills;  // Max 10

    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Education> educations;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Technology> technologies; // Max 15

    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL,  fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Certificate> certificates;

    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL,  fetch = FetchType.EAGER, orphanRemoval = true)
    private List<LanguageSkill> languageSkills;

    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL,  fetch = FetchType.EAGER, orphanRemoval = true)
    @ToString.Exclude
    private List<Project> projects;

    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL,  fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Feedback> feedbacks;

    private String driverLicense;

}
