package db.models.dao

import scalikejdbc._

import java.util.UUID

case class RuleEntity(id: UUID,
                      eventId: UUID,
                      r1: UUID,
                      r2: UUID) extends TEntity

object RuleEntity extends SQLSyntaxSupport[RuleEntity] {
  override val tableName = "rules"

  val r: QuerySQLSyntaxProvider[SQLSyntaxSupport[RuleEntity], RuleEntity] = RuleEntity.syntax("r")
  val rC: ColumnName[RuleEntity] = RuleEntity.column

  def apply(r: ResultName[RuleEntity])(rs: WrappedResultSet): RuleEntity =
    new RuleEntity(
      id = UUID.fromString(rs.get(r.id)),
      eventId = UUID.fromString(rs.get(r.eventId)),
      r1 = UUID.fromString(rs.get(r.r1)),
      r2 = UUID.fromString(rs.get(r.r2))
    )
}
