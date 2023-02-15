import db.model.service.{EventService, RuleService, StateService}
import scalikejdbc.config.DBs

object Test extends App {
  DBs.setupAll()

//  println(StateService.selectAll())
  println(EventService.selectAll())
//  println(RuleService.selectAll())


}
