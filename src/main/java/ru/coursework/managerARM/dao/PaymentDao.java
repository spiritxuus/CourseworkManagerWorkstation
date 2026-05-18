package ru.coursework.managerARM.dao;

import ru.coursework.managerARM.model.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentDao {
    void add(Payment payment);
    Optional<Payment> getById(Long id);
    List<Payment> getAll();
    void update(Payment payment);
    void delete(Long id);
}
