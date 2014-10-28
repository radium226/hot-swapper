#!/bin/bash

export TOOLS_JAR="${JAVA_HOME}/../lib/tools.jar"
export SOURCE_FP="./example/src/main/java/radium/example/impl/OneByOneLooper.java"
export CLASS_FP="./example/target/classes/radium/example/impl/OneByOneLooper.class"
export CLASSPATH="./example/target/classes"

maven_example()
{
	mvn -f "./example/pom.xml" "${@}"
}

java()
{
	"${JAVA_HOME}/bin/java" "${@}"
}

dcevm()
{
	java -XXaltjvm=dcevm "${@}"
}

jetty()
{
	dcevm -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000 -jar "./jetty/jetty-runner-9.2.3.v20140905.jar" "${@}"
}

package_webapp()
{
	mvn -f "./webapp/pom.xml" "clean" "compile" "war:exploded"
}

package_webapp
jetty "./webapp/target/webapp"
