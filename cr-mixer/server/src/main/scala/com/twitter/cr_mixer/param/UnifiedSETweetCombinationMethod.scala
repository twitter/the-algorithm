package com.tw ter.cr_m xer.param

 mport scala.language. mpl c Convers ons

object Un f edSET etComb nat on thod extends Enu rat on {

  protected case class Comb nat onType(s: Str ng) extends super.Val

   mpl c  def valueToComb nat onType(x: Value): Comb nat onType = x.as nstanceOf[Comb nat onType]

  val Default: Value = Comb nat onType("")
  val  nterleave: Value = Comb nat onType(" nterleave")
  val Frontload: Value = Comb nat onType("Frontload")
  val Backf ll: Value = Comb nat onType("Backf ll")
}
