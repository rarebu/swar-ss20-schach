package de.htwg.se.Schach

import com.google.inject.AbstractModule
import de.htwg.se.Schach.model.fileIoComponent.FileIOInterface
import net.codingwell.scalaguice.ScalaModule
import de.htwg.se.Schach.model.fileIoComponent._

class SchachModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = bind[FileIOInterface].to[fileIoJsonImpl.FileIO]
}
