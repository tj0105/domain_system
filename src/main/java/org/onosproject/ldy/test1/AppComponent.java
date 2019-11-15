/*
 * Copyright 2019-present Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onosproject.ldy.test1;
import org.apache.felix.scr.annotations.*;
import org.onlab.packet.Ethernet;
import org.onosproject.core.ApplicationId;
import org.onosproject.core.CoreService;
import org.onosproject.floodlightpof.protocol.OFMatch20;
import org.onosproject.floodlightpof.protocol.action.OFAction;
import org.onosproject.floodlightpof.protocol.action.OFActionPacketIn;
import org.onosproject.floodlightpof.protocol.table.OFFlowTable;
import org.onosproject.floodlightpof.protocol.table.OFTableType;
import org.onosproject.net.DeviceId;
import org.onosproject.net.Host;
import org.onosproject.net.HostId;
import org.onosproject.net.PortNumber;
import org.onosproject.net.flow.*;
import org.onosproject.net.flow.criteria.Criteria;
import org.onosproject.net.flow.criteria.Criterion;
import org.onosproject.net.flow.instructions.DefaultPofActions;
import org.onosproject.net.flow.instructions.DefaultPofInstructions;
import org.onosproject.net.flow.instructions.PofAction;
import org.onosproject.net.group.DefaultGroupKey;
import org.onosproject.net.group.GroupKey;
import org.onosproject.net.group.GroupService;
import org.onosproject.net.host.HostService;
import org.onosproject.net.packet.*;
import org.onosproject.net.table.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
/**
 * Skeletal ONOS application component.
 */
@Component(immediate = true)
public class AppComponent {
    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected FlowTableStore flowTableStore;
    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected FlowRuleService flowRuleService;
    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected CoreService coreService;
    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected FlowTableService flowTableService;
    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected FlowTableStore tableStore;
    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected GroupService groupService;
    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected PacketService packetService;

    private ReactivePacketProcessor processor = new ReactivePacketProcessor();
    private byte global_table_id_1 [] =new byte[6] ;    // used for pof
    private byte global_table_id_2 [] =new byte[6];
    //file_id
    public final short SIP = 12;

    private ApplicationId appId;
    public DeviceId deviceId1=null;
    public DeviceId deviceId2=null;
    public DeviceId deviceId3=null;
    public DeviceId deviceId4=null;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Activate
    protected void activate() {
        log.info("Started");
        appId=coreService.registerApplication("org.onosproject.test.action.ldy");
        try {
            new EochServer().start();
        }catch (Exception e){
            e.printStackTrace();
        }
//        pofstart();
//        packetService.addProcessor(processor, PacketProcessor.director(2));
//        log.info("###########this is packet in");
    }

