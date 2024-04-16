package com.tw ter. nteract on_graph.sc o.common

 mport com.tw ter.ut l.Durat on
 mport org.joda.t  . nterval

object DateUt l {
  def emb ggen(date nterval:  nterval, durat on: Durat on):  nterval = {

    val days = durat on. nDays
    val newStart = date nterval.getStart.m nusDays(days)
    val newEnd = date nterval.getEnd.plusDays(days)
    new  nterval(newStart, newEnd)
  }

  def subtract(date nterval:  nterval, durat on: Durat on):  nterval = {
    val days = durat on. nDays
    val newStart = date nterval.getStart.m nusDays(days)
    val newEnd = date nterval.getEnd.m nusDays(days)
    new  nterval(newStart, newEnd)
  }

  def prependDays(date nterval:  nterval, durat on: Durat on):  nterval = {
    val days = durat on. nDays
    val newStart = date nterval.getStart.m nusDays(days)
    new  nterval(newStart, date nterval.getEnd.to nstant)
  }
}
