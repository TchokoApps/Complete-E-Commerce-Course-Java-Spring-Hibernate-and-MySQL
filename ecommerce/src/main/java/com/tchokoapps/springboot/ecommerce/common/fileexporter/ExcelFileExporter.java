package com.tchokoapps.springboot.ecommerce.common.fileexporter;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ExcelFileExporter<T> {

    void exportToExcel(List<T> list, HttpServletResponse response);
}
