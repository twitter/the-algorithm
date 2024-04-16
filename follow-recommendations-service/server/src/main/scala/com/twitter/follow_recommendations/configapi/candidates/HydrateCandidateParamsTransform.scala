package com.tw ter.follow_recom ndat ons.conf gap .cand dates

 mport com.google. nject. nject
 mport com.google. nject.S ngleton
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasD splayLocat on
 mport com.tw ter.follow_recom ndat ons.common.base.Transform
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.ut l.logg ng.Logg ng

@S ngleton
class HydrateCand dateParamsTransform[Target <: HasParams w h HasD splayLocat on] @ nject() (
  cand dateParamsFactory: Cand dateUserParamsFactory[Target])
    extends Transform[Target, Cand dateUser]
    w h Logg ng {

  def transform(target: Target, cand dates: Seq[Cand dateUser]): St ch[Seq[Cand dateUser]] = {
    St ch.value(cand dates.map(cand dateParamsFactory.apply(_, target)))
  }
}
