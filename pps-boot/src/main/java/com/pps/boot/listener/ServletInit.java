/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.pps.boot.listener;

import javax.servlet.ServletContext;

/**
 * @author Pu PanSheng, 2021/12/29
 * @version OPRA v1.0
 */
public interface ServletInit {

    void startUp(ServletContext servletContext);

    default int sort(){return -10000;};
}
