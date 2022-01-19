package core;

import util.PpsInputSteram;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Pu PanSheng, 2022/1/17
 * @version v1.0
 */
public class Response {

    private PpsInputSteram ppsInputSteram;

    private Map<String, Object> contentMap=new HashMap<>();


    public Response(PpsInputSteram ppsInputSteram) {
        this.ppsInputSteram = ppsInputSteram;
    }

    public Map<String, Object> getContentMap() {
        return contentMap;
    }

    public PpsInputSteram getPpsInputSteram() {
        return ppsInputSteram;
    }
}
