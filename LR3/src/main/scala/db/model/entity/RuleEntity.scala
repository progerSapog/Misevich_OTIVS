package db.model.entity

import scalikejdbc._

import java.util.UUID

/**
 * Класс - отображение записей в таблице rules
 *
 * @param id      UUID primary key
 * @param eventId UUID foreign key events(id) - событие для которого строится правило
 * @param r1Id      UUID foreign key events(id) - переход при полож. ответе
 * @param r2Id      UUID foreign key events(id) - переход при отриц. ответ
 */
case class RuleEntity(id: UUID,
                      eventId: UUID,
                      r1Id: UUID,
                      r2Id: UUID) extends TEntity

/**
 * Объект компаньон для связи таблицы БД и классом отображения
 */
object RuleEntity extends SQLSyntaxSupport[RuleEntity] {
  override val tableName = "rules"

  val r: QuerySQLSyntaxProvider[SQLSyntaxSupport[RuleEntity], RuleEntity] = RuleEntity.syntax("r")
  val rC: ColumnName[RuleEntity] = RuleEntity.column

  def apply(r: ResultName[RuleEntity])(rs: WrappedResultSet): RuleEntity =
    new RuleEntity(
      id = UUID.fromString(rs.get(r.id)),
      eventId = UUID.fromString(rs.get(r.eventId)),
      r1Id = UUID.fromString(rs.get(r.r1Id)),
      r2Id = UUID.fromString(rs.get(r.r2Id))
    )
}
