package com.iuh.IUHStudent.repository;

import com.iuh.IUHStudent.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

}
