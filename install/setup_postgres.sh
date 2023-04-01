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
# Configure PostgreSQL
###############################################################################
SQL="SELECT COUNT(1) FROM pg_catalog.pg_database WHERE datname = 'reddit';"
IS_DATABASE_CREATED=$(sudo -u postgres psql -t -c "$SQL")

if [ $IS_DATABASE_CREATED -ne 1 ]; then
    cat <<PGSCRIPT | sudo -u postgres psql
CREATE DATABASE reddit WITH ENCODING = 'utf8' TEMPLATE template0 LC_COLLATE='en_US.utf8' LC_CTYPE='en_US.utf8';
CREATE USER reddit WITH PASSWORD 'password';
PGSCRIPT
fi

sudo -u postgres psql reddit <<FUNCTIONSQL
create or replace function hot(ups integer, downs integer, date timestamp with time zone) returns numeric as \$\$
    select round(cast(log(greatest(abs(\$1 - \$2), 1)) * sign(\$1 - \$2) + (date_part('epoch', \$3) - 1134028003) / 45000.0 as numeric), 7)
\$\$ language sql immutable;

create or replace function score(ups integer, downs integer) returns integer as \$\$
    select \$1 - \$2
\$\$ language sql immutable;

create or replace function controversy(ups integer, downs integer) returns float as \$\$
    select CASE WHEN \$1 <= 0 or \$2 <= 0 THEN 0
                WHEN \$1 > \$2 THEN power(\$1 + \$2, cast(\$2 as float) / \$1)
                ELSE power(\$1 + \$2, cast(\$1 as float) / \$2)
           END;
\$\$ language sql immutable;

create or replace function ip_network(ip text) returns text as \$\$
    select substring(\$1 from E'[\\d]+\.[\\d]+\.[\\d]+')
\$\$ language sql immutable;

create or replace function base_url(url text) returns text as \$\$
    select substring(\$1 from E'(?i)(?:.+?://)?(?:www[\\d]*\\.)?([^#]*[^#/])/?')
\$\$ language sql immutable;

create or replace function domain(url text) returns text as \$\$
    select substring(\$1 from E'(?i)(?:.+?://)?(?:www[\\d]*\\.)?([^#/]*)/?')
\$\$ language sql immutable;
FUNCTIONSQL
