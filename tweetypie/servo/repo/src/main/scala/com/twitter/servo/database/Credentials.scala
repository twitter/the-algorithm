package com.twitter.servo.database

import com.twitter.util.security
import java.io.File

sealed trait Credentials {
  def username: String
  def password: String
}

case class InlineCredentials(username: String, password: String) extends Credentials

case class FileCredentials(
  path: String,
  usernameField: String = "db_username",
  passwordField: String = "db_password")
    extends Credentials {
  lazy val (username, password) = {
    val credentials = security.Credentials(new File(path))
    (credentials(usernameField), credentials(passwordField))
  }
}
