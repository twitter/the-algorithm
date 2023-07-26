package com.twittew.pwoduct_mixew.cowe.sewvice.twansfowmew_executow

impowt com.twittew.finagwe.stats.statsweceivew
i-impowt com.twittew.pwoduct_mixew.cowe.functionaw_component.twansfowmew.twansfowmew
i-impowt com.twittew.pwoduct_mixew.cowe.sewvice.executow
i-impowt c-com.twittew.stitch.awwow
i-impowt c-com.twittew.utiw.twy
i-impowt j-javax.inject.inject
impowt javax.inject.singweton

/**
 * fow wwapping [[twansfowmew]]s that awe appwied pew-candidate
 *
 * w-wecowds a singwe span fow wunning aww t-the components, (U ﹏ U)
 * but stats p-pew-component. >_<
 */
@singweton
cwass pewcandidatetwansfowmewexecutow @inject() (ovewwide vaw statsweceivew: s-statsweceivew)
    extends e-executow {

  d-def awwow[in, rawr x3 out](
    twansfowmew: twansfowmew[in, mya out],
    context: executow.context, nyaa~~
  ): a-awwow[seq[in], seq[twy[out]]] = {
    vaw pewcandidateawwow = wwappewcandidatecomponentwithexecutowbookkeepingwithouttwacing(
      context, (⑅˘꒳˘)
      t-twansfowmew.identifiew
    )(awwow.map(twansfowmew.twansfowm)).wifttotwy

    wwapcomponentswithtwacingonwy(
      c-context, rawr x3
      t-twansfowmew.identifiew
    )(awwow.sequence(pewcandidateawwow))
  }
}
