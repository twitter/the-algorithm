package com.tw ter.product_m xer.component_l brary.exper  nts. tr cs

// Base tra  for all placeholder values
sealed tra  Na d {
  def na : Str ng
}

case class Const(overr de val na : Str ng) extends Na d

// conta ns only cl ent event patterns
case class CEPattern(
  overr de val na : Str ng,
  cl ent: Str ng = "",
  page: Str ng = "",
  sect on: Str ng = "",
  component: Str ng = "",
  ele nt: Str ng = "",
  act on: Str ng = "",
  stra ner: Str ng = "")
    extends Na d {

  overr de def toStr ng: Str ng = {
    "\"" + cl ent + ":" + page + ":" + sect on + ":" + component + ":" + ele nt + ":" + act on + "\""
  }

}

case class Top c(
  overr de val na : Str ng,
  top c d: Str ng = "")
    extends Na d

object PlaceholderConf g {
  type PlaceholderKey = Str ng
  type Placeholder = Seq[Na d]
  type PlaceholdersMap = Map[PlaceholderKey, Placeholder]
}
