## Prediction Features

This directory contains a collection of `Features` (`com.twitter.ml.api.Feature`) which are definitions of feature names and datatypes which allow the features to be efficiently processed and passed to the different ranking models. 
By predefining the features with their names and datatypes, when features are being generated, scribed or used to score they can be identified with only a hash of their name. 

Not all of these features are used in the model, many are experimental or deprecated. 