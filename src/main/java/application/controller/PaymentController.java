package application.controller;

import application.dto.*;
import application.mapper.OrganizationMapper;
import application.mapper.PaymentMapper;
import application.mapper.ReportMapper;
import application.model.Account;
import application.model.CommunalOrganization;
import application.model.OrganizationType;
import application.model.Payment;
import application.model.security.User;
import application.service.AccountService;
import application.service.CommunalOrganizationService;
import application.service.PaymentService;
import application.service.UserService;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.math.MathContext;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CommunalOrganizationService communalOrganizationService;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private OrganizationMapper organizationMapper;

    @GetMapping("/allPayments")
    @ResponseBody
    public List<PaymentDto> getPayments() {
        List<Payment> payments = paymentService.findAll();
        List<PaymentDto> paymentsDto = new ArrayList<>();
        for (Payment payment : payments) {
            PaymentDto paymentDto = paymentMapper.toDto(payment);
            paymentsDto.add(paymentDto);
        }
        return paymentsDto;
    }

    @GetMapping("/userPayments")
    @ResponseBody
    public List<PaymentDto> getUserPayments(@AuthenticationPrincipal User user) {
        List<Payment> userPayments = accountService.findByUser(user).getPayments();
        List<PaymentDto> paymentsDto = new ArrayList<>();
        for (Payment payment : userPayments) {
            PaymentDto paymentDto = paymentMapper.toDto(payment);
            paymentsDto.add(paymentDto);
        }
        return paymentsDto;
    }

    @GetMapping("/paidUserPayments")
    @ResponseBody
    public List<PaymentDto> getPaidUserPayments(@AuthenticationPrincipal User user) {
        List<Payment> userPayments = accountService.findByUser(user).getPayments();
        List<PaymentDto> paymentsDto = new ArrayList<>();
        for (Payment payment : userPayments) {
            if(payment.getIsPaid() != null && payment.getIsPaid()) {
                PaymentDto paymentDto = paymentMapper.toDto(payment);
                paymentsDto.add(paymentDto);
            }
        }
        return paymentsDto;
    }

    @GetMapping("/userPaymentsByOrganization")
    @ResponseBody
    public OrganizationPaymentsDto getUserPaymentsByOrganization(@AuthenticationPrincipal User user, @RequestParam Long id) {
        Account account = accountService.findByUser(user);
        List<Payment> userPayments = account.getPayments();
        List<PaymentDto> paymentsDto = new ArrayList<>();
        for (Payment payment : userPayments) {
            if (Objects.equals(payment.getCommunalOrganization().getId(), id)) {
                PaymentDto paymentDto = paymentMapper.toDto(payment);
                paymentsDto.add(paymentDto);
            }
        }
        CommunalOrganization communalOrganization = communalOrganizationService.findById(id);
        OrganizationPaymentsDto orgPaymentDto = new OrganizationPaymentsDto();
        orgPaymentDto.setOrganizationType(communalOrganization.getOrganizationType().toString());
        orgPaymentDto.setPayments(paymentsDto);
        return orgPaymentDto;
    }

    @GetMapping("/tariffs")
    @ResponseBody
    public List<CommunalOrganizationDto> getTariffs() {
        List<CommunalOrganization>  organizations = communalOrganizationService.findAllOrg();
        List<CommunalOrganizationDto> communalOrganizationsDto = new ArrayList<>();
        for (CommunalOrganization communalOrganization : organizations) {
            CommunalOrganizationDto communalOrganizationDto = organizationMapper.toDto(communalOrganization);
            communalOrganizationsDto.add(communalOrganizationDto);
        }
        return communalOrganizationsDto;
    }

    @ResponseBody
    @RequestMapping(value = "/newTariff", method = RequestMethod.GET)
    public List<CommunalOrganizationDto> newTariff(@RequestParam BigDecimal tariff, @RequestParam Long organizationId) {
        List<CommunalOrganization>  organizations = communalOrganizationService.findAllOrg();
        List<CommunalOrganizationDto> communalOrganizationsDto = new ArrayList<>();
        for (CommunalOrganization communalOrganization : organizations) {
            CommunalOrganizationDto communalOrganizationDto = organizationMapper.toDto(communalOrganization);
            communalOrganizationsDto.add(communalOrganizationDto);
            CommunalOrganization organization = communalOrganizationService.findById(organizationId);
            organization.setTariff(tariff);
        }
        return communalOrganizationsDto;
    }

    @GetMapping("/paymentsPage")
    public String getPaymentsPage() {
        return "payment_page";
    }

    @GetMapping("/createPayTemplate")
    public String create() {
        return "pay_template";
    }

    @ResponseBody
    @GetMapping("/getOrganizations")
    public List<CommunalOrganizationDto> getOrganizations(@RequestParam String type) {
        List<CommunalOrganization> organizations = communalOrganizationService.findByOrganizationType(OrganizationType.valueOf(type));
        List<CommunalOrganizationDto> organizationsDto = new ArrayList<>();
        organizations.forEach(organization -> {
            CommunalOrganizationDto organizationDto = new CommunalOrganizationDto();
            organizationDto.setId(organization.getId());
            organizationDto.setName(organization.getName());
            organizationsDto.add(organizationDto);
        });
        return organizationsDto;
    }

    @ResponseBody
    @RequestMapping(value = "/createPayment", method = RequestMethod.GET)
    public List<PaymentDto> createPayment(@AuthenticationPrincipal User user, @RequestParam Integer indicator, @RequestParam Long organizationId) {
        Account account = accountService.findByUser(user);
        CommunalOrganization organization = communalOrganizationService.findById(organizationId);
        Payment newPayment = new Payment();
        newPayment.setCommunalOrganization(organization);
        newPayment.setAccount(account);
        newPayment.setIndicator(indicator);
        newPayment.setTime(new Date());

        Payment lastPayment = paymentService.findLastByOrganizationIdAndAccount(organizationId, account);
        BigDecimal tariff = organization.getTariff();
        Integer lastIndicator = lastPayment != null ? lastPayment.getIndicator() : 0;
        newPayment.setAmount(tariff.multiply(BigDecimal.valueOf(newPayment.getIndicator() - lastIndicator)));

        paymentService.save(newPayment);

        return getUserPaymentsByOrganization(user, organizationId).getPayments();
    }

    @ResponseBody
    @RequestMapping(value = "/payPayment", method = RequestMethod.POST)
    public List<PaymentDto> payPayment(@RequestBody Long id, @AuthenticationPrincipal User user, @RequestParam(required = false) Long organizationId) {
        Payment payment = paymentService.findPaymentById(id);
        payment.setIsPaid(true);
        paymentService.save(payment);
        return organizationId != null ? getUserPaymentsByOrganization(user, organizationId).getPayments() : getUserPayments(user);
    }

    @ResponseBody
    @RequestMapping(value = "/deletePayment", method = RequestMethod.POST)
    public List<PaymentDto> deletePayment(@RequestBody Long id, @AuthenticationPrincipal User user, @RequestParam(required = false) Long organizationId) {
        Payment payment = paymentService.findPaymentById(id);
        paymentService.delete(payment);
        return organizationId != null ? getUserPaymentsByOrganization(user, organizationId).getPayments() : getUserPayments(user);
    }

    @ResponseBody
    @GetMapping(value = "/user")
    public UserDto getUser(Principal principal) {
        UserDto userDto = new UserDto();
        userDto.setUserName(principal.getName());
        return userDto;
    }

}