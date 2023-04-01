#!/bin/bash

mysql -h"127.0.0.1" -P"3306" -u"root" -p"password" -e \
  "SET GLOBAL TRANSACTION ISOLATION LEVEL READ COMMITTED;"
