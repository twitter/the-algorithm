Representation Scorer (RStwitter)
###########################

Overview
========

Representation Scorer (RStwitter) is a StratoFed service which serves scores for pairs of entities (User, Tweet, Topic...) based on some representation of those entities. For etwitterample, it serves User-Tweet scores based on the cosine similarity of SimClusters embeddings for each of these.  It aims to provide these with low latency and at high scale, to support applications such as scoring for ANN candidate generation and feature hydration via feature store.


Current use cases
-----------------

RStwitter currently serves traffic for the following use cases:

- User-Tweet similarity scores for Home ranking, using SimClusters embedding dot product
- Topic-Tweet similarity scores for topical tweet candidate generation and topic social proof, using SimClusters embedding cosine similarity and CERTO scores
- Tweet-Tweet and User-Tweet similarity scores for ANN candidate generation, using SimClusters embedding cosine similarity 
- (in development) User-Tweet similarity scores for Home ranking, based on various aggregations of similarities with recent faves, retweets and follows performed by the user

Getting Started
===============

Fetching scores
---------------

Scores are served from the recommendations/representation_scorer/score column.

Using RStwitter for your application
------------------------------

RStwitter may be a good fit for your application if you need scores based on combinations of SimCluster embeddings for core nouns. We also plan to support other embeddings and scoring approaches in the future.

.. toctree::
   :matwitterdepth: 2
   :hidden:

   indetwitter
   

