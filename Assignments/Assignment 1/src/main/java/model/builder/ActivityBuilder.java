package model.builder;

import model.Activity;

import java.time.LocalDate;
import java.util.Date;

public class ActivityBuilder {

    private final Activity activity;

    public ActivityBuilder() {
        activity = new Activity();
    }

    public ActivityBuilder setId(Long id){
        activity.setId(id);
        return this;
    }

    public ActivityBuilder setIdEmployee(Long idEmployee){
        activity.setIdEmployee(idEmployee);
        return this;
    }

    public ActivityBuilder setDescription(String description){
        activity.setDescription(description);
        return this;
    }

    public ActivityBuilder setDate(Date date){
        activity.setDate(date);
        return this;
    }

    public Activity build(){
        return activity;
    }
}
