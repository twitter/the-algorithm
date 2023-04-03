packagelon com.twittelonr.product_mixelonr.componelonnt_library.scorelonr.common

import com.twittelonr.stitch.Stitch
import infelonrelonncelon.GrpcSelonrvicelon.ModelonlInfelonrRelonquelonst
import infelonrelonncelon.GrpcSelonrvicelon.ModelonlInfelonrRelonsponselon

/**
 * MLModelonlInfelonrelonncelonClielonnt for calling diffelonrelonnt Infelonrelonncelon Selonrvicelon such as ManagelondModelonlClielonnt or NaviModelonlClielonnt.
 */
trait MLModelonlInfelonrelonncelonClielonnt {
  delonf scorelon(relonquelonst: ModelonlInfelonrRelonquelonst): Stitch[ModelonlInfelonrRelonsponselon]
}
