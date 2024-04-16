package com.tw ter.v s b l y.rules

 mport com.tw ter.v s b l y.features.AuthorMutesV e r
 mport com.tw ter.v s b l y.rules.Cond  on.BooleanFeatureCond  on
 mport com.tw ter.v s b l y.rules.Cond  on.ProtectedV e r
 mport com.tw ter.v s b l y.rules.Reason.Unspec f ed

object Follo rRelat ons {

  case object AuthorMutesV e rFeature extends BooleanFeatureCond  on(AuthorMutesV e r)

  object AuthorMutesV e rRule
      extends OnlyW nNotAuthorV e rRule(
        act on = Drop(Unspec f ed),
        cond  on = AuthorMutesV e rFeature)

  object ProtectedV e rRule
      extends OnlyW nNotAuthorV e rRule(act on = Drop(Unspec f ed), cond  on = ProtectedV e r)

}
