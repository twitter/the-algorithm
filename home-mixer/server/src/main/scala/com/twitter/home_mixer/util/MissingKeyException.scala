package com.twitter.home_mixer.util

object MissingKeyException extends Exception("Missing key") {
  override def toString: String = getMessage
}
