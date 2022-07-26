# This will rsync the proto/ dir with all paths containing "src/main/proto", i.e. all Quarkus protobuf directories

find * -ipath "*src/main/proto*" -type d | xargs -I {} rsync -all --recursive --delete proto/ {}
