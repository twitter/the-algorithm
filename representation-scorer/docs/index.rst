Representat on Scorer (RSX)
###########################

Overv ew
========

Representat on Scorer (RSX)  s a StratoFed serv ce wh ch serves scores for pa rs of ent  es (User, T et, Top c...) based on so  representat on of those ent  es. For example,   serves User-T et scores based on t  cos ne s m lar y of S mClusters embedd ngs for each of t se.    a ms to prov de t se w h low latency and at h gh scale, to support appl cat ons such as scor ng for ANN cand date generat on and feature hydrat on v a feature store.


Current use cases
-----------------

RSX currently serves traff c for t  follow ng use cases:

- User-T et s m lar y scores for Ho  rank ng, us ng S mClusters embedd ng dot product
- Top c-T et s m lar y scores for top cal t et cand date generat on and top c soc al proof, us ng S mClusters embedd ng cos ne s m lar y and CERTO scores
- T et-T et and User-T et s m lar y scores for ANN cand date generat on, us ng S mClusters embedd ng cos ne s m lar y 
- ( n develop nt) User-T et s m lar y scores for Ho  rank ng, based on var ous aggregat ons of s m lar  es w h recent faves, ret ets and follows perfor d by t  user

Gett ng Started
===============

Fetch ng scores
---------------

Scores are served from t  recom ndat ons/representat on_scorer/score column.

Us ng RSX for y  appl cat on
------------------------------

RSX may be a good f  for y  appl cat on  f   need scores based on comb nat ons of S mCluster embedd ngs for core nouns.   also plan to support ot r embedd ngs and scor ng approac s  n t  future.

.. toctree::
   :maxdepth: 2
   :h dden:

    ndex
   

