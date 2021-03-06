package com.gome.kafka.Example;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

public class PartitionerExample implements Partitioner {
    public PartitionerExample (VerifiableProperties props) {
    	System.out.println(props);
    }
    /**
     * key: topic
     * a_numPartitions: partition nums
     */
    @Override
    public int partition(Object key, int a_numPartitions) {
        int partition = 0;
        String stringKey = (String) key;
        int offset = stringKey.lastIndexOf('.');
        if (offset > 0) {
           partition = Integer.parseInt( stringKey.substring(offset+1)) % a_numPartitions;
        }
       return partition;
  }
 
}