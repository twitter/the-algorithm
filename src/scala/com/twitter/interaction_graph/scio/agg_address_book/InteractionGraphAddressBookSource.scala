package com.tw ter. nteract on_graph.sc o.agg_address_book

 mport com.spot fy.sc o.Sc oContext
 mport com.spot fy.sc o.values.SCollect on
 mport com.tw ter.addressbook.jobs.s mplematc s.S mpleUserMatc sScalaDataset
 mport com.tw ter.addressbook.matc s.thr ftscala.UserMatc sRecord
 mport com.tw ter.beam.job.Serv ce dent f erOpt ons
 mport com.tw ter.cde.sc o.dal_read.S ceUt l
 mport org.joda.t  . nterval

case class  nteract onGraphAddressBookS ce(
  p pel neOpt ons:  nteract onGraphAddressBookOpt on
)(
   mpl c  sc: Sc oContext,
) {
  val dalEnv ron nt: Str ng = p pel neOpt ons
    .as(classOf[Serv ce dent f erOpt ons])
    .getEnv ron nt()

  def readS mpleUserMatc s(
    date nterval:  nterval
  ): SCollect on[UserMatc sRecord] = {
    S ceUt l.readMostRecentSnapshotDALDataset[UserMatc sRecord](
      S mpleUserMatc sScalaDataset,
      date nterval,
      dalEnv ron nt)
  }
}
