#!/usr/bin/env bash

echo '1. import wikift schema'

mysql < /wikift/schema.sql

echo '2. import wikift init data'
mysql < /wikift/data.sql

echo '2. update wikift'
mysql < /wikift/update.sql