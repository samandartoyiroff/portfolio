package uz.tuit.portfolio.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.tuit.portfolio.domain.Project;
import uz.tuit.portfolio.dto.request.ProjectCreateDto;
import uz.tuit.portfolio.dto.response.ProjectResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProjectMapper {

    public ProjectResponseDto toResponse(Project project) {

        ProjectResponseDto projectResponseDto = new ProjectResponseDto();
        projectResponseDto.setId(project.getId());
        projectResponseDto.setName(project.getName());
        projectResponseDto.setDescription(project.getDescription());
        return projectResponseDto;

    }

    public Project toEntity(ProjectCreateDto projectCreateDto) {

        Project project = new Project();

        project.setName(projectCreateDto.getName());
        project.setDescription(projectCreateDto.getDescription());
        return project;

    }

    public List<Project> toListEntity(List<ProjectCreateDto> projects) {
        return  projects.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public List<ProjectResponseDto> toListDto(List<Project> projects) {

        return projects.stream().map(this::toResponse).collect(Collectors.toList());

    }
}
