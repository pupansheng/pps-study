/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package http;

import core.Context;

import java.io.IOException;

/**
 * @author Pu PanSheng, 2022/1/19
 * @version OPRA v1.0
 */
public interface ResoveHttpResponseStragety {

    void doResoveHttpBody(Context context, HttpResponse httpResponse) throws IOException;

}
