package io.w4t3rcs.task.repository;

import io.w4t3rcs.task.entity.SwiftCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SwiftCodeRepository extends JpaRepository<SwiftCode, String> {
    List<SwiftCode> findAllByCodeContainingAndCodeContaining(String prefix, String suffix);

    List<SwiftCode> findAllByCodeContainingAndCodeNotContaining(String prefix, String suffix);

    List<SwiftCode> findAllByCountry_Iso2(String iso2);
}
