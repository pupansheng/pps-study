/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package http;

import core.DataClient;
import core.Request;
import core.Response;

import java.util.function.Consumer;

/**
 * @author Pu PanSheng, 2022/1/18
 * @version OPRA v1.0
 */
public class HttpClient extends DataClient {


    public HttpClient(int parallel) {
        super(parallel);
    }

    public <T> void executeHttp(HttpRequest httpRequest, Consumer<HttpResponse<T>> callback) {


        Request request=new Request();


        request.setBody(httpRequest.createHttpMessage());
        request.setIp(httpRequest.getIp());
        request.setPort(httpRequest.getPort());

        Consumer<Response> consumer=(r)->{

            HttpResponse httpResponse=new HttpResponse(r);
            callback.accept(httpResponse);

        };


        super.execute(request, consumer);


    }
}
