import db.model.service.{EventService, RuleService, StateService}
import scalikejdbc.config.DBs

import java.awt._
import java.util.UUID
import javax.swing.{JButton, JFrame, JLabel, JPanel}

/**
 * Точка входа в программу
 */
object Main extends App {
  DBs.setupAll() //установка соединения с БД путём чтения конфиг. файла

  /**
   * Создание сетки располодения объектов
   */
  def getConstraints(gridX: Int,
                     gridY: Int,
                     gridHeight: Int,
                     gridWidth: Int,
                     gridWeightX: Int = 0,
                     gridWeightY: Int = 0,
                     anchor: Int = 10,
                     fill: Int = 0,
                     insets: Insets = new Insets(0, 0, 0, 0),
                     ipadX: Int = 0,
                     ipadY: Int = 0,
                    ): GridBagConstraints = {
    new GridBagConstraints(gridX,
      gridY,
      gridWidth,
      gridHeight,
      gridWeightX,
      gridWeightY,
      anchor,
      fill,
      insets,
      ipadX,
      ipadY)
  }

  /**
   * Класс обработчки событий
   */
  class EventDriver(events: Map[UUID, String], sequence: Map[UUID, (UUID, UUID)], first: UUID) {
    val revertedEvents: Map[String, UUID] = for ((k, v) <- events) yield (v, k)
    var current: String = events.getOrElse(first, throw new IllegalArgumentException("event list must not contain 0 event"))

    def move(isR: Boolean): Unit = {
      val i = revertedEvents(current)
      current = if (isR) events(sequence(i)._2) else events(sequence(i)._1)
    }

    def next(isR: Boolean): String = {
      val i = revertedEvents(current)
      if (isR) events(sequence(i)._2)
      else events(sequence(i)._1)
    }

    def isLeave: Boolean = !sequence.contains(revertedEvents(current))

    def curN: UUID = revertedEvents(current)

    def nextN(isR: Boolean): UUID = revertedEvents(next(isR))
  }

  object EventDriver {
    def apply(events: Map[UUID, String], sequence: Map[UUID, (UUID, UUID)], first: UUID): EventDriver = new
        EventDriver(events, sequence, first)

    val RIGHT = true
    val LEFT = false
  }

  /**
   * Создания "окна" приложения
   */
  val jFrame: JFrame = new JFrame() {
    val dimension: Dimension = Toolkit.getDefaultToolkit.getScreenSize
    val windowSize: (Int, Int) = (1300, 700)
    setTitle("MyApp")
    setBackground(Color.BLACK)
    setVisible(true)
    setDefaultCloseOperation(1)
    setBounds(dimension.width / 2 - windowSize._1 / 2,
      dimension.height / 2 - windowSize._2 / 2,
      windowSize._1,
      windowSize._2)
  }

  //Получение событий из БД
  val _events = EventService.selectAll()
  val events = _events.map(e => e.id -> e.content).toMap
  //получили пачки event->n,y
  val sequence = RuleService.selectAll()
    .map(r => r.eventId -> (r.r2, r.r1)).toMap

  //первый стейт
  val fState = StateService.selectAll().find(_.name == "Начально событие").get
  println(fState)

  //первый event
  val first = _events.find(fState.id == _.stateId)
  println(s"first = $first")


  val eventDriver = EventDriver(events, sequence, first.get.id)
  var nextAction = false

  //Добавление кнопок навигации
  val yButton = new JButton("Yes")
  val nButton = new JButton("No")
  val aButton = new JButton("Apply event")

  val curEventLabel = new JLabel("")
  val nextEventLabel = new JLabel("")

  //Перерисовка в зависимости от ответа
  def renew(): Unit = {
    curEventLabel.setText(s"${eventDriver.current}")
    if (eventDriver.isLeave)
      nextEventLabel.setText("Конечное событие")
    else
      nextEventLabel.setText(s"${eventDriver.next(nextAction)}")
  }

  renew()

  //Добавление обработчиков событий на кнопки
  yButton.addActionListener(_ => {
    nextAction = true
    renew()
  })

  nButton.addActionListener(_ => {
    nextAction = false
    renew()
  })

  aButton.addActionListener(_ => {
    if (eventDriver.isLeave) {}
    else {
      eventDriver.move(nextAction)
      nextAction = false
      renew()
    }
  })

  //Создание и добавление элементов приложения
  val jPanel1 = new JPanel()
  val jPanel2 = new JPanel()
  val jPanel3 = new JPanel()
  val jPanel4 = new JPanel()
  val jPanel = new JPanel()
  jPanel.setLayout(new GridBagLayout())

  jPanel1.add(curEventLabel)
  jPanel3.add(nextEventLabel)
  jPanel2.add(yButton)
  jPanel2.add(nButton)
  jPanel4.add(aButton)

  jPanel.add(jPanel1, getConstraints(0, 0, 1, 1))
  jPanel.add(jPanel2, getConstraints(0, 1, 1, 1))
  jPanel.add(jPanel3, getConstraints(0, 2, 1, 1))
  jPanel.add(jPanel4, getConstraints(0, 3, 1, 1))
  jFrame.add(jPanel)
  jFrame.revalidate()
}
