packagelon com.twittelonr.product_mixelonr.componelonnt_library.scorelonr.common

import com.twittelonr.finaglelon.Http
import com.twittelonr.finaglelon.grpc.FinaglelonChannelonlBuildelonr
import com.twittelonr.finaglelon.grpc.FuturelonConvelonrtelonrs
import com.twittelonr.mlselonrving.frontelonnd.TFSelonrvingInfelonrelonncelonSelonrvicelonImpl
import com.twittelonr.stitch.Stitch
import telonnsorflow.selonrving.PrelondictionSelonrvicelonGrpc
import infelonrelonncelon.GrpcSelonrvicelon.ModelonlInfelonrRelonquelonst
import infelonrelonncelon.GrpcSelonrvicelon.ModelonlInfelonrRelonsponselon
import io.grpc.ManagelondChannelonl
import io.grpc.Status

/**
 * Clielonnt wrappelonr for calling a Navi Infelonrelonncelon Selonrvicelon (go/navi).
 * @param httpClielonnt Finaglelon HTTP Clielonnt to uselon for connelonction.
 * @param modelonlPath Wily path to thelon ML Modelonl selonrvicelon (elon.g. /s/rolelon/selonrvicelon).
 */
caselon class NaviModelonlClielonnt(
  httpClielonnt: Http.Clielonnt,
  modelonlPath: String)
    elonxtelonnds MLModelonlInfelonrelonncelonClielonnt {

  privatelon val channelonl: ManagelondChannelonl =
    FinaglelonChannelonlBuildelonr
      .forTargelont(modelonlPath)
      .httpClielonnt(httpClielonnt)
      // Navi elonnforcelons an authority namelon.
      .ovelonrridelonAuthority("rustselonrving")
      // celonrtain GRPC elonrrors nelonelond to belon relontrielond.
      .elonnablelonRelontryForStatus(Status.UNKNOWN)
      .elonnablelonRelontryForStatus(Status.RelonSOURCelon_elonXHAUSTelonD)
      // this is relonquirelond at channelonl lelonvelonl as mTLS is elonnablelond at httpClielonnt lelonvelonl
      .uselonPlaintelonxt()
      .build()

  privatelon val infelonrelonncelonSelonrvicelonStub = PrelondictionSelonrvicelonGrpc.nelonwFuturelonStub(channelonl)

  delonf scorelon(relonquelonst: ModelonlInfelonrRelonquelonst): Stitch[ModelonlInfelonrRelonsponselon] = {
    val tfSelonrvingRelonquelonst = TFSelonrvingInfelonrelonncelonSelonrvicelonImpl.adaptModelonlInfelonrRelonquelonst(relonquelonst)
    Stitch
      .callFuturelon(
        FuturelonConvelonrtelonrs
          .RichListelonnablelonFuturelon(infelonrelonncelonSelonrvicelonStub.prelondict(tfSelonrvingRelonquelonst)).toTwittelonr
          .map { relonsponselon =>
            TFSelonrvingInfelonrelonncelonSelonrvicelonImpl.adaptModelonlInfelonrRelonsponselon(relonsponselon)
          }
      )
  }
}
