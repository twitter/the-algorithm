package com.tw ter.t  l nes.pred ct on.features.esc rb rd

 mport com.tw ter.dal.personal_data.thr ftjava.PersonalDataType._
 mport com.tw ter.ml.ap .Feature
 mport java.ut l.{Set => JSet}
 mport scala.collect on.JavaConverters._

object Esc rb rdFeatures {
  val T etGroup ds = new Feature.SparseB nary("esc rb rd.t et_group_ ds")
  val T etDoma n ds = new Feature.SparseB nary("esc rb rd.t et_doma n_ ds", Set(Doma n d).asJava)
  val T etEnt y ds =
    new Feature.SparseB nary("esc rb rd.t et_ent y_ ds", Set(Semant ccoreClass f cat on).asJava)
}

case class Esc rb rdFeatures(
  t et d: Long,
  t etGroup ds: JSet[Str ng],
  t etDoma n ds: JSet[Str ng],
  t etEnt y ds: JSet[Str ng])
