package com.AptiTekk.Agenda.web;

import com.AptiTekk.Agenda.core.ReservableTypeService;
import com.AptiTekk.Agenda.core.entity.ReservableType;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

@ManagedBean
@ApplicationScoped
public class ReservableTypeConverter implements Converter  {

    @Inject
    private ReservableTypeService reservableTypeService;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if(string == null || string.isEmpty() || reservableTypeService == null)
            return null;
        
        return reservableTypeService.findByName(string);
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o instanceof ReservableType)
        {
            return ((ReservableType) o).getName();
        }
        return "";
    }
    
}
