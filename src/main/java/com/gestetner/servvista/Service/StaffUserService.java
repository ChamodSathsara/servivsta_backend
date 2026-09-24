package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.identity.*;
import com.gestetner.servvista.Mapper.UserMapper;
import com.gestetner.servvista.Models.Enums.Identity.Role;
import com.gestetner.servvista.Models.Enums.Organization.Company;
import com.gestetner.servvista.Models.entity.identity.Coordinator;
import com.gestetner.servvista.Models.entity.identity.Finance;
import com.gestetner.servvista.Models.entity.identity.Technician;
import com.gestetner.servvista.Models.entity.identity.User;
import com.gestetner.servvista.Models.entity.sales.Salesman;
import com.gestetner.servvista.Models.entity.organization.UserCompany;
import com.gestetner.servvista.Repositories.identity.CoordinatorRepository;
import com.gestetner.servvista.Repositories.identity.FinanceRepository;
import com.gestetner.servvista.Repositories.identity.TechnicianRepository;
import com.gestetner.servvista.Repositories.identity.UserRepository;
import com.gestetner.servvista.Repositories.sales.SalesmanRepository;
import com.gestetner.servvista.Repositories.organization.UserCompanyRepository;
import org.springframework.dao.DataIntegrityViolationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.List;
import java.util.LinkedHashSet;

@Service
@Transactional
public class StaffUserService {

