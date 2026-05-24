package com.twitter.servo.util

class ThreadLocalStringBuilder(initialSize: Int) extends ThreadLocal[StringBuilder] {
  override def initialValue = new StringBuilder(initialSize)

  def apply() = {
    val buf = get
    buf.setLength(0)
    buf
  }
}
