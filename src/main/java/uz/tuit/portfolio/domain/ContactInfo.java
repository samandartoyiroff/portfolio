package uz.tuit.portfolio.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class ContactInfo {

    private String contactEmail;

    private String contactPhoneNumber;

    private String telegramUsername;

    private String instagramUsername;

    private String facebookLink;

    private String twitterLink;

    private String linkedinLink;

    private String leetcodeLink;

    private String headHunterLink;

}
