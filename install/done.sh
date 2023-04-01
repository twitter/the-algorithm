#!/bin/bash
# The contents of this file are subject to the Common Public Attribution
# License Version 1.0. (the "License"); you may not use this file except in
# compliance with the License. You may obtain a copy of the License at
# http://code.reddit.com/LICENSE. The License is based on the Mozilla Public
# License Version 1.1, but Sections 14 and 15 have been added to cover use of
# software over a computer network and provide for limited attribution for the
# Original Developer. In addition, Exhibit A has been modified to be consistent
# with Exhibit B.
#
# Software distributed under the License is distributed on an "AS IS" basis,
# WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
# the specific language governing rights and limitations under the License.
#
# The Original Code is reddit.
#
# The Original Developer is the Initial Developer.  The Initial Developer of
# the Original Code is reddit Inc.
#
# All portions of the code written by reddit are Copyright (c) 2006-2015 reddit
# Inc. All Rights Reserved.
###############################################################################

# load configuration
RUNDIR=$(dirname $0)
source $RUNDIR/install.cfg

###############################################################################
# All done!
###############################################################################
cat <<CONCLUSION

Congratulations! reddit is now installed.

The reddit application code is managed with upstart, to see what's currently
running, run

    sudo initctl list | grep reddit

Cron jobs start with "reddit-job-" and queue processors start with
"reddit-consumer-". The crons are managed by /etc/cron.d/reddit. You can
initiate a restart of all the consumers by running:

    sudo reddit-restart

or target specific ones:

    sudo reddit-restart scraper_q

See the GitHub wiki for more information on these jobs:

* https://github.com/reddit/reddit/wiki/Cron-jobs
* https://github.com/reddit/reddit/wiki/Services

The reddit code can be shut down or started up with

    sudo reddit-stop
    sudo reddit-start

And if you think caching might be hurting you, you can flush memcache with

    reddit-flush

Now that the core of reddit is installed, you may want to do some additional
steps:

* Ensure that $REDDIT_DOMAIN resolves to this machine.

* To populate the database with test data, run:

    cd $REDDIT_SRC/reddit
    reddit-run scripts/inject_test_data.py -c 'inject_test_data()'

* Manually run reddit-job-update_reddits immediately after populating the db
  or adding your own subreddits.
CONCLUSION
