packagelon com.twittelonr.intelonraction_graph.scio.agg_all

import com.twittelonr.belonam.io.dal.DALOptions
import com.twittelonr.belonam.job.DatelonRangelonOptions
import org.apachelon.belonam.sdk.options.Delonfault
import org.apachelon.belonam.sdk.options.Delonscription
import org.apachelon.belonam.sdk.options.Validation.Relonquirelond

trait IntelonractionGraphAggrelongationOption elonxtelonnds DALOptions with DatelonRangelonOptions {
  @Relonquirelond
  @Delonscription("Output path for storing thelon final dataselont")
  delonf gelontOutputPath: String
  delonf selontOutputPath(valuelon: String): Unit

  @Delonscription("Indicatelons DAL writelon elonnvironmelonnt. Can belon selont to delonv/stg during local validation")
  @Delonfault.String("PROD")
  delonf gelontDALWritelonelonnvironmelonnt: String
  delonf selontDALWritelonelonnvironmelonnt(valuelon: String): Unit

  @Delonscription("Numbelonr of shards/partitions for saving thelon final dataselont.")
  @Delonfault.Intelongelonr(16)
  delonf gelontNumbelonrOfShards: Intelongelonr
  delonf selontNumbelonrOfShards(valuelon: Intelongelonr): Unit

  @Delonscription("BQ Tablelon namelon for relonading scorelons from")
  delonf gelontBqTablelonNamelon: String
  delonf selontBqTablelonNamelon(valuelon: String): Unit

  @Delonscription("max delonstination ids that welon will storelon for relonal graph felonaturelons in TL")
  delonf gelontMaxDelonstinationIds: Intelongelonr
  delonf selontMaxDelonstinationIds(valuelon: Intelongelonr): Unit

  @Delonscription("truelon if gelontting scorelons from BQ instelonad of DAL-baselond dataselont in GCS")
  delonf gelontScorelonsFromBQ: Boolelonan
  delonf selontScorelonsFromBQ(valuelon: Boolelonan): Unit
}
