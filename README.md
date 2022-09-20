# Running Kafka within Kubernetes locally

## What we gonna do

The main idea of this tutorial is to provide ways to try and learn about these so important applications: Kafka and Kubernetes.

In addition, we gonna make some applications to produce and consume messages on the Kafka topics and also, and study and make use of:
- Docker
- [Docker-compose](https://github.com/compose-spec/compose-spec/blob/master/spec.md)
- [Kafka-UI](https://github.com/provectus/kafka-ui/blob/master/documentation/compose/kafka-ui.yaml)

### Our sample system
todo: create a diagram.

## So, what are we talking about?
### Kafka
#### Topics
#### Messages
#### Producers
#### Consumers
### ZooKeeper
[Apache ZooKeeper](https://zookeeper.apache.org/) is an open-source, *distributed coordination service* for distributed applications. 
It provides services such as naming, configuration management, synchronization, and group services. Using it you can implement consensus,
group management, leader election, and presence protocols.

Kafka relies on ZooKeeper for:
 - **Controller election:** In a Kafka cluster, one of the nodes is the Controller and it is responsible for managing the state of the partitions and replicas. So, whenever a Controller Node shuts down, a new one is elected.
 - **Configuration of topics:** Configuration regarding all the topics. It maintains the list of the existing topics, the number of partitions each one has, the location of all the replicas, which node is the preferred leader, etc.
 - **Access control lists (ACLs):** ZooKeeper maintains ACLs for all the topics.
 - **Membership of the cluster:** ZooKeeper also maintains a list of all the brokers that are running at any given moment and are part of the cluster. [1]
### Kubernetes

### Kafka-UI
https://github.com/provectus/kafka-ui
## Setup
Everything we need will be launched using docker-compose and we gonna describe the services in a compose file. I choose to use docker images from [Confluent](https://hub.docker.com/u/confluentinc).

 
[1]: https://zookeeper.apache.org/doc/r3.7.0/zookeeperUseCases.html

