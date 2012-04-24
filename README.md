Shark -- Hive on Spark


Build
-----
Shark requires Hive 0.7.0 and Spark (0.4-SNAPSHOT).

Get Hive from Apache:
# export HIVE_HOME=/path/to/hive
# wget http://archive.apache.org/dist/hive/hive-0.7.0/hive-0.7.0-bin.tar.gz
# tar xvzf hive-0.7.0-bin.tar.gz
# mv hive-0.7.0-bin.tar.gz $HIVE_HOME

Get Spark from Github and compile:
# export SPARK_HOME=/path/to/spark
# git clone git://github.com/mesos/spark.git spark 
# cd $SPARK_HOME 
# sbt/sbt publish-local

Get Shark from Github:
# export SHARK_HOME=/path/to/shark
# git clone git://github.com/amplab/shark.git $SHARK_HOME
# cd $SHARK_HOME

Before building Shark, first modify the config file:
# conf/shark-env.sh 

Compile Shark: 
# sbt/sbt compile

To generate the Eclipse project files, do
# sbt/sbt eclipse

Execution
---------
There are several executables in /bin:

shark: Runs Shark CLI.

shark-shell: Runs Shark scala console. This provides an experimental feature
to convert Hive QL queries into TableRDD's.

shark-withinfo: Runs Shark with debug info

shark-withdebug: Runs Shark with even more debu info

clear-buffer-cache.py: Automatically clears OS buffer caches on Mesos EC2
clusters. This is for development only.

Runtime Configuration
---------------------
Shark reuses Hive's configuration files, which are loaded from $HIVE_HOME/conf.

We also include a few Shark-specific configuration parameters that can be set
in the same way as you would set configuration parameters in Hive (e.g. from the 
Shark CLI):

shark> shark.exec.mode = [hive | shark (default)]
shark> shark.explain.mode = [hive | shark (default)]

Caching
----------------------

Shark caches tables in memory as long as their name ends in "_cached". For example, 
if you have a table named "test", you can create a cached version of it as follows:

shark> CREATE TABLE test_cached AS SELECT * FROM test;

Testing
-------
This requires the development package of Hive. Download it from github:
https://github.com/apache/hive/zipball/release-0.7.0

Then set $HIVE_HOME and $HIVE_DEV_HOME in conf/shark-env.sh

Note that $HIVE_HOME should be in build/dist in $HIVE_HOME.

To run Hive's test suite, first generate Hive's TestCliDriver script.
# ant package
# ant test -Dtestcase=TestCliDriver
The above command generates the Hive test Java files from Velocity templates,
and then starts executing the tests. You can stop once the tests start running.

Then compile our test
# sbt/sbt test:compile

Then run the test with

# TEST=down ./bin/test

You can control what tests to run by changing the TEST environmental variable.
If specified, only tests that match the TEST regex will be run. You can only
specify a whitelist of test suite to run using TEST_FILE. For example, to run
our regression test, you can do

# TEST_FILE=src/test/tests_pass.txt ./bin/test

You can also combine both TEST and TEST_FILE, in which case only tests that
satisfy both filters will be executed.


References
----------
For information on setting up Hive or HiveQl, please read:
https://cwiki.apache.org/confluence/display/Hive/GettingStarted

For information on Spark, please read:
https://github.com/mesos/spark

