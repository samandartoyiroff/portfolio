package uz.tuit.portfolio.dto.response;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponseDto {

    private UserResponseDto user;

    private OTPDTO otp;

}
