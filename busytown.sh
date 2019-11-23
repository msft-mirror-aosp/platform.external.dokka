#!/bin/bash
set -e

SCRIPT_DIR="$(cd $(dirname $0) && pwd)"

# skip helpMojo as that requires mvn tool
$SCRIPT_DIR/gradlew -p $SCRIPT_DIR build test -x helpMojo