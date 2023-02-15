package db.model.service

import db.model.dao.EventDAO
import db.model.domain.Event
import db.model.mapper.EventMapper
import scalikejdbc.DB

import java.util.UUID

/**
 * Объект для взаимодействия с таблицей events из-вне
 */
object EventService extends TEventService {
  /**
   * Вставка записи в таблицу
   *
   * @param model запись для вставки
   */
  override def insert(model: Event): Unit =
    DB autoCommit { implicit session =>
      EventDAO.insert(
        EventMapper.model2entity(model)
      )
    }


  /**
   * Вставка множества записей в таблицу
   *
   * @param models записи для вставки
   */
  override def insertMulti(models: Seq[Event]): Unit =
    DB autoCommit { implicit session =>
      EventDAO.multiInsert(
        models.map(EventMapper.model2entity)
      )
    }

  /**
   * Выбор записи по id
   *
   * @param id идентификатор по которому будет производиться выборка
   * @return Option найденной сущности. Если сущности нет, то Option будет пуст.
   */
  override def selectById(id: UUID): Option[Event] =
    DB readOnly { implicit session =>
      EventDAO.selectById(id).map(EventMapper.entity2domain)
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
                         orderBy: String = "ev.id",
                         sort: String): Seq[Event] =
    DB readOnly { implicit session =>
      EventDAO.selectAll(limit, offset, orderBy, sort)
        .map(EventMapper.entity2domain)
    }

  /**
   * Изменение записи
   *
   * @param model запись с измененными данными
   */
  override def update(model: Event): Unit =
    DB autoCommit { implicit session =>
      EventDAO.update(
        EventMapper.model2entity(model)
      )
    }

  /**
   * Удаление записи по id
   *
   * @param id идентификатор, по которому будет выполнено удаление
   */
  override def deleteById(id: UUID): Unit =
    DB autoCommit { implicit session =>
      if (EventDAO.selectById(id).nonEmpty)
        EventDAO.deleteById(id)
    }
}
