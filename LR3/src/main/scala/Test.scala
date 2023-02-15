import db.models.dao.{EventDAO, EventEntity, StateDAO, StateEntity}
import scalikejdbc.DB
import scalikejdbc.config.DBs

import java.util.UUID

object Test extends App {
  DBs.setupAll()

  //  DB localTx { implicit session =>
  //    StateDAO.insert(
  //      StateEntity(
  //        UUID.randomUUID(),
  //        "AAAAAAAAAAAA"
  //      )
  //    )
  //  }

  println(
    DB readOnly { implicit session =>
      EventDAO.selectAll()
    }
  )

}
