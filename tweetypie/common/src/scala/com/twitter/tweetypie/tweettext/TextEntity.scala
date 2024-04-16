package com.tw ter.t etyp e.t ettext

/**
 * A type class for ent  es found w h n a p ece of t et text.
 */
tra  TextEnt y[T] {
  def from ndex(ent y: T): Short
  def to ndex(ent y: T): Short
  def move(ent y: T, from ndex: Short, to ndex: Short): T
}

object TextEnt y {
  def from ndex[T: TextEnt y](ent y: T): Short =
     mpl c ly[TextEnt y[T]].from ndex(ent y)

  def to ndex[T: TextEnt y](ent y: T): Short =
     mpl c ly[TextEnt y[T]].to ndex(ent y)

  def move[T: TextEnt y](ent y: T, from ndex: Short, to ndex: Short): T =
     mpl c ly[TextEnt y[T]].move(ent y, from ndex, to ndex)

  def sh ft[T: TextEnt y](ent y: T, offset: Short): T =
    move(ent y, (from ndex(ent y) + offset).toShort, (to ndex(ent y) + offset).toShort)
}
