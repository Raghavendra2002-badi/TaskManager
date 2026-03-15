package com.TaskMange.Repository;

import com.TaskManage.Entity.TokenBlockList;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TokenBlockListRepository extends JpaRepository<TokenBlockList, Long> {
    Optional<TokenBlockList> findByToken(String token);
    boolean existsByToken(String token);
}
