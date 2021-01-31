package com.tp.arqui.model.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tp.arqui.model.UserModel;



@Repository
public interface UserRepository extends PagingAndSortingRepository<UserModel, Integer> {
	 List<UserModel> findByMailAndPassword(String mail, String password);

	List<UserModel> findByMail(String userMail);
	
}
