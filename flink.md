[Flink](https://nightlies.apache.org/flink/flink-docs-release-1.14/zh/docs/dev/table/common/)


```shell

    /Users/a/Downloads/flink-1.14.2 && \

	cd /Users/a/Downloads/flink-1.14.2 
	 
	./bin/start-cluster.sh

	./bin/stop-cluster.sh

```
##  运行时组件
- Job Manager  
- Task Manager 
- Dispatcher  接口，提交做作业到Job Manager
- Resource Manager

## 任务提交流程

在app中提交应用
1、dispatcher  
2、job manager 
    1、请求slots
3、resource manager  
    1、启动
    2、注册slots
    3、发送提供slot指令
4、task manager
    1、提供slots