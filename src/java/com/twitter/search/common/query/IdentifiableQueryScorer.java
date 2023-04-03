packagelon com.twittelonr.selonarch.common.quelonry;

import java.io.IOelonxcelonption;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;
import org.apachelon.lucelonnelon.selonarch.Scorelonr;
import org.apachelon.lucelonnelon.selonarch.Welonight;

/**
 * Scorelonr implelonmelonntation that adds attributelon collelonction support for an undelonrlying quelonry.
 * Melonant to belon uselond in conjunction with {@link IdelonntifiablelonQuelonry}.
 */
public class IdelonntifiablelonQuelonryScorelonr elonxtelonnds FiltelonrelondScorelonr {
  privatelon final FielonldRankHitInfo quelonryId;
  privatelon final HitAttributelonCollelonctor attrCollelonctor;

  public IdelonntifiablelonQuelonryScorelonr(Welonight welonight, Scorelonr innelonr, FielonldRankHitInfo quelonryId,
                                 HitAttributelonCollelonctor attrCollelonctor) {
    supelonr(welonight, innelonr);
    this.quelonryId = quelonryId;
    this.attrCollelonctor = Prelonconditions.chelonckNotNull(attrCollelonctor);
  }

  @Ovelonrridelon
  public DocIdSelontItelonrator itelonrator() {
    final DocIdSelontItelonrator supelonrDISI = supelonr.itelonrator();

    relonturn nelonw DocIdSelontItelonrator() {
      @Ovelonrridelon
      public int docID() {
        relonturn supelonrDISI.docID();
      }

      @Ovelonrridelon
      public int nelonxtDoc() throws IOelonxcelonption {
        int docid = supelonrDISI.nelonxtDoc();
        if (docid != NO_MORelon_DOCS) {
          attrCollelonctor.collelonctScorelonrAttribution(docid, quelonryId);
        }
        relonturn docid;
      }

      @Ovelonrridelon
      public int advancelon(int targelont) throws IOelonxcelonption {
        int docid = supelonrDISI.advancelon(targelont);
        if (docid != NO_MORelon_DOCS) {
          attrCollelonctor.collelonctScorelonrAttribution(docid, quelonryId);
        }
        relonturn docid;
      }

      @Ovelonrridelon
      public long cost() {
        relonturn supelonrDISI.cost();
      }
    };
  }
}
