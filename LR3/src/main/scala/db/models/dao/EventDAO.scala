package db.models.dao

import db.models.dao.EventEntity.ev
import db.models.dao.StateEntity.st
import scalikejdbc._

import java.util.UUID

object EventDAO extends TDAO[EventEntity] {
  /**
   * Вставка записи в таблицу
   *
   * @param row запись для вставки
   */
  override def insert(row: EventEntity)(implicit session: DBSession): Unit = ???

  /**
   * Вставка множества записей в таблицу
   *
   * @param rows записи для вставки
   */
  override def insertMulti(rows: Seq[EventEntity])(implicit session: DBSession): Unit = ???

  /**
   * Выбор записи по id
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
    }.map(rs => EventEntity(ev.resultName, st.resultName)(rs)).single.apply()

  /**
   * Выборка множества записей из таблицы
   *
   * @param limit   кол-во записей выборк
   * @param offset  отступ от первой записи
   * @param orderBy поле, по которому будет производится сортировка
   * @param sort    порядок сортирвоки
   * @return коллекцию найденных записей
   */
  override def selectAll(limit: Int,
                         offset: Int,
                         orderBy: String = "ev.id",
                         sort: String)
                        (implicit session: DBSession): Seq[EventEntity] =
    withSQL {
      select.all(ev).from(EventEntity as ev)
        .join(StateEntity as st)
        .on(ev.id, st.id)
        .orderBy(SQLSyntax.createUnsafely(orderBy))
        .append(SQLSyntax.createUnsafely(sort))
        .limit(limit)
        .offset(offset)
    }.map(rs => EventEntity(ev.resultName, st.resultName)(rs)).list.apply()

  /**
   * Изменение записи
   *
   * @param row запись с изменными данными
   */
  override def update(row: EventEntity)(implicit session: DBSession): Unit = ???

  /**
   * Удаление записи по id
   *
   * @param id идентификатор, по которому будет выполнено удаление
   */
  override def deleteById(id: UUID)(implicit session: DBSession): Unit = ???
}
