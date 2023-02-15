package db.model.dao

import db.model.entity.RuleEntity
import db.model.entity.RuleEntity.{r, rC}
import scalikejdbc._

import java.util.UUID

/**
 * Объект доступа к записям таблицы rules (правила)
 */
object RuleDAO extends TRuleDAO {
  /**
   * Вставка записи в таблицу rules
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
          rC.r1Id -> row.r1Id,
          rC.r2Id -> row.r2Id
        )
    }.update.apply()

  /**
   * Вставка множества записей в таблицу rules
   *
   * @param rows записи для вставки
   */
  override def multiInsert(rows: Seq[RuleEntity])
                          (implicit session: DBSession): Unit = {
    val batchParams: Seq[Seq[Any]] = rows.map(row => Seq(row.id, row.eventId, row.r1Id, row.r2Id))

    withSQL {
      insertInto(RuleEntity)
        .namedValues(
          rC.id -> sqls.?,
          rC.eventId -> sqls.?,
          rC.r1Id -> sqls.?,
          rC.r2Id -> sqls.?
        )
    }.batch(batchParams: _*).apply()

  }

  /**
   * Выбор записи по id из таблицы rules
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
   * Выборка множества записей из таблицы rules
   *
   * @param limit   кол-во записей выборки
   * @param offset  отступ от первой записи
   * @param orderBy поле, по которому будет производится сортировка
   * @param sort    порядок сортировки
   * @return коллекцию найденных записей
   */
  override def selectAll(limit: Int,
                         offset: Int,
                         orderBy: String = "r.id",
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
   * Изменение записи в таблице rules
   *
   * @param row запись с измененными данными
   */
  override def update(row: RuleEntity)
                     (implicit session: DBSession): Unit =
    withSQL {
      QueryDSL.update(RuleEntity)
        .set(
          rC.id -> row.id,
          rC.eventId -> row.eventId,
          rC.r1Id -> row.r1Id,
          rC.r2Id -> row.r2Id
        )
        .where.eq(rC.id, row.id)
    }.update.apply()

  /**
   * Удаление записи из таблицы rules по id
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
