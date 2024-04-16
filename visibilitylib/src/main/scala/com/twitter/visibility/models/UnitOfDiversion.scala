package com.tw ter.v s b l y.models

tra  Un OfD vers on {

  def apply: (Str ng, Any)
}

object Un OfD vers on {
  case class Conversat on d(conversat on d: Long) extends Un OfD vers on {
    overr de def apply: (Str ng, Any) = ("conversat on_ d", conversat on d)
  }

  case class T et d(t et d: Long) extends Un OfD vers on {
    overr de def apply: (Str ng, Any) = ("t et_ d", t et d)
  }
}
