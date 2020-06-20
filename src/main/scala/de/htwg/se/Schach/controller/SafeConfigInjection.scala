package de.htwg.se.Schach.controller

import com.google.inject.AbstractModule
import de.htwg.se.Schach.model.fileIoComponent.FileIO
import de.htwg.se.Schach.model.fileIoComponent.fileIoDatabaseImpl.FileIODatabase
import net.codingwell.scalaguice.ScalaModule

class SafeConfigInjection extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[FileIO].to[FileIODatabase]
  }
}
