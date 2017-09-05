package cn.eva.mini.infra.repository;

import cn.eva.mini.domain.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Feedback repository.
 */
public interface FeedbackRepository extends JpaRepository<Feedback, String> {

}
