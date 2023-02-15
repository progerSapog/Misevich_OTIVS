package db.models.dao

import db.models.dao.StateEntity.{st, stC}
import scalikejdbc._

import java.util.UUID

object StateDAO extends TDAO[StateEntity] {

  def insert(row: StateEntity)
            (implicit session: DBSession): Unit =
    withSQL {
      insertInto(StateEntity)
        .namedValues(
          stC.id -> row.id,
          stC.name -> row.name
        )
    }.update.apply()

  def insertMulti(rows: Seq[StateEntity])
                 (implicit session: DBSession): Unit = {
    val batchParams: Seq[Seq[Any]] = rows.map(row => Seq(row.id, row.name))

    withSQL {
      insertInto(StateEntity)
        .namedValues(
          stC.id -> sqls.?,
          stC.name -> sqls.?
        )
    }.batch(batchParams: _*).apply()
  }

  def selectById(id: UUID)
                (implicit session: DBSession): Option[StateEntity] =
    withSQL {
      select.from(StateEntity as st)
        .where.eq(st.id, id)
    }.map(StateEntity(st.resultName)).single.apply()

  def selectAll(limit: Int = 100,
                offset: Int = 0,
                orderBy: String = "id",
                sort: String = "asc")
               (implicit session: DBSession): List[StateEntity] =
    withSQL {
      select.all(st).from(StateEntity as st)
        .orderBy(SQLSyntax.createUnsafely(orderBy))
        .append(SQLSyntax.createUnsafely(sort))
        .limit(limit)
        .offset(offset)
    }.map(StateEntity(st.resultName)).list.apply()

  def update(row: StateEntity)
            (implicit session: DBSession): Unit =
    withSQL {
      QueryDSL.update(StateEntity)
        .set(
          stC.name -> row.name
        )
        .where.eq(stC.id, row.id)
    }.update.apply()

  def deleteById(id: UUID)
                (implicit session: DBSession): Unit =
    withSQL {
      deleteFrom(StateEntity)
        .where.eq(stC.id, id)
    }.update.apply()
}