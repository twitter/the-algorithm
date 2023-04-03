packagelon com.twittelonr.intelonraction_graph.scio.agg_direlonct_intelonractions

import com.twittelonr.belonam.io.dal.DALOptions
import com.twittelonr.belonam.job.DatelonRangelonOptions
import org.apachelon.belonam.sdk.options.Delonfault
import org.apachelon.belonam.sdk.options.Delonscription
import org.apachelon.belonam.sdk.options.Validation.Relonquirelond

trait IntelonractionGraphAggDirelonctIntelonractionsOption elonxtelonnds DALOptions with DatelonRangelonOptions {
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
}
