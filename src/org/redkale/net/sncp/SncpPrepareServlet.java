/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.net.sncp;

import org.redkale.net.PrepareServlet;
import org.redkale.util.AnyValue;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;
import org.redkale.util.*;

/**
 *
 * <p>
 * 详情见: https://redkale.org
 *
 * @author zhangjx
 */
public class SncpPrepareServlet extends PrepareServlet<DLong, SncpContext, SncpRequest, SncpResponse, SncpServlet> {

    private static final ByteBuffer pongBuffer = ByteBuffer.wrap("PONG".getBytes()).asReadOnlyBuffer();

    @Override
    public void addServlet(SncpServlet servlet, Object attachment, AnyValue conf, DLong... mappings) {
        addServlet((SncpServlet) servlet, conf);
    }

    public void addServlet(SncpServlet servlet, AnyValue conf) {
        setServletConf(servlet, conf);
        putMapping(servlet.getServiceid(), servlet);
        putServlet(servlet);
    }

    public List<SncpServlet> getSncpServlets() {
        return new ArrayList<>(getServlets());
    }

    @Override
    public void init(SncpContext context, AnyValue config) {
        getServlets().forEach(s -> s.init(context, getServletConf(s)));
    }

    @Override
    public void destroy(SncpContext context, AnyValue config) {
        getServlets().forEach(s -> s.destroy(context, getServletConf(s)));
    }

    @Override
    public void execute(SncpRequest request, SncpResponse response) throws IOException {
        if (request.isPing()) {
            response.finish(pongBuffer.duplicate());
            return;
        }
        SncpServlet servlet = (SncpServlet) mappingServlet(request.getServiceid());
        if (servlet == null) {
            response.finish(SncpResponse.RETCODE_ILLSERVICEID, null);  //无效serviceid
        } else {
            servlet.execute(request, response);
        }
    }

}
