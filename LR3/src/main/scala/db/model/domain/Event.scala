package db.model.domain

import java.util.UUID

/**
 * Класс Событий для отображения
 *
 * @param id идентификатор
 * @param content текст
 * @param state состояния события
 */
case class Event(id: UUID,
                 content: String,
                 stateId: UUID) extends TDomain
