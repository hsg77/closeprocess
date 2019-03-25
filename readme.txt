使用java开发的在linux下关闭进程的方法

使用案例：

关闭 hbase的方法 和重新启动hbase
#!/bin/bash
stop-hbase.sh
runCmd.sh "java -cp /usr/cwgis/tools/closeprocess.jar com.cwgis.closeprocess.Main HRegionServer" all
runCmd.sh "java -cp /usr/cwgis/tools/closeprocess.jar com.cwgis.closeprocess.Main HMaster" all
runCmd.sh "rm -r -f /usr/cwgis/app/hbase/logs/*"  all
start-hbase.sh

关闭spark的方法
#!/bin/bash
stop-slaves.sh
runCmd.sh "stop-master.sh"  master
runCmd.sh "java -cp /usr/cwgis/tools/closeprocess.jar com.cwgis.closeprocess.Main Master" all
runCmd.sh "java -cp /usr/cwgis/tools/closeprocess.jar com.cwgis.closeprocess.Main Worker" all