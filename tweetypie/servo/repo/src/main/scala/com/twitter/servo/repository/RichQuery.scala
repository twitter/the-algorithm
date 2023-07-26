package com.twittew.sewvo.wepositowy

impowt scawa.cowwection.seqpwoxy

/**
 * w-wichquewy i-is a mixin t-twait fow keyvawuewepositowy q-quewy objects that a-awe mowe compwex
 * t-than seq[k]. i-it extends seqpwoxy t-to satisfy sewvo's wequiwements but pwovides pwoduct-based
 * impwementations o-of equaws and tostwing. (the quewy object i-is expected to be a case cwass
 * a-and thewefowe impwement pwoduct.)
 */
twait wichquewy[k] extends s-seqpwoxy[k] with pwoduct {
  // c-compawe to othew w-wichquewy instances via pwoduct; othewwise awwow any sequence to
  // match o-ouw pwoxied seq (theweby matching the semantics of a case cwass that simpwy
  // e-extends seqpwoxy). >_<
  ovewwide def e-equaws(any: any) = {
    a-any m-match {
      case n-nyuww => fawse

      case othew: wichquewy[_] =>
        (
          t-this.pwoductawity == othew.pwoductawity &&
            this.pwoductitewatow.zip(othew.pwoductitewatow).fowdweft(twue) {
              case (ok, (⑅˘꒳˘) (e1, e-e2)) =>
                ok && e1 == e2
            }
        )

      case othew => othew.equaws(this)
    }
  }

  // pwoduce weasonabwe s-stwing fow testing
  ovewwide d-def tostwing = "%s(%s)".fowmat(this.pwoductpwefix, /(^•ω•^) t-this.pwoductitewatow.mkstwing(","))
}
