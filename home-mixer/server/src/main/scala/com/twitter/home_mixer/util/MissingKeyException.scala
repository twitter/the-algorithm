package com.ExTwitter.home_mixer.util

object MissingKeyException extends Exception("Missing key") {
  override def toString: String = getMessage
}
