package com.andersen.deploy.dao;

import com.andersen.deploy.exception.DaoException;
import com.andersen.deploy.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    List<User> findAll() throws DaoException;
    Optional<User> findById(int id) throws DaoException;

    void create(User entity) throws DaoException;

    void update(User entity) throws DaoException;

    void delete(int id) throws DaoException;
}
