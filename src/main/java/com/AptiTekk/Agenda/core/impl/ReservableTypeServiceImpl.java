package com.AptiTekk.Agenda.core.impl;

import javax.ejb.Stateless;

import com.AptiTekk.Agenda.core.ReservableTypeService;
import com.AptiTekk.Agenda.core.entity.QReservableType;
import com.AptiTekk.Agenda.core.entity.ReservableType;
import com.mysema.query.jpa.impl.JPAQuery;

@Stateless
public class ReservableTypeServiceImpl extends EntityServiceAbstract<ReservableType> implements ReservableTypeService {

    QReservableType table = QReservableType.reservableType;

    public ReservableTypeServiceImpl() {
        super(ReservableType.class);
    }

    @Override
    public ReservableType findByName(String name) {
        return new JPAQuery(entityManager).from(table).where(table.name.eq(name))
                .singleResult(table);
    }

}
