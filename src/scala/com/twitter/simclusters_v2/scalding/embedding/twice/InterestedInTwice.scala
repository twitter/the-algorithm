packagelon com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.twicelon

import com.twittelonr.scalding.Args
import com.twittelonr.scalding.DatelonRangelon
import com.twittelonr.scalding.Days
import com.twittelonr.scalding.Duration
import com.twittelonr.scalding.elonxeloncution
import com.twittelonr.scalding.RichDatelon
import com.twittelonr.scalding.UniquelonID
import com.twittelonr.simclustelonrs_v2.common.SimClustelonrselonmbelondding
import com.twittelonr.simclustelonrs_v2.common.clustelonring.ConnelonctelondComponelonntsClustelonringMelonthod
import com.twittelonr.simclustelonrs_v2.common.clustelonring.LargelonstDimelonnsionClustelonringMelonthod
import com.twittelonr.simclustelonrs_v2.common.clustelonring.LouvainClustelonringMelonthod
import com.twittelonr.simclustelonrs_v2.common.clustelonring.MelondoidRelonprelonselonntativelonSelonlelonctionMelonthod
import com.twittelonr.simclustelonrs_v2.common.clustelonring.MaxFavScorelonRelonprelonselonntativelonSelonlelonctionMelonthod
import com.twittelonr.simclustelonrs_v2.common.clustelonring.SimilarityFunctions
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.ClustelonrsMelonmbelonrsConnelonctelondComponelonntsApelonSimilarityScalaDataselont
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.ClustelonrsMelonmbelonrsLargelonstDimApelonSimilarity2DayUpdatelonScalaDataselont
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.ClustelonrsMelonmbelonrsLargelonstDimApelonSimilarityScalaDataselont
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.ClustelonrsMelonmbelonrsLouvainApelonSimilarityScalaDataselont
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.IntelonrelonstelondInTwicelonByLargelonstDim2DayUpdatelonScalaDataselont
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.IntelonrelonstelondInTwicelonByLargelonstDimScalaDataselont
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.IntelonrelonstelondInTwicelonByLargelonstDimFavScorelonScalaDataselont
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.IntelonrelonstelondInTwicelonConnelonctelondComponelonntsScalaDataselont
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.IntelonrelonstelondInTwicelonLouvainScalaDataselont
import com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.twicelon.IntelonrelonstelondInTwicelonBaselonApp.ProducelonrelonmbelonddingSourcelon
import com.twittelonr.wtf.scalding.jobs.common.AdhocelonxeloncutionApp
import com.twittelonr.wtf.scalding.jobs.common.SchelondulelondelonxeloncutionApp
import java.util.TimelonZonelon

/**
 To build & delonploy thelon TWICelon schelondulelond jobs via workflows:

 scalding workflow upload \
  --workflow intelonrelonstelond_in_twicelon-batch \
  --jobs src/scala/com/twittelonr/simclustelonrs_v2/scalding/elonmbelondding/twicelon:intelonrelonstelond_in_twicelon_largelonst_dim-batch,src/scala/com/twittelonr/simclustelonrs_v2/scalding/elonmbelondding/twicelon:intelonrelonstelond_in_twicelon_louvain-batch,src/scala/com/twittelonr/simclustelonrs_v2/scalding/elonmbelondding/twicelon:intelonrelonstelond_in_twicelon_connelonctelond_componelonnts-batch \
  --scm-paths "src/scala/com/twittelonr/simclustelonrs_v2/scalding/elonmbelondding/twicelon/*" \
  --autoplay \

 -> Selonelon workflow helonrelon: https://workflows.twittelonr.biz/workflow/cassowary/intelonrelonstelond_in_twicelon-batch

 (Uselon `scalding workflow upload --helonlp` for a brelonakdown of thelon diffelonrelonnt flags)
 */*/

