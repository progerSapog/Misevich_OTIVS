package db.model.domain

import java.util.UUID

/**
 * Класс правил для отображения
 *
 * @param id      идентификатор
 * @param eventId идентификатор события для которого построено правило
 * @param r1      переход при полож. ответе
 * @param r2      переход при отриц. ответе
 */
case class Rule(id: UUID,
                eventId: UUID,
                r1: UUID,
                r2: UUID) extends TDomain
