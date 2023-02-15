import db.model.service.{ EventService, RuleService, StateService }
import scalikejdbc.config.DBs

import java.awt._
import java.util.UUID
import javax.swing.{ JButton, JFrame, JLabel, JPanel }

object Main extends App {
  DBs.setupAll()
  def getConstraints(
                      gridx:Int,
                      gridy:Int,
                      gridheight:Int,
                      gridwidth:Int,
                      gridweightx:Int = 0,
                      gridweighty:Int = 0,
                      anchor:Int = 10,
                      fill:Int = 0,
                      insets:Insets = new Insets(0,0,0,0),
                      ipadx:Int = 0,
                      ipady:Int = 0,
                    ): GridBagConstraints = {
    new GridBagConstraints(gridx,gridy,gridwidth,gridheight,gridweightx,gridweighty,anchor,fill,insets,ipadx,ipady)
  }
  
  
  
  class EventDriver(events: Map[UUID,String], sequence:Map[UUID,(UUID,UUID)],first:UUID){
    val revertedEvents: Map[String, UUID ] = for((k,v)<- events) yield (v, k)
    var current: String = events.getOrElse(first, throw new IllegalArgumentException("event list must not contain 0 event"))
    def move(isR:Boolean): Unit = {
      val i = revertedEvents(current)
      current = if(isR) events(sequence(i)._2) else events(sequence(i)._1)
    }
    def next(isR:Boolean):String = {
      val i = revertedEvents(current)
      if(isR) events(sequence(i)._2)
      else events(sequence(i)._1)
    }
    def isLeave: Boolean = !sequence.contains(revertedEvents(current))
    def curN: UUID = revertedEvents(current)
    def nextN(isR:Boolean): UUID = revertedEvents(next(isR))
  }
  
  object EventDriver{
    def apply( events: Map[ UUID, String ], sequence: Map[ UUID, (UUID, UUID) ], first: UUID ): EventDriver = new
        EventDriver(events, sequence, first)
    val RIGHT = true
    val LEFT = false
  }
  
  val jFrame: JFrame = new JFrame(){
    val dimension: Dimension = Toolkit.getDefaultToolkit.getScreenSize
    val windowSize: (Int, Int) = (1300, 700)
    setTitle("MyApp")
    setBackground(Color.BLACK)
    setVisible(true)
    setDefaultCloseOperation(1)
    setBounds(dimension.width/2 - windowSize._1/2,
              dimension.height/2 - windowSize._2/2,
              windowSize._1,
              windowSize._2)
  }
  val _events = EventService.selectAll()
  val events = _events.map(e=>e.id->e.content).toMap
  //получили пачки event->n,y
  val sequence = RuleService.selectAll()
                         .map(r=>r.eventId->(r.r2,r.r1)).toMap
  //первый стейт
  val fstate = StateService.selectAll().find(_.name == "Начально событие").get
  println(fstate)
  
  //первый event
  val first = _events.find(fstate.id==_.stateId)
  println(s"first = ${ first }")
  
  
  val eventDriver = EventDriver(events,sequence,first.get.id)
  
  var nextAction = false
  
  val yButton = new JButton("Yes")
  val nButton = new JButton("No")
  val aButton = new JButton("Apply event")
  
  val curEventLabel = new JLabel("")
  val nextEventLabel = new JLabel("")
  
  
  
  def renew(): Unit = {
    curEventLabel.setText(s"${eventDriver.current}")
    if(eventDriver.isLeave)
      nextEventLabel.setText("Конечное событие")
    else
      nextEventLabel.setText(s"${eventDriver.next(nextAction)}")
  }
  renew()
  
  yButton.addActionListener( _ => {
    nextAction = true
    renew()
  })
  
  nButton.addActionListener( _ => {
    nextAction = false
    renew()
  })
  
  aButton.addActionListener( _ => {
    if(eventDriver.isLeave) {}
    else {
      eventDriver.move(nextAction)
      nextAction = false
      renew()
    }
  })
  
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
  
  jPanel.add(jPanel1,getConstraints(0,0,1,1))
  jPanel.add(jPanel2,getConstraints(0,1,1,1))
  jPanel.add(jPanel3,getConstraints(0,2,1,1))
  jPanel.add(jPanel4,getConstraints(0,3,1,1))
  jFrame.add(jPanel)
  jFrame.revalidate()
}
