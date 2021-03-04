package com.tp.arqui.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tp.arqui.model.ReceiverModel;



@Repository
public interface ReceiverRepository extends PagingAndSortingRepository<ReceiverModel, Integer> {



	List<ReceiverModel> findByIdMailAndTypeOrderByDate(Integer idMail, Integer type);
	
	List<ReceiverModel> findByIdReceiverOrderByDate(Integer idMail);

	
}