package pm.group01.courseproject.product.controller;

import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import pm.group01.courseproject.product.service.impl.ReportServiceImpl;
import pm.group01.courseproject.securityconfig.jwt.util.JwtUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/*
 * @author Battuguldur Ganbold
 */

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportServiceImpl reportService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    //@PreAuthorize("hasAnyAuthority('SELLER','ADMIN')")
    @GetMapping("/product")
    public ResponseEntity<Object> productReport(
            //@PathVariable("sellerId") Integer sellerId,
            @RequestParam(value = "format", required = false, defaultValue = "pdf") String format
    ) throws FileNotFoundException, JRException {

        String filename = reportService.exportProductReport(format.toLowerCase());
        ResponseEntity<Object> responseEntity = reportService.createEntity(filename, format);
        return responseEntity;
    }

    @GetMapping("/stock/{sellerId}")
    public ResponseEntity<Object> stockReport(
            @PathVariable("sellerId") Integer sellerId,
            @RequestParam(value = "sellerName", required = false, defaultValue = "") String sellerName,
            @RequestParam(value = "format", required = false, defaultValue = "pdf") String format
    ) throws FileNotFoundException, JRException {

        String filename = reportService.exportStockReport(sellerId, sellerName, format.toLowerCase());
        ResponseEntity<Object> responseEntity = reportService.createEntity(filename, format);
        return responseEntity;
    }

}
