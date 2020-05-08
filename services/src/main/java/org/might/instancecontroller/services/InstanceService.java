package org.might.instancecontroller.services;

import org.might.instancecontroller.dba.entity.Function;

public interface InstanceService {
     /**
      * Perform instantiation new instance of function.
      * @param function {@link Function}.
      * @throws InterruptedException
      */
     void instantiate(Function function) throws InterruptedException;

     /**
      * Perform releasing selected instance of function.
      * @param serverId instance id of server.
      * @throws InterruptedException
      */
     void releaseInstanceByServerId(Long serverId) throws InterruptedException;

     /**
      * Perform the releasing and new instantiation based on old instance of function.
      * @param serverId instance id of server.
      * @throws InterruptedException
      */
     void reInstantiateByServerId(Long serverId) throws InterruptedException;
}
