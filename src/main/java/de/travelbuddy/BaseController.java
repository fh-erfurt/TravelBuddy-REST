/*
package de.travelbuddy;

import de.travelbuddy.controller.v1.api.exceptions.IdMismatchAPIException;
import de.travelbuddy.controller.v1.api.exceptions.MissingValuesAPIException;
import de.travelbuddy.controller.v1.api.place.exceptions.EntityNotFoundAPIException;
import de.travelbuddy.model.Person;
import de.travelbuddy.model.finance.Expense;
import de.travelbuddy.model.place.Connection;
import de.travelbuddy.model.place.Place;
import de.travelbuddy.storage.repositories.IGenericRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
public class BaseController<T extends Place> {

    IGenericRepo<T> repoT;
    Class exception;

    @Autowired
    public BaseController(IGenericRepo<T> repoT) {
        this.repoT= repoT;
    }

    private String fetchClassName()
    {
        return this.repoT.getType().getSimpleName();
    }

    private T fetchEntity(Long entityId) {
        T entity = repoT.read(entityId);
exception::new;
        if (entity == null)
            throw new EntityNotFoundAPIException(String.format(""));

        return entity;
    }

    //<editor-fold desc="CRUD">
    //###################
    //##### CREATE ######
    //###################
    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    public T createEntity(@RequestBody T Entity) {
        return repoT.save(Entity);
    }

    //###################
    //###### READ #######
    //###################
    @GetMapping("/{entityId}")
    @ResponseStatus(code = HttpStatus.OK)
    public T getEntity(@PathVariable Long entityId) throws EntityNotFoundAPIException {
        return fetchEntity(entityId);
    }


    @GetMapping("")
    @ResponseStatus(code = HttpStatus.OK)
    public List<T> getEntity() throws EntityNotFoundAPIException {
        return repoT.getSelectQuery().fetch();
    }

    //###################
    //##### UPDATE ######
    //###################
    @PutMapping("/{entityId}")
    @ResponseStatus(code = HttpStatus.OK)
    public T updateEntity(@PathVariable Long entityId, @RequestBody T entity) throws EntityNotFoundAPIException {
        //Check if exist
        fetchEntity(entityId);

        if (entity.getId() == null)
            throw new MissingValuesAPIException("Missing values: id");

        if (!entity.getId().equals(entityId))
            throw new IdMismatchAPIException(String.format("Ids %d and %d do not match.", entityId, entity.getId()));

        return repoT.save(entity);
    }

    //###################
    //##### DELETE ######
    //###################
    @DeleteMapping("/{entityId}")
    @ResponseStatus(code = HttpStatus.OK)
    void deleteEmployee(@PathVariable Long entityId) throws EntityNotFoundAPIException {
        //Check if exist
        fetchEntity(entityId);

        repoT.remove(entityId);
    }
    //</editor-fold>
    
}
*/
