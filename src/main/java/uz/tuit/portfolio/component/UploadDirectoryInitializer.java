package uz.tuit.portfolio.component;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class UploadDirectoryInitializer {

    private static final String ROOT_DIR = "uploads";
    private static final String[] SUB_DIRS = {"files", "images", "static"};

    @PostConstruct
    public void init() {
        File root = new File(ROOT_DIR);

        if (!root.exists()) {
            root.mkdirs();
            System.out.println("Created: " + root.getAbsolutePath());
        }

        for (String dir : SUB_DIRS) {
            File subDir = new File(root, dir);
            if (!subDir.exists()) {
                subDir.mkdirs();
                System.out.println("Created: " + subDir.getAbsolutePath());
            }
        }
    }
}
