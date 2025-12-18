package uz.tuit.portfolio.util;

import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.tuit.portfolio.component.Data;
import uz.tuit.portfolio.domain.*;
import uz.tuit.portfolio.model.Gender;
import uz.tuit.portfolio.model.RoleName;
import uz.tuit.portfolio.model.UserStatus;
import uz.tuit.portfolio.repository.*;

@Component
@RequiredArgsConstructor
public class GenerateDataUtil {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleUtil roleUtil;
    private final PermissionUtil permissionUtil;
    private final AdminRepository adminRepository;
    private final LanguageRepository languageRepository;
    private final TechnologyRepository technologyRepository;
    private final HardSkillRepository hardSkillRepository;
    private final SoftSkillRepository softSkillRepository;
    private final PortfolioRepository portfolioRepository;

    public void generateSuperAdmin(){
        User userForSuperAdmin = new User();
        userForSuperAdmin.setVerified(true);
        userForSuperAdmin.setStatus(UserStatus.ACTIVE);
        userForSuperAdmin.setUsername("superadmin01");
        userForSuperAdmin.setPassword(passwordEncoder.encode("superadmin01"));
        userForSuperAdmin.setRoles(
                roleUtil.rolesForSuperAdmin()
        );
        userForSuperAdmin.setPermissions(
                permissionUtil.permissionForSuperAdmin()
        );
        userForSuperAdmin.setEmail("samandartoyirov021@gmail.com");
        userForSuperAdmin.setFullName("Toyirov Samandar Turg'un o'g'li");
        userRepository.save(userForSuperAdmin);

        Admin admin = new Admin();
        admin.setUser(userForSuperAdmin);
        adminRepository.save(admin);
    }

    public void generateRoles(){

        Role superadminRole = new Role();
        superadminRole.setName(RoleName.ROLE_SUPERADMIN);
        superadminRole.setDescription("Super Admin");
        roleRepository.save(superadminRole);

        Role adminRole = new Role();
        adminRole.setName(RoleName.ROLE_ADMIN);
        adminRole.setDescription("Admin");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setName(RoleName.ROLE_USER);
        userRole.setDescription("User");
        roleRepository.save(userRole);

    }

    public void generateAdmin() {

        User userForAdmin = new User();
        userForAdmin.setVerified(true);
        userForAdmin.setStatus(UserStatus.ACTIVE);
        userForAdmin.setUsername("admin01");
        userForAdmin.setPassword(passwordEncoder.encode("admin01"));
        userForAdmin.setRoles(
                roleUtil.rolesForAdmin()
        );
        userForAdmin.setPermissions(
                permissionUtil.permissionForAdmin()
        );
        userForAdmin.setEmail("toyirovsamandar645@gmail.com");
        userForAdmin.setFullName("Toyirov Samandar Turg'un o'g'li");
        userRepository.save(userForAdmin);

        Admin admin = new Admin();
        admin.setUser(userForAdmin);
        adminRepository.save(admin);

    }

    public void generateLanguage(){

        for (String language : Data.LANGUAGES) {
            Language lang = new Language();
            lang.setName(language);
            languageRepository.save(lang);
        }

    }


    public void generateTechnology() {

        for (String technology : Data.TECHNOLOGIES) {

            if (technologyRepository.findByName(technology).isEmpty()){
                Technology tech = new Technology();
                tech.setName(technology);
                technologyRepository.save(tech);
            }

        }

    }

    public void generateHardSkills() {

        for (String hardSkill : Data.HARD_SKILLS) {

            if (hardSkillRepository.findByName(hardSkill).isEmpty()) {
                HardSkill skill = new HardSkill();
                skill.setName(hardSkill);
                hardSkillRepository.save(skill);
            }


        }

    }

    public void generateSoftSkills() {


        for (String softSkill : Data.SOFT_SKILLS) {

            SoftSkill skill = new SoftSkill();
            skill.setName(softSkill);
            softSkillRepository.save(skill);
        }

    }

    public void generateUsers() {

        Faker faker = new Faker();

        for (int i = 0; i < 20; i++) {

            Portfolio portfolio = new Portfolio();

            User user = new User();
            user.setVerified(true);
            user.setUsername("user0" + i);
            user.setEmail("user0" + i + "@gmail.com");
            user.setPassword(passwordEncoder.encode("user0" + i));
            user.setFullName(faker.name().fullName());
            user.setGender(Gender.values()[faker.random().nextInt(Gender.values().length)]);
            user.setStatus(UserStatus.ACTIVE);
            user.setRoles(roleUtil.rolesForUser());
            user.setPermissions(permissionUtil.permissionForUser());
            user.setPortfolio(portfolio);
            userRepository.save(user);


        }

    }
}
