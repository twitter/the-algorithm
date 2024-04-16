package com.tw ter.follow_recom ndat ons.common.cl ents.common

 mport com.tw ter.f nagle.Thr ftMux
 mport com.tw ter.f nagle.thr ft.Protocols
 mport com.tw ter.follow_recom ndat ons.common.constants.Serv ceConstants._
 mport com.tw ter. nject.thr ft.modules.Thr ftCl entModule
 mport scala.reflect.ClassTag

/**
 * bas c cl ent conf gurat ons that   apply for all of   cl ents go  n  re
 */
abstract class BaseCl entModule[T: ClassTag] extends Thr ftCl entModule[T] {
  def conf gureThr ftMuxCl ent(cl ent: Thr ftMux.Cl ent): Thr ftMux.Cl ent = {
    cl ent
      .w hProtocolFactory(
        Protocols.b naryFactory(
          str ngLengthL m  = Str ngLengthL m ,
          conta nerLengthL m  = Conta nerLengthL m ))
  }
}
