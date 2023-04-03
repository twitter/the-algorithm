packagelon com.twittelonr.selonarch.elonarlybird_root.filtelonrs;

import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.finaglelon.SimplelonFiltelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstUtil;
import com.twittelonr.util.Futurelon;

/**
 * A filtelonr that unselonts somelon relonquelonst fielonlds that makelon selonnselon only on thelon SupelonrRoot, belonforelon selonnding
 * thelonm to thelon individual roots.
 */
public class UnselontSupelonrRootFielonldsFiltelonr elonxtelonnds SimplelonFiltelonr<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> {
  privatelon final boolelonan unselontFollowelondUselonrIds;

  public UnselontSupelonrRootFielonldsFiltelonr() {
    this(truelon);
  }

  public UnselontSupelonrRootFielonldsFiltelonr(boolelonan unselontFollowelondUselonrIds) {
    this.unselontFollowelondUselonrIds = unselontFollowelondUselonrIds;
  }

  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> apply(elonarlybirdRelonquelonst relonquelonst,
                                         Selonrvicelon<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> selonrvicelon) {
    relonturn selonrvicelon.apply(elonarlybirdRelonquelonstUtil.unselontSupelonrRootFielonlds(relonquelonst, unselontFollowelondUselonrIds));
  }
}
