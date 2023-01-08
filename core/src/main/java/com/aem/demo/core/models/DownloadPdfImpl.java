package com.aem.demo.core.models;

import com.aem.demo.core.service.DownloadPdfService;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.RepositoryException;
import java.util.List;
import java.util.Optional;

@Model(adaptables = SlingHttpServletRequest.class,
        adapters = DownloadPdf.class,
        resourceType = DownloadPdfImpl.RESOURCE_TYPE,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class DownloadPdfImpl implements DownloadPdf{
    private static final Logger LOG = LoggerFactory.getLogger(DownloadPdfImpl.class);
    final protected static String RESOURCE_TYPE="aemgeeks/components/content/author";

    @ValueMapValue
    @Default(values = "/content/dam/aem-demo")
    private String pdfPath;

    @ValueMapValue(name = "pdfTag")
    String[] pdfTag;

    private List<PdfDetails> pdfDetailsList;
    @Inject
    private DownloadPdfService downloadPdfService;

    @PostConstruct
    protected void init() throws RepositoryException {
        pdfDetailsList=downloadPdfService.getPdfFiles(pdfPath,pdfTag);
    }

    @Override
    public List<PdfDetails> getPdfList() {
        return pdfDetailsList;
    }

    @Override
    public String getPdfPath() {
        return pdfPath;
    }
}
