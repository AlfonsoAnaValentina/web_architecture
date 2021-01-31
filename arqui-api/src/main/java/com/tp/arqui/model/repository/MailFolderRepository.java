package com.tp.arqui.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tp.arqui.model.FolderModel;
import com.tp.arqui.model.MailFolderModel;

@Repository
public interface MailFolderRepository extends PagingAndSortingRepository<MailFolderModel, Integer> {
	
	Page<FolderModel> findByIdMessage(Integer idMessage, Pageable pageable);
	Page<FolderModel> findByIdFolder(Integer idFolder, Pageable pageable);
	Page<FolderModel> findByIdMessageAndIdFolder(Integer idMessage, Integer idFolder, Pageable pageable);
}
