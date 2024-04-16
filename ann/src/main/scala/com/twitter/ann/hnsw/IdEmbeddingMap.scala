package com.tw ter.ann.hnsw

 mport com.tw ter.ann.common.Embedd ngType._
 mport java. o.OutputStream

tra   dEmbedd ngMap[T] {
  def put fAbsent( d: T, embedd ng: Embedd ngVector): Embedd ngVector
  def put( d: T, embedd ng: Embedd ngVector): Embedd ngVector
  def get( d: T): Embedd ngVector
  def  er():  erator[(T, Embedd ngVector)]
  def s ze():  nt
  def toD rectory(embedd ngF leOutputStream: OutputStream): Un 
}
