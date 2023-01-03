package com.aem.demo.core.service;
import com.adobe.cq.wcm.core.components.commons.link.Link;
import com.adobe.cq.wcm.core.components.internal.servlets.DownloadServlet;
import com.aem.demo.core.commons.link.LinkManager;
import com.aem.demo.core.models.PdfDetails;
import com.day.cq.commons.DownloadResource;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.DamConstants;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.drew.lang.annotations.NotNull;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.*;

@Component(service = DownloadPdfService.class,
        property = {
                Constants.SERVICE_DESCRIPTION + "= PDF Asset Service"
        })
public class DownloadPdfService {

        @Reference
        private transient ResourceResolverService resourceResolverService;


        @Reference
        QueryBuilder queryBuilder;
        protected static final Logger log = LoggerFactory.getLogger(DownloadPdfService.class);



        public List<PdfDetails> getPdfFiles(String assetPath) throws RepositoryException {

                ResourceResolver resourceResolver=resourceResolverService.getResourceResolver();
                List<PdfDetails> pdfDetailsList=new ArrayList<>();
                if (resourceResolver != null) {
                        Map<String, String> parameterMap = new HashMap<>();
                        parameterMap.put("type", "dam:Asset");
                        parameterMap.put("path", assetPath);
                        parameterMap.put("1_property","jcr:content/metadata/dc:format");
                        parameterMap.put("1_property.value","application/pdf");
                       // parameterMap.put("p.propertiese","jcr:content/metadata/dc:format jcr:content/metadata/dam:size jcr:content/metadata/cq:tags");
                        parameterMap.put("p.limit", "-1");

                        Session session = resourceResolver.adaptTo(Session.class);
                       // logger.info("Create Session Completed {}", session);
                        Query query = queryBuilder.createQuery(PredicateGroup.create(parameterMap), session);
                        List<Hit> result=query.getResult().getHits();
                        for (Hit hit:result) {
                                Resource resource = hit.getResource();
                                PdfDetails pdfDetails=null;
                                if(resource!=null){
                                         pdfDetails = resource.adaptTo(PdfDetails.class);
                                         pdfDetails.setFilePath(hit.getPath());
                                         pdfDetails.setDownloadUrl(getDownloadUrl(hit.getPath()));
//                                         if (getDownloadUrl(resource)!=null){
//                                                // pdfDetails.setDownloadUrl(initAssetDownload(hit.getPath()).getURL());
//                                         }

                                        log.info("The asset {} was modified on {}", pdfDetails.getFileName(), pdfDetails.getFileSize());
                                }
                                pdfDetailsList.add(pdfDetails);

                        }

                }
                return pdfDetailsList;
        }

        @NotNull
        private String getDownloadUrl(String path) {

                StringBuilder downloadUrlBuilder = new StringBuilder();
                downloadUrlBuilder.append(path)
                        .append(".")
                        .append(DownloadServlet.SELECTOR)
                        .append(".");

                downloadUrlBuilder.append("pdf");
                return downloadUrlBuilder.toString();
        }

}
