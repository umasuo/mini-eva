package cn.eva.mini.infra.updater;

/**
 * configurations for common update actions that will be used in more thant one service
 * and this action also extends other action configure in each service.
 */
public interface UpdateAction {
  /**
   * get action name.
   *
   * @return name in string.
   */
  String getActionName();
}
