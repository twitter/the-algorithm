na space java com.tw ter.s mclustersann.thr ftjava
#@na space scala com.tw ter.s mclustersann.thr ftscala

 nclude "f natra-thr ft/f natra_thr ft_except ons.thr ft"
 nclude "com/tw ter/s mclusters_v2/ dent f er.thr ft"
 nclude "com/tw ter/s mclusters_v2/score.thr ft"

struct Query {
    1: requ red  dent f er.S mClustersEmbedd ng d s ceEmbedd ng d;
    2: requ red S mClustersANNConf g conf g;
}

struct S mClustersANNT etCand date {
    1: requ red  64 t et d (personalDataType = 'T et d');
    2: requ red double score;
}

struct S mClustersANNConf g {
    1: requ red  32 maxNumResults;
    2: requ red double m nScore;
    3: requ red  dent f er.Embedd ngType cand dateEmbedd ngType;
    4: requ red  32 maxTopT etsPerCluster;
    5: requ red  32 maxScanClusters;
    6: requ red  32 maxT etCand dateAgeH s;
    7: requ red  32 m nT etCand dateAgeH s;
    8: requ red Scor ngAlgor hm annAlgor hm;
}

/**
  * T  algor hm type to  dent fy t  score algor hm.
  **/
enum Scor ngAlgor hm {
	DotProduct = 1,
	Cos neS m lar y = 2,
  LogCos neS m lar y = 3,
  Cos neS m lar yNoS ceEmbedd ngNormal zat on = 4,  // Score = (S ce dot Cand date) / cand date_l2_norm
}(hasPersonalData = 'false')

enum  nval dResponsePara ter {
	 NVAL D_EMBEDD NG_TYPE = 1,
	 NVAL D_MODEL_VERS ON = 2,
}

except on  nval dResponsePara terExcept on {
	1: requ red  nval dResponsePara ter errorCode,
	2: opt onal str ng  ssage // fa lure reason
}

serv ce S mClustersANNServ ce {

    l st<S mClustersANNT etCand date> getT etCand dates(
        1: requ red Query query;
    ) throws (
      1:  nval dResponsePara terExcept on e;
      2: f natra_thr ft_except ons.ServerError serverError;
      3: f natra_thr ft_except ons.Cl entError cl entError;
    );

}