    @Deactivate
    protected void deactivate() {
        log.info("Stopped");
//        pofTestStop();
    }
    public void pofstart(){
        log.info("org.onosproject.pof.test.action started");
        deviceId1=DeviceId.deviceId("pof:0000000000000002");
//        deviceId2=DeviceId.deviceId("pof:0000000000000002");
//        deviceId3=DeviceId.deviceId("pof:0000000000000003");
//        deviceId4=DeviceId.deviceId("pof:0000000000000004");
        byte tableId1 = sendPofFlowTable(deviceId1, "FirstEntryTable");
//        byte next_table_id1 = sendPofFlowTable(deviceId1, "AddVlcHeaderTable");
//        byte tableId2 = sendPofFlowTable(deviceId2, "FirstEntryTable");
//        byte next_table_id2 = sendPofFlowTable(deviceId2, "AddVlcHeaderTable");
//        byte tableId3 = sendPofFlowTable(deviceId3, "FirstEntryTable");
//        byte next_table_id3 = sendPofFlowTable(deviceId3, "AddVlcHeaderTable");
//        byte tableId4 = sendPofFlowTable(deviceId4, "FirstEntryTable");
//        byte next_table_id4 = sendPofFlowTable(deviceId4,"AddVlcHeaderTable");
        log.info("pof-ovs-action-test-app: deviceId={}, tableId1={}", deviceId1, tableId1);
//        log.info("pof-ovs-action-test-app: next_table_id1={}", next_table_id1);
//        log.info("pof-ovs-action-test-app: deviceId={}, tableId1={}", deviceId2, tableId2);
//        log.info("pof-ovs-action-test-app: next_table_id1={}", next_table_id2);
//        log.info("pof-ovs-action-test-app: deviceId={}, tableId1={}", deviceId3, tableId3);
//        log.info("pof-ovs-action-test-app: next_table_id1={}", next_table_id3);
//        log.info("pof-ovs-action-test-app: deviceId={}, tableId1={}", deviceId4, tableId4);
//        log.info("pof-ovs-action-test-app: next_table_id1={}", next_table_id4);
        global_table_id_1[0] = tableId1;
//        global_table_id_2 [0]= next_table_id1;
//        global_table_id_1[1] = tableId2;
//        global_table_id_2 [1]= next_table_id2;
//        global_table_id_1[2] = tableId3;
//        global_table_id_2 [2]= next_table_id3;
//        global_table_id_1[3] = tableId4;
//        global_table_id_2 [3]= next_table_id4;
//        int controller_port = (int) PortNumber.CONTROLLER.toLong();//if packetIN to the controller.it can must send a output controller rule.
//        try {
//            Thread.sleep(1000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        log.info("tableID:{}",tableId1);
//        log.info("tableID:{}",tableId2);
//        log.info("tableID:{}",tableId3);
//        log.info("tableID:{}",tableId4);
        //test first path 207->222->213
        try {
            Thread.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        install_pof_no_int_output_flow_rule(deviceId1, tableId1, "0a000001", 2, 10);
//        install_pof_no_int_output_flow_rule(deviceId2, tableId3, "0a000001", 1, 10);
//        install_pof_no_int_output_flow_rule(deviceId3, tableId3, "0a000001", 2, 10);
//        add_field_pof_ovs(deviceId,tableId1,"0a000002",2,11);
//        add_field_pof_ovs(deviceId,tableId1,"0a000001",1,11);
//        set_field_pof_ovs(deviceId,tableId1,"0a000002",2,11);
//        modify_field_pof_ovs(deviceId,tableId1,"0a000002",2,11);
//        delete_field_pof_ovs(deviceId,tableId1,"0a000002",2,11);
//        drop_pof_ovs(deviceId,tableId1,"0a000002",2,11);
////        log.info("###########this is packet in");
//        requestIntercepts();
//        packetIn_pof_ovs(deviceId1, tableId1, "0a000001", 2, 10);

//        log.info("###########this is packet in");
    }

    public void pofTestStop() {
        /* remove group tables */
        remove_pof_group_tables(deviceId1, "abc");
        packetService.removeProcessor(processor);
        remove_pof_flow_table(deviceId1, global_table_id_1[0]);
//        remove_pof_flow_table(deviceId1, global_table_id_2[0]);
//        remove_pof_flow_table(deviceId1,(byte) 0);
//        remove_pof_flow_table(deviceId2, global_table_id_1[1]);
//        remove_pof_flow_table(deviceId2, global_table_id_2[1]);
//        remove_pof_flow_table(deviceId2,(byte) 0);
//        remove_pof_flow_table(deviceId3, global_table_id_1[2]);
//        remove_pof_flow_table(deviceId3, global_table_id_2[2]);
//        remove_pof_flow_table(deviceId3,(byte) 0);
//        remove_pof_flow_table(deviceId4, global_table_id_1[3]);
//        remove_pof_flow_table(deviceId4, global_table_id_2[3]);
//        remove_pof_flow_table(deviceId4,(byte) 0);
        log.info("org.onosproject.test.action Stopped");
    }
    public void remove_pof_group_tables(DeviceId deviceId, String key_str) {
        byte[] keyData = key_str.getBytes();
        final GroupKey key = new DefaultGroupKey(keyData);
        groupService.removeGroup(deviceId, key, appId);
    }
    public void remove_pof_flow_table(DeviceId deviceId, byte tableId) {
        flowRuleService.removeFlowRulesById(appId);  // for ovs-pof
        flowTableService.removeFlowTablesByTableId(deviceId, FlowTableId.valueOf(tableId));
    }

    public byte sendPofFlowTable(DeviceId deviceId, String table_name) {
        byte globeTableId = (byte) tableStore.getNewGlobalFlowTableId(deviceId, OFTableType.OF_MM_TABLE);
      //  byte tableId = tableStore.parseToSmallTableId(deviceId, globeTableId);

        OFMatch20 srcIP = new OFMatch20();
        srcIP.setFieldId((short) SIP);
        srcIP.setFieldName("srcIP");
        srcIP.setOffset((short) 208);
        srcIP.setLength((short) 32);

        ArrayList<OFMatch20> match20List = new ArrayList<>();
        match20List.add(srcIP);

        OFFlowTable ofFlowTable = new OFFlowTable();
        ofFlowTable.setTableId((byte)0);
        ofFlowTable.setTableName(table_name);
        ofFlowTable.setMatchFieldList(match20List);
        ofFlowTable.setMatchFieldNum((byte) 1);
        ofFlowTable.setTableSize(20);
        ofFlowTable.setTableType(OFTableType.OF_MM_TABLE);
        ofFlowTable.setCommand(null);
        ofFlowTable.setKeyLength((short) 32);

        FlowTable.Builder flowTable = DefaultFlowTable.builder()
                .withFlowTable(ofFlowTable)
                .forTable((byte)0)
                .forDevice(deviceId)
                .fromApp(appId);

        flowTableService.applyFlowTables(flowTable.build());

        log.info("table<{}> applied to device<{}> successfully.", (byte)0, deviceId.toString());

        return 0;
    }
    //output
    public void install_pof_no_int_output_flow_rule(DeviceId deviceId, byte tableId, String srcIP, int outport, int priority) {
        // match
        TrafficSelector.Builder trafficSelector = DefaultTrafficSelector.builder();
        ArrayList<Criterion> matchList = new ArrayList<>();
        matchList.add(Criteria.matchOffsetLength((short) SIP, (short) 208, (short) 32, srcIP, "FFFFFFFF"));
        trafficSelector.add(Criteria.matchOffsetLength(matchList));

        // action
        TrafficTreatment.Builder trafficTreamt = DefaultTrafficTreatment.builder();
        List<OFAction> actions = new ArrayList<>();
        OFAction action_output = DefaultPofActions.output((short) 0, (short) 0, (short) 0, outport).action();
        actions.add(action_output);
        trafficTreamt.add(DefaultPofInstructions.applyActions(actions));
        log.info("action_output: {}.", actions);

        // apply
        long newFlowEntryId = flowTableStore.getNewFlowEntryId(deviceId, tableId);
        FlowRule.Builder flowRule = DefaultFlowRule.builder()
                .forDevice(deviceId)
                .forTable((byte)0)
                .withSelector(trafficSelector.build())
                .withTreatment(trafficTreamt.build())
                .withPriority(priority)
                .withCookie(newFlowEntryId)
                .makePermanent();
        flowRuleService.applyFlowRules(flowRule.build());
        log.info("Test no INT: apply to deviceId<{}> tableId<{}>, entryId=<{}>", deviceId.toString(), tableId, newFlowEntryId);
    }
    //add_field
    public void add_field_pof_ovs(DeviceId deviceId, byte tableId, String srcIP, int outport, int priority) {
        TrafficSelector.Builder trafficSelecor = DefaultTrafficSelector.builder();
        ArrayList<Criterion> matchlist = new ArrayList<>();
        matchlist.add(Criteria.matchOffsetLength((short) SIP, (short) 208, (short) 32, srcIP, "FFFFFFFF"));
        trafficSelecor.add(Criteria.matchOffsetLength(matchlist));

        //action
        short field_id1 = 17;
        short field_id2 = 18;
        short field_id3 = 19;
        TrafficTreatment.Builder trafficTreamt = DefaultTrafficTreatment.builder();
        List<OFAction> actions = new ArrayList<>();
        OFAction action_add_field = DefaultPofActions.addField(field_id2, (short) 272, (short) 16, "0908").action();
        OFAction action_output = DefaultPofActions.output((short) 0, (short) 0, (short) 0, outport).action();
        actions.add(action_add_field);
        actions.add(action_output);
        trafficTreamt.add(DefaultPofInstructions.applyActions(actions));
        log.info("action_set_field:{}", actions);

        //apply
        long newFlowEntryID = flowTableStore.getNewFlowEntryId(deviceId, tableId);
        FlowRule.Builder flowRule = DefaultFlowRule.builder()
                .forDevice(deviceId)
                .forTable(tableId)
                .withSelector(trafficSelecor.build())
                .withTreatment(trafficTreamt.build())
                .withPriority(priority)
                .withCookie(newFlowEntryID)
                .makePermanent();
        flowRuleService.applyFlowRules(flowRule.build());
        log.info("installAddFieldFlowRule: apply to deviceId<{}> tableId<{}>", deviceId.toString(), tableId);
    }
    //set field
    public void set_field_pof_ovs(DeviceId deviceId, byte tableId, String srcIP, int outport, int priority){
        TrafficSelector.Builder trafficSelecor = DefaultTrafficSelector.builder();
        ArrayList<Criterion> matchlist = new ArrayList<>();
        matchlist.add(Criteria.matchOffsetLength((short) SIP, (short) 208, (short) 32, srcIP, "FFFFFFFF"));
        trafficSelecor.add(Criteria.matchOffsetLength(matchlist));

        //action
        short field_id1 = 17;
        short field_id2 = 18;
        short field_id3 = 19;
        TrafficTreatment.Builder trafficTreamt = DefaultTrafficTreatment.builder();
        List<OFAction> actions = new ArrayList<>();
        OFAction set_field=DefaultPofActions.setField(field_id1,(short)96,(short)16,"0908","FFFF").action();
        OFAction action_output = DefaultPofActions.output((short) 0, (short) 0, (short) 0, outport).action();
        actions.add(set_field);
        actions.add(action_output);
        trafficTreamt.add(DefaultPofInstructions.applyActions(actions));
        log.info("action_add_field:{}", actions);
        //apply
        long newFlowEntryID = flowTableStore.getNewFlowEntryId(deviceId, tableId);
        FlowRule.Builder flowRule = DefaultFlowRule.builder()
                .forDevice(deviceId)
                .forTable(tableId)
                .withSelector(trafficSelecor.build())
                .withTreatment(trafficTreamt.build())
                .withPriority(priority)
                .withCookie(newFlowEntryID)
                .makePermanent();
        flowRuleService.applyFlowRules(flowRule.build());
        log.info("installAddFieldFlowRule: apply to deviceId<{}> tableId<{}>", deviceId.toString(), tableId);
    }
    // modify field
    public void modify_field_pof_ovs(DeviceId deviceId, byte tableId, String srcIP, int outport, int priority){
        TrafficSelector.Builder trafficSelecor = DefaultTrafficSelector.builder();
        ArrayList<Criterion> matchlist = new ArrayList<>();
        matchlist.add(Criteria.matchOffsetLength((short) SIP, (short) 208, (short) 32, srcIP, "FFFFFFFF"));
        trafficSelecor.add(Criteria.matchOffsetLength(matchlist));

        //action
        OFMatch20 src_field=new OFMatch20();
        src_field.setFieldName("SIP");
        src_field.setFieldId(SIP);
        src_field.setLength((short)(208+16));
        src_field.setOffset((short)8);

        TrafficTreatment.Builder trafficTreamt = DefaultTrafficTreatment.builder();
        List<OFAction> actions = new ArrayList<>();
        OFAction modify_field=DefaultPofActions.modifyField(src_field,20).action();
        OFAction action_output = DefaultPofActions.output((short) 0, (short) 0, (short) 0, outport).action();
        actions.add(modify_field);
        actions.add(action_output);
        trafficTreamt.add(DefaultPofInstructions.applyActions(actions));
        log.info("action_modify_field: {}.", actions);

        //apply
        long newFlowEntryId = flowTableStore.getNewFlowEntryId(deviceId, tableId);
        FlowRule.Builder flowRule = DefaultFlowRule.builder()
                .forDevice(deviceId)
                .forTable(tableId)
                .withSelector(trafficSelecor.build())
                .withTreatment(trafficTreamt.build())
                .withPriority(priority)
                .withCookie(newFlowEntryId)
                .makePermanent();
        flowRuleService.applyFlowRules(flowRule.build());

        log.info("installModifyFieldFlowRule: apply to deviceId<{}> tableId<{}>", deviceId.toString(), tableId);
    }
    // delete_field
    public void delete_field_pof_ovs(DeviceId deviceId, byte tableId, String srcIP, int outport, int priority){
        TrafficSelector.Builder trafficSelecor = DefaultTrafficSelector.builder();
        ArrayList<Criterion> matchlist = new ArrayList<>();
        matchlist.add(Criteria.matchOffsetLength((short) SIP, (short) 208, (short) 32, srcIP, "FFFFFFFF"));
        trafficSelecor.add(Criteria.matchOffsetLength(matchlist));

        //actions
        OFActionPacketIn ofActionPacketIn=new OFActionPacketIn();
        ofActionPacketIn.setReason(1);
        ofActionPacketIn.setLength((short)2);
        TrafficTreatment.Builder trafficTreamt = DefaultTrafficTreatment.builder();
        List<OFAction> actions = new ArrayList<>();
        OFAction delete_field=DefaultPofActions.deleteField(272,16).action();
        OFAction action_output = DefaultPofActions.output((short) 0, (short) 0, (short) 0, outport).action();
        actions.add(delete_field);
        actions.add(action_output);
        trafficTreamt.add(DefaultPofInstructions.applyActions(actions));
        log.info("action_delete_field: {}.", actions);

        //apply
        long newFlowEntryId = flowTableStore.getNewFlowEntryId(deviceId, tableId);
        FlowRule.Builder flowRule = DefaultFlowRule.builder()
                .forDevice(deviceId)
                .forTable(tableId)
                .withSelector(trafficSelecor.build())
                .withTreatment(trafficTreamt.build())
                .withPriority(priority)
                .withCookie(newFlowEntryId)
                .makePermanent();
        flowRuleService.applyFlowRules(flowRule.build());

        log.info("installModifyFieldFlowRule: apply to deviceId<{}> tableId<{}>", deviceId.toString(), tableId);

    }
    //drop
    public void drop_pof_ovs(DeviceId deviceId, byte tableId, String srcIP, int outport, int priority){
        TrafficSelector.Builder trafficSelector = DefaultTrafficSelector.builder();
        ArrayList<Criterion> matchList = new ArrayList<>();
        matchList.add(Criteria.matchOffsetLength(SIP, (short) 208, (short) 32, srcIP, "ffffffff"));
        trafficSelector.add(Criteria.matchOffsetLength(matchList));

        //action
        TrafficTreatment.Builder trafficTreamt = DefaultTrafficTreatment.builder();
        List<OFAction> actions = new ArrayList<>();
        OFAction drop=DefaultPofActions.drop(1).action();
        actions.add(drop);
        trafficTreamt.add(DefaultPofInstructions.applyActions(actions));
        log.info("action_drop:{}",actions);
        long newFlowEntryId = flowTableStore.getNewFlowEntryId(deviceId, tableId);
        FlowRule.Builder flowRule = DefaultFlowRule.builder()
                .forDevice(deviceId)
                .forTable(tableId)
                .withSelector(trafficSelector.build())
                .withTreatment(trafficTreamt.build())
                .withPriority(priority)
                .withCookie(newFlowEntryId)
                .makePermanent();
        flowRuleService.applyFlowRules(flowRule.build());
        log.info("installDropFlowRule: apply to deviceId<{}> tableId<{}>", deviceId.toString(), tableId);
    }

    //packetIn
    public void packetIn_pof_ovs(DeviceId deviceId, byte tableId, String srcIP, int outport, int priority){
        TrafficSelector.Builder trafficSelector = DefaultTrafficSelector.builder();
        ArrayList<Criterion> matchList = new ArrayList<>();
        matchList.add(Criteria.matchOffsetLength(SIP, (short) 208, (short) 32, srcIP, "ffffffff"));
        trafficSelector.add(Criteria.matchOffsetLength(matchList));

        //action
        TrafficTreatment.Builder trafficTreamt = DefaultTrafficTreatment.builder();
        List<OFAction> actions = new ArrayList<>();
        OFAction packet_in=DefaultPofActions.packetIn(1).action();
        actions.add(packet_in);
        trafficTreamt.add(DefaultPofInstructions.applyActions(actions));
        log.info("actions:{}",actions);

        long newflowentry=flowTableService.getNewFlowEntryId(deviceId,tableId);
        FlowRule.Builder flowrule=DefaultFlowRule.builder()
                .forDevice(deviceId)
                .forTable(tableId)
                .withSelector(trafficSelector.build())
                .withTreatment(trafficTreamt.build())
                .withPriority(priority)
                .withCookie(newflowentry)
                .makePermanent();
        flowRuleService.applyFlowRules(flowrule.build());
        log.info("apply to deviceId<{}> tableId<{}>, entryId=<{}>", deviceId.toString(), tableId, newflowentry);
    }
    private void requestIntercepts() {
        TrafficSelector.Builder selector = DefaultTrafficSelector.builder();
        /** match packet*/
        //PacketService.requestPackets:
        //Requests that packets matching the given selector are punted from the dataplane to the controller.
        selector.matchEthType(Ethernet.TYPE_IPV4);//设定匹配类型
        packetService.requestPackets(selector.build(), PacketPriority.CONTROL, appId);

        selector.matchEthType(Ethernet.TYPE_ARP);
        packetService.requestPackets(selector.build(), PacketPriority.REACTIVE, appId);
    }


  private class ReactivePacketProcessor implements PacketProcessor{

        @Override
        public void process(PacketContext context) {
            if (context.isHandled()) {
                return;
            }
            log.info("##########################");
            log.info("deviceID:{}",deviceId1);
            InboundPacket pkt=context.inPacket();
            Ethernet ethPkt = pkt.parsed();
            Short Ptype = ethPkt.getEtherType();
            HostId id = HostId.hostId(ethPkt.getDestinationMAC());//获取他的id，来表示承载远程对象的进程,map to the logical graph
            add_field_pof_ovs(deviceId1,(byte)0,"0a000001",2,10);

        }
    }

}



