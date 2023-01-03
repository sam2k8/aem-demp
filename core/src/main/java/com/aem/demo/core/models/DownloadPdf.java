package com.aem.demo.core.models;


import com.adobe.cq.wcm.core.components.models.Component;

import java.util.List;

public interface DownloadPdf{

    List<PdfDetails> getPdfList();
    String getPdfPath();


}
