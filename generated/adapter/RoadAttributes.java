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

public interface RoadAttributes extends BaseItem
{
    void setMotorway(boolean isMotorway, com.zeroc.Ice.Current current);

    void setUrban(boolean isUrban, com.zeroc.Ice.Current current);

    void setServiceArea(boolean isServiceArea, com.zeroc.Ice.Current current);

    void setControlledAccess(boolean isControlledAccess, com.zeroc.Ice.Current current);

    void setToll(boolean isToll, com.zeroc.Ice.Current current);

    void setBridge(boolean isBridge, com.zeroc.Ice.Current current);

    void setTunnel(boolean isTunnel, com.zeroc.Ice.Current current);

    void setFerry(boolean isFerry, com.zeroc.Ice.Current current);

    /** @hidden */
    static final String[] _iceIds =
    {
        "::Ice::Object",
        "::adapter::BaseItem",
        "::adapter::RoadAttributes"
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
        return "::adapter::RoadAttributes";
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_setMotorway(RoadAttributes obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        boolean iceP_isMotorway;
        iceP_isMotorway = istr.readBool();
        inS.endReadParams();
        obj.setMotorway(iceP_isMotorway, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_setUrban(RoadAttributes obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        boolean iceP_isUrban;
        iceP_isUrban = istr.readBool();
        inS.endReadParams();
        obj.setUrban(iceP_isUrban, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_setServiceArea(RoadAttributes obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        boolean iceP_isServiceArea;
        iceP_isServiceArea = istr.readBool();
        inS.endReadParams();
        obj.setServiceArea(iceP_isServiceArea, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_setControlledAccess(RoadAttributes obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        boolean iceP_isControlledAccess;
        iceP_isControlledAccess = istr.readBool();
        inS.endReadParams();
        obj.setControlledAccess(iceP_isControlledAccess, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_setToll(RoadAttributes obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        boolean iceP_isToll;
        iceP_isToll = istr.readBool();
        inS.endReadParams();
        obj.setToll(iceP_isToll, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_setBridge(RoadAttributes obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        boolean iceP_isBridge;
        iceP_isBridge = istr.readBool();
        inS.endReadParams();
        obj.setBridge(iceP_isBridge, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_setTunnel(RoadAttributes obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        boolean iceP_isTunnel;
        iceP_isTunnel = istr.readBool();
        inS.endReadParams();
        obj.setTunnel(iceP_isTunnel, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_setFerry(RoadAttributes obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        boolean iceP_isFerry;
        iceP_isFerry = istr.readBool();
        inS.endReadParams();
        obj.setFerry(iceP_isFerry, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /** @hidden */
    final static String[] _iceOps =
    {
        "getId",
        "ice_id",
        "ice_ids",
        "ice_isA",
        "ice_ping",
        "setBridge",
        "setControlledAccess",
        "setFerry",
        "setMotorway",
        "setServiceArea",
        "setToll",
        "setTunnel",
        "setUrban"
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
                return BaseItem._iceD_getId(this, in, current);
            }
            case 1:
            {
                return com.zeroc.Ice.Object._iceD_ice_id(this, in, current);
            }
            case 2:
            {
                return com.zeroc.Ice.Object._iceD_ice_ids(this, in, current);
            }
            case 3:
            {
                return com.zeroc.Ice.Object._iceD_ice_isA(this, in, current);
            }
            case 4:
            {
                return com.zeroc.Ice.Object._iceD_ice_ping(this, in, current);
            }
            case 5:
            {
                return _iceD_setBridge(this, in, current);
            }
            case 6:
            {
                return _iceD_setControlledAccess(this, in, current);
            }
            case 7:
            {
                return _iceD_setFerry(this, in, current);
            }
            case 8:
            {
                return _iceD_setMotorway(this, in, current);
            }
            case 9:
            {
                return _iceD_setServiceArea(this, in, current);
            }
            case 10:
            {
                return _iceD_setToll(this, in, current);
            }
            case 11:
            {
                return _iceD_setTunnel(this, in, current);
            }
            case 12:
            {
                return _iceD_setUrban(this, in, current);
            }
        }

        assert(false);
        throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
    }
}
