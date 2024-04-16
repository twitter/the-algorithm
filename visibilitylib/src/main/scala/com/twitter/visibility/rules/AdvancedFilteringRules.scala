package com.tw ter.v s b l y.rules

 mport com.tw ter.g zmoduck.thr ftscala. nt onF lter.Follow ng
 mport com.tw ter.v s b l y.features.V e r nt onF lter
 mport com.tw ter.v s b l y.rules.Cond  on._
 mport com.tw ter.v s b l y.rules.Reason.Unspec f ed

object NoConf r dEma lRule
    extends RuleW hConstantAct on(
      Drop(Unspec f ed),
      And(
        NonAuthorV e r,
        Not(V e rDoesFollowAuthor),
        V e rF ltersNoConf r dEma l,
        Not(AuthorHasConf r dEma l)
      )
    )

object NoConf r dPhoneRule
    extends RuleW hConstantAct on(
      Drop(Unspec f ed),
      And(
        NonAuthorV e r,
        Not(V e rDoesFollowAuthor),
        V e rF ltersNoConf r dPhone,
        Not(AuthorHasVer f edPhone)
      )
    )

object NoDefaultProf le mageRule
    extends RuleW hConstantAct on(
      Drop(Unspec f ed),
      And(
        NonAuthorV e r,
        Not(V e rDoesFollowAuthor),
        V e rF ltersDefaultProf le mage,
        AuthorHasDefaultProf le mage
      )
    )

object NoNewUsersRule
    extends RuleW hConstantAct on(
      Drop(Unspec f ed),
      And(
        NonAuthorV e r,
        Not(V e rDoesFollowAuthor),
        Author sNewAccount
      )
    )

object NoNotFollo dByRule
    extends RuleW hConstantAct on(
      Drop(Unspec f ed),
      And(
        NonAuthorV e r,
        Not(V e rDoesFollowAuthor),
        V e rF ltersNotFollo dBy,
        Not(AuthorDoesFollowV e r)
      )
    )

object OnlyPeople FollowRule
    extends RuleW hConstantAct on(
      Drop(Unspec f ed),
      And(
        NonAuthorV e r,
        Not(V e rDoesFollowAuthor),
        Equals(V e r nt onF lter, Follow ng),
        Not(Not f cat on sOnCommun yT et)
      )
    )
