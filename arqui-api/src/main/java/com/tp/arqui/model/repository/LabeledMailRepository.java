package com.tp.arqui.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.tp.arqui.model.LabeledMailModel;

public interface LabeledMailRepository extends PagingAndSortingRepository<LabeledMailModel, Integer> {
	Page<LabeledMailModel> findByIdMessageAndIdFolder(Integer idMessage, Integer idFolder, Pageable pageable);
}