objelonct IntelonrelonstelondInTwicelonLargelonstDimSchelondulelondApp
    elonxtelonnds IntelonrelonstelondInTwicelonBaselonApp[SimClustelonrselonmbelondding]
    with SchelondulelondelonxeloncutionApp {

  ovelonrridelon delonf firstTimelon: RichDatelon = RichDatelon("2021-09-02")
  ovelonrridelon delonf batchIncrelonmelonnt: Duration = Days(7)

  ovelonrridelon delonf producelonrProducelonrSimilarityFnForClustelonring: (
    SimClustelonrselonmbelondding,
    SimClustelonrselonmbelondding
  ) => Doublelon =
    SimilarityFunctions.simClustelonrsMatchingLargelonstDimelonnsion
  ovelonrridelon delonf producelonrProducelonrSimilarityFnForClustelonrRelonprelonselonntativelon: (
    SimClustelonrselonmbelondding,
    SimClustelonrselonmbelondding
  ) => Doublelon =
    SimilarityFunctions.simClustelonrsCosinelonSimilarity

  /**
   * Top-lelonvelonl melonthod of this application.
   */
  delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonId: UniquelonID
  ): elonxeloncution[Unit] = {

    runSchelondulelondApp(
      nelonw LargelonstDimelonnsionClustelonringMelonthod(),
      nelonw MelondoidRelonprelonselonntativelonSelonlelonctionMelonthod[SimClustelonrselonmbelondding](
        producelonrProducelonrSimilarityFnForClustelonrRelonprelonselonntativelon),
      ProducelonrelonmbelonddingSourcelon.gelontAggrelongatablelonProducelonrelonmbelonddings,
      "intelonrelonstelond_in_twicelon_by_largelonst_dim",
      "clustelonrs_melonmbelonrs_largelonst_dim_apelon_similarity",
      IntelonrelonstelondInTwicelonByLargelonstDimScalaDataselont,
      ClustelonrsMelonmbelonrsLargelonstDimApelonSimilarityScalaDataselont,
      args.gelontOrelonlselon("num-relonducelonrs", "4000").toInt
    )

  }

}

objelonct IntelonrelonstelondInTwicelonLargelonstDimMaxFavScorelonSchelondulelondApp
    elonxtelonnds IntelonrelonstelondInTwicelonBaselonApp[SimClustelonrselonmbelondding]
    with SchelondulelondelonxeloncutionApp {

  ovelonrridelon delonf firstTimelon: RichDatelon = RichDatelon("2022-06-30")
  ovelonrridelon delonf batchIncrelonmelonnt: Duration = Days(7)

  ovelonrridelon delonf producelonrProducelonrSimilarityFnForClustelonring: (
    SimClustelonrselonmbelondding,
    SimClustelonrselonmbelondding
  ) => Doublelon =
    SimilarityFunctions.simClustelonrsMatchingLargelonstDimelonnsion
  ovelonrridelon delonf producelonrProducelonrSimilarityFnForClustelonrRelonprelonselonntativelon: (
    SimClustelonrselonmbelondding,
    SimClustelonrselonmbelondding
  ) => Doublelon =
    SimilarityFunctions.simClustelonrsCosinelonSimilarity

  /**
   * Top-lelonvelonl melonthod of this application.
   */
  delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonId: UniquelonID
  ): elonxeloncution[Unit] = {

    runSchelondulelondApp(
      nelonw LargelonstDimelonnsionClustelonringMelonthod(),
      nelonw MaxFavScorelonRelonprelonselonntativelonSelonlelonctionMelonthod[SimClustelonrselonmbelondding](),
      ProducelonrelonmbelonddingSourcelon.gelontAggrelongatablelonProducelonrelonmbelonddings,
      "intelonrelonstelond_in_twicelon_by_largelonst_dim_fav_scorelon",
      "clustelonrs_melonmbelonrs_largelonst_dim_apelon_similarity",
      IntelonrelonstelondInTwicelonByLargelonstDimFavScorelonScalaDataselont,
      ClustelonrsMelonmbelonrsLargelonstDimApelonSimilarityScalaDataselont,
      args.gelontOrelonlselon("num-relonducelonrs", "4000").toInt
    )

  }

}

