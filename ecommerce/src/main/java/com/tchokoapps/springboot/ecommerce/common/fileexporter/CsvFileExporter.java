package com.tchokoapps.springboot.ecommerce.common.fileexporter;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public interface CsvFileExporter<T> {
    public void exportToCsv(List<T> list, Writer writer) throws IOException;
}
