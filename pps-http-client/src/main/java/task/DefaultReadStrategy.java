/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package task;

import core.Context;
import util.BufferUtil;
import util.PpsInputSteram;

import java.io.IOException;

/**
 * @author Pu PanSheng, 2022/1/18
 * @version OPRA v1.0
 */
public class DefaultReadStrategy implements DataReadStrategy {
    @Override
    public void execute(Context context) {

        PpsInputSteram ppsInputSteram = context.getPpsInputSteram();

        //读取数据 触发回调
        byte[] buffer = new byte[1024];

        int count = 0;

        boolean isEnd=false;

        while (true) {

            int read = 0;
            try {
                read = ppsInputSteram.read();
            } catch (IOException e) {
                ppsInputSteram.cancel();
                e.printStackTrace();
            }

            //说明 通道可能关闭了或者没有数据了
            if (read <= -1000) {

                //关闭 直接结束 触发会到
                if(read==-1001){
                    isEnd=true;
                    //没有数据了  看看是否符合条件 流的数据是否全部结束  粘包等问题在这里解决
                }else if(read==-1000){
                    isEnd=context.isEnd(BufferUtil.getByteArray(buffer, 0, count));
                }

                break;
            }
            buffer[count++] = (byte) read;
            //扩容
            if (count >= buffer.length) {
                byte[] newLine = new byte[buffer.length * 2];
                System.arraycopy(count, 0, newLine, 0, count);
                buffer = newLine;
            }

        }

        byte [] bytes=BufferUtil.getByteArray(buffer,0, count);


        context.addData(bytes);

        if(isEnd){
            context.endCallback();
        }


    }
}
