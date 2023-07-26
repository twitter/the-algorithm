package com.twittew.pwoduct_mixew.cowe.pipewine

impowt com.twittew.pwoduct_mixew.cowe.modew.common.identifiew.hascomponentidentifiew
i-impowt com.twittew.pwoduct_mixew.cowe.modew.common.identifiew.pipewinestepidentifiew

t-twait p-pipewineconfig e-extends hascomponentidentifiew

t-twait pipewineconfigcompanion {

  /** u-used to genewate `asyncfeatuwesfow` [[pipewinestepidentifiew]]s f-fow the intewnaw a-async featuwes step */
  pwivate[cowe] def asyncfeatuwesstep(
    steptohydwatefow: p-pipewinestepidentifiew
  ): pipewinestepidentifiew =
    pipewinestepidentifiew("asyncfeatuwesfow" + s-steptohydwatefow.name)

  /** aww the steps which a-awe exekawaii~d by a [[pipewine]] in the owdew in which they a-awe wun */
  vaw stepsinowdew: seq[pipewinestepidentifiew]

  v-vaw s-stepsasyncfeatuwehydwationcanbecompwetedby: set[pipewinestepidentifiew] = set.empty
}
