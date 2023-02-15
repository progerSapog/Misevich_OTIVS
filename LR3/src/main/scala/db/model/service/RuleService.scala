package db.model.service

import db.model.dao.RuleDAO
import db.model.domain.Rule
import db.model.mapper.RuleMapper
import scalikejdbc.DB

import java.util.UUID

/**
 * Объект для взаимодействия с таблицей rules из-вне
 */
object RuleService extends TRuleService {
  /**
   * Вставка записи в таблицу
   *
   * @param model запись для вставки
   */
  override def insert(model: Rule): Unit =
    DB autoCommit { implicit session =>
      RuleDAO.insert(
        RuleMapper.model2entity(model)
      )
    }

  /**
   * Вставка множества записей в таблицу
   *
   * @param models записи для вставки
   */
  override def insertMulti(models: Seq[Rule]): Unit =
    DB autoCommit { implicit session =>
      RuleDAO.multiInsert(
        models.map(RuleMapper.model2entity)
      )
    }

  /**
   * Выбор записи по id
   *
   * @param id идентификатор по которому будет производиться выборка
   * @return Option найденной сущности. Если сущности нет, то Option будет пуст.
   */
  override def selectById(id: UUID): Option[Rule] =
    DB readOnly { implicit session =>
      RuleDAO.selectById(id).map(RuleMapper.entity2domain)
    }

  /**
   * Выборка множества записей из таблицы
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
                         sort: String): Seq[Rule] =
    DB readOnly { implicit session =>
      RuleDAO.selectAll(limit, offset, orderBy, sort)
        .map(RuleMapper.entity2domain)
    }

  /**
   * Изменение записи
   *
   * @param model запись с изменными данными
   */
  override def update(model: Rule): Unit =
    DB autoCommit { implicit session =>
      RuleDAO.update(
        RuleMapper.model2entity(model)
      )
    }

  /**
   * Удаление записи по id
   *
   * @param id идентификатор, по которому будет выполнено удаление
   */
  override def deleteById(id: UUID): Unit =
    DB autoCommit { implicit session =>
      if (RuleDAO.selectById(id).nonEmpty)
        RuleDAO.deleteById(id)
    }
}
