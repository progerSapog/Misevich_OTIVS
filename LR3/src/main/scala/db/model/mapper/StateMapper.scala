package db.model.mapper

import db.model.domain.State
import db.model.entity.StateEntity

/**
 * Перевод StateEntity (класс для записи в таблицу) в модель представления State
 */
object StateMapper extends TStateMapper {
  /**
   * Перевод класса сущности в класс для отображения
   *
   * @param entity сущность
   * @return полученная модель
   */
  override def entity2domain(entity: StateEntity): State =
    State(
      id = entity.id,
      name = entity.name
    )

  /**
   * Перевод модели отображения в класс для записи в таблице
   *
   * @param model модель
   * @return полученный экземпляр класс для записи в таблицу
   */
  override def model2entity(model: State): StateEntity =
    StateEntity(
      id = model.id,
      name = model.name
    )
}
