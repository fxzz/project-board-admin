package com.example.projectboardadmin;

import com.example.projectboardadmin.domain.AdminAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<AdminAccount, String> {
}