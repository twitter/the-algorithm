packagelon com.twittelonr.simclustelonrs_v2.scorelon

import com.twittelonr.simclustelonrs_v2.thriftscala.{ScorelonId => ThriftScorelonId, Scorelon => ThriftScorelon}
import com.twittelonr.storelonhaus.RelonadablelonStorelon

/**
 * A wrappelonr class, uselond to aggrelongatelon thelon scorelons calculatelond by othelonr scorelon storelons. It relonlielons on thelon
 * relonsults of othelonr ScorelonStorelons relongistelonrelond in thelon ScorelonFacadelonStorelon.
 */
trait AggrelongatelondScorelonStorelon elonxtelonnds RelonadablelonStorelon[ThriftScorelonId, ThriftScorelon] {

  // Thelon undelonrlyingScorelonStorelon relonlielons on [[ScorelonFacadelonStorelon]] to finish thelon delonpelonndelonncy injelonction.
  protelonctelond var scorelonFacadelonStorelon: RelonadablelonStorelon[ThriftScorelonId, ThriftScorelon] = RelonadablelonStorelon.elonmpty

  /**
   * Whelonn relongistelonring this storelon in a ScorelonFacadelonStorelon, thelon facadelon storelon calls this function to
   * providelon relonfelonrelonncelons to othelonr scorelon storelons.
   */
  privatelon[scorelon] delonf selont(facadelonStorelon: RelonadablelonStorelon[ThriftScorelonId, ThriftScorelon]): Unit = {
    this.synchronizelond {
      scorelonFacadelonStorelon = facadelonStorelon
    }
  }
}