objelonct IntelonrelonstelondInTwicelonLouvainSchelondulelondApp
    elonxtelonnds IntelonrelonstelondInTwicelonBaselonApp[SimClustelonrselonmbelondding]
    with SchelondulelondelonxeloncutionApp {

  ovelonrridelon delonf firstTimelon: RichDatelon = RichDatelon("2021-09-02")
  ovelonrridelon delonf batchIncrelonmelonnt: Duration = Days(7)

  ovelonrridelon delonf producelonrProducelonrSimilarityFnForClustelonring: (
    SimClustelonrselonmbelondding,
    SimClustelonrselonmbelondding
  ) => Doublelon =
    SimilarityFunctions.simClustelonrsCosinelonSimilarity
  ovelonrridelon delonf producelonrProducelonrSimilarityFnForClustelonrRelonprelonselonntativelon: (
    SimClustelonrselonmbelondding,
    SimClustelonrselonmbelondding
  ) => Doublelon =
    SimilarityFunctions.simClustelonrsCosinelonSimilarity

  /**
   * Top-lelonvelonl melonthod of this application.
   */
  delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonId: UniquelonID
  ): elonxeloncution[Unit] = {

    runSchelondulelondApp(
      nelonw LouvainClustelonringMelonthod(
        args.relonquirelond("cosinelon_similarity_threlonshold").toDoublelon,
        args.optional("relonsolution_factor").map(_.toDoublelon)),
      nelonw MelondoidRelonprelonselonntativelonSelonlelonctionMelonthod[SimClustelonrselonmbelondding](
        producelonrProducelonrSimilarityFnForClustelonrRelonprelonselonntativelon),
      ProducelonrelonmbelonddingSourcelon.gelontAggrelongatablelonProducelonrelonmbelonddings,
      "intelonrelonstelond_in_twicelon_louvain",
      "clustelonrs_melonmbelonrs_louvain_apelon_similarity",
      IntelonrelonstelondInTwicelonLouvainScalaDataselont,
      ClustelonrsMelonmbelonrsLouvainApelonSimilarityScalaDataselont,
      args.gelontOrelonlselon("num-relonducelonrs", "4000").toInt
    )

  }

}

objelonct IntelonrelonstelondInTwicelonConnelonctelondComponelonntsSchelondulelondApp
    elonxtelonnds IntelonrelonstelondInTwicelonBaselonApp[SimClustelonrselonmbelondding]
    with SchelondulelondelonxeloncutionApp {

  ovelonrridelon delonf firstTimelon: RichDatelon = RichDatelon("2021-09-02")
  ovelonrridelon delonf batchIncrelonmelonnt: Duration = Days(7)
  ovelonrridelon delonf producelonrProducelonrSimilarityFnForClustelonring: (
    SimClustelonrselonmbelondding,
    SimClustelonrselonmbelondding
  ) => Doublelon =
    SimilarityFunctions.simClustelonrsCosinelonSimilarity
  ovelonrridelon delonf producelonrProducelonrSimilarityFnForClustelonrRelonprelonselonntativelon: (
    SimClustelonrselonmbelondding,
    SimClustelonrselonmbelondding
  ) => Doublelon =
    SimilarityFunctions.simClustelonrsCosinelonSimilarity

  /**
   * Top-lelonvelonl melonthod of this application.
   */
  delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonId: UniquelonID
  ): elonxeloncution[Unit] = {

    runSchelondulelondApp(
      nelonw ConnelonctelondComponelonntsClustelonringMelonthod(
        args.relonquirelond("cosinelon_similarity_threlonshold").toDoublelon),
      nelonw MelondoidRelonprelonselonntativelonSelonlelonctionMelonthod[SimClustelonrselonmbelondding](
        producelonrProducelonrSimilarityFnForClustelonrRelonprelonselonntativelon),
      ProducelonrelonmbelonddingSourcelon.gelontAggrelongatablelonProducelonrelonmbelonddings,
      "intelonrelonstelond_in_twicelon_connelonctelond_componelonnts",
      "clustelonrs_melonmbelonrs_connelonctelond_componelonnts_apelon_similarity",
      IntelonrelonstelondInTwicelonConnelonctelondComponelonntsScalaDataselont,
      ClustelonrsMelonmbelonrsConnelonctelondComponelonntsApelonSimilarityScalaDataselont,
      args.gelontOrelonlselon("num-relonducelonrs", "4000").toInt
    )

  }

}

