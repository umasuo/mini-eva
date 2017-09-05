package cn.eva.mini.infra.updater;

/**
 * Updater.
 */
public interface Updater<E, A> {

  /**
   * updater action handler.
   *
   * @param entity entity
   * @param action action
   */
  void handle(E entity, A action);
}
