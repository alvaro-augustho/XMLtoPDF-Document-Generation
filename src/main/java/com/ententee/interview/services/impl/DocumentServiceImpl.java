package com.ententee.interview.services.impl;

import com.ententee.interview.jaxbfiles.TreatmentDocumentation;
import com.ententee.interview.services.DocumentService;
import com.ententee.interview.utils.PdfGenerator;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Implementation for document service
 *
 * @author Alvaro Silva
 */
@Component
public class DocumentServiceImpl implements DocumentService {

    private static final Logger LOGGER = Logger.getLogger(DocumentServiceImpl.class.getName());

    private static final String TEMP_DIR = "src/temp/";
    private static final String PDF_OUTPUT = TEMP_DIR + "treatmentDescription.pdf";
    private static final String XML_FILE = TEMP_DIR + "treatmentDescription.xml";
    private static final String XSL_FILE = "src/main/resources/xslt/document-template.xsl";

    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_DISEASES = "diseases";
    private static final String FIELD_SIDE_EFFECTS = "sideEffects";

    @Override
    public byte[] generateDocument(Map<String, String> body) {

        byte[] pdf = null;

        try {
            this.createTempFolder();
            this.generateXML(body);
            pdf = this.generatePDF();
        } catch (JAXBException | IOException | TransformerException | SAXException e) {
            LOGGER.log(Level.SEVERE, "[DocumentService] Erro while generating XML or PDF files");
            e.printStackTrace();
        }

        return pdf;
    }

    /**
     * Creates a temp folder for PDF and XML files
     * @throws IOException
     */
    private void createTempFolder() throws IOException {
        Path tempPath = Paths.get(TEMP_DIR);
        Files.createDirectory(tempPath);
    }

    /**
     * Generates XML file from parameters submitted by the user in web form
     * @param body params submitted
     * @throws FileNotFoundException
     * @throws JAXBException
     */
    private void generateXML(Map<String, String> body) throws FileNotFoundException, JAXBException {
        JAXBContext contextObj = JAXBContext.newInstance(TreatmentDocumentation.class);

        Marshaller marshallerObj = contextObj.createMarshaller();
        marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        TreatmentDocumentation que = new TreatmentDocumentation(
            1,
            body.get(FIELD_DESCRIPTION),
            body.get(FIELD_DISEASES),
            body.get(FIELD_SIDE_EFFECTS)
        );
        marshallerObj.marshal(que, new FileOutputStream(XML_FILE));

        LOGGER.log(Level.INFO, "[DocumentService#generateXML] Generated XML file");
    }

    /**
     * Generates the final PDF document from the XML previously generated
     * @return byte array with the PDF file
     * @throws IOException
     * @throws TransformerException
     * @throws SAXException
     */
    private byte[] generatePDF() throws IOException, TransformerException, SAXException {
        PdfGenerator pdfGenerator = new PdfGenerator(new HashMap<>());

        FileOutputStream pdfOutput = new FileOutputStream(PDF_OUTPUT);
        pdfGenerator.createPdfFile(XML_FILE, XSL_FILE, pdfOutput);

        LOGGER.log(Level.INFO, "[DocumentService#generatePDF] Generated PDF file");

        Path pdfPath = Paths.get(PDF_OUTPUT);
        return Files.readAllBytes(pdfPath);
    }
}
