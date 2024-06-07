package com.capgemini.wsb.persistence.dao.impl;
import com.capgemini.wsb.persistence.dao.PatientDao;
import com.capgemini.wsb.persistence.entity.PatientEntity;
import com.capgemini.wsb.persistence.enums.TreatmentType;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {

    @Override
    public List<PatientEntity> findByDoctor(String firstName, String lastName) {
        return entityManager.createQuery(
            "SELECT p FROM PatientEntity p " +
            "JOIN p.visits v " +
            "JOIN v.doctor d " +
            "WHERE d.firstName = :firstName AND d.lastName = :lastName", 
            PatientEntity.class)
            .setParameter("firstName", firstName)
            .setParameter("lastName", lastName)
            .getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsHavingTreatmentType(TreatmentType treatmentType) {
        
        return entityManager.createQuery(
            "SELECT DISTINCT p FROM PatientEntity p " +
            "JOIN p.visits v " +
            "JOIN v.medicalTreatments mt " +
            "WHERE mt.type = :treatmentType", 
            PatientEntity.class)
            .setParameter("treatmentType", treatmentType)
            .getResultList();
    }
    @Override
    public List<PatientEntity> findPatientsSharingSameLocationWithDoc(String firstName, String lastName) {
        return entityManager.createQuery(
            "SELECT DISTINCT p FROM PatientEntity p " +
            "JOIN p.addresses a " +
            "JOIN a.doctors d " +
            "WHERE d.firstName = :firstName AND d.lastName = :lastName", 
            PatientEntity.class)
        .setParameter("firstName", firstName)
        .setParameter("lastName", lastName)
        .getResultList();
    }
    @Override
    public List<PatientEntity> findPatientsWithoutLocation() {
        return entityManager.createQuery(
            "SELECT p FROM PatientEntity p " +
            "LEFT JOIN p.addresses a " +
            "WHERE a IS NULL", 
            PatientEntity.class)
        .getResultList();
    }
}
