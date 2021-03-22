package com.ruoyi.common.canal;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.CanalEntry.*;
import com.alibaba.otter.canal.protocol.Message;

import com.ruoyi.common.websocket.WebSocketApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Component
public class SimpleCanalClientExample {
    @Autowired
    private WebSocketApiController webSocketApiControllere;

    public void listen() {
        // 创建链接
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(AddressUtils.getHostIp(),
                11111), "example", "", "");
        int batchSize = 1000;//获取指定数量的数据
        int emptyCount = 0;//空数据数量
        try {
            connector.connect();
            connector.subscribe(".*\\..*");
            connector.rollback();
            int totalEmptyCount = 50;//容忍空数据的数量
            System.out.println("canal conn -------------------");
            while (true) {
                if (emptyCount >= totalEmptyCount) emptyCount=0;
                Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    emptyCount++;
//                    System.out.println("empty count : " + emptyCount+new Date());
                    try {
                        Thread.sleep(10*1000);
                    } catch (InterruptedException e) {
                    }
                } else {
                    emptyCount = 0;
                    // System.out.printf("message[batchId=%s,size=%s] \n", batchId, size);
                    printEntry(message.getEntries());
                }

                connector.ack(batchId); // 提交确认
                // connector.rollback(batchId); // 处理失败, 回滚数据
            }

//            System.out.println("empty too many times, exit");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connector.disconnect();
            this.listen();
        }
    }

    private  void printEntry(List<Entry> entrys) {
        for (Entry entry : entrys) {
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }

            RowChange rowChage = null;
            try {
                rowChage = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }

            EventType eventType = rowChage.getEventType();
            System.out.println(String.format("================> binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

            for (RowData rowData : rowChage.getRowDatasList()) {
                if (eventType == EventType.DELETE) {
                    printColumn(rowData.getBeforeColumnsList());
                } else if (eventType == EventType.INSERT) {
                    printColumn(rowData.getAfterColumnsList());

                    if (entry.getHeader().getSchemaName().equals(CommonConstant.DB_NAME)
                            &&entry.getHeader().getTableName().equals(CommonConstant.TABLE_NAME)){
                        Map map = columnsToMap(rowData.getAfterColumnsList());
                        map.put("tableName",entry.getHeader().getTableName());
                        new Thread(){
                            @Override
                            public void run() {
                        /**
                         * 发送消息*/

                        String msg = (String) map.get("msg");
//                                JSONObject jsonObject =  JSONObject.parseObject(msg);
                            webSocketApiControllere.sendAll(msg);


                            }
                        }.start();
                    }



                } else {
                    System.out.println("-------> before");
                    printColumn(rowData.getBeforeColumnsList());
                    System.out.println("-------> after");
                    printColumn(rowData.getAfterColumnsList());
                }
            }
        }
    }

    private  void printColumn(List<Column> columns) {
        for (Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }

    private Map columnsToMap(List<Column> columns){
        Map map = new HashMap();
        for (Column column : columns) {
            map.put(column.getName() , column.getValue());
        }
        return map;
    }

}

