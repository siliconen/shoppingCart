package pm.group01.courseproject.product.service.impl;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import pm.group01.courseproject.product.model.ProductForReport;
import pm.group01.courseproject.product.model.ProductStockForReport;
import pm.group01.courseproject.product.repository.ProductRepository;
import pm.group01.courseproject.product.repository.ProductStockRepository;
import pm.group01.courseproject.product.service.ReportService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * @author Battuguldur Ganbold (986874)
 */

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductStockRepository productStockRepository;

    public String exportProductReport(String format) throws FileNotFoundException, JRException {

        List<ProductForReport> productForReports = productRepository.findAll().stream()
                .map(p -> new ProductForReport(
                        p.getProductID(),
                        p.getName(),
                        p.getPrice(),
                        p.getDescription(),
                        p.getCategory().getCategoryId(),
                        p.getCategory().getName()
                ))
                .collect(Collectors.toList());

        File file = ResourceUtils.getFile("classpath:reports/products.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(productForReports);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Team1");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        String path = file.getPath() + "." + format;
        saveReport(jasperPrint, format, parameters, file, path);
        System.out.println("path:" + path);
        return path;
    }

    @Override
    public String exportStockReport(Integer sellerId, String sellerName, String format) throws FileNotFoundException, JRException {
        List<ProductStockForReport> stockForReports = productStockRepository.findAllByProduct_SellerId(sellerId).stream()
                .map(p -> new ProductStockForReport(
                        p.getStockId(),
                        p.getProduct().getProductID(),
                        p.getProduct().getName(),
                        p.getProduct().getPrice(),
                        p.getQuantity(),
                        p.getProduct().getDescription(),
                        p.getProduct().getCategory().getCategoryId(),
                        p.getProduct().getCategory().getName(),
                        sellerId,
                        sellerName
                ))
                .collect(Collectors.toList());

        File file = ResourceUtils.getFile("classpath:reports/productstock.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(stockForReports);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Team1");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        String path = file.getPath() + "." + format;

        saveReport(jasperPrint, format, parameters, file, path);

        System.out.println("path:" + path);
        return path;
    }

    public static void saveReport(JasperPrint jasperPrint, String format,
                                  Map<String, Object> exportParameters, File file, String path) throws JRException {
        long start = System.currentTimeMillis();
        switch (format.toLowerCase()) {
            case "html": {
                JasperExportManager.exportReportToHtmlFile(jasperPrint, path);
            }
            break;
            case "pdf": {
                JasperExportManager.exportReportToPdfFile(jasperPrint, path);
            }
            break;
        }
        System.err.println(format + " creation time : " + (System.currentTimeMillis() - start));
    }


    public ResponseEntity<Object> createEntity(String filename, String format)
            throws FileNotFoundException, JRException{
        File file = ResourceUtils.getFile(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok().headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/" + format)).body(resource);
    }

}
