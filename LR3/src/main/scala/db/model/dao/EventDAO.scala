package db.model.dao

import db.model.entity.EventEntity.{ev, evC}
import db.model.entity.{EventEntity, StateEntity}
import db.model.entity.StateEntity.st
import scalikejdbc._

import java.util.UUID

/**
 * Объект доступа к записям таблицы events (события)
 */
object EventDAO extends TEventDAO {
  /**
   * Вставка записи в таблицу events
   *
   * @param row запись для вставки
   */
  override def insert(row: EventEntity)
                     (implicit session: DBSession): Unit =
    withSQL {
      insertInto(EventEntity)
        .namedValues(
          evC.id -> row.id,
          evC.content -> row.content,
          evC.stateId -> row.stateId
        )
    }.update.apply()

  /**
   * Вставка множества записей в таблицу events
   *
   * @param rows записи для вставки
   */
  override def multiInsert(rows: Seq[EventEntity])
                          (implicit session: DBSession): Unit = {
    val batchParams: Seq[Seq[Any]] = rows.map(row => Seq(row.id, row.content, row.stateId))

    withSQL {
      insertInto(EventEntity)
        .namedValues(
          evC.id -> sqls.?,
          evC.content -> sqls.?,
          evC.stateId -> sqls.?
        )
    }.batch(batchParams: _*).apply()
  }

  /**
   * Выбор записи по id из таблицы events
   *
   * @param id идентификатор по которому будет производиться выборка
   * @return Option найденной сущности. Если сущности нет, то Option будет пуст.
   */
  override def selectById(id: UUID)
                         (implicit session: DBSession): Option[EventEntity] =
    withSQL {
      select.from(EventEntity as ev)
        .join(StateEntity as st)
        .on(ev.id, st.id)
        .where.eq(ev.id, id)
    }.map(EventEntity(ev.resultName)).single.apply()

  /**
   * Выборка множества записей из таблицы events
   *
   * @param limit   кол-во записей выборки
   * @param offset  отступ от первой записи
   * @param orderBy поле, по которому будет производится сортировка
   * @param sort    порядок сортировки
   * @return коллекцию найденных записей
   */
  override def selectAll(limit: Int,
                         offset: Int,
                         orderBy: String = "ev.id",
                         sort: String)
                        (implicit session: DBSession): Seq[EventEntity] =
    withSQL {
      select.all(ev).from(EventEntity as ev)
        .orderBy(SQLSyntax.createUnsafely(orderBy))
        .append(SQLSyntax.createUnsafely(sort))
        .limit(limit)
        .offset(offset)
    }.map(EventEntity(ev.resultName)).list.apply()

  /**
   * Изменение записи в таблице Event
   *
   * @param row запись с измененными данными
   */
  override def update(row: EventEntity)
                     (implicit session: DBSession): Unit =
    withSQL {
      QueryDSL.update(EventEntity)
        .set(
          evC.id -> row.id,
          evC.content -> row.content,
          evC.stateId -> row.stateId
        )
        .where.eq(evC.id, row.id)
    }.update.apply()

  /**
   * Удаление записи из таблицы events по id
   *
   * @param id идентификатор, по которому будет выполнено удаление
   */
  override def deleteById(id: UUID)
                         (implicit session: DBSession): Unit =
    withSQL {
      deleteFrom(EventEntity)
        .where.eq(evC.id, id)
    }.update.apply()
}
