package com.andersen.deploy.service;

import com.andersen.deploy.dao.UserDao;
import com.andersen.deploy.exception.DaoException;
import com.andersen.deploy.exception.ServiceException;
import com.andersen.deploy.exception.UserNotFoundException;
import com.andersen.deploy.model.User;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> findAllUsers() throws ServiceException {
        List<User> users;
        try {
            users = userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return users;
    }

    @Override
    public User findById(int id) throws ServiceException {
        try {
            Optional<User> optionalUser = userDao.findById(id);
            if (optionalUser.isPresent()) {
                return optionalUser.get();
            }
            throw new UserNotFoundException("User with id=%d doesn't exist".formatted(id));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void create(User user) throws ServiceException {
        try {
            userDao.create(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(User user) throws ServiceException {
        try {
            userDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(int id) throws ServiceException {
        try {
            userDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
