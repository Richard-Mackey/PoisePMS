package com.richard.poise;

import java.util.Optional;

public class ProjectService {
    private ProjectRepository projectRepository;
    private PersonRepository personRepository;

    public ProjectService(ProjectRepository projectRepository, PersonRepository personRepository){
        this.projectRepository = projectRepository;
        this.personRepository = personRepository;
    }
    public ProjectUpdateResult updateProject(int projectID, ProjectUpdateRequest request){
        Optional<Projects> projectOptional = projectRepository.findByID(projectID);
        if (projectOptional.isEmpty()) {
            return new ProjectUpdateResult(false, "Project not found");
        }
        Projects foundProject = projectOptional.get();
        if (request.getProjectName() != null) {
            foundProject.setProjectName(request.getProjectName());
        }
        if (request.getBuildingType() != null) {
            foundProject.setBuildingType(request.getBuildingType());
        }
        if (request.getProjectAddress() != null) {
            foundProject.setProjectAddress(request.getProjectAddress());
        }
        if (request.getERFNumber() != null) {
            foundProject.setERFNumber(request.getERFNumber());
        }
        if (request.getTotalFee() != null) {
            foundProject.setTotalFee(request.getTotalFee());
        }
        if (request.getAmountPaidToDate() != null) {
            foundProject.setAmountPaidToDate(request.getAmountPaidToDate());
        }
        if (request.getProjectDeadline() != null) {
            foundProject.setProjectDeadline(request.getProjectDeadline());
        }
        if (request.getArchitectID() != null) {
            foundProject.setArchitectID(request.getArchitectID());
        }
        if (request.getContractorID() != null) {
            foundProject.setContractorID(request.getContractorID());
        }
        if (request.getCustomerID() != null) {
            foundProject.setCustomerID(request.getCustomerID());
        }
        if (request.getEngineerID() != null) {
            foundProject.setEngineerID(request.getEngineerID());
        }
        if (request.getManagerID() != null) {
            foundProject.setManagerID(request.getManagerID());
        }
        if (request.isProjectFinalised() != null) {
            foundProject.setProjectFinalised(request.isProjectFinalised());
        }
        if (request.getCompletionDate() != null) {
            foundProject.setCompletionDate(request.getCompletionDate());
        }
        boolean success = projectRepository.updateProjectData(
                foundProject.getProjectID(),
                foundProject.getProjectName(),
                foundProject.getBuildingType(),
                foundProject.getProjectAddress(),
                foundProject.getERFNumber(),
                foundProject.getTotalFee(),
                foundProject.getAmountPaidToDate(),
                foundProject.getProjectDeadline(),
                foundProject.getArchitectID(),
                foundProject.getContractorID(),
                foundProject.getCustomerID(),
                foundProject.getEngineerID(),
                foundProject.getManagerID(),
                foundProject.isProjectFinalised(),
                foundProject.getCompletionDate()

        );
        if (success) {
            return new ProjectUpdateResult(true, "Project updated successfully");
        } else {
            return new ProjectUpdateResult(false, "Failed to update project");
        }
    }
    public ProjectUpdateResult deleteProject(int projectID) {
        Optional<Projects> projectOptional = projectRepository.findByID(projectID);
        if (projectOptional.isEmpty()) {
            return new ProjectUpdateResult(false, "Project not found");
        }
        boolean success = projectRepository.deleteProjectData(projectID);
        if (success) {
            return new ProjectUpdateResult(true, "Project deleted successfully");
        } else {
            return new ProjectUpdateResult(false, "Failed to delete project");
        }
    }


    public ProjectUpdateResult finaliseProject(int projectID, java.sql.Date finalisedDate) {
        Optional<Projects> projectOptional = projectRepository.findByID(projectID);
        if (projectOptional.isEmpty()) {
            return new ProjectUpdateResult(false, "Project not found");
        }
        Projects foundProject = projectOptional.get();
        if (foundProject.isProjectFinalised()) {
            return new ProjectUpdateResult(false, "This project is already finalised");
        }

    boolean success = projectRepository.finaliseProjectData(projectID, finalisedDate);
        if (success) {
        return new ProjectUpdateResult(true, "Project finalised successfully");
    } else {
        return new ProjectUpdateResult(false, "Failed to finalise project");
    }
        }

    public ProjectUpdateResult createProject(ProjectCreateRequest request) {
        int newProjectID = projectRepository.createProjectData(
                request.getProjectName(),
                request.getBuildingType(),
                request.getProjectAddress(),
                request.getERFNumber(),
                request.getTotalFee(),
                request.getAmountPaidToDate(),
                request.getProjectDeadline(),
                request.getArchitectID(),
                request.getContractorID(),
                request.getCustomerID(),
                request.getEngineerID(),
                request.getManagerID(),
                request.isProjectFinalised(),
                request.getCompletionDate()
        );

        if (newProjectID > 0) {
            return new ProjectUpdateResult(true, "Project created successfully with ID: " + newProjectID);
        } else {
            return new ProjectUpdateResult(false, "Failed to create project");
        }
    }
        }
