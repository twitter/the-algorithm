## Ingesters
Ingesters are services that consume raw tweets and user updates, process them through a series of transformations and write them to kafka topics for Earlybird to consume and subsequently index. 

There are two types of ingesters:
1. Tweet ingesters
2. UserUpdates ingesters

Tweet ingesters consume raw tweets and extract different fields and features for Earlybird to index. User updates ingester produces user safety information such as whether the user is deactivated, suspended or off-boarded. The user and tweet features produced by ingesters are then used by Earlybird during tweet retieval and ranking.  

Ingesters are made up of a pipeline of stages with each stage performing a different field/feature extraction. The pipeline configuration of the ingesters can be found at science/search/ingester/config