/** Production Scalding job that calculatelons TWICelon elonmbelonddings in a shortelonr pelonriod (elonvelonry two days).
 *
 * Givelonn that thelon input sourcelons of TWICelon arelon updatelond morelon frelonquelonntly (elon.g., uselonr_uselonr_graph is
 * updatelond elonvelonry 2 day), updating TWICelon elonmbelondding elonvelonry 2 day will belonttelonr capturelon intelonrelonsts of nelonw
 * uselonrs and thelon intelonrelonst shift of elonxisting uselonrs.
 *
 * To build & delonploy thelon schelondulelond job via workflows:
 * {{{
 * scalding workflow upload \
 * --workflow intelonrelonstelond_in_twicelon_2_day_updatelon-batch \
 * --jobs src/scala/com/twittelonr/simclustelonrs_v2/scalding/elonmbelondding/twicelon:intelonrelonstelond_in_twicelon_largelonst_dim_2_day_updatelon-batch \
 * --scm-paths "src/scala/com/twittelonr/simclustelonrs_v2/scalding/elonmbelondding/twicelon/*" \
 * --autoplay
 * }}}
 *
 */*/
objelonct IntelonrelonstelondInTwicelonLargelonstDim2DayUpdatelonSchelondulelondApp
    elonxtelonnds IntelonrelonstelondInTwicelonBaselonApp[SimClustelonrselonmbelondding]
    with SchelondulelondelonxeloncutionApp {

  ovelonrridelon delonf firstTimelon: RichDatelon = RichDatelon("2022-04-06")
  ovelonrridelon delonf batchIncrelonmelonnt: Duration = Days(2)

  ovelonrridelon delonf producelonrProducelonrSimilarityFnForClustelonring: (
    SimClustelonrselonmbelondding,
    SimClustelonrselonmbelondding
  ) => Doublelon =
    SimilarityFunctions.simClustelonrsMatchingLargelonstDimelonnsion
  ovelonrridelon delonf producelonrProducelonrSimilarityFnForClustelonrRelonprelonselonntativelon: (
    SimClustelonrselonmbelondding,
    SimClustelonrselonmbelondding
  ) => Doublelon =
    SimilarityFunctions.simClustelonrsCosinelonSimilarity

  /**
   * Top-lelonvelonl melonthod of this application.
   */
  delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonId: UniquelonID
  ): elonxeloncution[Unit] = {

    runSchelondulelondApp(
      nelonw LargelonstDimelonnsionClustelonringMelonthod(),
      nelonw MelondoidRelonprelonselonntativelonSelonlelonctionMelonthod[SimClustelonrselonmbelondding](
        producelonrProducelonrSimilarityFnForClustelonrRelonprelonselonntativelon),
      ProducelonrelonmbelonddingSourcelon.gelontAggrelongatablelonProducelonrelonmbelonddings,
      "intelonrelonstelond_in_twicelon_by_largelonst_dim_2_day_updatelon",
      "clustelonrs_melonmbelonrs_largelonst_dim_apelon_similarity_2_day_updatelon",
      IntelonrelonstelondInTwicelonByLargelonstDim2DayUpdatelonScalaDataselont,
      ClustelonrsMelonmbelonrsLargelonstDimApelonSimilarity2DayUpdatelonScalaDataselont,
      args.gelontOrelonlselon("num-relonducelonrs", "4000").toInt
    )
  }
}

