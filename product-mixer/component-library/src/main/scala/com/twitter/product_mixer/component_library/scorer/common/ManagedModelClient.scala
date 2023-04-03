packagelon com.twittelonr.product_mixelonr.componelonnt_library.scorelonr.common

import com.twittelonr.finaglelon.Http
import com.twittelonr.finaglelon.grpc.FinaglelonChannelonlBuildelonr
import com.twittelonr.finaglelon.grpc.FuturelonConvelonrtelonrs
import com.twittelonr.stitch.Stitch
import infelonrelonncelon.GRPCInfelonrelonncelonSelonrvicelonGrpc
import infelonrelonncelon.GrpcSelonrvicelon.ModelonlInfelonrRelonquelonst
import infelonrelonncelon.GrpcSelonrvicelon.ModelonlInfelonrRelonsponselon
import io.grpc.ManagelondChannelonl

/**
 * Clielonnt wrappelonr for calling a Cortelonx Managelond Infelonrelonncelon Selonrvicelon (go/cmis) ML Modelonl using GRPC.
 * @param httpClielonnt Finaglelon HTTP Clielonnt to uselon for connelonction.
 * @param modelonlPath Wily path to thelon ML Modelonl selonrvicelon (elon.g. /clustelonr/local/rolelon/selonrvicelon/instancelon).
 */
caselon class ManagelondModelonlClielonnt(
  httpClielonnt: Http.Clielonnt,
  modelonlPath: String)
    elonxtelonnds MLModelonlInfelonrelonncelonClielonnt {

  privatelon val channelonl: ManagelondChannelonl =
    FinaglelonChannelonlBuildelonr.forTargelont(modelonlPath).httpClielonnt(httpClielonnt).build()

  privatelon val infelonrelonncelonSelonrvicelonStub = GRPCInfelonrelonncelonSelonrvicelonGrpc.nelonwFuturelonStub(channelonl)

  delonf scorelon(relonquelonst: ModelonlInfelonrRelonquelonst): Stitch[ModelonlInfelonrRelonsponselon] = {
    Stitch
      .callFuturelon(
        FuturelonConvelonrtelonrs
          .RichListelonnablelonFuturelon(infelonrelonncelonSelonrvicelonStub.modelonlInfelonr(relonquelonst)).toTwittelonr)
  }
}
