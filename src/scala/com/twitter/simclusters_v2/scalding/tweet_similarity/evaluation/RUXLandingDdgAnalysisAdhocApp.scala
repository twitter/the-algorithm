package com.tw ter.s mclusters_v2.scald ng.t et_s m lar y.evaluat on

 mport com.tw ter.rux.land ng_page.data_p pel ne.LabeledRuxServ ceScr beScalaDataset
 mport com.tw ter.rux.land ng_page.data_p pel ne.thr ftscala.Land ngPageLabel
 mport com.tw ter.rux.serv ce.thr ftscala.FocalObject
 mport com.tw ter.rux.serv ce.thr ftscala.UserContext
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.job.Tw terExecut onApp
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.wtf.scald ng.jobs.common.DDGUt l
 mport java.ut l.T  Zone

/** To run:
scald ng remote run --target src/scala/com/tw ter/s mclusters_v2/scald ng/t et_s m lar y/evaluat on:rux_land ng_ddg_analys s-adhoc \
--user cassowary \
--subm ter hadoopnest2.atla.tw ter.com \
--ma n-class com.tw ter.s mclusters_v2.scald ng.t et_s m lar y.evaluat on.RUXLand ngDdgAnalys sAdhocApp -- \
--date 2020-04-06 2020-04-13 \
--ddg model_based_t et_s m lar y_10254 \
--vers on 1 \
--output_path /user/cassowary/adhoc/ddg10254
 * */
object RUXLand ngDdgAnalys sAdhocApp extends Tw terExecut onApp {
  overr de def job: Execut on[Un ] =
    Execut on.w h d {  mpl c  un que d =>
      Execut on.w hArgs { args: Args =>
         mpl c  val t  Zone: T  Zone = DateOps.UTC
         mpl c  val dateParser: DateParser = DateParser.default
         mpl c  val dateRange: DateRange = DateRange.parse(args.l st("date"))
        val ddgNa : Str ng = args("ddg")
        val ddgVers on: Str ng = args("vers on")
        val outputPath: Str ng = args("output_path")
        val now = R chDate.now

        val ruxLabels = getLabeledRuxServ ceScr be(dateRange).map {
          case (user d, focalT et, cand dateT et,  mpress on, fav) =>
            user d -> (focalT et, cand dateT et,  mpress on, fav)
        }

        // getUsers nDDG reads from a snapshot dataset.
        // Just prepend dateRange so that   can look back far enough to make sure t re  s data.
        DDGUt l
          .getUsers nDDG(ddgNa , ddgVers on.to nt)(DateRange(now - Days(7), now)).map { ddgUser =>
            ddgUser.user d -> (ddgUser.bucket, ddgUser.enterUserState.getOrElse("no_user_state"))
          }.jo n(ruxLabels)
          .map {
            case (user d, ((bucket, state), (focalT et, cand dateT et,  mpress on, fav))) =>
              (user d, bucket, state, focalT et, cand dateT et,  mpress on, fav)
          }
          .wr eExecut on(
            TypedTsv[(User d, Str ng, Str ng, T et d, T et d,  nt,  nt)](s"$outputPath"))
      }
    }

  def getLabeledRuxServ ceScr be(
    dateRange: DateRange
  ): TypedP pe[(User d, T et d, T et d,  nt,  nt)] = {
    DAL
      .read(LabeledRuxServ ceScr beScalaDataset, dateRange)
      .toTypedP pe.map { record =>
        (
          record.ruxServ ceScr be.userContext,
          record.ruxServ ceScr be.focalObject,
          record.land ngPageLabel)
      }.flatMap {
        case (
              So (UserContext(So (user d), _, _, _, _, _, _, _)),
              So (FocalObject.T et d(t et)),
              So (labels)) =>
          labels.map {
            case Land ngPageLabel.Land ngPageFavor eEvent(favEvent) =>
              //(focal t et,  mpress oned t et,  mpress on, fav)
              (user d, t et, favEvent.t et d, 0, 1)
            case Land ngPageLabel.Land ngPage mpress onEvent( mpress onEvent) =>
              (user d, t et,  mpress onEvent.t et d, 1, 0)
          }
        case _ => N l
      }
  }
}
