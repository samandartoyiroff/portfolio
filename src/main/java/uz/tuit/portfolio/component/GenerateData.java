package uz.tuit.portfolio.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.tuit.portfolio.util.GenerateDataUtil;


@Component
@RequiredArgsConstructor
public class GenerateData implements CommandLineRunner {



    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    private final GenerateDataUtil generateDataUtil;

    @Override
    public void run(String... args) throws Exception {

        if (ddl.equals("create")) {

            generateDataUtil.generateRoles();

            generateDataUtil.generateSuperAdmin();

            generateDataUtil.generateAdmin();

            generateDataUtil.generateLanguage();

            generateDataUtil.generateTechnology();

            generateDataUtil.generateHardSkills();

            generateDataUtil.generateOccupation();

            generateDataUtil.generateSoftSkills();

        }

    }

}
