/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.hops.metadata.hdfs.entity;

public final class XAttrMetadataLogEntry extends MetadataLogEntry{
  
  public enum Operation implements OperationBase {
    Add((short) 10),
    AddAll((short) 11),
    Update((short) 12),
    Delete((short) 13);
  
    private final short opId;
    Operation(short opId) {
      this.opId = opId;
    }
  
    @Override
    public short getId() {
      return opId;
    }
  
    static Operation valueOf(short id) {
      for(Operation op : Operation.values()){
        if(op.getId() == id){
          return op;
        }
      }
      return null;
    }
  }
  
  private final Operation operation;
  public XAttrMetadataLogEntry(MetadataLogEntry entry){
    this(entry.getDatasetId(), entry.getInodeId(), entry.getLogicalTime(),
        (byte)entry.getPk2(), entry.getPk3(),
        Operation.valueOf(entry.getOperationId()));
  }
  
  public XAttrMetadataLogEntry(long datasetId, long inodeId,
      int logicalTime, byte namespace, String name, Operation operation) {
    super(datasetId, inodeId, logicalTime, inodeId, namespace, name,
        operation.getId());
    this.operation = operation;
  }
  
  public XAttrMetadataLogEntry(long datasetId, long inodeId,
      int logicalTime) {
    super(datasetId, inodeId, logicalTime, -1, -1, "-1",
        Operation.AddAll.getId());
    this.operation = Operation.AddAll;
  }
  
  public byte getNamespace(){
    return (byte) getPk2();
  }
  
  public String getName(){
    return getPk3();
  }
  
  public Operation getOperation(){
    return operation;
  }
  
  public static boolean isValidOperation(short operationId) {
    return Operation.valueOf(operationId) != null;
  }
}
