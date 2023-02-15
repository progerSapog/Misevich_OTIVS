package db.model.dao

import db.model.entity.{EventEntity, RuleEntity, StateEntity, TEntity}
import scalikejdbc.{DBSession, ParameterBinderFactory}

import java.util.UUID

/**
 * Общенный трейт DAO
 *
 * @tparam T тип сущности
 */
sealed trait TDAO[T <: TEntity] {
  implicit val uuidFactory: ParameterBinderFactory[UUID] = ParameterBinderFactory[UUID] {
    value => (stmt, idx) => stmt.setObject(idx, value)
  }

  /**
   * Вставка записи в таблицу
   *
   * @param row запись для вставки
   */
  def insert(row: T)
            (implicit session: DBSession): Unit

  /**
   * Вставка множества записей в таблицу
   *
   * @param rows записи для вставки
   */
  def multiInsert(rows: Seq[T])
                 (implicit session: DBSession): Unit

  /**
   * Выбор записи по id
   *
   * @param id идентификатор по которому будет производиться выборка
   * @return Option найденной сущности. Если сущности нет, то Option будет пуст.
   */
  def selectById(id: UUID)
                (implicit session: DBSession): Option[T]

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
                sort: String = "asc")
               (implicit session: DBSession): Seq[T]

  /**
   * Изменение записи
   *
   * @param row запись с изменными данными
   */
  def update(row: T)
            (implicit session: DBSession): Unit

  /**
   * Удаление записи по id
   *
   * @param id идентификатор, по которому будет выполнено удаление
   */
  def deleteById(id: UUID)
                (implicit session: DBSession): Unit
}

trait TEventDAO extends TDAO[EventEntity]

trait TRuleDAO extends TDAO[RuleEntity]

trait TStateDAO extends TDAO[StateEntity]
