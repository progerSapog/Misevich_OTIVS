package db.model.entity

import scalikejdbc._

import java.util.UUID

/**
 * Класс - отображение записей в таблице states
 *
 * @param id   UUID primary key
 * @param name varchar(255)
 */
case class StateEntity(id: UUID,
                       name: String) extends TEntity

/**
 * Объект компаньон для связи таблицы БД и классом отображения
 */
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
