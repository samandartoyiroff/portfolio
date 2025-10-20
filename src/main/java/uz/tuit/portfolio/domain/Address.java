package uz.tuit.portfolio.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Setter
@Getter
public class Address {

    private String address;

    private String cityTown;

    private String zipCode;

}
