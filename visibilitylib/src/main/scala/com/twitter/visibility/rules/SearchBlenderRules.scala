package com.tw ter.v s b l y.rules

 mport com.tw ter.v s b l y.models.T etSafetyLabelType
 mport com.tw ter.v s b l y.rules.Cond  on.And
 mport com.tw ter.v s b l y.rules.Cond  on.HasSearchCand dateCountGreaterThan45
 mport com.tw ter.v s b l y.rules.Cond  on. sF rstPageSearchResult
 mport com.tw ter.v s b l y.rules.Cond  on.Not
 mport com.tw ter.v s b l y.rules.Reason.F rstPageSearchResult

abstract class F rstPageSearchResultW hT etLabelRule(
  act on: Act on,
  t etSafetyLabelType: T etSafetyLabelType)
    extends Cond  onW hT etLabelRule(
      act on,
       sF rstPageSearchResult,
      t etSafetyLabelType
    )

abstract class F rstPageSearchResultSmartOutOfNetworkW hT etLabelRule(
  act on: Act on,
  t etSafetyLabelType: T etSafetyLabelType)
    extends Cond  onW hT etLabelRule(
      act on,
      And(
         sF rstPageSearchResult,
        HasSearchCand dateCountGreaterThan45,
        Cond  on.NonAuthorV e r,
        Not(Cond  on.V e rDoesFollowAuthor),
        Not(Cond  on.Ver f edAuthor)
      ),
      t etSafetyLabelType
    )

object F rstPageSearchResultAgathaSpamDropRule
    extends F rstPageSearchResultW hT etLabelRule(
      Drop(F rstPageSearchResult),
      T etSafetyLabelType.AgathaSpam)
