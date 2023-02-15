package db.model.mapper

import db.model.domain.Rule
import db.model.entity.RuleEntity

/**
 * Перевод RuleEntity (класс для записи в таблицу) в модель представления Rule
 */
object RuleMapper extends TRuleMapper {
  /**
   * Перевод класса сущности в класс для отображения
   *
   * @param entity сущность
   * @return полученная модель
   */
  override def entity2domain(entity: RuleEntity): Rule =
    Rule(
      id = entity.id,
      eventId = entity.eventId,
      r1 = entity.r1Id,
      r2 = entity.r2Id
    )

  /**
   * Перевод модели отображения в класс для записи в таблице
   *
   * @param model модель
   * @return полученный экземпляр класс для записи в таблицу
   */
  override def model2entity(model: Rule): RuleEntity =
    RuleEntity(
      id = model.id,
      eventId = model.eventId,
      r1Id = model.r1,
      r2Id = model.r2
    )
}
