# Notification Heavy Ranker Model

## Model Context
There are 4 major components of Twitter notifications recommendation system: 1) candidate generation 2) light ranking 3) heavy ranking & 4) quality control. This notification heavy ranker model is the core ranking model for the personalised notifications recommendation. It's a multi-task learning model to predict the probabilities that the target users will open and engage with the sent notifications. 


## Directory Structure
- BUILD: this file defines python library dependencies
- deep_norm.py: this file contains how to set up continuous training, model evaluation and model exporting for the notification heavy ranker model
- eval.py: the main python entry file to set up the overall model evaluation pipeline
- features.py: this file contains importing feature list and support functions for feature engineering
- graph.py: this file defines how to build the tensorflow graph with specified model architecture, loss function and training configuration
- model_pools.py: this file defines the available model types for the heavy ranker
- params.py: this file defines hyper-parameters used in the notification heavy ranker 
- run_args.py: this file defines command line parameters to run model training & evaluation
- update_warm_start_checkpoint.py: this file contains the support to modify checkpoints of the given saved heavy ranker model
- lib/BUILD: this file defines python library dependencies for tensorflow model architecture
- lib/layers.py: this file defines different type of convolution layers to be used in the heavy ranker model
- lib/model.py: this file defines the module containing ClemNet, the heavy ranker model type
- lib/params.py: this file defines parameters used in the heavy ranker model 
