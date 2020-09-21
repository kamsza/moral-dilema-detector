//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.3
//
// <auto-generated>
//
// Generated from file `adapter.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package adapter;

public interface Pedestrian extends Entity
{
    /** @hidden */
    static final String[] _iceIds =
    {
        "::Ice::Object",
        "::adapter::BaseItem",
        "::adapter::Entity",
        "::adapter::Pedestrian"
    };

    @Override
    default String[] ice_ids(com.zeroc.Ice.Current current)
    {
        return _iceIds;
    }

    @Override
    default String ice_id(com.zeroc.Ice.Current current)
    {
        return ice_staticId();
    }

    static String ice_staticId()
    {
        return "::adapter::Pedestrian";
    }

    /** @hidden */
    final static String[] _iceOps =
    {
        "getDistance",
        "getId",
        "getLaneId",
        "ice_id",
        "ice_ids",
        "ice_isA",
        "ice_ping",
        "setDistance",
        "setId",
        "setLane"
    };

    /** @hidden */
    @Override
    default java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceDispatch(com.zeroc.IceInternal.Incoming in, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        int pos = java.util.Arrays.binarySearch(_iceOps, current.operation);
        if(pos < 0)
        {
            throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
        }

        switch(pos)
        {
            case 0:
            {
                return Entity._iceD_getDistance(this, in, current);
            }
            case 1:
            {
                return BaseItem._iceD_getId(this, in, current);
            }
            case 2:
            {
                return Entity._iceD_getLaneId(this, in, current);
            }
            case 3:
            {
                return com.zeroc.Ice.Object._iceD_ice_id(this, in, current);
            }
            case 4:
            {
                return com.zeroc.Ice.Object._iceD_ice_ids(this, in, current);
            }
            case 5:
            {
                return com.zeroc.Ice.Object._iceD_ice_isA(this, in, current);
            }
            case 6:
            {
                return com.zeroc.Ice.Object._iceD_ice_ping(this, in, current);
            }
            case 7:
            {
                return Entity._iceD_setDistance(this, in, current);
            }
            case 8:
            {
                return BaseItem._iceD_setId(this, in, current);
            }
            case 9:
            {
                return Entity._iceD_setLane(this, in, current);
            }
        }

        assert(false);
        throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
    }
}