/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package task;

import core.Context;
import util.PpsInputSteram;

import java.io.IOException;

/**
 * @author Pu PanSheng, 2022/1/18
 * @version OPRA v1.0
 */
public class DefaultReadStrategy implements DataReadStrategy {
    @Override
    public void execute(Context context) throws IOException {


        context.read();


    }
}
