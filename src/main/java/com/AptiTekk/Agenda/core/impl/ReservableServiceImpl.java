package com.AptiTekk.Agenda.core.impl;

import javax.ejb.Stateless;

import com.AptiTekk.Agenda.core.ReservableService;
import com.AptiTekk.Agenda.core.ReservableTypeService;
import com.AptiTekk.Agenda.core.entity.QReservable;
import com.AptiTekk.Agenda.core.entity.Reservable;
import com.AptiTekk.Agenda.core.entity.ReservableType;
import com.mysema.query.jpa.impl.JPAQuery;
import java.util.List;
import javax.inject.Inject;

@Stateless
public class ReservableServiceImpl extends EntityServiceAbstract<Reservable> implements ReservableService {

    private QReservable reservableTable = QReservable.reservable;
    
    @Inject
    private ReservableTypeService reservableTypeService;

    public ReservableServiceImpl() {
        super(Reservable.class);
    }

    @Override
    public Reservable findByName(String roomName) {
        return new JPAQuery(entityManager).from(reservableTable).where(reservableTable.name.eq(roomName))
                .singleResult(reservableTable);
    }

    @Override
    public List<Reservable> getAllByType(ReservableType type) {
        return new JPAQuery(entityManager).from(reservableTable).where(reservableTable.type.eq(type)).list(reservableTable);
    }
    
    

}
