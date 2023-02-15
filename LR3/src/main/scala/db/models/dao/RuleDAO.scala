package db.models.dao

import db.models.dao.RuleEntity.{r, rC}
import scalikejdbc._

import java.util.UUID

object RuleDAO extends TDAO[RuleEntity] {
  /**
   * Вставка записи в таблицу
   *
   * @param row запись для вставки
   */
  override def insert(row: RuleEntity)
                     (implicit session: DBSession): Unit =
    withSQL {
      insertInto(RuleEntity)
        .namedValues(
          rC.id -> row.id,
          rC.eventId -> row.eventId,
          rC.r1 -> row.r1,
          rC.r2 -> row.r2
        )
    }.update.apply()

  /**
   * Вставка множества записей в таблицу
   *
   * @param rows записи для вставки
   */
  override def insertMulti(rows: Seq[RuleEntity])
                          (implicit session: DBSession): Unit = {
    val batchParams: Seq[Seq[Any]] = rows.map(row => Seq(row.id, row.eventId, row.r1, row.r2))

    withSQL {
      insertInto(RuleEntity)
        .namedValues(
          rC.id -> sqls.?,
          rC.eventId -> sqls.?,
          rC.r1 -> sqls.?,
          rC.r2 -> sqls.?
        )
    }.batch(batchParams: _*).apply()

  }

  /**
   * Выбор записи по id
   *
   * @param id идентификатор по которому будет производиться выборка
   * @return Option найденной сущности. Если сущности нет, то Option будет пуст.
   */
  override def selectById(id: UUID)
                         (implicit session: DBSession): Option[RuleEntity] =
    withSQL {
      select.from(RuleEntity as r)
        .where.eq(r.id, id)
    }.map(RuleEntity(r.resultName)).single.apply()

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
                         orderBy: String,
                         sort: String)
                        (implicit session: DBSession): Seq[RuleEntity] =
    withSQL {
      select.all(r).from(RuleEntity as r)
        .orderBy(SQLSyntax.createUnsafely(orderBy))
        .append(SQLSyntax.createUnsafely(sort))
        .limit(limit)
        .offset(offset)
    }.map(RuleEntity(r.resultName)).list.apply()


  /**
   * Изменение записи
   *
   * @param row запись с изменными данными
   */
  override def update(row: RuleEntity)
                     (implicit session: DBSession): Unit =
    withSQL {
      QueryDSL.update(RuleEntity)
        .set(
          rC.id -> row.id,
          rC.eventId -> row.eventId,
          rC.r1 -> row.r1,
          rC.r2 -> row.r2
        )
        .where.eq(rC.id, row.id)
    }.update.apply()

  /**
   * Удаление записи по id
   *
   * @param id идентификатор, по которому будет выполнено удаление
   */
  override def deleteById(id: UUID)
                         (implicit session: DBSession): Unit =
    withSQL {
      deleteFrom(RuleEntity)
        .where.eq(rC.id, id)
    }.update.apply()
}
