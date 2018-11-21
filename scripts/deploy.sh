#!/bin/bash

# Change working directory
SCRIPTDIR="$(dirname $0)"
cd $SCRIPTDIR

# Find jar
file=$(ls -1 ../target/ | grep ".*jar$")
if [ ! -f "../target/${file}" ]; then
        echo "No jar file found to deploy"
        exit 1
fi
echo "Found $file to deploy"

ssh user@slc-ap-prd-49 <<-EOF
 service newrelicqueryinsight stop
EOF
scp ../target/$file user@slc-ap-prd-49:~/newrelic-query-insight.jar
ssh user@slc-ap-prd-49 <<-EOF
 sudo cp /home/user/newrelic-query-insight.jar /home/nr-query/newrelicqueryinsight/newrelic-query-insight.jar
 sudo chmod +x /home/nr-query/newrelicqueryinsight/newrelic-query-insight.jar
 sudo chown nr-query:nr-query /home/nr-query/newrelicqueryinsight/newrelic-query-insight.jar
 sudo systemctl start newrelicqueryinsight
EOF