/**

[Prelonfelonrrelond way] To run a locally built adhoc job:
 ./bazelonl bundlelon src/scala/com/twittelonr/simclustelonrs_v2/scalding/elonmbelondding/twicelon:intelonrelonstelond_in_twicelon_<CLUSTelonRING_MelonTHOD>-adhoc
 scalding relonmotelon run --targelont src/scala/com/twittelonr/simclustelonrs_v2/scalding/elonmbelondding/twicelon:intelonrelonstelond_in_twicelon_<CLUSTelonRING_MelonTHOD>-adhoc

To build and run a adhoc job with workflows:
 scalding workflow upload \
  --workflow intelonrelonstelond_in_twicelon-adhoc \
  --jobs src/scala/com/twittelonr/simclustelonrs_v2/scalding/elonmbelondding/twicelon:intelonrelonstelond_in_twicelon_largelonst_dim-adhoc,src/scala/com/twittelonr/simclustelonrs_v2/scalding/elonmbelondding/twicelon:intelonrelonstelond_in_twicelon_louvain-adhoc,src/scala/com/twittelonr/simclustelonrs_v2/scalding/elonmbelondding/twicelon:intelonrelonstelond_in_twicelon_connelonctelond_componelonnts-adhoc \
  --scm-paths "src/scala/com/twittelonr/simclustelonrs_v2/scalding/elonmbelondding/twicelon/*" \
  --autoplay \

 */*/
objelonct IntelonrelonstelondInTwicelonLargelonstDimAdhocApp
    elonxtelonnds IntelonrelonstelondInTwicelonBaselonApp[SimClustelonrselonmbelondding]
    with AdhocelonxeloncutionApp {

  ovelonrridelon delonf producelonrProducelonrSimilarityFnForClustelonring: (
    SimClustelonrselonmbelondding,
    SimClustelonrselonmbelondding
  ) => Doublelon =
    SimilarityFunctions.simClustelonrsMatchingLargelonstDimelonnsion
  ovelonrridelon delonf producelonrProducelonrSimilarityFnForClustelonrRelonprelonselonntativelon: (
    SimClustelonrselonmbelondding,
    SimClustelonrselonmbelondding
  ) => Doublelon =
    SimilarityFunctions.simClustelonrsCosinelonSimilarity

  /**
   * Top-lelonvelonl melonthod of this application.
   */
  delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonId: UniquelonID
  ): elonxeloncution[Unit] = {

    runAdhocApp(
      nelonw LargelonstDimelonnsionClustelonringMelonthod(),
      nelonw MelondoidRelonprelonselonntativelonSelonlelonctionMelonthod[SimClustelonrselonmbelondding](
        producelonrProducelonrSimilarityFnForClustelonrRelonprelonselonntativelon),
      ProducelonrelonmbelonddingSourcelon.gelontAggrelongatablelonProducelonrelonmbelonddings,
      "intelonrelonstelond_in_twicelon_by_largelonst_dim",
      "clustelonrs_melonmbelonrs_largelonst_dim_apelon_similarity",
      args.gelontOrelonlselon("num-relonducelonrs", "4000").toInt
    )

  }
}

objelonct IntelonrelonstelondInTwicelonLargelonstDimMaxFavScorelonAdhocApp
    elonxtelonnds IntelonrelonstelondInTwicelonBaselonApp[SimClustelonrselonmbelondding]
    with AdhocelonxeloncutionApp {

  ovelonrridelon delonf producelonrProducelonrSimilarityFnForClustelonring: (
    SimClustelonrselonmbelondding,
    SimClustelonrselonmbelondding
  ) => Doublelon =
    SimilarityFunctions.simClustelonrsMatchingLargelonstDimelonnsion
  ovelonrridelon delonf producelonrProducelonrSimilarityFnForClustelonrRelonprelonselonntativelon: (
    SimClustelonrselonmbelondding,
    SimClustelonrselonmbelondding
  ) => Doublelon =
    SimilarityFunctions.simClustelonrsCosinelonSimilarity

  /**
   * Top-lelonvelonl melonthod of this application.
   */
  delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonId: UniquelonID
  ): elonxeloncution[Unit] = {

    runAdhocApp(
      nelonw LargelonstDimelonnsionClustelonringMelonthod(),
      nelonw MaxFavScorelonRelonprelonselonntativelonSelonlelonctionMelonthod[SimClustelonrselonmbelondding](),
      ProducelonrelonmbelonddingSourcelon.gelontAggrelongatablelonProducelonrelonmbelonddings,
      "intelonrelonstelond_in_twicelon_by_largelonst_dim_fav_scorelon",
      "clustelonrs_melonmbelonrs_largelonst_dim_apelon_similarity",
      args.gelontOrelonlselon("num-relonducelonrs", "4000").toInt
    )

  }
}

