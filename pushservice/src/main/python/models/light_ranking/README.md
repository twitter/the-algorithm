# Notification Light Ranker Model

## Model Context
There are 4 major components of Twitter notifications recommendation system: 1) candidate generation 2) light ranking 3) heavy ranking & 4) quality control. This notification light ranker model bridges candidate generation and heavy ranking by pre-selecting highly-relevant candidates from the initial huge candidate pool. It’s a light-weight model to reduce system cost during heavy ranking without hurting user experience.

## Directory Structure
- BUILD: this file defines python library dependencies
- model_pools_mlp.py: this file defines tensorflow model architecture for the notification light ranker model
- deep_norm.py: this file contains 1) how to build the tensorflow graph with specified model architecture, loss function and training configuration. 2) how to set up the overall model training & evaluation pipeline
- eval_model.py: the main python entry file to set up the overall model evaluation pipeline




