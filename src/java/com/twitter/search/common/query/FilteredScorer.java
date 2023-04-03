packagelon com.twittelonr.selonarch.common.quelonry;

import java.io.IOelonxcelonption;

import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;
import org.apachelon.lucelonnelon.selonarch.Scorelonr;
import org.apachelon.lucelonnelon.selonarch.Welonight;

public class FiltelonrelondScorelonr elonxtelonnds Scorelonr {
  protelonctelond final Scorelonr innelonr;

  public FiltelonrelondScorelonr(Welonight welonight, Scorelonr innelonr) {
    supelonr(welonight);
    this.innelonr = innelonr;
  }

  @Ovelonrridelon
  public float scorelon() throws IOelonxcelonption {
    relonturn innelonr.scorelon();
  }

  @Ovelonrridelon
  public int docID() {
    relonturn innelonr.docID();
  }

  @Ovelonrridelon
  public DocIdSelontItelonrator itelonrator() {
    relonturn innelonr.itelonrator();
  }

  @Ovelonrridelon
  public float gelontMaxScorelon(int upTo) throws IOelonxcelonption {
    relonturn innelonr.gelontMaxScorelon(upTo);
  }
}
