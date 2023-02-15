package db.model.dao

import db.model.entity.StateEntity
import db.model.entity.StateEntity.{st, stC}
import scalikejdbc._

import java.util.UUID

/**
 * Объект доступа к записям таблицы states (события)
 */
object StateDAO extends TStateDAO {

  /**
   * Вставка записи в таблицу states
   *
   * @param row запись для вставки
   */
  def insert(row: StateEntity)
            (implicit session: DBSession): Unit =
    withSQL {
      insertInto(StateEntity)
        .namedValues(
          stC.id -> row.id,
          stC.name -> row.name
        )
    }.update.apply()

  /**
   * Вставка множества записей в таблицу states
   *
   * @param rows записи для вставки
   */
  def multiInsert(rows: Seq[StateEntity])
                 (implicit session: DBSession): Unit = {
    val batchParams: Seq[Seq[Any]] = rows.map(row => Seq(row.id, row.name))

    withSQL {
      insertInto(StateEntity)
        .namedValues(
          stC.id -> sqls.?,
          stC.name -> sqls.?
        )
    }.batch(batchParams: _*).apply()
  }

  /**
   * Выбор записи по id из таблицы states
   *
   * @param id идентификатор по которому будет производиться выборка
   * @return Option найденной сущности. Если сущности нет, то Option будет пуст.
   */
  def selectById(id: UUID)
                (implicit session: DBSession): Option[StateEntity] =
    withSQL {
      select.from(StateEntity as st)
        .where.eq(st.id, id)
    }.map(StateEntity(st.resultName)).single.apply()

  /**
   * Выборка множества записей из таблицы states
   *
   * @param limit   кол-во записей выборки
   * @param offset  отступ от первой записи
   * @param orderBy поле, по которому будет производится сортировка
   * @param sort    порядок сортировки
   * @return коллекцию найденных записей
   */
  def selectAll(limit: Int = 100,
                offset: Int = 0,
                orderBy: String = "st.id",
                sort: String = "asc")
               (implicit session: DBSession): List[StateEntity] =
    withSQL {
      select.all(st).from(StateEntity as st)
        .orderBy(SQLSyntax.createUnsafely(orderBy))
        .append(SQLSyntax.createUnsafely(sort))
        .limit(limit)
        .offset(offset)
    }.map(StateEntity(st.resultName)).list.apply()

  /**
   * Изменение записи в таблице states
   *
   * @param row запись с измененными данными
   */
  def update(row: StateEntity)
            (implicit session: DBSession): Unit =
    withSQL {
      QueryDSL.update(StateEntity)
        .set(
          stC.name -> row.name
        )
        .where.eq(stC.id, row.id)
    }.update.apply()

  /**
   * Удаление записи из таблицы states по id
   *
   * @param id идентификатор, по которому будет выполнено удаление
   */
  def deleteById(id: UUID)
                (implicit session: DBSession): Unit =
    withSQL {
      deleteFrom(StateEntity)
        .where.eq(stC.id, id)
    }.update.apply()
}