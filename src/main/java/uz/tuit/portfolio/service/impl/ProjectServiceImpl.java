package uz.tuit.portfolio.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.tuit.portfolio.domain.CV;
import uz.tuit.portfolio.domain.Portfolio;
import uz.tuit.portfolio.domain.Project;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.ProjectCreateDto;
import uz.tuit.portfolio.dto.request.ProjectUpdateDto;
import uz.tuit.portfolio.mapper.ProjectMapper;
import uz.tuit.portfolio.repository.CVRepository;
import uz.tuit.portfolio.repository.PortfolioRepository;
import uz.tuit.portfolio.repository.ProjectRepository;
import uz.tuit.portfolio.service.ProjectService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectMapper projectMapper;
    private final ProjectRepository projectRepository;
    private final CVRepository cVRepository;
    private final PortfolioRepository portfolioRepository;

    @Override
    @Transactional
    public ResponseEntity<?> addProject(User user, ProjectCreateDto projectCreateDto, Long cvId) {

        CV cv;

        if (user != null) {
            cv = cVRepository.findByIdAndUserId(cvId, user.getId()).orElseThrow(()-> new EntityNotFoundException("cv not found"));
        }
        else {
            cv = cVRepository.findById(cvId).orElseThrow(()-> new EntityNotFoundException("cv not found"));
        }

        Project project = projectMapper.toEntity(projectCreateDto);

        project.setCv(cv);

        System.out.println("ProjectServiceImpl addProject"+ project);

        projectRepository.save(project);

        return ResponseEntity.ok(projectMapper.toResponse(project));

    }

    @Override
    @Transactional
    public ResponseEntity<?> updateProject(Long id, User user, ProjectUpdateDto projectUpdateDto, Long cvId) {


        CV cv;

        if (user != null) {
            cv = cVRepository.findByIdAndUserId(cvId, user.getId()).orElseThrow(()-> new EntityNotFoundException("cv not found"));
        }
        else {
            cv = cVRepository.findById(cvId).orElseThrow(()-> new EntityNotFoundException("cv not found"));
        }

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
    public ResponseEntity<?> deleteProject(Long id, User user, Long cvId) {

        CV cv;

        if (user != null) {
            cv = cVRepository.findByIdAndUserId(cvId, user.getId()).orElseThrow(()-> new EntityNotFoundException("cv not found"));
        }
        else {
            cv = cVRepository.findById(cvId).orElseThrow(()-> new EntityNotFoundException("cv not found"));
        }

        List<Project> projects = cv.getProjects();

        Project project1 = projects.stream().filter(project -> project.getId().equals(id))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Project not belong to this user"));

        projects.remove(project1);

        cv.setProjects(projects);

        project1.setCv(null);

        cVRepository.save(cv);

        return ResponseEntity.ok("Project has been deleted");

    }

    @Override
    @Transactional
    public ResponseEntity<?> updatePortfolioProject(Long id, User user, ProjectUpdateDto projectUpdateDto) {

        Portfolio portfolio = user.getPortfolio();

        List<Project> projects = portfolio.getProjects();

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
    public ResponseEntity<?> deletePortfolioProject(Long id, User user) {

        Portfolio portfolio = user.getPortfolio();

        List<Project> projects = portfolio.getProjects();

        Project project1 = projects.stream().filter(project -> project.getId().equals(id))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Project not belong to this user"));

        projects.remove(project1);

        portfolio.setProjects(projects);

        project1.setCv(null);

        portfolioRepository.save(portfolio);

        return ResponseEntity.ok("Project has been deleted");
    }

    @Override
    @Transactional
    public ResponseEntity<?> addProjectToPortfolio(User user, ProjectCreateDto projectCreateDto) {

        Portfolio portfolio = user.getPortfolio();

        Project project = projectMapper.toEntity(projectCreateDto);

        project.setPortfolio(portfolio);

        projectRepository.save(project);

        return ResponseEntity.ok(projectMapper.toResponse(project));
    }
}
