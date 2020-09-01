package pm.group01.courseproject.product.service;

import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;

/*
 * @author Battuguldur Ganbold (986874)
 */

public interface ReportService {
    public String exportProductReport(String reportFormat) throws FileNotFoundException, JRException;
    public String exportStockReport(Integer sellerId, String sellerName, String reportFormat) throws FileNotFoundException, JRException;
    public ResponseEntity<Object> createEntity(String filename, String format) throws FileNotFoundException, JRException;
}
