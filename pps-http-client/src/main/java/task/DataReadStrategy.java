/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package task;

import core.Context;

/**
 * @author Pu PanSheng, 2022/1/18
 * @version OPRA v1.0
 */
public interface DataReadStrategy {

    void execute(Context ppsSocketContext);

}
