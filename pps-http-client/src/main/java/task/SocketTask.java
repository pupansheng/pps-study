/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package task;

import core.Context;
import util.PpsInputSteram;

import java.io.IOException;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Pu PanSheng, 2022/1/18
 * @version OPRA v1.0
 */
public class SocketTask implements Runnable {

    private Selector selector;

    private ConcurrentHashMap<SocketChannel, Context> contextMap;

    private DataReadStrategy dataReadStrategy;

    public SocketTask(Selector selector, ConcurrentHashMap<SocketChannel, Context> contextMap, DataReadStrategy dataReadStrategy) {
        this.selector = selector;
        this.contextMap = contextMap;
        this.dataReadStrategy = dataReadStrategy;
    }

    @Override
    public void run() {

        while (true) {


            try {

                int v = selector.select(500);


                if (v <= 0) {
                    continue;
                }

                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                Iterator<SelectionKey> iterator = selectionKeys.iterator();

                while (iterator.hasNext()) {

                    SelectableChannel channel=null;
                    PpsInputSteram ppsInputSteram=null;
                    try {


                        SelectionKey next = iterator.next();
                        if (!next.isValid()) {
                            next.cancel();
                            break;
                        }

                        channel = next.channel();
                        Context context = contextMap.get(channel);
                        if(context==null){
                            contextMap.remove(channel);
                            channel.close();
                        }

                        ppsInputSteram = new PpsInputSteram(context.getSocketChannel(), next);
                        context.setPpsInputSteram(ppsInputSteram);
                        dataReadStrategy.execute(context);

                    } catch (Exception e){

                        if(e instanceof ClosedChannelException) {
                            contextMap.remove(channel);
                            channel.close();
                        }
                        e.printStackTrace();

                    }finally {
                        iterator.remove();
                    }




                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }
}
