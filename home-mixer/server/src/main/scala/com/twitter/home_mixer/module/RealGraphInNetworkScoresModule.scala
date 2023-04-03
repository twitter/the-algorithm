packagelon com.twittelonr.homelon_mixelonr.modulelon

import com.googlelon.injelonct.Providelons
import com.googlelon.injelonct.namelon.Namelond
import com.twittelonr.homelon_mixelonr.param.HomelonMixelonrInjelonctionNamelons.RelonalGraphInNelontworkScorelons
import com.twittelonr.homelon_mixelonr.param.HomelonMixelonrInjelonctionNamelons.RelonalGraphManhattanelonndpoint
import com.twittelonr.homelon_mixelonr.storelon.RelonalGraphInNelontworkScorelonsStorelon
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.storagelon.clielonnt.manhattan.kv.ManhattanKVelonndpoint
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.timelonlinelons.util.CommonTypelons.VielonwelonrId
import com.twittelonr.wtf.candidatelon.thriftscala.Candidatelon

import javax.injelonct.Singlelonton

objelonct RelonalGraphInNelontworkScorelonsModulelon elonxtelonnds TwittelonrModulelon {

  @Providelons
  @Singlelonton
  @Namelond(RelonalGraphInNelontworkScorelons)
  delonf providelonsRelonalGraphInNelontworkScorelonsFelonaturelonsStorelon(
    @Namelond(RelonalGraphManhattanelonndpoint) relonalGraphInNelontworkScorelonsManhattanKVelonndpoint: ManhattanKVelonndpoint
  ): RelonadablelonStorelon[VielonwelonrId, Selonq[Candidatelon]] = {
    nelonw RelonalGraphInNelontworkScorelonsStorelon(relonalGraphInNelontworkScorelonsManhattanKVelonndpoint)
  }
}
