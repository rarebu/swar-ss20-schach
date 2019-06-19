import java.awt.Color
import java.text.Format

import javax.swing.{JTextField, SwingConstants}

import scala.swing.{FormattedTextField, TextField}

val a = new TextField("hallo").background = Color.BLACK
val b = new JTextField("hi")
b.setHorizontalAlignment(SwingConstants.CENTER)
val c = new FormattedTextField(new Format())
