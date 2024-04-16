package com.tw ter.v s b l y.ut l

object Nam ngUt ls {
  def getFr endlyNa (a: Any): Str ng = getFr endlyNa FromClass(a.getClass)
  def getFr endlyNa FromClass(a: Class[_]): Str ng = a.getS mpleNa .str pSuff x("$")
}
