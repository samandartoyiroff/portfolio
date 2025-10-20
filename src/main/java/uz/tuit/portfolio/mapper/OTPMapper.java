package uz.tuit.portfolio.mapper;

import org.springframework.stereotype.Component;
import uz.tuit.portfolio.domain.OTPCode;
import uz.tuit.portfolio.dto.response.OTPDTO;

@Component
public class OTPMapper {

    public OTPDTO toDto(OTPCode otpCode){
        OTPDTO otpDTO = new OTPDTO();
        otpDTO.setId(otpCode.getId());
        otpDTO.setEmail(otpCode.getEmail());
        otpDTO.setExpirationDate(otpCode.getExpirationDate());
        return otpDTO;
    }

}
