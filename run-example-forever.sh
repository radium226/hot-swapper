#!/bin/bash

export TOOLS_JAR="${JAVA_HOME}/../lib/tools.jar"
export SOURCE_FP="./example/src/main/java/radium/example/impl/OneByOneLooper.java"
export CLASS_FP="./example/target/classes/radium/example/impl/OneByOneLooper.class"
export CLASSPATH="./example/target/classes"

maven_example()
{
	mvn -f "./example/pom.xml" "${@}"
}

dcevm()
{
	java -XXaltjvm=dcevm "${@}"
}

clean_example()
{
	maven_example "clean"
}

compile_example()
{
	maven_example "compile"
}

run_example()
{
	dcevm -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000 -cp "${CLASSPATH}" "radium.example.Loop"
}

modify_example_sources()
{
	if grep "Loop Iteration" "${SOURCE_FP}"; then
		sed -i 's,Loop Iteration,Index Incrementation,g' "${SOURCE_FP}"
	else
		sed -i 's,Index Incrementation,Loop Iteration,g' "${SOURCE_FP}"
	fi
}

hot_swap()
{
	mvn -f "./hot-swapper/pom.xml" clean package
	java -cp "./hot-swapper/target/hot-swapper-0.0.1-SNAPSHOT.jar;$( cygpath --windows "${TOOLS_JAR}" )" "radium.hotswapper.HotSwap" "${CLASS_FP}"
}

compile_example
while true; do
	run_example
done
