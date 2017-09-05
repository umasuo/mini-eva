package cn.eva.mini.infra.repository;

import cn.eva.mini.domain.entity.DeviceDataDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Data definition repository.
 */
@Repository
public interface DataDefinitionRepository extends JpaRepository<DeviceDataDefinition, String> {

}
