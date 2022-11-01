package com.ocean.discovery.core.context;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.ocean.discovery.core.route.RouteRequest;

public class DiscoveryContext {

    private static final TransmittableThreadLocal<RouteRequest> THREAD_LOCAL_ROUTE_REQUEST = new TransmittableThreadLocal(){

        /**
         *
         * @param parentValue
         * @return
         */
        @Override
        protected Object childValue(Object parentValue) {
            if(parentValue instanceof RouteRequest){
                return ObjectUtil.cloneByStream(parentValue);
            }
            return null;
        }

        @Override
        public Object copy(Object parentValue) {
            if(parentValue instanceof RouteRequest){
                return ObjectUtil.cloneByStream(parentValue);
            }
            return null;
        }
    };

    public static final void setRouteRequest(RouteRequest routeRequest){
        THREAD_LOCAL_ROUTE_REQUEST.set(routeRequest);
    }

    public static final RouteRequest getRouteRequest(){
        return THREAD_LOCAL_ROUTE_REQUEST.get();
    }

    public static void clearRouteRequest() {
        THREAD_LOCAL_ROUTE_REQUEST.remove();
    }
}
