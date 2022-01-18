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

    public void executeHttp(HttpRequest httpRequest, Consumer<HttpResponse> callback) {


        Request request=new Request();


        request.setBody(httpRequest.createHttpMessage());
        request.setIp(httpRequest.resoveIp());
        request.setPort(httpRequest.resovePort());

        Consumer<Response> consumer=(r)->{

            HttpResponse httpResponse=new HttpResponse(r.getContent());
            callback.accept(httpResponse);

        };


        super.execute(request, consumer);


    }
}
