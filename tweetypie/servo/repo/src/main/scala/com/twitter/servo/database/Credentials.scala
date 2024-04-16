package com.tw ter.servo.database

 mport com.tw ter.ut l.secur y
 mport java. o.F le

sealed tra  Credent als {
  def userna : Str ng
  def password: Str ng
}

case class  nl neCredent als(userna : Str ng, password: Str ng) extends Credent als

case class F leCredent als(
  path: Str ng,
  userna F eld: Str ng = "db_userna ",
  passwordF eld: Str ng = "db_password")
    extends Credent als {
  lazy val (userna , password) = {
    val credent als = secur y.Credent als(new F le(path))
    (credent als(userna F eld), credent als(passwordF eld))
  }
}
