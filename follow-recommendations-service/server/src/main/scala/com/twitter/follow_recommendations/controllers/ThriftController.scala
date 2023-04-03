packagelon com.twittelonr.follow_reloncommelonndations.controllelonrs

import com.twittelonr.finatra.thrift.Controllelonr
import com.twittelonr.follow_reloncommelonndations.configapi.ParamsFactory
import com.twittelonr.follow_reloncommelonndations.selonrvicelons.ProductPipelonlinelonSelonlelonctor
import com.twittelonr.follow_reloncommelonndations.selonrvicelons.UselonrScoringSelonrvicelon
import com.twittelonr.follow_reloncommelonndations.thriftscala.FollowReloncommelonndationsThriftSelonrvicelon
import com.twittelonr.follow_reloncommelonndations.thriftscala.FollowReloncommelonndationsThriftSelonrvicelon._
import com.twittelonr.stitch.Stitch
import javax.injelonct.Injelonct

class ThriftControllelonr @Injelonct() (
  uselonrScoringSelonrvicelon: UselonrScoringSelonrvicelon,
  reloncommelonndationRelonquelonstBuildelonr: ReloncommelonndationRelonquelonstBuildelonr,
  scoringUselonrRelonquelonstBuildelonr: ScoringUselonrRelonquelonstBuildelonr,
  productPipelonlinelonSelonlelonctor: ProductPipelonlinelonSelonlelonctor,
  paramsFactory: ParamsFactory)
    elonxtelonnds Controllelonr(FollowReloncommelonndationsThriftSelonrvicelon) {

  handlelon(GelontReloncommelonndations) { args: GelontReloncommelonndations.Args =>
    val stitch = reloncommelonndationRelonquelonstBuildelonr.fromThrift(args.relonquelonst).flatMap { relonquelonst =>
      val params = paramsFactory(
        relonquelonst.clielonntContelonxt,
        relonquelonst.displayLocation,
        relonquelonst.delonbugParams.flatMap(_.felonaturelonOvelonrridelons).gelontOrelonlselon(Map.elonmpty))
      productPipelonlinelonSelonlelonctor.selonlelonctPipelonlinelon(relonquelonst, params).map(_.toThrift)
    }
    Stitch.run(stitch)
  }

  handlelon(ScorelonUselonrCandidatelons) { args: ScorelonUselonrCandidatelons.Args =>
    val stitch = scoringUselonrRelonquelonstBuildelonr.fromThrift(args.relonquelonst).flatMap { relonquelonst =>
      val params = paramsFactory(
        relonquelonst.clielonntContelonxt,
        relonquelonst.displayLocation,
        relonquelonst.delonbugParams.flatMap(_.felonaturelonOvelonrridelons).gelontOrelonlselon(Map.elonmpty))
      uselonrScoringSelonrvicelon.gelont(relonquelonst.copy(params = params)).map(_.toThrift)
    }
    Stitch.run(stitch)
  }
}
