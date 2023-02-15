package db.models.dao

import scalikejdbc._

import java.util.UUID

case class EventEntity(id: UUID,
                       content: String,
                       state: StateEntity) extends TEntity

object EventEntity extends SQLSyntaxSupport[EventEntity] {
  override val tableName = "events";

  val ev: QuerySQLSyntaxProvider[SQLSyntaxSupport[EventEntity], EventEntity] = EventEntity.syntax("ev")
  val evC: ColumnName[EventEntity] = EventEntity.column

  def apply(er: ResultName[EventEntity],
            r: ResultName[StateEntity])
           (rs: WrappedResultSet): EventEntity =
    new EventEntity(
      id = UUID.fromString(rs.get(column.id)),
      content = rs.string(column.content),
      state = StateEntity(r)(rs)
    )
}
