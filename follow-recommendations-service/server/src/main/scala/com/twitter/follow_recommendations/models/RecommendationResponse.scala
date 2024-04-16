package com.tw ter.follow_recom ndat ons.models

 mport com.tw ter.follow_recom ndat ons.{thr ftscala => t}
 mport com.tw ter.follow_recom ndat ons.logg ng.{thr ftscala => offl ne}
 mport com.tw ter.follow_recom ndat ons.common.models.Recom ndat on
 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng

case class Recom ndat onResponse(recom ndat ons: Seq[Recom ndat on]) extends HasMarshall ng {
  lazy val toThr ft: t.Recom ndat onResponse =
    t.Recom ndat onResponse(recom ndat ons.map(_.toThr ft))

  lazy val toOffl neThr ft: offl ne.Offl neRecom ndat onResponse =
    offl ne.Offl neRecom ndat onResponse(recom ndat ons.map(_.toOffl neThr ft))
}
