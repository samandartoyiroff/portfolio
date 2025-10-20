package uz.tuit.portfolio.util;

import org.springframework.stereotype.Component;
import uz.tuit.portfolio.model.Permission;


import java.util.HashSet;
import java.util.Set;

@Component
public class PermissionUtil {

    public Set<Permission> permissionForAdmin(){

        Set<Permission> permissions = new HashSet<>();

        //ADMIN
        permissions.add(Permission.ADMIN_READ);
        permissions.add(Permission.ADMIN_UPDATE);



        return permissions;

    }

     public Set<Permission> permissionForSuperAdmin(){

        Set<Permission> permissions = new HashSet<>();

        //ADMIN
        permissions.add(Permission.ADMIN_CREATE);
        permissions.add(Permission.ADMIN_DELETE);
        permissions.add(Permission.ADMIN_UPDATE);
        permissions.add(Permission.ADMIN_READ);

        return permissions;

    }


    public Set<Permission> permissionForUser() {

        Set<Permission> permissions = new HashSet<>();



        //STUDENT



        return permissions;

    }
}
