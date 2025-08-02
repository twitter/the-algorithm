package com.twitter.servo.util

class ThreadLocalStringBuilder(initialSize: Int) etwittertends ThreadLocal[StringBuilder] {
  override def initialValue = new StringBuilder(initialSize)

  def apply() = {
    val buf = get
    buf.setLength(0)
    buf
  }
}
