Representation Scorer (RSX)
###########################

Overview
========

Representation Scorer (RSX) is a StratoFed service which serves scores for pairs of entities (User, Tweet, Topic...) based on some representation of those entities. For example, it serves User-Tweet scores based on the cosine similarity of SimClusters embeddings for each of these.  It aims to provide these with low latency and at high scale, to support applications such as scoring for ANN candidate generation and feature hydration via feature store.


Current use cases
-----------------

RSX currently serves traffic for the following use cases:

- User-Tweet similarity scores for Home ranking, using SimClusters embedding dot product
- Topic-Tweet similarity scores for topical tweet candidate generation and topic social proof, using SimClusters embedding cosine similarity and CERTO scores
- Tweet-Tweet and User-Tweet similarity scores for ANN candidate generation, using SimClusters embedding cosine similarity 
- (in development) User-Tweet similarity scores for Home ranking, based on various aggregations of similarities with recent faves, retweets and follows performed by the user

Getting Started
===============

Fetching scores
---------------

Scores are served from the recommendations/representation_scorer/score column.

Using RSX for your application
------------------------------

RSX may be a good fit for your application if you need scores based on combinations of SimCluster embeddings for core nouns. We also plan to support other embeddings and scoring approaches in the future.

.. toctree::
   :maxdepth: 2
   :hidden:

   index
   

