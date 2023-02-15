package db.model.service
import db.model.dao.StateDAO
import db.model.domain.State
import db.model.mapper.StateMapper
import scalikejdbc.DB

import java.util.UUID

/**
 * Объект для взаимодействия с таблицей states из-вне
 */
object StateService extends TStateService {
  /**
   * Вставка записи в таблицу
   *
   * @param model запись для вставки
   */
  override def insert(model: State): Unit =
    DB autoCommit { implicit session =>
      StateDAO.insert(
        StateMapper.model2entity(model)
      )
    }

  /**
   * Вставка множества записей в таблицу
   *
   * @param models записи для вставки
   */
  override def insertMulti(models: Seq[State]): Unit =
    DB autoCommit { implicit session =>
      StateDAO.multiInsert(
        models.map(StateMapper.model2entity)
      )
    }

  /**
   * Выбор записи по id
   *
   * @param id идентификатор по которому будет производиться выборка
   * @return Option найденной сущности. Если сущности нет, то Option будет пуст.
   */
  override def selectById(id: UUID): Option[State] =
    DB readOnly { implicit session =>
      StateDAO.selectById(id).map(StateMapper.entity2domain)
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
                         orderBy: String = "st.id",
                         sort: String): Seq[State] =
    DB readOnly { implicit session =>
      StateDAO.selectAll(limit, offset, orderBy, sort)
        .map(StateMapper.entity2domain)
    }

  /**
   * Изменение записи
   *
   * @param model запись с изменными данными
   */
  override def update(model: State): Unit =
    DB autoCommit { implicit session =>
      StateDAO.update(
        StateMapper.model2entity(model)
      )
    }

  /**
   * Удаление записи по id
   *
   * @param id идентификатор, по которому будет выполнено удаление
   */
  override def deleteById(id: UUID): Unit =
    DB autoCommit { implicit session =>
      if (StateDAO.selectById(id).nonEmpty)
        StateDAO.deleteById(id)
    }
}
