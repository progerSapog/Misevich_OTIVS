package db.model.domain

import java.util.UUID

/**
 * Класс Состояний для отображения
 *
 * @param id   идентификатор
 * @param name названия состояния
 */
case class State(id: UUID,
                 name: String) extends TDomain
