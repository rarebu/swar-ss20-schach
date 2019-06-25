//package de.htwg.se.Schach.model.fileIoComponent.fileIoXmlImpl
val a = 2
println(a)
import _root_.de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.Field


  val size = 9
//  print(size)

  def toXml = {
    <grid size={size.toString}>
    </grid>
  }

  println(toXml)

  val field = new Field
  val filledField = field.move(1, 0, 3, 0).get
  println(filledField.toString)

  val fileIO = new de.htwg.se.Schach.model.fileIoComponent.fileIoXmlImpl.FileIOXml(filledField)
  val xml = fileIO.fieldToXml
print(xml)


//print(ioWorksheet.)