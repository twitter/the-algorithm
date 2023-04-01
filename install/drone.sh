#!/usr/bin/env bash
###############################################################################
# reddit Drone environment installer
# ----------------------------------
# This script re-purposes some of our existing vagrant/Travis install and
# setup scripts for our Drone CI builds.
#
# NOTE: You don't want to run this script directly in your development
# environment, since we assume that it's running within this Docker image
# that Drone runs our builds within: https://github.com/reddit/docker-reddit-py
#
# docker-reddit-py has most of the apt dependencies pre-installed in order to
# significantly reduce our build times.
#
# Refer to .drone.yml in the repo root to see where this script gets called
# during a build.
###############################################################################

###############################################################################
# Install prerequisites
###############################################################################

# Under normal operation, this won't install anything new. We're re-using the
# logic that checks to make sure all services have finished starting before
# continuing.
install/install_services.sh

###############################################################################
# Install and configure the reddit code
###############################################################################

pushd r2
python setup.py develop
make pyx
ln -sf example.ini test.ini
popd

###############################################################################
# Configure local services
###############################################################################

# Creates the column families required for the tests
install/setup_cassandra.sh
