package db.model.mapper

import db.model.domain.Event
import db.model.entity.EventEntity

/**
 * Перевод EventEntity (класс для записи в таблицу) в модель представления Event
 */
object EventMapper extends TEventMapper {
  /**
   * Перевод класса сущности в класс для отображения
   *
   * @param entity сущность
   * @return полученная модель
   */
  override def entity2domain(entity: EventEntity): Event =
    Event(
      id = entity.id,
      content = entity.content,
      stateId = entity.stateId
    )

  /**
   * Перевод модели отображения в класс для записи в таблице
   *
   * @param model модель
   * @return полученный экземпляр класс для записи в таблицу
   */
  override def model2entity(model: Event): EventEntity =
    EventEntity(
      id = model.id,
      content = model.content,
      stateId = model.stateId
    )
}
