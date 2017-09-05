package cn.eva.mini.infra.repository;

import cn.eva.mini.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Product repository.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, String>,
  QueryByExampleExecutor<Product> {

  /**
   * Find all by developer id.
   *
   * @param developerId the developer id
   * @return the list
   */
  List<Product> findAllByDeveloperId(String developerId);
}
