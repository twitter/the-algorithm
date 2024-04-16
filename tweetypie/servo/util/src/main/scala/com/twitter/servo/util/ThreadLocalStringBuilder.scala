package com.tw ter.servo.ut l

class ThreadLocalStr ngBu lder( n  alS ze:  nt) extends ThreadLocal[Str ngBu lder] {
  overr de def  n  alValue = new Str ngBu lder( n  alS ze)

  def apply() = {
    val buf = get
    buf.setLength(0)
    buf
  }
}
