package db.model.mapper

import db.model.domain.{Event, Rule, State, TDomain}
import db.model.entity.{EventEntity, RuleEntity, StateEntity, TEntity}

sealed trait TMapper[EntityType <: TEntity, DomainType <: TDomain] {
  /**
   * Перевод класса сущности в класс для отображения
   *
   * @param entity сущность
   * @return полученная модель
   */
  def entity2domain(entity: EntityType): DomainType

  /**
   * Перевод модели отображения в класс для записи в таблице
   *
   * @param model модель
   * @return полученный экземпляр класс для записи в таблицу
   */
  def model2entity(model: DomainType): EntityType
}

trait TEventMapper extends TMapper[EventEntity, Event]

trait TRuleMapper extends TMapper[RuleEntity, Rule]

trait TStateMapper extends TMapper[StateEntity, State]