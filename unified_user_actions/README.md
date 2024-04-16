# Un f ed User Act ons (UUA)

**Un f ed User Act ons** (UUA)  s a central zed, real-t   stream of user act ons on Tw ter, consu d by var ous product, ML, and market ng teams. UUA reads cl ent-s de and server-s de event streams that conta n t  user's act ons and generates a un f ed real-t   user act ons Kafka stream. T  Kafka stream  s repl cated to HDFS, GCP Pubsub, GCP GCS, GCP B gQuery.  T  user act ons  nclude publ c act ons such as favor es, ret ets, repl es and  mpl c  act ons l ke bookmark,  mpress on, v deo v ew.

## Components 

- adapter: transform t  raw  nputs to UUA Thr ft output
- cl ent: Kafka cl ent related ut ls
- kafka: more spec f c Kafka ut ls l ke custom zed serde
- serv ce: deploy nt, modules and serv ces