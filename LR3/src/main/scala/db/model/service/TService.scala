package db.model.service

import db.model.domain.{Event, Rule, State, TDomain}

import java.util.UUID

sealed trait TService[T <: TDomain] {
  /**
   * Вставка записи в таблицу
   *
   * @param model запись для вставки
   */
  def insert(model: T): Unit

  /**
   * Вставка множества записей в таблицу
   *
   * @param models записи для вставки
   */
  def insertMulti(models: Seq[T]): Unit

  /**
   * Выбор записи по id
   *
   * @param id идентификатор по которому будет производиться выборка
   * @return Option найденной сущности. Если сущности нет, то Option будет пуст.
   */
  def selectById(id: UUID): Option[T]

  /**
   * Выборка множества записей из таблицы
   *
   * @param limit   кол-во записей выборки
   * @param offset  отступ от первой записи
   * @param orderBy поле, по которому будет производится сортировка
   * @param sort    порядок сортировки
   * @return коллекцию найденных записей
   */
  def selectAll(limit: Int = 100,
                offset: Int = 0,
                orderBy: String = "id",
                sort: String = "asc"): Seq[T]

  /**
   * Изменение записи
   *
   * @param model запись с изменными данными
   */
  def update(model: T): Unit

  /**
   * Удаление записи по id
   *
   * @param id идентификатор, по которому будет выполнено удаление
   */
  def deleteById(id: UUID): Unit
}

trait TEventService extends TService[Event]

trait TRuleService extends TService[Rule]

trait TStateService extends TService[State]