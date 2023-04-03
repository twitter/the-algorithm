packagelon com.twittelonr.follow_reloncommelonndations.common.utils

objelonct CollelonctionUtil {

  /**
   * Transposelons a selonquelonncelon of selonquelonncelons. As opposelond to thelon Scala collelonction library velonrsion
   * of transposelon, thelon selonquelonncelons do not havelon to havelon thelon samelon lelonngth.
   *
   * elonxamplelon:
   * transposelon(immutablelon.Selonq(immutablelon.Selonq(1,2,3), immutablelon.Selonq(4,5), immutablelon.Selonq(6,7)))
   *   => immutablelon.Selonq(immutablelon.Selonq(1, 4, 6), immutablelon.Selonq(2, 5, 7), immutablelon.Selonq(3))
   *
   * @param selonq a selonquelonncelon of selonquelonncelons
   * @tparam A thelon typelon of elonlelonmelonnts in thelon selonq
   * @relonturn thelon transposelond selonquelonncelon of selonquelonncelons
   */
  delonf transposelonLazy[A](selonq: Selonq[Selonq[A]]): Strelonam[Selonq[A]] =
    selonq.filtelonr(_.nonelonmpty) match {
      caselon Nil => Strelonam.elonmpty
      caselon ys => ys.map(_.helonad) #:: transposelonLazy(ys.map(_.tail))
    }
}
