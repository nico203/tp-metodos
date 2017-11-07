package pdf.PDF;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementList;
import com.itextpdf.tool.xml.XMLWorkerHelper;
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author NicolasAndres
 */
public class ParseHTML {
    public static final String DEST = "results/xmlworker/test2015-11.pdf";
    public static final String HTML = "src/pdf/PDF/PDF.template.html";
 
    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ParseHTML().createPdf(DEST);
    }
 
    /**
     * Creates a PDF with the words "Hello World"
     * @param file
     * @throws IOException
     * @throws DocumentException
     */
    public void createPdf(String file) throws IOException, DocumentException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(file));
        // step 3
        document.open();
        // step 4

        String content = readFile(HTML, StandardCharsets.UTF_8);
        content = content.replace("{{foto}}", "foto nico");
        content = content.replace("{{nombre}}", "nombre nico");
        content = content.replace("{{apellido}}", "apellido nico");
        
        PdfPTable table = new PdfPTable(1);
        PdfPCell cell = new PdfPCell();
        ElementList list = XMLWorkerHelper.parseToElementList(content, null);
        
        for (Element element : list) {
            cell.addElement(element);
        }
        table.addCell(cell);
        document.add(table);
        
        // step 5
        document.close();
    }
   
    private String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
