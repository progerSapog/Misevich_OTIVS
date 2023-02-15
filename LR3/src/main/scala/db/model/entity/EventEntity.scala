package db.model.entity

import scalikejdbc._

import java.util.UUID

/**
 * Класс - отображение записей в таблице events
 *
 * @param id      UUID primary key
 * @param content varchar(255)
 * @param state   UUID foreign key states(id)
 */
case class EventEntity(id: UUID,
                       content: String,
                       state: StateEntity) extends TEntity

/**
 * Объект компаньон для связи таблицы БД и классом отображения
 */
object EventEntity extends SQLSyntaxSupport[EventEntity] {
  override val tableName = "events";

  val ev: QuerySQLSyntaxProvider[SQLSyntaxSupport[EventEntity], EventEntity] = EventEntity.syntax("ev")
  val evC: ColumnName[EventEntity] = EventEntity.column

  def apply(r: ResultName[StateEntity])
           (rs: WrappedResultSet): EventEntity =
    new EventEntity(
      id = UUID.fromString(rs.get(column.id)),
      content = rs.string(column.content),
      state = StateEntity(r)(rs)
    )
}
