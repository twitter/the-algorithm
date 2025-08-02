package com.twitter.search.earlybird.search.queries;

import java.io.IOEtwitterception;

import org.apache.lucene.indetwitter.LeafReader;
import org.apache.lucene.indetwitter.LeafReaderContetwittert;
import org.apache.lucene.indetwitter.NumericDocValues;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.IndetwitterSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreMode;
import org.apache.lucene.search.Weight;

import com.twitter.search.common.query.DefaultFilterWeight;
import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant;
import com.twitter.search.core.earlybird.indetwitter.util.AllDocsIterator;
import com.twitter.search.core.earlybird.indetwitter.util.RangeFilterDISI;
import com.twitter.search.earlybird.common.userupdates.UserTable;

public final class UserFlagsEtwittercludeFilter etwittertends Query {
  /**
   * Returns a query that filters hits based on their author flags.
   *
   * @param etwittercludeAntisocial Determines if the filter should etwitterclude hits from antisocial users.
   * @param etwittercludeOffensive Determines if the filter should etwitterclude hits from offensive users.
   * @param etwittercludeProtected Determines if the filter should etwitterclude hits from protected users
   * @return A query that filters hits based on their author flags.
   */
  public static Query getUserFlagsEtwittercludeFilter(UserTable userTable,
                                                boolean etwittercludeAntisocial,
                                                boolean etwittercludeOffensive,
                                                boolean etwittercludeProtected) {
    return new BooleanQuery.Builder()
        .add(new UserFlagsEtwittercludeFilter(
                userTable, etwittercludeAntisocial, etwittercludeOffensive, etwittercludeProtected),
            BooleanClause.Occur.FILTER)
        .build();
  }

  private final UserTable userTable;
  private final boolean etwittercludeAntisocial;
  private final boolean etwittercludeOffensive;
  private final boolean etwittercludeProtected;

  private UserFlagsEtwittercludeFilter(
      UserTable userTable,
      boolean etwittercludeAntisocial,
      boolean etwittercludeOffensive,
      boolean etwittercludeProtected) {
    this.userTable = userTable;
    this.etwittercludeAntisocial = etwittercludeAntisocial;
    this.etwittercludeOffensive = etwittercludeOffensive;
    this.etwittercludeProtected = etwittercludeProtected;
  }

  @Override
  public int hashCode() {
    return (etwittercludeAntisocial ? 13 : 0) + (etwittercludeOffensive ? 1 : 0) + (etwittercludeProtected ? 2 : 0);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof UserFlagsEtwittercludeFilter)) {
      return false;
    }

    UserFlagsEtwittercludeFilter filter = UserFlagsEtwittercludeFilter.class.cast(obj);
    return (etwittercludeAntisocial == filter.etwittercludeAntisocial)
        && (etwittercludeOffensive == filter.etwittercludeOffensive)
        && (etwittercludeProtected == filter.etwittercludeProtected);
  }

  @Override
  public String toString(String field) {
    return "UserFlagsEtwittercludeFilter";
  }

  @Override
  public Weight createWeight(IndetwitterSearcher searcher, ScoreMode scoreMode, float boost) {
    return new DefaultFilterWeight(this) {
      @Override
      protected DocIdSetIterator getDocIdSetIterator(LeafReaderContetwittert contetwittert) throws IOEtwitterception {
        LeafReader reader = contetwittert.reader();
        if (userTable == null) {
          return new AllDocsIterator(reader);
        }

        final int bits =
            (etwittercludeAntisocial ? UserTable.ANTISOCIAL_BIT : 0)
                | (etwittercludeOffensive ? UserTable.OFFENSIVE_BIT | UserTable.NSFW_BIT : 0)
                | (etwittercludeProtected ? UserTable.IS_PROTECTED_BIT : 0);
        if (bits != 0) {
          return new UserFlagsEtwittercludeDocIdSetIterator(reader, userTable) {
            @Override
            protected boolean checkUserFlags(UserTable table, long userID) {
              return !table.isSet(userID, bits);
            }
          };
        }

        return new AllDocsIterator(reader);
      }
    };
  }

  private abstract static class UserFlagsEtwittercludeDocIdSetIterator etwittertends RangeFilterDISI {
    private final UserTable userTable;
    private final NumericDocValues fromUserID;

    public UserFlagsEtwittercludeDocIdSetIterator(
        LeafReader indetwitterReader, UserTable table) throws IOEtwitterception {
      super(indetwitterReader);
      userTable = table;
      fromUserID =
          indetwitterReader.getNumericDocValues(EarlybirdFieldConstant.FROM_USER_ID_CSF.getFieldName());
    }

    @Override
    protected boolean shouldReturnDoc() throws IOEtwitterception {
      return fromUserID.advanceEtwitteract(docID())
          && checkUserFlags(userTable, fromUserID.longValue());
    }

    protected abstract boolean checkUserFlags(UserTable table, long userID);
  }
}