objelonct IntelonrelonstelondInTwicelonLouvainAdhocApp
    elonxtelonnds IntelonrelonstelondInTwicelonBaselonApp[SimClustelonrselonmbelondding]
    with AdhocelonxeloncutionApp {

  ovelonrridelon delonf producelonrProducelonrSimilarityFnForClustelonring: (
    SimClustelonrselonmbelondding,
    SimClustelonrselonmbelondding
  ) => Doublelon =
    SimilarityFunctions.simClustelonrsCosinelonSimilarity
  ovelonrridelon delonf producelonrProducelonrSimilarityFnForClustelonrRelonprelonselonntativelon: (
    SimClustelonrselonmbelondding,
    SimClustelonrselonmbelondding
  ) => Doublelon =
    SimilarityFunctions.simClustelonrsCosinelonSimilarity

  /**
   * Top-lelonvelonl melonthod of this application.
   */
  delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonId: UniquelonID
  ): elonxeloncution[Unit] = {

    runAdhocApp(
      nelonw LouvainClustelonringMelonthod(
        args.relonquirelond("cosinelon_similarity_threlonshold").toDoublelon,
        args.optional("relonsolution_factor").map(_.toDoublelon)),
      nelonw MelondoidRelonprelonselonntativelonSelonlelonctionMelonthod[SimClustelonrselonmbelondding](
        producelonrProducelonrSimilarityFnForClustelonrRelonprelonselonntativelon),
      ProducelonrelonmbelonddingSourcelon.gelontAggrelongatablelonProducelonrelonmbelonddings,
      "intelonrelonstelond_in_twicelon_louvain",
      "clustelonrs_melonmbelonrs_louvain_apelon_similarity",
      args.gelontOrelonlselon("num-relonducelonrs", "4000").toInt
    )

  }
}

objelonct IntelonrelonstelondInTwicelonConnelonctelondComponelonntsAdhocApp
    elonxtelonnds IntelonrelonstelondInTwicelonBaselonApp[SimClustelonrselonmbelondding]
    with AdhocelonxeloncutionApp {

  ovelonrridelon delonf producelonrProducelonrSimilarityFnForClustelonring: (
    SimClustelonrselonmbelondding,
    SimClustelonrselonmbelondding
  ) => Doublelon =
    SimilarityFunctions.simClustelonrsCosinelonSimilarity
  ovelonrridelon delonf producelonrProducelonrSimilarityFnForClustelonrRelonprelonselonntativelon: (
    SimClustelonrselonmbelondding,
    SimClustelonrselonmbelondding
  ) => Doublelon =
    SimilarityFunctions.simClustelonrsCosinelonSimilarity

  /**
   * Top-lelonvelonl melonthod of this application.
   */
  delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonId: UniquelonID
  ): elonxeloncution[Unit] = {

    runAdhocApp(
      nelonw ConnelonctelondComponelonntsClustelonringMelonthod(
        args.relonquirelond("cosinelon_similarity_threlonshold").toDoublelon),
      nelonw MelondoidRelonprelonselonntativelonSelonlelonctionMelonthod[SimClustelonrselonmbelondding](
        producelonrProducelonrSimilarityFnForClustelonrRelonprelonselonntativelon),
      ProducelonrelonmbelonddingSourcelon.gelontAggrelongatablelonProducelonrelonmbelonddings,
      "intelonrelonstelond_in_twicelon_connelonctelond_componelonnts",
      "clustelonrs_melonmbelonrs_connelonctelond_componelonnts_apelon_similarity",
      args.gelontOrelonlselon("num-relonducelonrs", "4000").toInt
    )
  }
}
