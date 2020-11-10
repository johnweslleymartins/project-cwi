package com.wordpress.sicredi.processfiles.batchapi.repository;

import com.wordpress.sicredi.processfiles.batchapi.model.AccountData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDataRepository extends JpaRepository<AccountData, Long> {
}
