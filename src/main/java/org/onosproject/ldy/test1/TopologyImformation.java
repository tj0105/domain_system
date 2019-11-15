package org.onosproject.ldy.test1;


import org.onosproject.net.DeviceId;

import java.util.List;
import java.util.Map;

public class TopologyImformation {
    private List<DeviceId> deviceIdList;
    private Map<DeviceId,List<Edge>> devices_edgelist_map;

    private TopologyImformation(List<DeviceId> deviceIdList,Map<DeviceId,List<Edge>> devices_edgelist_map){
        this.deviceIdList=deviceIdList;
        this.devices_edgelist_map=devices_edgelist_map;
    }

    public List<DeviceId> getDeviceIdList() {
        return deviceIdList;
    }

    public void setDeviceIdList(List<DeviceId> deviceIdList) {
        this.deviceIdList = deviceIdList;
    }

    public Map<DeviceId, List<Edge>> getDevices_edgelist_map() {
        return devices_edgelist_map;
    }

    public void setDevices_edgelist_map(Map<DeviceId, List<Edge>> devices_edgelist_map) {
        this.devices_edgelist_map = devices_edgelist_map;
    }

    static class Edge{
        private DeviceId startDeviceID;
        private DeviceId endDeviceID;
        private double bandwigth_weight;

        public Edge(DeviceId startDeviceID,DeviceId endDeviceID,double bandwigth_weight){
            this.startDeviceID=startDeviceID;
            this.endDeviceID=endDeviceID;
            this.bandwigth_weight=bandwigth_weight;
        }

        public Edge(){

        }
        public DeviceId getStartDeviceID() {
            return startDeviceID;
        }

        public void setStartDeviceID(DeviceId startDeviceID) {
            this.startDeviceID = startDeviceID;
        }

        public DeviceId getEndDeviceID() {
            return endDeviceID;
        }

        public void setEndDeviceID(DeviceId endDeviceID) {
            this.endDeviceID = endDeviceID;
        }

        public double getBandwigth_weight() {
            return bandwigth_weight;
        }

        public void setBandwigth_weight(double bandwigth_weight) {
            this.bandwigth_weight = bandwigth_weight;
        }
    }

}
