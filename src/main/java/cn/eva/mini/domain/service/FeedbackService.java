package cn.eva.mini.domain.service;

import cn.eva.mini.domain.entity.Feedback;
import cn.eva.mini.infra.enums.FeedbackStatus;
import cn.eva.mini.infra.enums.FeedbackType;
import cn.eva.mini.infra.exception.NotExistException;
import cn.eva.mini.infra.repository.FeedbackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Feedback service.
 */
@Service
public class FeedbackService {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackService.class);

  /**
   * Feedback repository.
   */
  @Autowired
  private transient FeedbackRepository feedbackRepository;

  /**
   * Save feedback.
   *
   * @param feedback
   * @return
   */
  public Feedback save(Feedback feedback) {
    LOGGER.debug("Enter. feedback: {}.", feedback);

    Feedback saved = feedbackRepository.save(feedback);

    LOGGER.debug("Exit. savedFeedback: {}.", saved);
    return saved;
  }

  /**
   * Get feedback by id.
   *
   * @param id
   * @return
   */
  public Feedback get(String id) {
    LOGGER.debug("Enter. id: {}.", id);

    Feedback feedback = feedbackRepository.findOne(id);
    if (feedback == null) {
      LOGGER.debug("Feedback: {} not exist.", id);
      throw new NotExistException("Feedback not exist: " + id);
    }

    LOGGER.debug("Exit. feedback: {}.", feedback);
    return feedback;
  }

  /**
   * 根据开发者的ID，反馈的处理状态和类型进行查询。
   * TODO 后期做分页.
   *
   * @param developerId 开发者ID
   * @param status      反馈状态
   * @param type        反馈种类
   * @return 反馈列表
   */
  public List<Feedback> getByDeveloper(String developerId, String userId, FeedbackStatus status,
                                       FeedbackType type) {
    LOGGER.debug("Enter. developerId: {}, userId: {}, status: {}, type: {}.", developerId,
      userId, status, type);

    Feedback feedback = new Feedback();
    feedback.setDeveloperId(developerId);
    feedback.setDeveloperStatus(status);
    feedback.setType(type);
    Example<Feedback> example = Example.of(feedback);
    List<Feedback> feedbackList = feedbackRepository.findAll(example);

    LOGGER.debug("Exit. feedbackSize: {}.", feedbackList.size());
    return feedbackList;
  }

  /**
   * 查询所有用户的反馈.
   * TODO 后期做分页.
   *
   * @param userId 用户userId
   * @return 反馈列表
   */
  public List<Feedback> getByUser(String userId) {
    LOGGER.debug("Enter. userId: {}.", userId);

    Feedback feedback = new Feedback();
    feedback.setUserId(userId);
    Example<Feedback> example = Example.of(feedback);
    List<Feedback> feedbackList = feedbackRepository.findAll(example);

    LOGGER.debug("Exit. feedbackSize: {}.", feedbackList.size());
    return feedbackList;
  }
}
