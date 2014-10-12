/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noprint;

import java.io.IOException;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.BadSecurityHandlerException;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
/**
 *
 * @author Robbe Van der Gucht
 */
public class NoPrint {

    /**
     * @param args the command line arguments
     * @throws IOException in case input file is can't be read or output written
     * @throws org.apache.pdfbox.pdmodel.encryption.BadSecurityHandlerException
     * @throws org.apache.pdfbox.exceptions.COSVisitorException
     */
    public static void main(String[] args) throws IOException, 
            BadSecurityHandlerException, COSVisitorException {
        String infile = "input.pdf";
        String outfile = "output.pdf";
        
        String ownerPass = "";
        String userPass = "";
        /**
         * TODO: read up what the actual difference is between
         * userpassword and ownerpassword.
         */
        int keylength = 40; 
        
        AccessPermission ap = new AccessPermission();
        PDDocument document = null;
        
        ap.setCanAssembleDocument(true);
        ap.setCanExtractContent(true);
        ap.setCanExtractForAccessibility(true);
        ap.setCanFillInForm(true);
        ap.setCanModify(true);
        ap.setCanModifyAnnotations(true);
        ap.setCanPrintDegraded(true);
        
        ap.setCanPrint(false); 
        // YOU CAN'T PRINT
        // at least not when your PDFreader adheres to DRM (some don't)
        // also this is trivial to remove
        
        document = PDDocument.load(infile);
        
        if (!document.isEncrypted()) {
            StandardProtectionPolicy spp;
            spp = new StandardProtectionPolicy(ownerPass, userPass, ap);
            spp.setEncryptionKeyLength(keylength);
            document.protect(spp);
            document.save(outfile);
        }
        
        if (document != null) {
            document.close();
        }
    }
    
}
