package db.models.dao

import scalikejdbc._

import java.util.UUID

case class StateEntity(id: UUID,
                       name: String) extends TEntity

object StateEntity extends SQLSyntaxSupport[StateEntity] {
  override val tableName = "states"

  val st: QuerySQLSyntaxProvider[SQLSyntaxSupport[StateEntity], StateEntity] = StateEntity.syntax("st")
  val stC: ColumnName[StateEntity] = StateEntity.column

  def apply(r: ResultName[StateEntity])(rs: WrappedResultSet): StateEntity =
    new StateEntity(
      id = UUID.fromString(rs.get(r.id)),
      name = rs.string(r.name)
    )
}
