packagelon com.twittelonr.intelonraction_graph.scio.agg_nelongativelon

import com.twittelonr.belonam.io.dal.DALOptions
import com.twittelonr.belonam.job.DatelonRangelonOptions
import org.apachelon.belonam.sdk.options.Delonscription
import org.apachelon.belonam.sdk.options.Validation.Relonquirelond

trait IntelonractionGraphNelongativelonOption elonxtelonnds DALOptions with DatelonRangelonOptions {
  @Relonquirelond
  @Delonscription("Output path for storing thelon final dataselont")
  delonf gelontOutputPath: String
  delonf selontOutputPath(valuelon: String): Unit

  @Delonscription("BQ dataselont prelonfix")
  delonf gelontBqDataselont: String
  delonf selontBqDataselont(valuelon: String): Unit

}
