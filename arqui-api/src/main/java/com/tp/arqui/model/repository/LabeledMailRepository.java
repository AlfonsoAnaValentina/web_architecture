package com.tp.arqui.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tp.arqui.model.LabeledMailModel;

@Repository
public interface LabeledMailRepository extends PagingAndSortingRepository<LabeledMailModel, Integer> {
	Page<LabeledMailModel> findByToAddressAndIdFolder(String toAddress, Integer idFolder, Pageable pageable);

//	List<LabeledMailModel> findDistinctByIdFolderAndToAddress(String toAddress, Pageable pageable);
	List<LabeledMailModel> findDistinctByToAddress(String toAddress, Pageable pageable);

}
