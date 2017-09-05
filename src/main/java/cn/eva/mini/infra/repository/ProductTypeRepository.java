package cn.eva.mini.infra.repository;

import cn.eva.mini.domain.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Product type repository.
 */
public interface ProductTypeRepository extends JpaRepository<ProductType, String> {

}
