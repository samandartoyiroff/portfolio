package uz.tuit.portfolio.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.tuit.portfolio.domain.Role;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.model.RoleName;
import uz.tuit.portfolio.repository.RoleRepository;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RoleUtil {

    private final RoleRepository roleRepository;

    public Set<Role> rolesForSuperAdmin(){

        Role admin = roleRepository.findByName(RoleName.ROLE_ADMIN);
        Role superadmin = roleRepository.findByName(RoleName.ROLE_SUPERADMIN);


        return Set.of(admin,superadmin);

    }



    public Set<Role> rolesForAdmin(){

        Role admin = roleRepository.findByName(RoleName.ROLE_ADMIN);

        return Set.of(admin);

    }



    public Set<Role> customRoles(Set<Long> roleIds){

        Set<Role> roles = new HashSet<>();
        roleIds.forEach(roleId -> {
            roles.add(roleRepository.findById(roleId).orElse(null));
        });
        return roles;

    }



    public boolean isUser(User user){

        for (Role role : user.getRoles()) {
            if (role.getName().equals(RoleName.ROLE_SUPERADMIN) || role.getName().equals(RoleName.ROLE_ADMIN)) return  false;
        }
        return true;

    }

     public boolean isAdmin(User user){

        for (Role role : user.getRoles()) {
            if (role.getName().equals(RoleName.ROLE_SUPERADMIN) || role.getName().equals(RoleName.ROLE_ADMIN)) return  true;
        }
        return false;

    }


    public Set<Role> rolesForUser() {

        Role admin = roleRepository.findByName(RoleName.ROLE_USER);

        return Set.of(admin);

    }

    public boolean isOnlyAdmin(User user){

        int i = 0;

        for (Role role : user.getRoles()) {
            if (role.getName().equals(RoleName.ROLE_SUPERADMIN)) return false;
            if (role.getName().equals(RoleName.ROLE_ADMIN)) i++;
        }
        return i==1;

    }

     public boolean isOnlySuperAdmin(User user){

        for (Role role : user.getRoles()) {
            if (role.getName().equals(RoleName.ROLE_SUPERADMIN)) return true;
        }
        return false;

    }



}
