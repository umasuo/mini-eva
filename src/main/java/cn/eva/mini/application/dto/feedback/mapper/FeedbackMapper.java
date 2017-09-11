package cn.eva.mini.application.dto.feedback.mapper;

import cn.eva.mini.application.dto.feedback.FeedbackDraft;
import cn.eva.mini.application.dto.feedback.FeedbackView;
import cn.eva.mini.domain.entity.Content;
import cn.eva.mini.domain.entity.Feedback;
import cn.eva.mini.infra.enums.FeedbackStatus;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * Feedback mapper.
 */
public final class FeedbackMapper {

  /**
   * Instantiates a new Feedback mapper.
   */
  private FeedbackMapper() {
  }

  /**
   * Convert Feedback to FeedbackView.
   *
   * @param feedback the Feedback
   * @return the FeedbackView
   */
  public static FeedbackView toView(Feedback feedback) {

    FeedbackView view = new FeedbackView();
    view.setId(feedback.getId());
    view.setCreatedAt(feedback.getCreatedAt());
    view.setLastModifiedAt(feedback.getLastModifiedAt());
    view.setVersion(feedback.getVersion());
    view.setUserId(feedback.getUserId());
    view.setDeveloperId(feedback.getDeveloperId());
    view.setDeviceId(feedback.getDeviceId());
    view.setDeveloperStatus(feedback.getDeveloperStatus());
    view.setUserStatus(feedback.getUserStatus());
    view.setTitle(feedback.getTitle());
    view.setType(feedback.getType());
    view.setEmail(feedback.getEmail());
    view.setPhone(feedback.getPhone());

    view.setContents(ContentMapper.toView(feedback.getContents()));

    return view;
  }

  /**
   * Convert List of Feedback to List of FeedbackView.
   *
   * @param feedbackList List of Feedback
   * @return List of FeedbackView
   */
  public static List<FeedbackView> toView(List<Feedback> feedbackList) {
    List<FeedbackView> list = new ArrayList<>();

    feedbackList.stream().forEach(
        feedback -> list.add(toView(feedback))
    );

    return list;
  }

  /**
   * Convert FeedbackDraft to Feedback.
   *
   * @param userId the user id
   * @param developerId the developer id
   * @param draft the FeedbackDraft
   * @return the feedback
   */
  public static Feedback toModel(String userId, String developerId, FeedbackDraft draft) {
    Feedback feedback = new Feedback();

    feedback.setUserId(userId);
    feedback.setDeveloperId(developerId);
    feedback.setDeviceId(draft.getDeviceId());
    feedback.setTitle(draft.getTitle());
    feedback.setPhone(draft.getPhone());
    feedback.setEmail(draft.getEmail());
    feedback.setType(draft.getType());

    Content content = new Content(draft.getContent(), userId);
    feedback.setContents(Lists.newArrayList(content));

    feedback.setUserStatus(FeedbackStatus.VIEWED);
    feedback.setDeveloperStatus(FeedbackStatus.UNVIEWED);

    return feedback;
  }

}
