package com.tw ter.product_m xer.core.funct onal_component.conf gap 

 mport com.tw ter.t  l nes.conf gap .Param

/** A [[Param]] used for constant values w re   do not requ re back ng by feature sw c s or dec ders */
case class Stat cParam[ValueType](value: ValueType) extends Param[ValueType](value)
