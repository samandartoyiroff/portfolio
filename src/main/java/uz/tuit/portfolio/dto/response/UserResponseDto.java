package uz.tuit.portfolio.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.tuit.portfolio.model.UserStatus;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto extends AuditableResponseDto {

    private Long id;

    private String username;

    private String fullName;

    private String email;

    private String phoneNumber;

    private Set<String> roles;

    private Set<String> permissions;

    private UserStatus status;

    private Long cvId;

    private String imageUrl;

    private boolean isverified;


}
