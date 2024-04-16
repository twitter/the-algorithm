package com.tw ter.v s b l y.rules

 mport com.tw ter.v s b l y.conf gap .params.RuleParams
 mport com.tw ter.v s b l y.rules.Cond  on.And
 mport com.tw ter.v s b l y.rules.Cond  on.DmConversat onLastReadableEvent d sVal d
 mport com.tw ter.v s b l y.rules.Cond  on.DmConversat onT  l ne sEmpty
 mport com.tw ter.v s b l y.rules.Cond  on.V e r sDmConversat onPart c pant
 mport com.tw ter.v s b l y.rules.Cond  on.DmConversat on nfoEx sts
 mport com.tw ter.v s b l y.rules.Cond  on.DmConversat onT  l neEx sts
 mport com.tw ter.v s b l y.rules.Cond  on.Not
 mport com.tw ter.v s b l y.rules.Cond  on.Deact vatedAuthor
 mport com.tw ter.v s b l y.rules.Cond  on.ErasedAuthor
 mport com.tw ter.v s b l y.rules.Cond  on.OneToOneDmConversat on
 mport com.tw ter.v s b l y.rules.Cond  on.Or
 mport com.tw ter.v s b l y.rules.Cond  on.SuspendedAuthor
 mport com.tw ter.v s b l y.rules.Reason.Unspec f ed

object DmConversat onRules {

  object DropEmptyDmConversat onRule
      extends RuleW hConstantAct on(
        Drop(Unspec f ed),
        Or(
          Not(DmConversat onLastReadableEvent d sVal d),
          And(OneToOneDmConversat on, DmConversat onT  l ne sEmpty))) {
    overr de def enableFa lClosed = Seq(RuleParams.True)
  }

  object Drop naccess bleDmConversat onRule
      extends RuleW hConstantAct on(Drop(Unspec f ed), Not(V e r sDmConversat onPart c pant)) {
    overr de def enableFa lClosed = Seq(RuleParams.True)
  }

  object DropDmConversat onW hUndef nedConversat on nfoRule
      extends RuleW hConstantAct on(Drop(Unspec f ed), Not(DmConversat on nfoEx sts)) {
    overr de def enableFa lClosed = Seq(RuleParams.True)
  }

  object DropDmConversat onW hUndef nedConversat onT  l neRule
      extends RuleW hConstantAct on(Drop(Unspec f ed), Not(DmConversat onT  l neEx sts)) {
    overr de def enableFa lClosed = Seq(RuleParams.True)
  }

  object DropOneToOneDmConversat onW hUnava lablePart c pantsRule
      extends RuleW hConstantAct on(
        Drop(Unspec f ed),
        And(OneToOneDmConversat on, Or(SuspendedAuthor, Deact vatedAuthor, ErasedAuthor))) {
    overr de def enableFa lClosed = Seq(RuleParams.True)
  }
}
