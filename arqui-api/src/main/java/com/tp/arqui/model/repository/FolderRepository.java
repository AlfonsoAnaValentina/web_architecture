package com.tp.arqui.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tp.arqui.model.FolderModel;

@Repository
public interface FolderRepository extends PagingAndSortingRepository<FolderModel, Integer> {
	
	Page<FolderModel> findById(Integer id, Pageable pageable);
	Page<FolderModel> findDistinctByIdUser(Integer userId, Pageable pageable);
}