    private final UserRepository userRepository;
    private final TechnicianRepository technicianRepository;
    private final CoordinatorRepository coordinatorRepository;
    private final FinanceRepository financeRepository;
    private final SalesmanRepository salesmanRepository;
    private final UserCompanyRepository userCompanyRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public StaffUserService(
            UserRepository userRepository,
            TechnicianRepository technicianRepository,
            CoordinatorRepository coordinatorRepository,
            FinanceRepository financeRepository,
            SalesmanRepository salesmanRepository,
            UserCompanyRepository userCompanyRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.technicianRepository = technicianRepository;
        this.coordinatorRepository = coordinatorRepository;
        this.financeRepository = financeRepository;
        this.salesmanRepository = salesmanRepository;
        this.userCompanyRepository = userCompanyRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public TechnicianUserResponse createTechnician(CreateTechnicianUserRequest request) {
        try {
            User user = createUser(request, Role.TECHNICIAN);
            List<Company> companies = assignCompanies(user, request);
            Technician technician = new Technician();
            technician.setUserId(user.getUserId());
            technician.setTechCode(request.techCode().trim());
            technician.setTechnicianName(user.getUserName());
            technician.setMobileNumber(user.getMobileNumber());
            technician.setEmail(user.getEmail());
            technician.setDivision(user.getDivision());
            technician.setArea(user.getArea());
            technician.setTechnicianRole(request.technicianRole());
            technician = technicianRepository.saveAndFlush(technician);
            return new TechnicianUserResponse(
                    userMapper.toResponse(user), technician.getTechnicianId(),
                    technician.getTechCode(), technician.getTechnicianRole(), companies);
        } catch (DataIntegrityViolationException exception) {
            throw duplicateDetails(exception);
        }
    }

    public CoordinatorUserResponse createCoordinator(CreateCoordinatorUserRequest request) {
        try {
            User user = createUser(request, Role.COORDINATOR);
            List<Company> companies = assignCompanies(user, request);
            Coordinator coordinator = new Coordinator();
            coordinator.setUserId(user.getUserId());
            coordinator.setCoordinatorName(user.getUserName());
            coordinator.setCoordinatorRole(request.coordinatorRole());
            coordinator.setMobileNumber(user.getMobileNumber());
            coordinator.setEmail(user.getEmail());
            coordinator.setDivision(user.getDivision());
            coordinator.setArea(user.getArea());
            coordinator = coordinatorRepository.saveAndFlush(coordinator);
            return new CoordinatorUserResponse(
                    userMapper.toResponse(user), coordinator.getCoordinatorId(),
                    coordinator.getCoordinatorRole(), companies);
        } catch (DataIntegrityViolationException exception) {
            throw duplicateDetails(exception);
        }
    }

    public FinanceUserResponse createFinance(CreateFinanceUserRequest request) {
        try {
            User user = createUser(request, Role.FINANCE);
            List<Company> companies = assignCompanies(user, request);
            Finance finance = new Finance();
            finance.setUserId(user.getUserId());
            finance.setFinanceName(user.getUserName());
            finance.setMobileNumber(user.getMobileNumber());
            finance.setEmail(user.getEmail());
            finance = financeRepository.saveAndFlush(finance);
            return new FinanceUserResponse(
                    userMapper.toResponse(user), finance.getFinanceId(), companies);
        } catch (DataIntegrityViolationException exception) {
            throw duplicateDetails(exception);
        }
    }

    public SalesmanUserResponse createSalesman(CreateSalesmanUserRequest request) {
        try {
            User user = createUser(request, Role.SALESMAN);
            List<Company> companies = validateCompanies(request.companies());
            if (request.company() != null && !companies.contains(request.company())) {
                throw new IllegalArgumentException(
                        "The salesman's primary company must be included in companies");
            }
            companies = assignCompanies(user, companies);
            Salesman salesman = new Salesman();
            salesman.setUserId(user.getUserId());
            salesman.setSalesmanCode(request.salesmanCode().trim());
            salesman.setSalesmanName(user.getUserName());
            salesman.setMobileNumber(user.getMobileNumber());
            salesman.setEmail(user.getEmail());
            salesman.setCompany(request.company());
            salesman.setIsActive(user.getIsActive());
            salesman.setCreatedAt(user.getCreatedAt());
            salesman.setCreatedBy(user.getCreatedBy());
            salesman = salesmanRepository.saveAndFlush(salesman);
            return new SalesmanUserResponse(
                    userMapper.toResponse(user), salesman.getSalesmanId(),
                    salesman.getSalesmanCode(), salesman.getCompany(), companies);
        } catch (DataIntegrityViolationException exception) {
            throw duplicateDetails(exception);
        }
    }

    @Transactional(readOnly = true)
    public List<TechnicianUserResponse> getTechnicians() {
        return technicianRepository.findAllWithUser().stream()
                .map(technician -> new TechnicianUserResponse(
                        userMapper.toResponse(technician.getUser()),
                        technician.getTechnicianId(),
                        technician.getTechCode(),
                        technician.getTechnicianRole(),
                        getCompanies(technician.getUserId())))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CoordinatorUserResponse> getCoordinators() {
        return coordinatorRepository.findAllWithUser().stream()
                .map(coordinator -> new CoordinatorUserResponse(
                        userMapper.toResponse(coordinator.getUser()),
                        coordinator.getCoordinatorId(),
                        coordinator.getCoordinatorRole(),
                        getCompanies(coordinator.getUserId())))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FinanceUserResponse> getFinanceUsers() {
        return financeRepository.findAllWithUser().stream()
                .map(finance -> new FinanceUserResponse(
                        userMapper.toResponse(finance.getUser()),
                        finance.getFinanceId(),
                        getCompanies(finance.getUserId())))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SalesmanUserResponse> getSalesmen() {
        return salesmanRepository.findAllWithUser().stream()
                .map(salesman -> new SalesmanUserResponse(
                        userMapper.toResponse(salesman.getUser()),
                        salesman.getSalesmanId(),
                        salesman.getSalesmanCode(),
                        salesman.getCompany(),
                        getCompanies(salesman.getUserId())))
                .toList();
    }

    public TechnicianUserResponse updateTechnician(
            Long technicianId,
            UpdateTechnicianUserRequest request) {
        try {
            Technician technician = technicianRepository.findById(technicianId)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Technician " + technicianId + " was not found"));
            User user = requireLinkedUser(technician.getUser(), "technician", technicianId);
            updateUser(user, request, Role.TECHNICIAN);

            technician.setTechCode(request.techCode().trim());
            technician.setTechnicianName(user.getUserName());
            technician.setMobileNumber(user.getMobileNumber());
            technician.setEmail(user.getEmail());
            technician.setDivision(user.getDivision());
            technician.setArea(user.getArea());
            technician.setTechnicianRole(request.technicianRole());
            technicianRepository.saveAndFlush(technician);
            return new TechnicianUserResponse(
                    userMapper.toResponse(user), technician.getTechnicianId(),
                    technician.getTechCode(), technician.getTechnicianRole(),
                    getCompanies(user.getUserId()));
        } catch (DataIntegrityViolationException exception) {
            throw duplicateDetails(exception);
        }
    }

    public CoordinatorUserResponse updateCoordinator(
            Long coordinatorId,
            UpdateCoordinatorUserRequest request) {
        try {
            Coordinator coordinator = coordinatorRepository.findById(coordinatorId)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Coordinator " + coordinatorId + " was not found"));
            User user = requireLinkedUser(coordinator.getUser(), "coordinator", coordinatorId);
            updateUser(user, request, Role.COORDINATOR);

            coordinator.setCoordinatorName(user.getUserName());
            coordinator.setCoordinatorRole(request.coordinatorRole());
            coordinator.setMobileNumber(user.getMobileNumber());
            coordinator.setEmail(user.getEmail());
            coordinator.setDivision(user.getDivision());
            coordinator.setArea(user.getArea());
            coordinatorRepository.saveAndFlush(coordinator);
            return new CoordinatorUserResponse(
                    userMapper.toResponse(user), coordinator.getCoordinatorId(),
                    coordinator.getCoordinatorRole(), getCompanies(user.getUserId()));
        } catch (DataIntegrityViolationException exception) {
            throw duplicateDetails(exception);
        }
    }

    public FinanceUserResponse updateFinance(Long financeId, UpdateFinanceUserRequest request) {
        try {
            Finance finance = financeRepository.findById(financeId)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Finance profile " + financeId + " was not found"));
            User user = requireLinkedUser(finance.getUser(), "finance", financeId);
            updateUser(user, request, Role.FINANCE);

            finance.setFinanceName(user.getUserName());
            finance.setMobileNumber(user.getMobileNumber());
            finance.setEmail(user.getEmail());
            financeRepository.saveAndFlush(finance);
            return new FinanceUserResponse(
                    userMapper.toResponse(user), finance.getFinanceId(),
                    getCompanies(user.getUserId()));
        } catch (DataIntegrityViolationException exception) {
            throw duplicateDetails(exception);
        }
    }

    public SalesmanUserResponse updateSalesman(
            Long salesmanId,
            UpdateSalesmanUserRequest request) {
        try {
            Salesman salesman = salesmanRepository.findById(salesmanId)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Salesman " + salesmanId + " was not found"));
            User user = requireLinkedUser(salesman.getUser(), "salesman", salesmanId);
            updateUser(user, request, Role.SALESMAN);

            salesman.setSalesmanCode(request.salesmanCode().trim());
            salesman.setSalesmanName(user.getUserName());
            salesman.setMobileNumber(user.getMobileNumber());
            salesman.setEmail(user.getEmail());
            salesman.setCompany(request.company());
            salesman.setIsActive(user.getIsActive());
            salesmanRepository.saveAndFlush(salesman);
            return new SalesmanUserResponse(
                    userMapper.toResponse(user), salesman.getSalesmanId(),
                    salesman.getSalesmanCode(), salesman.getCompany(),
                    getCompanies(user.getUserId()));
        } catch (DataIntegrityViolationException exception) {
            throw duplicateDetails(exception);
        }
    }

    private User createUser(UserAccountRequest request, Role role) {
        String normalizedEmail = request.email().trim().toLowerCase(Locale.ROOT);
        if (userRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new IllegalStateException("A user with email '" + normalizedEmail + "' already exists");
        }

        User user = userMapper.toEntity(
                request,
                role,
                passwordEncoder.encode(request.password()),
                LocalDateTime.now());
        return userRepository.saveAndFlush(user);
    }

    private List<Company> assignCompanies(User user, CompanyAssignmentRequest request) {
        return assignCompanies(user, validateCompanies(request.companies()));
    }

    private List<Company> assignCompanies(User user, List<Company> companies) {
        List<UserCompany> assignments = companies.stream().map(company -> {
            UserCompany assignment = new UserCompany();
            assignment.setUserId(user.getUserId());
            assignment.setCompany(company);
            return assignment;
        }).toList();
        userCompanyRepository.saveAllAndFlush(assignments);
        return List.copyOf(companies);
    }

    private List<Company> validateCompanies(List<Company> companies) {
        if (companies == null || companies.isEmpty() || companies.size() > Company.values().length) {
            throw new IllegalArgumentException("One or two companies must be supplied");
        }
        LinkedHashSet<Company> uniqueCompanies = new LinkedHashSet<>(companies);
        if (uniqueCompanies.contains(null) || uniqueCompanies.size() != companies.size()) {
            throw new IllegalArgumentException("Company assignments must be unique and non-null");
        }
        return List.copyOf(uniqueCompanies);
    }

    private List<Company> getCompanies(Long userId) {
        return userCompanyRepository.findAllByUserIdOrderByUserCompanyId(userId).stream()
                .map(UserCompany::getCompany)
                .toList();
    }

    private void updateUser(User user, UpdateUserAccountRequest request, Role role) {
        String normalizedEmail = request.email().trim().toLowerCase(Locale.ROOT);
        if (userRepository.existsByEmailIgnoreCaseAndUserIdNot(normalizedEmail, user.getUserId())) {
            throw new IllegalStateException("A user with email '" + normalizedEmail + "' already exists");
        }

        user.setUserName(request.userName().trim());
        user.setMobileNumber(normalizeOptional(request.mobileNumber()));
        user.setEmail(normalizedEmail);
        if (request.password() != null && !request.password().isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(request.password()));
        }
        user.setIsActive(request.isActive());
        user.setDivision(request.division());
        user.setArea(request.area());
        user.setRole(role);
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(request.updatedBy());
        userRepository.saveAndFlush(user);
    }

    private User requireLinkedUser(User user, String profileType, Long profileId) {
        if (user == null) {
            throw new EntityNotFoundException(
                    "The " + profileType + " profile " + profileId + " is not linked to a user");
        }
        return user;
    }

    private String normalizeOptional(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    private IllegalStateException duplicateDetails(DataIntegrityViolationException exception) {
        return new IllegalStateException(
                "A user or profile with the supplied unique details already exists", exception);
    }
}
