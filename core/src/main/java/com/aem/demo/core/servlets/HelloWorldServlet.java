package com.aem.demo.core.servlets;

import com.aem.demo.core.service.DownloadPdfService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component( service = Servlet.class,
        property = {
                Constants.SERVICE_DESCRIPTION + "=DOWNLOAD PDF LIST SERVLET",
                ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/aem-demo/helloworld",
                ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET

        })
public class HelloWorldServlet extends SlingAllMethodsServlet {

    //@Reference
    //transient private DownloadPdfService downloadPdfService;

    @Override
    protected void doGet(SlingHttpServletRequest request,  SlingHttpServletResponse response) throws ServletException, IOException {


        String path="/content/dam/aem-demo";
        String apiResponse= "test";
//        ObjectMapper mapper=new ObjectMapper();
//        try {
//            List<PdfDetails> pdfDetailsList=downloadPdfService.getPdfFiles(path);
//            apiResponse=mapper.writeValueAsString(pdfDetailsList);
//        } catch (RepositoryException e) {
//            e.printStackTrace();
//        }
        response.setContentType("application/json");
        response.getWriter().write(apiResponse);
    }
}
