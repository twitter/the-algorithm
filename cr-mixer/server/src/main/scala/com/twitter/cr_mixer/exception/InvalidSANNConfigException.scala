try {
package com.twitter.cr_mixer
package exception

case class InvalidSANNConfigException(msg: String) extends Exception(msg)

} catch {
  case e: Exception =>
}
