package com.tp.arqui.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tp.arqui.model.MessageModel;



@Repository
public interface MessageRepository extends PagingAndSortingRepository<MessageModel, Integer> {
	List<MessageModel> findAllByOrderBySendDateDesc();

	Page<MessageModel> findByFromAddress(String userMail, Pageable pageable);

	Page<MessageModel> findByToAddress(String userMail, Pageable pageable);

	
}
