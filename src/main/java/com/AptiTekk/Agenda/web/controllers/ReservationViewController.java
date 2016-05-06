package com.AptiTekk.Agenda.web.controllers;

import com.AptiTekk.Agenda.core.ReservationService;
import com.AptiTekk.Agenda.core.UserService;
import com.AptiTekk.Agenda.core.entity.Reservation;
import com.AptiTekk.Agenda.core.entity.User;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author kevint
 */
public class ReservationViewController {

    @Inject
    private ReservationService resService;

    @Inject
    private UserService userService;

    @ManagedProperty(value = "#{AccountController}")
    private AccountController accountController;

    private User user;

    private ScheduleModel lazyEventModel;

    @PostConstruct
    public void init() {
        setUser(accountController.getUser());
    }

    public static DefaultScheduleEvent toEvent(Reservation res) {
        DefaultScheduleEvent event = new DefaultScheduleEvent();
        event.setStartDate(res.getTimeStart());
        event.setEndDate(res.getTimeEnd());
        event.setTitle(res.getReservable().getName() + " - " + res.getTitle());
        event.setDescription(res.getDescription());
        return event;
    }
    
    public AccountController getAccountController() {
        return accountController;
    }

    public void setAccountController(AccountController accountController) {
        this.accountController = accountController;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        
        lazyEventModel = new LazyScheduleModel() {

            @Override
            public void loadEvents(Date start, Date end) {
                for (Reservation res : ((user == null) ? resService.getAll() : 
                        userService.get(user.getId()).getReservations())) {
                    addEvent(toEvent(res));
                }
            }
        };
    }

    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }

    public void setLazyEventModel(ScheduleModel lazyEventModel) {
        this.lazyEventModel = lazyEventModel;
    }

}
