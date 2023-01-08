package com.aem.demo.core.models;

import com.aem.demo.core.service.DownloadPdfService;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class DownloadPdfImplTest {
    private final AemContext aemContext = new AemContext();

    @Mock
    DownloadPdfService downloadPdfService;

    @BeforeEach
    void setUp() throws RepositoryException {
        aemContext.addModelsForClasses(DownloadPdfImpl.class);
        aemContext.load().json("/models/downloadPdf.json", "/component");
        aemContext.registerService(downloadPdfService);
        List<PdfDetails> pdfDetailsList = new ArrayList<>();
        PdfDetails pdfDetails = new PdfDetails();
        pdfDetails.setFilePath("/content/aem-demo/pdfFiles");
        pdfDetails.setFileName("File1");
        pdfDetails.setFileSize(200L);
        pdfDetails.setTags(Arrays.asList("PdfTag1", "PdfTag2"));
        pdfDetailsList.add(pdfDetails);
        PdfDetails pdfDetails1 = new PdfDetails();
        pdfDetails1.setFilePath("/content/aem-demo/pdfFiles");
        pdfDetails1.setFileName("File2");
        pdfDetails1.setFileSize(100L);
        pdfDetails1.setTags(Arrays.asList("PdfTag1"));
        pdfDetailsList.add(pdfDetails1);
        when(downloadPdfService.getPdfFiles(any(), any())).thenReturn(pdfDetailsList);
    }

    @Test
    void getPdfList() {
        aemContext.currentResource("/component");
        DownloadPdf downloadPdf = aemContext.request().adaptTo(DownloadPdf.class);
        List<PdfDetails> pdfDetailsList = downloadPdf.getPdfList();
        assertEquals(2, pdfDetailsList.size());
        assertEquals("File1", pdfDetailsList.get(0).getFileName());
    }
}