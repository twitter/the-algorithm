package com.tw ter.follow_recom ndat ons.common.transforms.recom ndat on_flow_ dent f er

 mport com.google. nject. nject
 mport com.tw ter.follow_recom ndat ons.common.base.Transform
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasRecom ndat onFlow dent f er
 mport com.tw ter.st ch.St ch

class AddRecom ndat onFlow dent f erTransform @ nject()
    extends Transform[HasRecom ndat onFlow dent f er, Cand dateUser] {

  overr de def transform(
    target: HasRecom ndat onFlow dent f er,
     ems: Seq[Cand dateUser]
  ): St ch[Seq[Cand dateUser]] = {
    St ch.value( ems.map { cand dateUser =>
      cand dateUser.copy(recom ndat onFlow dent f er = target.recom ndat onFlow dent f er)
    })
  }
}
