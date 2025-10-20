package uz.tuit.portfolio.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.tuit.portfolio.domain.CV;
import uz.tuit.portfolio.domain.Project;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.ProjectCreateDto;
import uz.tuit.portfolio.dto.request.ProjectUpdateDto;
import uz.tuit.portfolio.mapper.ProjectMapper;
import uz.tuit.portfolio.repository.CVRepository;
import uz.tuit.portfolio.repository.ProjectRepository;
import uz.tuit.portfolio.service.ProjectService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectMapper projectMapper;
    private final ProjectRepository projectRepository;
    private final CVRepository cVRepository;

    @Override
    @Transactional
    public ResponseEntity<?> addProject(User user, ProjectCreateDto projectCreateDto) {

        CV cv = user.getCv();

        Project project = projectMapper.toEntity(projectCreateDto);

        project.setCv(cv);

        System.out.println("ProjectServiceImpl addProject"+ project);

        projectRepository.save(project);

        return ResponseEntity.ok(projectMapper.toResponse(project));

    }

    @Override
    @Transactional
    public ResponseEntity<?> updateProject(Long id, User user, ProjectUpdateDto projectUpdateDto) {

        CV cv = user.getCv();

        List<Project> projects = cv.getProjects();

        Project project1 = projects.stream().filter(project -> project.getId().equals(id))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Project not belong to this user"));


        if (projectUpdateDto.getName()!=null){
            project1.setName(projectUpdateDto.getName());
        }
        if (projectUpdateDto.getDescription()!=null){
            project1.setDescription(projectUpdateDto.getDescription());
        }

        projectRepository.save(project1);

        return ResponseEntity.ok(projectMapper.toResponse(project1));

    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteProject(Long id, User user) {

        CV cv = user.getCv();

        List<Project> projects = cv.getProjects();

        Project project1 = projects.stream().filter(project -> project.getId().equals(id))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Project not belong to this user"));

        projects.remove(project1);

        cv.setProjects(projects);

        project1.setCv(null);

        cVRepository.save(cv);

        return ResponseEntity.ok("Project has been deleted");

    }
}
